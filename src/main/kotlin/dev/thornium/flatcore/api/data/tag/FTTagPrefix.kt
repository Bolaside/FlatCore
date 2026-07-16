package dev.thornium.flatcore.api.data.tag

import com.gregtechceu.gtceu.api.GTValues.M
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.Conditions.hasToolProperty
import com.gregtechceu.gtceu.common.data.GTMaterialItems
import dev.thornium.flatcore.api.Initialized
import dev.thornium.flatcore.api.chemical.material.info.FTMaterialIconType
import dev.thornium.flatcore.api.item.tool.FTToolType

object FTTagPrefix : Initialized {
    val toolHeadMultiTool: TagPrefix = TagPrefix("multiToolHead")
        .itemTable { GTMaterialItems.MATERIAL_ITEMS }
        .langValue("%s Multi Tool Head")
        .materialAmount(M * 9 * 3)
        .maxStackSize(2)
        .materialIconType(FTMaterialIconType.toolHeadMultiTool)
        .unificationEnabled(true)
        .enableRecycling()
        .generateItem(true)
        .generationCondition(
            hasToolProperty
                .and { mat -> mat.hasFlag(MaterialFlags.GENERATE_DENSE) }
                .and { mat -> mat.getProperty(PropertyKey.TOOL).hasType(FTToolType.MULTI_TOOL) },
        )
}
