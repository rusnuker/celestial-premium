/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class InventoryChangeTrigger
implements ICriterionTrigger<Instance> {
    private static final ResourceLocation field_192209_a = new ResourceLocation("inventory_changed");
    private final Map<PlayerAdvancements, Listeners> field_192210_b = Maps.newHashMap();

    @Override
    public ResourceLocation func_192163_a() {
        return field_192209_a;
    }

    @Override
    public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
        Listeners inventorychangetrigger$listeners = this.field_192210_b.get(p_192165_1_);
        if (inventorychangetrigger$listeners == null) {
            inventorychangetrigger$listeners = new Listeners(p_192165_1_);
            this.field_192210_b.put(p_192165_1_, inventorychangetrigger$listeners);
        }
        inventorychangetrigger$listeners.func_192489_a(p_192165_2_);
    }

    @Override
    public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
        Listeners inventorychangetrigger$listeners = this.field_192210_b.get(p_192164_1_);
        if (inventorychangetrigger$listeners != null) {
            inventorychangetrigger$listeners.func_192487_b(p_192164_2_);
            if (inventorychangetrigger$listeners.func_192488_a()) {
                this.field_192210_b.remove(p_192164_1_);
            }
        }
    }

    @Override
    public void func_192167_a(PlayerAdvancements p_192167_1_) {
        this.field_192210_b.remove(p_192167_1_);
    }

    @Override
    public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
        JsonObject jsonobject = JsonUtils.getJsonObject(p_192166_1_, "slots", new JsonObject());
        MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(jsonobject.get("occupied"));
        MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(jsonobject.get("full"));
        MinMaxBounds minmaxbounds2 = MinMaxBounds.func_192515_a(jsonobject.get("empty"));
        ItemPredicate[] aitempredicate = ItemPredicate.func_192494_b(p_192166_1_.get("items"));
        return new Instance(minmaxbounds, minmaxbounds1, minmaxbounds2, aitempredicate);
    }

    public void func_192208_a(EntityPlayerMP p_192208_1_, InventoryPlayer p_192208_2_) {
        Listeners inventorychangetrigger$listeners = this.field_192210_b.get(p_192208_1_.func_192039_O());
        if (inventorychangetrigger$listeners != null) {
            inventorychangetrigger$listeners.func_192486_a(p_192208_2_);
        }
    }

    static class Listeners {
        private final PlayerAdvancements field_192490_a;
        private final Set<ICriterionTrigger.Listener<Instance>> field_192491_b = Sets.newHashSet();

        public Listeners(PlayerAdvancements p_i47391_1_) {
            this.field_192490_a = p_i47391_1_;
        }

        public boolean func_192488_a() {
            return this.field_192491_b.isEmpty();
        }

        public void func_192489_a(ICriterionTrigger.Listener<Instance> p_192489_1_) {
            this.field_192491_b.add(p_192489_1_);
        }

        public void func_192487_b(ICriterionTrigger.Listener<Instance> p_192487_1_) {
            this.field_192491_b.remove(p_192487_1_);
        }

        public void func_192486_a(InventoryPlayer p_192486_1_) {
            ArrayList<ICriterionTrigger.Listener<Instance>> list = null;
            for (ICriterionTrigger.Listener<Instance> listener : this.field_192491_b) {
                if (!listener.func_192158_a().func_192265_a(p_192486_1_)) continue;
                if (list == null) {
                    list = Lists.newArrayList();
                }
                list.add(listener);
            }
            if (list != null) {
                for (ICriterionTrigger.Listener<Instance> listener1 : list) {
                    listener1.func_192159_a(this.field_192490_a);
                }
            }
        }
    }

    public static class Instance
    extends AbstractCriterionInstance {
        private final MinMaxBounds field_192266_a;
        private final MinMaxBounds field_192267_b;
        private final MinMaxBounds field_192268_c;
        private final ItemPredicate[] field_192269_d;

        public Instance(MinMaxBounds p_i47390_1_, MinMaxBounds p_i47390_2_, MinMaxBounds p_i47390_3_, ItemPredicate[] p_i47390_4_) {
            super(field_192209_a);
            this.field_192266_a = p_i47390_1_;
            this.field_192267_b = p_i47390_2_;
            this.field_192268_c = p_i47390_3_;
            this.field_192269_d = p_i47390_4_;
        }

        public boolean func_192265_a(InventoryPlayer p_192265_1_) {
            int i = 0;
            int j = 0;
            int k = 0;
            ArrayList<ItemPredicate> list = Lists.newArrayList(this.field_192269_d);
            for (int l = 0; l < p_192265_1_.getSizeInventory(); ++l) {
                ItemStack itemstack = p_192265_1_.getStackInSlot(l);
                if (itemstack.isEmpty()) {
                    ++j;
                    continue;
                }
                ++k;
                if (itemstack.getCount() >= itemstack.getMaxStackSize()) {
                    ++i;
                }
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    ItemPredicate itempredicate = (ItemPredicate)iterator.next();
                    if (!itempredicate.func_192493_a(itemstack)) continue;
                    iterator.remove();
                }
            }
            if (!this.field_192267_b.func_192514_a(i)) {
                return false;
            }
            if (!this.field_192268_c.func_192514_a(j)) {
                return false;
            }
            if (!this.field_192266_a.func_192514_a(k)) {
                return false;
            }
            return list.isEmpty();
        }
    }
}

