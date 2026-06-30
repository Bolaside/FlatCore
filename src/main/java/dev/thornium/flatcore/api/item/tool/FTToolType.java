package dev.thornium.flatcore.api.item.tool;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.item.tool.ToolHelper;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.common.data.item.GTToolActions;
import com.gregtechceu.gtceu.common.item.tool.behavior.BlockRotatingBehavior;
import com.gregtechceu.gtceu.common.item.tool.behavior.ToolModeSwitchBehavior;
import com.gregtechceu.gtceu.common.item.tool.behavior.TorchPlaceBehavior;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import dev.thornium.flatcore.api.data.tag.FTTagPrefix;
import net.minecraft.Util;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.HashSet;
import java.util.Set;

public class FTToolType {
    private static final Set<ToolAction> MULTI_TOOL_ACTIONS = Util.make(new HashSet<>(), set -> {
        set.add(ToolActions.PICKAXE_DIG);
        set.add(ToolActions.AXE_DIG);
        set.add(ToolActions.SWORD_DIG);
        set.add(GTToolActions.SAW_DIG);

        set.addAll(GTToolActions.DEFAULT_WRENCH_ACTIONS);
        set.addAll(GTToolActions.DEFAULT_WIRE_CUTTER_ACTIONS);
        set.addAll(GTToolActions.DEFAULT_DRILL_ACTIONS);
    });

    public static final GTToolType MULTI_TOOL = GTToolType.builder("multi_tool")
            .idFormat("%s_multi_tool")
            .toolTag(GTToolType.ToolItemTagType.CRAFTING, CustomTags.CRAFTING_WRENCHES)
            .toolTag(GTToolType.ToolItemTagType.CRAFTING, CustomTags.CRAFTING_SCREWDRIVERS)
            .toolTag(GTToolType.ToolItemTagType.CRAFTING, CustomTags.CRAFTING_WIRE_CUTTERS)
            .toolTag(GTToolType.ToolItemTagType.MATCH, CustomTags.WRENCHES)
            .toolTag(GTToolType.ToolItemTagType.MATCH, CustomTags.WRENCH)
            .toolTag(GTToolType.ToolItemTagType.MATCH, CustomTags.SCREWDRIVERS)
            .toolTag(GTToolType.ToolItemTagType.MATCH, CustomTags.WIRE_CUTTERS)
            .toolTag(GTToolType.ToolItemTagType.MATCH, CustomTags.PLUNGERS)
            .toolTag(GTToolType.ToolItemTagType.MATCH, CustomTags.DRILLS)
            .toolTag(GTToolType.ToolItemTagType.MATCH, ItemTags.AXES)
            .toolTag(GTToolType.ToolItemTagType.MATCH, CustomTags.CHAINSAWS)
            .toolTag(ItemTags.PICKAXES)
            .toolTag(ItemTags.SHOVELS)
            .toolTag(ItemTags.HOES)
            .toolTag(ItemTags.CLUSTER_MAX_HARVESTABLES)
            .harvestTag(CustomTags.MINEABLE_WITH_WRENCH)
            .harvestTag(CustomTags.MINEABLE_WITH_WIRE_CUTTER)
            .harvestTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .harvestTag(BlockTags.MINEABLE_WITH_SHOVEL)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .harvestTag(BlockTags.MINEABLE_WITH_AXE)
            .harvestTag(BlockTags.MINEABLE_WITH_HOE)
            .harvestTag(BlockTags.SWORD_EFFICIENT)
            .toolStats(b -> b.blockBreaking()
                    .crafting()
                    .sneakBypassUse()
                    .attackDamage(1.0F)
                    .attackSpeed(-2.8F)
                    .behaviors(TorchPlaceBehavior.INSTANCE, BlockRotatingBehavior.INSTANCE, ToolModeSwitchBehavior.INSTANCE)
                    .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_HV))
            .sound(GTSoundEntries.WRENCH_TOOL, true)
            .electric(GTValues.HV)
            .defaultActions(MULTI_TOOL_ACTIONS)
            .materialAmount(FTTagPrefix.toolHeadMultiTool.materialAmount())
            .build();
}
