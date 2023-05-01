/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockShulkerBox
extends BlockContainer {
    public static final PropertyEnum<EnumFacing> field_190957_a = PropertyDirection.create("facing");
    private final EnumDyeColor field_190958_b;

    public BlockShulkerBox(EnumDyeColor p_i47248_1_) {
        super(Material.ROCK, MapColor.AIR);
        this.field_190958_b = p_i47248_1_;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(field_190957_a, EnumFacing.UP));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityShulkerBox(this.field_190958_b);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean causesSuffocation(IBlockState p_176214_1_) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean func_190946_v(IBlockState p_190946_1_) {
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
        if (worldIn.isRemote) {
            return true;
        }
        if (playerIn.isSpectator()) {
            return true;
        }
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityShulkerBox) {
            AxisAlignedBB axisalignedbb;
            EnumFacing enumfacing = state.getValue(field_190957_a);
            boolean flag = ((TileEntityShulkerBox)tileentity).func_190591_p() == TileEntityShulkerBox.AnimationStatus.CLOSED ? !worldIn.collidesWithAnyBlock((axisalignedbb = FULL_BLOCK_AABB.addCoord(0.5f * (float)enumfacing.getXOffset(), 0.5f * (float)enumfacing.getYOffset(), 0.5f * (float)enumfacing.getZOffset()).func_191195_a(enumfacing.getXOffset(), enumfacing.getYOffset(), enumfacing.getZOffset())).offset(pos.offset(enumfacing))) : true;
            if (flag) {
                playerIn.addStat(StatList.field_191272_ae);
                playerIn.displayGUIChest((IInventory)((Object)tileentity));
            }
            return true;
        }
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(field_190957_a, facing);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer((Block)this, field_190957_a);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(field_190957_a).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        return this.getDefaultState().withProperty(field_190957_a, enumfacing);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityShulkerBox) {
            TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)worldIn.getTileEntity(pos);
            tileentityshulkerbox.func_190579_a(player.capabilities.isCreativeMode);
            tileentityshulkerbox.fillWithLoot(player);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileentity;
        if (stack.hasDisplayName() && (tileentity = worldIn.getTileEntity(pos)) instanceof TileEntityShulkerBox) {
            ((TileEntityShulkerBox)tileentity).func_190575_a(stack.getDisplayName());
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityShulkerBox) {
            TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)tileentity;
            if (!tileentityshulkerbox.func_190590_r() && tileentityshulkerbox.func_190582_F()) {
                ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound.setTag("BlockEntityTag", ((TileEntityShulkerBox)tileentity).func_190580_f(nbttagcompound1));
                itemstack.setTagCompound(nbttagcompound);
                if (tileentityshulkerbox.hasCustomName()) {
                    itemstack.setStackDisplayName(tileentityshulkerbox.getName());
                    tileentityshulkerbox.func_190575_a("");
                }
                BlockShulkerBox.spawnAsEntity(worldIn, pos, itemstack);
            }
            worldIn.updateComparatorOutputLevel(pos, state.getBlock());
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void func_190948_a(ItemStack p_190948_1_, @Nullable World p_190948_2_, List<String> p_190948_3_, ITooltipFlag p_190948_4_) {
        super.func_190948_a(p_190948_1_, p_190948_2_, p_190948_3_, p_190948_4_);
        NBTTagCompound nbttagcompound = p_190948_1_.getTagCompound();
        if (nbttagcompound != null && nbttagcompound.hasKey("BlockEntityTag", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");
            if (nbttagcompound1.hasKey("LootTable", 8)) {
                p_190948_3_.add("???????");
            }
            if (nbttagcompound1.hasKey("Items", 9)) {
                NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
                ItemStackHelper.func_191283_b(nbttagcompound1, nonnulllist);
                int i = 0;
                int j = 0;
                for (ItemStack itemstack : nonnulllist) {
                    if (itemstack.isEmpty()) continue;
                    ++j;
                    if (i > 4) continue;
                    ++i;
                    p_190948_3_.add(String.format("%s x%d", itemstack.getDisplayName(), itemstack.getCount()));
                }
                if (j - i > 0) {
                    p_190948_3_.add(String.format((Object)((Object)TextFormatting.ITALIC) + I18n.translateToLocal("container.shulkerBox.more"), j - i));
                }
            }
        }
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        TileEntity tileentity = source.getTileEntity(pos);
        return tileentity instanceof TileEntityShulkerBox ? ((TileEntityShulkerBox)tileentity).func_190584_a(state) : FULL_BLOCK_AABB;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstoneFromInventory((IInventory)((Object)worldIn.getTileEntity(pos)));
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        ItemStack itemstack = super.getItem(worldIn, pos, state);
        TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)worldIn.getTileEntity(pos);
        NBTTagCompound nbttagcompound = tileentityshulkerbox.func_190580_f(new NBTTagCompound());
        if (!nbttagcompound.hasNoTags()) {
            itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
        }
        return itemstack;
    }

    public static EnumDyeColor func_190955_b(Item p_190955_0_) {
        return BlockShulkerBox.func_190954_c(Block.getBlockFromItem(p_190955_0_));
    }

    public static EnumDyeColor func_190954_c(Block p_190954_0_) {
        return p_190954_0_ instanceof BlockShulkerBox ? ((BlockShulkerBox)p_190954_0_).func_190956_e() : EnumDyeColor.PURPLE;
    }

    public static Block func_190952_a(EnumDyeColor p_190952_0_) {
        switch (p_190952_0_) {
            case WHITE: {
                return Blocks.WHITE_SHULKER_BOX;
            }
            case ORANGE: {
                return Blocks.ORANGE_SHULKER_BOX;
            }
            case MAGENTA: {
                return Blocks.MAGENTA_SHULKER_BOX;
            }
            case LIGHT_BLUE: {
                return Blocks.LIGHT_BLUE_SHULKER_BOX;
            }
            case YELLOW: {
                return Blocks.YELLOW_SHULKER_BOX;
            }
            case LIME: {
                return Blocks.LIME_SHULKER_BOX;
            }
            case PINK: {
                return Blocks.PINK_SHULKER_BOX;
            }
            case GRAY: {
                return Blocks.GRAY_SHULKER_BOX;
            }
            case SILVER: {
                return Blocks.SILVER_SHULKER_BOX;
            }
            case CYAN: {
                return Blocks.CYAN_SHULKER_BOX;
            }
            default: {
                return Blocks.PURPLE_SHULKER_BOX;
            }
            case BLUE: {
                return Blocks.BLUE_SHULKER_BOX;
            }
            case BROWN: {
                return Blocks.BROWN_SHULKER_BOX;
            }
            case GREEN: {
                return Blocks.GREEN_SHULKER_BOX;
            }
            case RED: {
                return Blocks.RED_SHULKER_BOX;
            }
            case BLACK: 
        }
        return Blocks.BLACK_SHULKER_BOX;
    }

    public EnumDyeColor func_190956_e() {
        return this.field_190958_b;
    }

    public static ItemStack func_190953_b(EnumDyeColor p_190953_0_) {
        return new ItemStack(BlockShulkerBox.func_190952_a(p_190953_0_));
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(field_190957_a, rot.rotate(state.getValue(field_190957_a)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(field_190957_a)));
    }

    @Override
    public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        p_193383_2_ = this.getActualState(p_193383_2_, p_193383_1_, p_193383_3_);
        EnumFacing enumfacing = p_193383_2_.getValue(field_190957_a);
        TileEntityShulkerBox.AnimationStatus tileentityshulkerbox$animationstatus = ((TileEntityShulkerBox)p_193383_1_.getTileEntity(p_193383_3_)).func_190591_p();
        return tileentityshulkerbox$animationstatus != TileEntityShulkerBox.AnimationStatus.CLOSED && (tileentityshulkerbox$animationstatus != TileEntityShulkerBox.AnimationStatus.OPENED || enumfacing != p_193383_4_.getOpposite() && enumfacing != p_193383_4_) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
    }
}

