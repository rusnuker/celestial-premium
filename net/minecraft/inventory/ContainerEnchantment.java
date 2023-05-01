/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.inventory;

import java.util.List;
import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerEnchantment
extends Container {
    public IInventory tableInventory = new InventoryBasic("Enchant", true, 2){

        @Override
        public int getInventoryStackLimit() {
            return 64;
        }

        @Override
        public void markDirty() {
            super.markDirty();
            ContainerEnchantment.this.onCraftMatrixChanged(this);
        }
    };
    private final World worldPointer;
    private final BlockPos position;
    private final Random rand = new Random();
    public int xpSeed;
    public int[] enchantLevels = new int[3];
    public int[] enchantClue = new int[]{-1, -1, -1};
    public int[] worldClue = new int[]{-1, -1, -1};

    public ContainerEnchantment(InventoryPlayer playerInv, World worldIn) {
        this(playerInv, worldIn, BlockPos.ORIGIN);
    }

    public ContainerEnchantment(InventoryPlayer playerInv, World worldIn, BlockPos pos) {
        this.worldPointer = worldIn;
        this.position = pos;
        this.xpSeed = playerInv.player.getXPSeed();
        this.addSlotToContainer(new Slot(this.tableInventory, 0, 15, 47){

            @Override
            public boolean isItemValid(ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotStackLimit() {
                return 1;
            }
        });
        this.addSlotToContainer(new Slot(this.tableInventory, 1, 35, 47){

            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() == Items.DYE && EnumDyeColor.byDyeDamage(stack.getMetadata()) == EnumDyeColor.BLUE;
            }
        });
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
        }
    }

    protected void broadcastData(IContainerListener crafting) {
        crafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
        crafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
        crafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
        crafting.sendProgressBarUpdate(this, 3, this.xpSeed & 0xFFFFFFF0);
        crafting.sendProgressBarUpdate(this, 4, this.enchantClue[0]);
        crafting.sendProgressBarUpdate(this, 5, this.enchantClue[1]);
        crafting.sendProgressBarUpdate(this, 6, this.enchantClue[2]);
        crafting.sendProgressBarUpdate(this, 7, this.worldClue[0]);
        crafting.sendProgressBarUpdate(this, 8, this.worldClue[1]);
        crafting.sendProgressBarUpdate(this, 9, this.worldClue[2]);
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        this.broadcastData(listener);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            this.broadcastData(icontainerlistener);
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id >= 0 && id <= 2) {
            this.enchantLevels[id] = data;
        } else if (id == 3) {
            this.xpSeed = data;
        } else if (id >= 4 && id <= 6) {
            this.enchantClue[id - 4] = data;
        } else if (id >= 7 && id <= 9) {
            this.worldClue[id - 7] = data;
        } else {
            super.updateProgressBar(id, data);
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        if (inventoryIn == this.tableInventory) {
            ItemStack itemstack = inventoryIn.getStackInSlot(0);
            if (!itemstack.isEmpty() && itemstack.isItemEnchantable()) {
                if (!this.worldPointer.isRemote) {
                    int l = 0;
                    for (int j = -1; j <= 1; ++j) {
                        for (int k = -1; k <= 1; ++k) {
                            if (j == 0 && k == 0 || !this.worldPointer.isAirBlock(this.position.add(k, 0, j)) || !this.worldPointer.isAirBlock(this.position.add(k, 1, j))) continue;
                            if (this.worldPointer.getBlockState(this.position.add(k * 2, 0, j * 2)).getBlock() == Blocks.BOOKSHELF) {
                                ++l;
                            }
                            if (this.worldPointer.getBlockState(this.position.add(k * 2, 1, j * 2)).getBlock() == Blocks.BOOKSHELF) {
                                ++l;
                            }
                            if (k == 0 || j == 0) continue;
                            if (this.worldPointer.getBlockState(this.position.add(k * 2, 0, j)).getBlock() == Blocks.BOOKSHELF) {
                                ++l;
                            }
                            if (this.worldPointer.getBlockState(this.position.add(k * 2, 1, j)).getBlock() == Blocks.BOOKSHELF) {
                                ++l;
                            }
                            if (this.worldPointer.getBlockState(this.position.add(k, 0, j * 2)).getBlock() == Blocks.BOOKSHELF) {
                                ++l;
                            }
                            if (this.worldPointer.getBlockState(this.position.add(k, 1, j * 2)).getBlock() != Blocks.BOOKSHELF) continue;
                            ++l;
                        }
                    }
                    this.rand.setSeed(this.xpSeed);
                    for (int i1 = 0; i1 < 3; ++i1) {
                        this.enchantLevels[i1] = EnchantmentHelper.calcItemStackEnchantability(this.rand, i1, l, itemstack);
                        this.enchantClue[i1] = -1;
                        this.worldClue[i1] = -1;
                        if (this.enchantLevels[i1] >= i1 + 1) continue;
                        this.enchantLevels[i1] = 0;
                    }
                    for (int j1 = 0; j1 < 3; ++j1) {
                        List<EnchantmentData> list;
                        if (this.enchantLevels[j1] <= 0 || (list = this.getEnchantmentList(itemstack, j1, this.enchantLevels[j1])) == null || list.isEmpty()) continue;
                        EnchantmentData enchantmentdata = list.get(this.rand.nextInt(list.size()));
                        this.enchantClue[j1] = Enchantment.getEnchantmentID(enchantmentdata.enchantmentobj);
                        this.worldClue[j1] = enchantmentdata.enchantmentLevel;
                    }
                    this.detectAndSendChanges();
                }
            } else {
                for (int i = 0; i < 3; ++i) {
                    this.enchantLevels[i] = 0;
                    this.enchantClue[i] = -1;
                    this.worldClue[i] = -1;
                }
            }
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer playerIn, int id) {
        ItemStack itemstack = this.tableInventory.getStackInSlot(0);
        ItemStack itemstack1 = this.tableInventory.getStackInSlot(1);
        int i = id + 1;
        if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !playerIn.capabilities.isCreativeMode) {
            return false;
        }
        if (this.enchantLevels[id] > 0 && !itemstack.isEmpty() && (playerIn.experienceLevel >= i && playerIn.experienceLevel >= this.enchantLevels[id] || playerIn.capabilities.isCreativeMode)) {
            List<EnchantmentData> list;
            if (!this.worldPointer.isRemote && !(list = this.getEnchantmentList(itemstack, id, this.enchantLevels[id])).isEmpty()) {
                boolean flag;
                playerIn.func_192024_a(itemstack, i);
                boolean bl = flag = itemstack.getItem() == Items.BOOK;
                if (flag) {
                    itemstack = new ItemStack(Items.ENCHANTED_BOOK);
                    this.tableInventory.setInventorySlotContents(0, itemstack);
                }
                for (int j = 0; j < list.size(); ++j) {
                    EnchantmentData enchantmentdata = list.get(j);
                    if (flag) {
                        ItemEnchantedBook.addEnchantment(itemstack, enchantmentdata);
                        continue;
                    }
                    itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                }
                if (!playerIn.capabilities.isCreativeMode) {
                    itemstack1.func_190918_g(i);
                    if (itemstack1.isEmpty()) {
                        this.tableInventory.setInventorySlotContents(1, ItemStack.field_190927_a);
                    }
                }
                playerIn.addStat(StatList.ITEM_ENCHANTED);
                if (playerIn instanceof EntityPlayerMP) {
                    CriteriaTriggers.field_192129_i.func_192190_a((EntityPlayerMP)playerIn, itemstack, i);
                }
                this.tableInventory.markDirty();
                this.xpSeed = playerIn.getXPSeed();
                this.onCraftMatrixChanged(this.tableInventory);
                this.worldPointer.playSound(null, this.position, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, this.worldPointer.rand.nextFloat() * 0.1f + 0.9f);
            }
            return true;
        }
        return false;
    }

    private List<EnchantmentData> getEnchantmentList(ItemStack stack, int p_178148_2_, int p_178148_3_) {
        this.rand.setSeed(this.xpSeed + p_178148_2_);
        List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(this.rand, stack, p_178148_3_, false);
        if (stack.getItem() == Items.BOOK && list.size() > 1) {
            list.remove(this.rand.nextInt(list.size()));
        }
        return list;
    }

    public int getLapisAmount() {
        ItemStack itemstack = this.tableInventory.getStackInSlot(1);
        return itemstack.isEmpty() ? 0 : itemstack.getCount();
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (!this.worldPointer.isRemote) {
            this.func_193327_a(playerIn, playerIn.world, this.tableInventory);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        if (this.worldPointer.getBlockState(this.position).getBlock() != Blocks.ENCHANTING_TABLE) {
            return false;
        }
        return playerIn.getDistanceSq((double)this.position.getX() + 0.5, (double)this.position.getY() + 0.5, (double)this.position.getZ() + 0.5) <= 64.0;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.field_190927_a;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.field_190927_a;
                }
            } else if (index == 1) {
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.field_190927_a;
                }
            } else if (itemstack1.getItem() == Items.DYE && EnumDyeColor.byDyeDamage(itemstack1.getMetadata()) == EnumDyeColor.BLUE) {
                if (!this.mergeItemStack(itemstack1, 1, 2, true)) {
                    return ItemStack.field_190927_a;
                }
            } else {
                if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemstack1)) {
                    return ItemStack.field_190927_a;
                }
                if (itemstack1.hasTagCompound() && itemstack1.getCount() == 1) {
                    ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.copy());
                    itemstack1.func_190920_e(0);
                } else if (!itemstack1.isEmpty()) {
                    ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.getItem(), 1, itemstack1.getMetadata()));
                    itemstack1.func_190918_g(1);
                }
            }
            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.field_190927_a);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.field_190927_a;
            }
            slot.func_190901_a(playerIn, itemstack1);
        }
        return itemstack;
    }
}

