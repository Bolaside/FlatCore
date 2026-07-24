package dev.thornium.flatcore.common.data.model

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import dev.thornium.flatcore.registry.FTMaterialIconSet
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class FTMaterialModelProvider(
    private val output: PackOutput,
    private val existingFileHelper: ExistingFileHelper,
) : DataProvider {

    override fun run(cache: CachedOutput): CompletableFuture<*> {
        val futures = mutableListOf<CompletableFuture<*>>()

        for (iconSet in FTMaterialIconSet.ALL) {
            for (typeName in ITEM_TYPES) {
                futures.add(generateItemModel(cache, iconSet, typeName))
            }
            futures.add(generateBlockModel(cache, iconSet, "block", "solid"))
            futures.add(generateBlockModel(cache, iconSet, "frame_gt", "cutout"))
        }

        return CompletableFuture.allOf(*futures.toTypedArray())
    }

    private fun generateItemModel(
        cache: CachedOutput,
        iconSet: MaterialIconSet,
        typeName: String,
    ): CompletableFuture<*> {
        val parentSet = resolveParentModel(iconSet, typeName) ?: return completedFuture()
        val modelLoc = GTCEu.id("item/material_sets/${iconSet.name}/$typeName")
        val parentLoc = GTCEu.id("item/material_sets/${parentSet.name}/$typeName")

        val model = JsonObject().apply {
            addProperty("parent", parentLoc.toString())
            add("textures", JsonObject().apply {
                addProperty("layer0", "gtceu:item/material_sets/${iconSet.name}/$typeName")
            })
        }

        existingFileHelper.trackGenerated(modelLoc, MODEL_TYPE)
        return writeJson(cache, modelLoc, model)
    }

    private fun generateBlockModel(
        cache: CachedOutput,
        iconSet: MaterialIconSet,
        blockType: String,
        renderType: String,
    ): CompletableFuture<*> {
        val modelLoc = GTCEu.id("block/material_sets/${iconSet.name}/$blockType")
        val texturePath = "gtceu:block/material_sets/${iconSet.name}/$blockType"

        val model = JsonObject().apply {
            addProperty("parent", "block/block")
            addProperty("loader", "forge:composite")
            add("textures", JsonObject().apply {
                addProperty("particle", texturePath)
            })
            add("children", JsonObject().apply {
                add("base", JsonObject().apply {
                    addProperty("parent", "gtceu:block/cube/tinted/all_0")
                    addProperty("render_type", renderType)
                    add("textures", JsonObject().apply {
                        addProperty("all", texturePath)
                    })
                })
            })
            add("item_render_order", JsonParser.parseString("[\"base\"]"))
        }

        existingFileHelper.trackGenerated(modelLoc, MODEL_TYPE)
        return writeJson(cache, modelLoc, model)
    }

    private fun resolveParentModel(
        iconSet: MaterialIconSet,
        typeName: String,
    ): MaterialIconSet? {
        var current = iconSet.parentIconset ?: return null
        while (true) {
            val loc = GTCEu.id("item/material_sets/${current.name}/$typeName")
            if (existingFileHelper.exists(loc, MODEL_TYPE)) return current
            current = current.parentIconset ?: break
        }
        return null
    }

    private fun writeJson(
        cache: CachedOutput,
        location: ResourceLocation,
        json: JsonObject,
    ): CompletableFuture<*> {
        val path = output.outputFolder
            .resolve("assets/${location.namespace}/${location.path}.json")
        return DataProvider.saveStable(cache, json, path)
    }

    private fun completedFuture() = CompletableFuture.completedFuture<Any>(null)

    override fun getName() = "FlatCore Material Models"

    companion object {
        private val MODEL_TYPE = ExistingFileHelper.ResourceType(
            PackType.CLIENT_RESOURCES, ".json", "models"
        )

        private val ITEM_TYPES = listOf(
            "bolt",
            "dust",
            "dust_small",
            "dust_tiny",
            "foil",
            "gear",
            "gear_small",
            "ingot",
            "nugget",
            "plate",
            "plate_dense",
            "plate_double",
            "ring",
            "rod",
            "rod_long",
            "rotor",
            "round",
            "screw",
            "spring",
            "spring_small",
            "wire_fine",
        )
    }
}
