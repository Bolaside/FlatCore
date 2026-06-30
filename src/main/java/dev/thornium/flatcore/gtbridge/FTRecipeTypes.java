package dev.thornium.flatcore.gtbridge;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import dev.thornium.flatcore.api.gui.FTTextures;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ComposterBlock;

import static com.gregtechceu.gtceu.api.GTValues.ULV;
import static com.gregtechceu.gtceu.api.GTValues.VHA;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;

public class FTRecipeTypes {
    public static final GTRecipeType GREENHOUSE_RECIPES = register("greenhouse", MULTIBLOCK)
            .setMaxIOSize(3, 6, 1, 0)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.ARROW_INPUT_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE);

    public static final GTRecipeType STONE_OREIFIER_RECIPES = register("stone_oreifier", MULTIBLOCK)
            .setMaxIOSize(2, 6, 1, 0)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.ARROW_INPUT_OVERLAY)
            .setProgressBar(FTTextures.PROGRESS_BAR_STONE_OREIFIER, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE);

    public static final GTRecipeType COMPOSTER_RECIPES = register("electric_composter", ELECTRIC)
            .setMaxIOSize(1, 1, 0, 0)
            .prepareBuilder(recipeBuilder -> recipeBuilder.EUt(VHA[ULV]).duration(20))
            .onRecipeBuild((recipeBuilder, provider) -> {
                // There can only be one.
                SizedIngredient ingredient = (SizedIngredient) recipeBuilder.input
                        .get(ItemRecipeCapability.CAP)
                        .get(0)
                        .getContent();
                Item inputItem = ingredient.getItems()[0].getItem();

                int chance = (int) (ComposterBlock.COMPOSTABLES.getFloat(inputItem) * 10000);
                recipeBuilder.chancedOutput(Items.BONE_MEAL.getDefaultInstance(), chance, 0);
            })
            .setProgressBar(GuiTextures.PROGRESS_BAR_RECYCLER, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MORTAR_TOOL);

    public static void init() {}
}
