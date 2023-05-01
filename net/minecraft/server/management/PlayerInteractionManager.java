/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PlayerInteractionManager {
    public World theWorld;
    public EntityPlayerMP thisPlayerMP;
    private GameType gameType = GameType.NOT_SET;
    private boolean isDestroyingBlock;
    private int initialDamage;
    private BlockPos destroyPos = BlockPos.ORIGIN;
    private int curblockDamage;
    private boolean receivedFinishDiggingPacket;
    private BlockPos delayedDestroyPos = BlockPos.ORIGIN;
    private int initialBlockDamage;
    private int durabilityRemainingOnBlock = -1;

    public PlayerInteractionManager(World worldIn) {
        this.theWorld = worldIn;
    }

    public void setGameType(GameType type) {
        this.gameType = type;
        type.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
        this.thisPlayerMP.sendPlayerAbilities();
        this.thisPlayerMP.mcServer.getPlayerList().sendPacketToAllPlayers(new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_GAME_MODE, this.thisPlayerMP));
        this.theWorld.updateAllPlayersSleepingFlag();
    }

    public GameType getGameType() {
        return this.gameType;
    }

    public boolean survivalOrAdventure() {
        return this.gameType.isSurvivalOrAdventure();
    }

    public boolean isCreative() {
        return this.gameType.isCreative();
    }

    public void initializeGameType(GameType type) {
        if (this.gameType == GameType.NOT_SET) {
            this.gameType = type;
        }
        this.setGameType(this.gameType);
    }

    public void updateBlockRemoving() {
        ++this.curblockDamage;
        if (this.receivedFinishDiggingPacket) {
            int i = this.curblockDamage - this.initialBlockDamage;
            IBlockState iblockstate = this.theWorld.getBlockState(this.delayedDestroyPos);
            if (iblockstate.getMaterial() == Material.AIR) {
                this.receivedFinishDiggingPacket = false;
            } else {
                float f = iblockstate.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.world, this.delayedDestroyPos) * (float)(i + 1);
                int j = (int)(f * 10.0f);
                if (j != this.durabilityRemainingOnBlock) {
                    this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.delayedDestroyPos, j);
                    this.durabilityRemainingOnBlock = j;
                }
                if (f >= 1.0f) {
                    this.receivedFinishDiggingPacket = false;
                    this.tryHarvestBlock(this.delayedDestroyPos);
                }
            }
        } else if (this.isDestroyingBlock) {
            IBlockState iblockstate1 = this.theWorld.getBlockState(this.destroyPos);
            if (iblockstate1.getMaterial() == Material.AIR) {
                this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.destroyPos, -1);
                this.durabilityRemainingOnBlock = -1;
                this.isDestroyingBlock = false;
            } else {
                int k = this.curblockDamage - this.initialDamage;
                float f1 = iblockstate1.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.world, this.delayedDestroyPos) * (float)(k + 1);
                int l = (int)(f1 * 10.0f);
                if (l != this.durabilityRemainingOnBlock) {
                    this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.destroyPos, l);
                    this.durabilityRemainingOnBlock = l;
                }
            }
        }
    }

    public void onBlockClicked(BlockPos pos, EnumFacing side) {
        if (this.isCreative()) {
            if (!this.theWorld.extinguishFire(null, pos, side)) {
                this.tryHarvestBlock(pos);
            }
        } else {
            IBlockState iblockstate = this.theWorld.getBlockState(pos);
            Block block = iblockstate.getBlock();
            if (this.gameType.isAdventure()) {
                if (this.gameType == GameType.SPECTATOR) {
                    return;
                }
                if (!this.thisPlayerMP.isAllowEdit()) {
                    ItemStack itemstack = this.thisPlayerMP.getHeldItemMainhand();
                    if (itemstack.isEmpty()) {
                        return;
                    }
                    if (!itemstack.canDestroy(block)) {
                        return;
                    }
                }
            }
            this.theWorld.extinguishFire(null, pos, side);
            this.initialDamage = this.curblockDamage;
            float f = 1.0f;
            if (iblockstate.getMaterial() != Material.AIR) {
                block.onBlockClicked(this.theWorld, pos, this.thisPlayerMP);
                f = iblockstate.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.world, pos);
            }
            if (iblockstate.getMaterial() != Material.AIR && f >= 1.0f) {
                this.tryHarvestBlock(pos);
            } else {
                this.isDestroyingBlock = true;
                this.destroyPos = pos;
                int i = (int)(f * 10.0f);
                this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, i);
                this.durabilityRemainingOnBlock = i;
            }
        }
    }

    public void blockRemoving(BlockPos pos) {
        if (pos.equals(this.destroyPos)) {
            int i = this.curblockDamage - this.initialDamage;
            IBlockState iblockstate = this.theWorld.getBlockState(pos);
            if (iblockstate.getMaterial() != Material.AIR) {
                float f = iblockstate.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.world, pos) * (float)(i + 1);
                if (f >= 0.7f) {
                    this.isDestroyingBlock = false;
                    this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, -1);
                    this.tryHarvestBlock(pos);
                } else if (!this.receivedFinishDiggingPacket) {
                    this.isDestroyingBlock = false;
                    this.receivedFinishDiggingPacket = true;
                    this.delayedDestroyPos = pos;
                    this.initialBlockDamage = this.initialDamage;
                }
            }
        }
    }

    public void cancelDestroyingBlock() {
        this.isDestroyingBlock = false;
        this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.destroyPos, -1);
    }

    private boolean removeBlock(BlockPos pos) {
        IBlockState iblockstate = this.theWorld.getBlockState(pos);
        iblockstate.getBlock().onBlockHarvested(this.theWorld, pos, iblockstate, this.thisPlayerMP);
        boolean flag = this.theWorld.setBlockToAir(pos);
        if (flag) {
            iblockstate.getBlock().onBlockDestroyedByPlayer(this.theWorld, pos, iblockstate);
        }
        return flag;
    }

    public boolean tryHarvestBlock(BlockPos pos) {
        if (this.gameType.isCreative() && !this.thisPlayerMP.getHeldItemMainhand().isEmpty() && this.thisPlayerMP.getHeldItemMainhand().getItem() instanceof ItemSword) {
            return false;
        }
        IBlockState iblockstate = this.theWorld.getBlockState(pos);
        TileEntity tileentity = this.theWorld.getTileEntity(pos);
        Block block = iblockstate.getBlock();
        if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !this.thisPlayerMP.canUseCommandBlock()) {
            this.theWorld.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
            return false;
        }
        if (this.gameType.isAdventure()) {
            if (this.gameType == GameType.SPECTATOR) {
                return false;
            }
            if (!this.thisPlayerMP.isAllowEdit()) {
                ItemStack itemstack = this.thisPlayerMP.getHeldItemMainhand();
                if (itemstack.isEmpty()) {
                    return false;
                }
                if (!itemstack.canDestroy(block)) {
                    return false;
                }
            }
        }
        this.theWorld.playEvent(this.thisPlayerMP, 2001, pos, Block.getStateId(iblockstate));
        boolean flag1 = this.removeBlock(pos);
        if (this.isCreative()) {
            this.thisPlayerMP.connection.sendPacket(new SPacketBlockChange(this.theWorld, pos));
        } else {
            ItemStack itemstack1 = this.thisPlayerMP.getHeldItemMainhand();
            ItemStack itemstack2 = itemstack1.isEmpty() ? ItemStack.field_190927_a : itemstack1.copy();
            boolean flag = this.thisPlayerMP.canHarvestBlock(iblockstate);
            if (!itemstack1.isEmpty()) {
                itemstack1.onBlockDestroyed(this.theWorld, iblockstate, pos, this.thisPlayerMP);
            }
            if (flag1 && flag) {
                iblockstate.getBlock().harvestBlock(this.theWorld, this.thisPlayerMP, pos, iblockstate, tileentity, itemstack2);
            }
        }
        return flag1;
    }

    public EnumActionResult processRightClick(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand) {
        if (this.gameType == GameType.SPECTATOR) {
            return EnumActionResult.PASS;
        }
        if (player.getCooldownTracker().hasCooldown(stack.getItem())) {
            return EnumActionResult.PASS;
        }
        int i = stack.getCount();
        int j = stack.getMetadata();
        ActionResult<ItemStack> actionresult = stack.useItemRightClick(worldIn, player, hand);
        ItemStack itemstack = actionresult.getResult();
        if (itemstack == stack && itemstack.getCount() == i && itemstack.getMaxItemUseDuration() <= 0 && itemstack.getMetadata() == j) {
            return actionresult.getType();
        }
        if (actionresult.getType() == EnumActionResult.FAIL && itemstack.getMaxItemUseDuration() > 0 && !player.isHandActive()) {
            return actionresult.getType();
        }
        player.setHeldItem(hand, itemstack);
        if (this.isCreative()) {
            itemstack.func_190920_e(i);
            if (itemstack.isItemStackDamageable()) {
                itemstack.setItemDamage(j);
            }
        }
        if (itemstack.isEmpty()) {
            player.setHeldItem(hand, ItemStack.field_190927_a);
        }
        if (!player.isHandActive()) {
            ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
        }
        return actionresult.getType();
    }

    public EnumActionResult processRightClickBlock(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block block;
        IBlockState iblockstate;
        if (this.gameType == GameType.SPECTATOR) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof ILockableContainer) {
                Block block1 = worldIn.getBlockState(pos).getBlock();
                ILockableContainer ilockablecontainer = (ILockableContainer)((Object)tileentity);
                if (ilockablecontainer instanceof TileEntityChest && block1 instanceof BlockChest) {
                    ilockablecontainer = ((BlockChest)block1).getLockableContainer(worldIn, pos);
                }
                if (ilockablecontainer != null) {
                    player.displayGUIChest(ilockablecontainer);
                    return EnumActionResult.SUCCESS;
                }
            } else if (tileentity instanceof IInventory) {
                player.displayGUIChest((IInventory)((Object)tileentity));
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.PASS;
        }
        if ((!player.isSneaking() || player.getHeldItemMainhand().isEmpty() && player.getHeldItemOffhand().isEmpty()) && (iblockstate = worldIn.getBlockState(pos)).getBlock().onBlockActivated(worldIn, pos, iblockstate, player, hand, facing, hitX, hitY, hitZ)) {
            return EnumActionResult.SUCCESS;
        }
        if (stack.isEmpty()) {
            return EnumActionResult.PASS;
        }
        if (player.getCooldownTracker().hasCooldown(stack.getItem())) {
            return EnumActionResult.PASS;
        }
        if (stack.getItem() instanceof ItemBlock && !player.canUseCommandBlock() && ((block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockCommandBlock || block instanceof BlockStructure)) {
            return EnumActionResult.FAIL;
        }
        if (this.isCreative()) {
            int j = stack.getMetadata();
            int i = stack.getCount();
            EnumActionResult enumactionresult = stack.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            stack.setItemDamage(j);
            stack.func_190920_e(i);
            return enumactionresult;
        }
        return stack.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    public void setWorld(WorldServer serverWorld) {
        this.theWorld = serverWorld;
    }
}

