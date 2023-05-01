/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class ItemIntIDToString
implements IFixableData {
    private static final String[] ID_MAP = new String[2268];

    @Override
    public int getFixVersion() {
        return 102;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        short short1;
        if (compound.hasKey("id", 99) && (short1 = compound.getShort("id")) > 0 && short1 < ID_MAP.length && ID_MAP[short1] != null) {
            compound.setString("id", ID_MAP[short1]);
        }
        return compound;
    }

    static {
        ItemIntIDToString.ID_MAP[1] = "minecraft:stone";
        ItemIntIDToString.ID_MAP[2] = "minecraft:grass";
        ItemIntIDToString.ID_MAP[3] = "minecraft:dirt";
        ItemIntIDToString.ID_MAP[4] = "minecraft:cobblestone";
        ItemIntIDToString.ID_MAP[5] = "minecraft:planks";
        ItemIntIDToString.ID_MAP[6] = "minecraft:sapling";
        ItemIntIDToString.ID_MAP[7] = "minecraft:bedrock";
        ItemIntIDToString.ID_MAP[8] = "minecraft:flowing_water";
        ItemIntIDToString.ID_MAP[9] = "minecraft:water";
        ItemIntIDToString.ID_MAP[10] = "minecraft:flowing_lava";
        ItemIntIDToString.ID_MAP[11] = "minecraft:lava";
        ItemIntIDToString.ID_MAP[12] = "minecraft:sand";
        ItemIntIDToString.ID_MAP[13] = "minecraft:gravel";
        ItemIntIDToString.ID_MAP[14] = "minecraft:gold_ore";
        ItemIntIDToString.ID_MAP[15] = "minecraft:iron_ore";
        ItemIntIDToString.ID_MAP[16] = "minecraft:coal_ore";
        ItemIntIDToString.ID_MAP[17] = "minecraft:log";
        ItemIntIDToString.ID_MAP[18] = "minecraft:leaves";
        ItemIntIDToString.ID_MAP[19] = "minecraft:sponge";
        ItemIntIDToString.ID_MAP[20] = "minecraft:glass";
        ItemIntIDToString.ID_MAP[21] = "minecraft:lapis_ore";
        ItemIntIDToString.ID_MAP[22] = "minecraft:lapis_block";
        ItemIntIDToString.ID_MAP[23] = "minecraft:dispenser";
        ItemIntIDToString.ID_MAP[24] = "minecraft:sandstone";
        ItemIntIDToString.ID_MAP[25] = "minecraft:noteblock";
        ItemIntIDToString.ID_MAP[27] = "minecraft:golden_rail";
        ItemIntIDToString.ID_MAP[28] = "minecraft:detector_rail";
        ItemIntIDToString.ID_MAP[29] = "minecraft:sticky_piston";
        ItemIntIDToString.ID_MAP[30] = "minecraft:web";
        ItemIntIDToString.ID_MAP[31] = "minecraft:tallgrass";
        ItemIntIDToString.ID_MAP[32] = "minecraft:deadbush";
        ItemIntIDToString.ID_MAP[33] = "minecraft:piston";
        ItemIntIDToString.ID_MAP[35] = "minecraft:wool";
        ItemIntIDToString.ID_MAP[37] = "minecraft:yellow_flower";
        ItemIntIDToString.ID_MAP[38] = "minecraft:red_flower";
        ItemIntIDToString.ID_MAP[39] = "minecraft:brown_mushroom";
        ItemIntIDToString.ID_MAP[40] = "minecraft:red_mushroom";
        ItemIntIDToString.ID_MAP[41] = "minecraft:gold_block";
        ItemIntIDToString.ID_MAP[42] = "minecraft:iron_block";
        ItemIntIDToString.ID_MAP[43] = "minecraft:double_stone_slab";
        ItemIntIDToString.ID_MAP[44] = "minecraft:stone_slab";
        ItemIntIDToString.ID_MAP[45] = "minecraft:brick_block";
        ItemIntIDToString.ID_MAP[46] = "minecraft:tnt";
        ItemIntIDToString.ID_MAP[47] = "minecraft:bookshelf";
        ItemIntIDToString.ID_MAP[48] = "minecraft:mossy_cobblestone";
        ItemIntIDToString.ID_MAP[49] = "minecraft:obsidian";
        ItemIntIDToString.ID_MAP[50] = "minecraft:torch";
        ItemIntIDToString.ID_MAP[51] = "minecraft:fire";
        ItemIntIDToString.ID_MAP[52] = "minecraft:mob_spawner";
        ItemIntIDToString.ID_MAP[53] = "minecraft:oak_stairs";
        ItemIntIDToString.ID_MAP[54] = "minecraft:chest";
        ItemIntIDToString.ID_MAP[56] = "minecraft:diamond_ore";
        ItemIntIDToString.ID_MAP[57] = "minecraft:diamond_block";
        ItemIntIDToString.ID_MAP[58] = "minecraft:crafting_table";
        ItemIntIDToString.ID_MAP[60] = "minecraft:farmland";
        ItemIntIDToString.ID_MAP[61] = "minecraft:furnace";
        ItemIntIDToString.ID_MAP[62] = "minecraft:lit_furnace";
        ItemIntIDToString.ID_MAP[65] = "minecraft:ladder";
        ItemIntIDToString.ID_MAP[66] = "minecraft:rail";
        ItemIntIDToString.ID_MAP[67] = "minecraft:stone_stairs";
        ItemIntIDToString.ID_MAP[69] = "minecraft:lever";
        ItemIntIDToString.ID_MAP[70] = "minecraft:stone_pressure_plate";
        ItemIntIDToString.ID_MAP[72] = "minecraft:wooden_pressure_plate";
        ItemIntIDToString.ID_MAP[73] = "minecraft:redstone_ore";
        ItemIntIDToString.ID_MAP[76] = "minecraft:redstone_torch";
        ItemIntIDToString.ID_MAP[77] = "minecraft:stone_button";
        ItemIntIDToString.ID_MAP[78] = "minecraft:snow_layer";
        ItemIntIDToString.ID_MAP[79] = "minecraft:ice";
        ItemIntIDToString.ID_MAP[80] = "minecraft:snow";
        ItemIntIDToString.ID_MAP[81] = "minecraft:cactus";
        ItemIntIDToString.ID_MAP[82] = "minecraft:clay";
        ItemIntIDToString.ID_MAP[84] = "minecraft:jukebox";
        ItemIntIDToString.ID_MAP[85] = "minecraft:fence";
        ItemIntIDToString.ID_MAP[86] = "minecraft:pumpkin";
        ItemIntIDToString.ID_MAP[87] = "minecraft:netherrack";
        ItemIntIDToString.ID_MAP[88] = "minecraft:soul_sand";
        ItemIntIDToString.ID_MAP[89] = "minecraft:glowstone";
        ItemIntIDToString.ID_MAP[90] = "minecraft:portal";
        ItemIntIDToString.ID_MAP[91] = "minecraft:lit_pumpkin";
        ItemIntIDToString.ID_MAP[95] = "minecraft:stained_glass";
        ItemIntIDToString.ID_MAP[96] = "minecraft:trapdoor";
        ItemIntIDToString.ID_MAP[97] = "minecraft:monster_egg";
        ItemIntIDToString.ID_MAP[98] = "minecraft:stonebrick";
        ItemIntIDToString.ID_MAP[99] = "minecraft:brown_mushroom_block";
        ItemIntIDToString.ID_MAP[100] = "minecraft:red_mushroom_block";
        ItemIntIDToString.ID_MAP[101] = "minecraft:iron_bars";
        ItemIntIDToString.ID_MAP[102] = "minecraft:glass_pane";
        ItemIntIDToString.ID_MAP[103] = "minecraft:melon_block";
        ItemIntIDToString.ID_MAP[106] = "minecraft:vine";
        ItemIntIDToString.ID_MAP[107] = "minecraft:fence_gate";
        ItemIntIDToString.ID_MAP[108] = "minecraft:brick_stairs";
        ItemIntIDToString.ID_MAP[109] = "minecraft:stone_brick_stairs";
        ItemIntIDToString.ID_MAP[110] = "minecraft:mycelium";
        ItemIntIDToString.ID_MAP[111] = "minecraft:waterlily";
        ItemIntIDToString.ID_MAP[112] = "minecraft:nether_brick";
        ItemIntIDToString.ID_MAP[113] = "minecraft:nether_brick_fence";
        ItemIntIDToString.ID_MAP[114] = "minecraft:nether_brick_stairs";
        ItemIntIDToString.ID_MAP[116] = "minecraft:enchanting_table";
        ItemIntIDToString.ID_MAP[119] = "minecraft:end_portal";
        ItemIntIDToString.ID_MAP[120] = "minecraft:end_portal_frame";
        ItemIntIDToString.ID_MAP[121] = "minecraft:end_stone";
        ItemIntIDToString.ID_MAP[122] = "minecraft:dragon_egg";
        ItemIntIDToString.ID_MAP[123] = "minecraft:redstone_lamp";
        ItemIntIDToString.ID_MAP[125] = "minecraft:double_wooden_slab";
        ItemIntIDToString.ID_MAP[126] = "minecraft:wooden_slab";
        ItemIntIDToString.ID_MAP[127] = "minecraft:cocoa";
        ItemIntIDToString.ID_MAP[128] = "minecraft:sandstone_stairs";
        ItemIntIDToString.ID_MAP[129] = "minecraft:emerald_ore";
        ItemIntIDToString.ID_MAP[130] = "minecraft:ender_chest";
        ItemIntIDToString.ID_MAP[131] = "minecraft:tripwire_hook";
        ItemIntIDToString.ID_MAP[133] = "minecraft:emerald_block";
        ItemIntIDToString.ID_MAP[134] = "minecraft:spruce_stairs";
        ItemIntIDToString.ID_MAP[135] = "minecraft:birch_stairs";
        ItemIntIDToString.ID_MAP[136] = "minecraft:jungle_stairs";
        ItemIntIDToString.ID_MAP[137] = "minecraft:command_block";
        ItemIntIDToString.ID_MAP[138] = "minecraft:beacon";
        ItemIntIDToString.ID_MAP[139] = "minecraft:cobblestone_wall";
        ItemIntIDToString.ID_MAP[141] = "minecraft:carrots";
        ItemIntIDToString.ID_MAP[142] = "minecraft:potatoes";
        ItemIntIDToString.ID_MAP[143] = "minecraft:wooden_button";
        ItemIntIDToString.ID_MAP[145] = "minecraft:anvil";
        ItemIntIDToString.ID_MAP[146] = "minecraft:trapped_chest";
        ItemIntIDToString.ID_MAP[147] = "minecraft:light_weighted_pressure_plate";
        ItemIntIDToString.ID_MAP[148] = "minecraft:heavy_weighted_pressure_plate";
        ItemIntIDToString.ID_MAP[151] = "minecraft:daylight_detector";
        ItemIntIDToString.ID_MAP[152] = "minecraft:redstone_block";
        ItemIntIDToString.ID_MAP[153] = "minecraft:quartz_ore";
        ItemIntIDToString.ID_MAP[154] = "minecraft:hopper";
        ItemIntIDToString.ID_MAP[155] = "minecraft:quartz_block";
        ItemIntIDToString.ID_MAP[156] = "minecraft:quartz_stairs";
        ItemIntIDToString.ID_MAP[157] = "minecraft:activator_rail";
        ItemIntIDToString.ID_MAP[158] = "minecraft:dropper";
        ItemIntIDToString.ID_MAP[159] = "minecraft:stained_hardened_clay";
        ItemIntIDToString.ID_MAP[160] = "minecraft:stained_glass_pane";
        ItemIntIDToString.ID_MAP[161] = "minecraft:leaves2";
        ItemIntIDToString.ID_MAP[162] = "minecraft:log2";
        ItemIntIDToString.ID_MAP[163] = "minecraft:acacia_stairs";
        ItemIntIDToString.ID_MAP[164] = "minecraft:dark_oak_stairs";
        ItemIntIDToString.ID_MAP[170] = "minecraft:hay_block";
        ItemIntIDToString.ID_MAP[171] = "minecraft:carpet";
        ItemIntIDToString.ID_MAP[172] = "minecraft:hardened_clay";
        ItemIntIDToString.ID_MAP[173] = "minecraft:coal_block";
        ItemIntIDToString.ID_MAP[174] = "minecraft:packed_ice";
        ItemIntIDToString.ID_MAP[175] = "minecraft:double_plant";
        ItemIntIDToString.ID_MAP[256] = "minecraft:iron_shovel";
        ItemIntIDToString.ID_MAP[257] = "minecraft:iron_pickaxe";
        ItemIntIDToString.ID_MAP[258] = "minecraft:iron_axe";
        ItemIntIDToString.ID_MAP[259] = "minecraft:flint_and_steel";
        ItemIntIDToString.ID_MAP[260] = "minecraft:apple";
        ItemIntIDToString.ID_MAP[261] = "minecraft:bow";
        ItemIntIDToString.ID_MAP[262] = "minecraft:arrow";
        ItemIntIDToString.ID_MAP[263] = "minecraft:coal";
        ItemIntIDToString.ID_MAP[264] = "minecraft:diamond";
        ItemIntIDToString.ID_MAP[265] = "minecraft:iron_ingot";
        ItemIntIDToString.ID_MAP[266] = "minecraft:gold_ingot";
        ItemIntIDToString.ID_MAP[267] = "minecraft:iron_sword";
        ItemIntIDToString.ID_MAP[268] = "minecraft:wooden_sword";
        ItemIntIDToString.ID_MAP[269] = "minecraft:wooden_shovel";
        ItemIntIDToString.ID_MAP[270] = "minecraft:wooden_pickaxe";
        ItemIntIDToString.ID_MAP[271] = "minecraft:wooden_axe";
        ItemIntIDToString.ID_MAP[272] = "minecraft:stone_sword";
        ItemIntIDToString.ID_MAP[273] = "minecraft:stone_shovel";
        ItemIntIDToString.ID_MAP[274] = "minecraft:stone_pickaxe";
        ItemIntIDToString.ID_MAP[275] = "minecraft:stone_axe";
        ItemIntIDToString.ID_MAP[276] = "minecraft:diamond_sword";
        ItemIntIDToString.ID_MAP[277] = "minecraft:diamond_shovel";
        ItemIntIDToString.ID_MAP[278] = "minecraft:diamond_pickaxe";
        ItemIntIDToString.ID_MAP[279] = "minecraft:diamond_axe";
        ItemIntIDToString.ID_MAP[280] = "minecraft:stick";
        ItemIntIDToString.ID_MAP[281] = "minecraft:bowl";
        ItemIntIDToString.ID_MAP[282] = "minecraft:mushroom_stew";
        ItemIntIDToString.ID_MAP[283] = "minecraft:golden_sword";
        ItemIntIDToString.ID_MAP[284] = "minecraft:golden_shovel";
        ItemIntIDToString.ID_MAP[285] = "minecraft:golden_pickaxe";
        ItemIntIDToString.ID_MAP[286] = "minecraft:golden_axe";
        ItemIntIDToString.ID_MAP[287] = "minecraft:string";
        ItemIntIDToString.ID_MAP[288] = "minecraft:feather";
        ItemIntIDToString.ID_MAP[289] = "minecraft:gunpowder";
        ItemIntIDToString.ID_MAP[290] = "minecraft:wooden_hoe";
        ItemIntIDToString.ID_MAP[291] = "minecraft:stone_hoe";
        ItemIntIDToString.ID_MAP[292] = "minecraft:iron_hoe";
        ItemIntIDToString.ID_MAP[293] = "minecraft:diamond_hoe";
        ItemIntIDToString.ID_MAP[294] = "minecraft:golden_hoe";
        ItemIntIDToString.ID_MAP[295] = "minecraft:wheat_seeds";
        ItemIntIDToString.ID_MAP[296] = "minecraft:wheat";
        ItemIntIDToString.ID_MAP[297] = "minecraft:bread";
        ItemIntIDToString.ID_MAP[298] = "minecraft:leather_helmet";
        ItemIntIDToString.ID_MAP[299] = "minecraft:leather_chestplate";
        ItemIntIDToString.ID_MAP[300] = "minecraft:leather_leggings";
        ItemIntIDToString.ID_MAP[301] = "minecraft:leather_boots";
        ItemIntIDToString.ID_MAP[302] = "minecraft:chainmail_helmet";
        ItemIntIDToString.ID_MAP[303] = "minecraft:chainmail_chestplate";
        ItemIntIDToString.ID_MAP[304] = "minecraft:chainmail_leggings";
        ItemIntIDToString.ID_MAP[305] = "minecraft:chainmail_boots";
        ItemIntIDToString.ID_MAP[306] = "minecraft:iron_helmet";
        ItemIntIDToString.ID_MAP[307] = "minecraft:iron_chestplate";
        ItemIntIDToString.ID_MAP[308] = "minecraft:iron_leggings";
        ItemIntIDToString.ID_MAP[309] = "minecraft:iron_boots";
        ItemIntIDToString.ID_MAP[310] = "minecraft:diamond_helmet";
        ItemIntIDToString.ID_MAP[311] = "minecraft:diamond_chestplate";
        ItemIntIDToString.ID_MAP[312] = "minecraft:diamond_leggings";
        ItemIntIDToString.ID_MAP[313] = "minecraft:diamond_boots";
        ItemIntIDToString.ID_MAP[314] = "minecraft:golden_helmet";
        ItemIntIDToString.ID_MAP[315] = "minecraft:golden_chestplate";
        ItemIntIDToString.ID_MAP[316] = "minecraft:golden_leggings";
        ItemIntIDToString.ID_MAP[317] = "minecraft:golden_boots";
        ItemIntIDToString.ID_MAP[318] = "minecraft:flint";
        ItemIntIDToString.ID_MAP[319] = "minecraft:porkchop";
        ItemIntIDToString.ID_MAP[320] = "minecraft:cooked_porkchop";
        ItemIntIDToString.ID_MAP[321] = "minecraft:painting";
        ItemIntIDToString.ID_MAP[322] = "minecraft:golden_apple";
        ItemIntIDToString.ID_MAP[323] = "minecraft:sign";
        ItemIntIDToString.ID_MAP[324] = "minecraft:wooden_door";
        ItemIntIDToString.ID_MAP[325] = "minecraft:bucket";
        ItemIntIDToString.ID_MAP[326] = "minecraft:water_bucket";
        ItemIntIDToString.ID_MAP[327] = "minecraft:lava_bucket";
        ItemIntIDToString.ID_MAP[328] = "minecraft:minecart";
        ItemIntIDToString.ID_MAP[329] = "minecraft:saddle";
        ItemIntIDToString.ID_MAP[330] = "minecraft:iron_door";
        ItemIntIDToString.ID_MAP[331] = "minecraft:redstone";
        ItemIntIDToString.ID_MAP[332] = "minecraft:snowball";
        ItemIntIDToString.ID_MAP[333] = "minecraft:boat";
        ItemIntIDToString.ID_MAP[334] = "minecraft:leather";
        ItemIntIDToString.ID_MAP[335] = "minecraft:milk_bucket";
        ItemIntIDToString.ID_MAP[336] = "minecraft:brick";
        ItemIntIDToString.ID_MAP[337] = "minecraft:clay_ball";
        ItemIntIDToString.ID_MAP[338] = "minecraft:reeds";
        ItemIntIDToString.ID_MAP[339] = "minecraft:paper";
        ItemIntIDToString.ID_MAP[340] = "minecraft:book";
        ItemIntIDToString.ID_MAP[341] = "minecraft:slime_ball";
        ItemIntIDToString.ID_MAP[342] = "minecraft:chest_minecart";
        ItemIntIDToString.ID_MAP[343] = "minecraft:furnace_minecart";
        ItemIntIDToString.ID_MAP[344] = "minecraft:egg";
        ItemIntIDToString.ID_MAP[345] = "minecraft:compass";
        ItemIntIDToString.ID_MAP[346] = "minecraft:fishing_rod";
        ItemIntIDToString.ID_MAP[347] = "minecraft:clock";
        ItemIntIDToString.ID_MAP[348] = "minecraft:glowstone_dust";
        ItemIntIDToString.ID_MAP[349] = "minecraft:fish";
        ItemIntIDToString.ID_MAP[350] = "minecraft:cooked_fished";
        ItemIntIDToString.ID_MAP[351] = "minecraft:dye";
        ItemIntIDToString.ID_MAP[352] = "minecraft:bone";
        ItemIntIDToString.ID_MAP[353] = "minecraft:sugar";
        ItemIntIDToString.ID_MAP[354] = "minecraft:cake";
        ItemIntIDToString.ID_MAP[355] = "minecraft:bed";
        ItemIntIDToString.ID_MAP[356] = "minecraft:repeater";
        ItemIntIDToString.ID_MAP[357] = "minecraft:cookie";
        ItemIntIDToString.ID_MAP[358] = "minecraft:filled_map";
        ItemIntIDToString.ID_MAP[359] = "minecraft:shears";
        ItemIntIDToString.ID_MAP[360] = "minecraft:melon";
        ItemIntIDToString.ID_MAP[361] = "minecraft:pumpkin_seeds";
        ItemIntIDToString.ID_MAP[362] = "minecraft:melon_seeds";
        ItemIntIDToString.ID_MAP[363] = "minecraft:beef";
        ItemIntIDToString.ID_MAP[364] = "minecraft:cooked_beef";
        ItemIntIDToString.ID_MAP[365] = "minecraft:chicken";
        ItemIntIDToString.ID_MAP[366] = "minecraft:cooked_chicken";
        ItemIntIDToString.ID_MAP[367] = "minecraft:rotten_flesh";
        ItemIntIDToString.ID_MAP[368] = "minecraft:ender_pearl";
        ItemIntIDToString.ID_MAP[369] = "minecraft:blaze_rod";
        ItemIntIDToString.ID_MAP[370] = "minecraft:ghast_tear";
        ItemIntIDToString.ID_MAP[371] = "minecraft:gold_nugget";
        ItemIntIDToString.ID_MAP[372] = "minecraft:nether_wart";
        ItemIntIDToString.ID_MAP[373] = "minecraft:potion";
        ItemIntIDToString.ID_MAP[374] = "minecraft:glass_bottle";
        ItemIntIDToString.ID_MAP[375] = "minecraft:spider_eye";
        ItemIntIDToString.ID_MAP[376] = "minecraft:fermented_spider_eye";
        ItemIntIDToString.ID_MAP[377] = "minecraft:blaze_powder";
        ItemIntIDToString.ID_MAP[378] = "minecraft:magma_cream";
        ItemIntIDToString.ID_MAP[379] = "minecraft:brewing_stand";
        ItemIntIDToString.ID_MAP[380] = "minecraft:cauldron";
        ItemIntIDToString.ID_MAP[381] = "minecraft:ender_eye";
        ItemIntIDToString.ID_MAP[382] = "minecraft:speckled_melon";
        ItemIntIDToString.ID_MAP[383] = "minecraft:spawn_egg";
        ItemIntIDToString.ID_MAP[384] = "minecraft:experience_bottle";
        ItemIntIDToString.ID_MAP[385] = "minecraft:fire_charge";
        ItemIntIDToString.ID_MAP[386] = "minecraft:writable_book";
        ItemIntIDToString.ID_MAP[387] = "minecraft:written_book";
        ItemIntIDToString.ID_MAP[388] = "minecraft:emerald";
        ItemIntIDToString.ID_MAP[389] = "minecraft:item_frame";
        ItemIntIDToString.ID_MAP[390] = "minecraft:flower_pot";
        ItemIntIDToString.ID_MAP[391] = "minecraft:carrot";
        ItemIntIDToString.ID_MAP[392] = "minecraft:potato";
        ItemIntIDToString.ID_MAP[393] = "minecraft:baked_potato";
        ItemIntIDToString.ID_MAP[394] = "minecraft:poisonous_potato";
        ItemIntIDToString.ID_MAP[395] = "minecraft:map";
        ItemIntIDToString.ID_MAP[396] = "minecraft:golden_carrot";
        ItemIntIDToString.ID_MAP[397] = "minecraft:skull";
        ItemIntIDToString.ID_MAP[398] = "minecraft:carrot_on_a_stick";
        ItemIntIDToString.ID_MAP[399] = "minecraft:nether_star";
        ItemIntIDToString.ID_MAP[400] = "minecraft:pumpkin_pie";
        ItemIntIDToString.ID_MAP[401] = "minecraft:fireworks";
        ItemIntIDToString.ID_MAP[402] = "minecraft:firework_charge";
        ItemIntIDToString.ID_MAP[403] = "minecraft:enchanted_book";
        ItemIntIDToString.ID_MAP[404] = "minecraft:comparator";
        ItemIntIDToString.ID_MAP[405] = "minecraft:netherbrick";
        ItemIntIDToString.ID_MAP[406] = "minecraft:quartz";
        ItemIntIDToString.ID_MAP[407] = "minecraft:tnt_minecart";
        ItemIntIDToString.ID_MAP[408] = "minecraft:hopper_minecart";
        ItemIntIDToString.ID_MAP[417] = "minecraft:iron_horse_armor";
        ItemIntIDToString.ID_MAP[418] = "minecraft:golden_horse_armor";
        ItemIntIDToString.ID_MAP[419] = "minecraft:diamond_horse_armor";
        ItemIntIDToString.ID_MAP[420] = "minecraft:lead";
        ItemIntIDToString.ID_MAP[421] = "minecraft:name_tag";
        ItemIntIDToString.ID_MAP[422] = "minecraft:command_block_minecart";
        ItemIntIDToString.ID_MAP[2256] = "minecraft:record_13";
        ItemIntIDToString.ID_MAP[2257] = "minecraft:record_cat";
        ItemIntIDToString.ID_MAP[2258] = "minecraft:record_blocks";
        ItemIntIDToString.ID_MAP[2259] = "minecraft:record_chirp";
        ItemIntIDToString.ID_MAP[2260] = "minecraft:record_far";
        ItemIntIDToString.ID_MAP[2261] = "minecraft:record_mall";
        ItemIntIDToString.ID_MAP[2262] = "minecraft:record_mellohi";
        ItemIntIDToString.ID_MAP[2263] = "minecraft:record_stal";
        ItemIntIDToString.ID_MAP[2264] = "minecraft:record_strad";
        ItemIntIDToString.ID_MAP[2265] = "minecraft:record_ward";
        ItemIntIDToString.ID_MAP[2266] = "minecraft:record_11";
        ItemIntIDToString.ID_MAP[2267] = "minecraft:record_wait";
    }
}
