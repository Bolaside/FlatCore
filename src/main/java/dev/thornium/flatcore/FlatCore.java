package dev.thornium.flatcore;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.api.sound.SoundEntry;
import dev.thornium.flatcore.common.data.FTBlocks;
import dev.thornium.flatcore.common.data.FTCreativeTabs;
import dev.thornium.flatcore.common.data.FTItems;
import dev.thornium.flatcore.gtbridge.FTRecipeTypes;
import dev.thornium.flatcore.registry.FTMaterialIconSet;
import dev.thornium.flatcore.registry.FTMaterials;
import dev.thornium.flatcore.registry.machine.FTMachines;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FlatCore.MOD_ID)
@SuppressWarnings("removal")
public class FlatCore {
    public static final String MOD_ID = "flatcore";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String NAME = "FlatCore";
    public static GTRegistrate REGISTRATE = GTRegistrate.create(FlatCore.MOD_ID);

    public FlatCore() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        modEventBus.addListener(this::addMaterialRegistries);
        modEventBus.addListener(this::addMaterials);
        modEventBus.addListener(this::modifyMaterials);

        FTCreativeTabs.init();
        FTBlocks.init();
        FTItems.init();

        modEventBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        modEventBus.addGenericListener(MachineDefinition.class, this::registerMachines);
        modEventBus.addGenericListener(SoundEntry.class, this::registerSounds);

        // Most other events are fired on Forge's bus.
        // If we want to use annotations to register event listeners,
        // we need to register our object like this!
        MinecraftForge.EVENT_BUS.register(this);

        REGISTRATE.registerRegistrate();
    }

    /**
     * Create a ResourceLocation in the format "modid:path"
     *
     * @param path
     * @return ResourceLocation with the namespace of your mod
     */
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    /**
     * Create a material manager for your mod using GT's API.
     * You MUST have this if you have custom materials.
     * Remember to register them not to GT's namespace, but your own.
     *
     * @param event
     */
    private void addMaterialRegistries(MaterialRegistryEvent event) {
        GTCEuAPI.materialManager.createRegistry(FlatCore.MOD_ID);
    }

    /**
     * You will also need this for registering custom materials
     * Call init() from your Material class(es) here
     *
     * @param event
     */
    private void addMaterials(MaterialEvent event) {
        FTMaterialIconSet.init();
        FTMaterials.init();
    }

    /**
     * (Optional) Used to modify pre-existing materials from GregTech
     *
     * @param event
     */
    private void modifyMaterials(PostMaterialEvent event) {
        FTMaterials.modify();
    }

    /**
     * Used to register your own new RecipeTypes.
     * Call init() from your RecipeType class(es) here
     *
     * @param event
     */
    private void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        FTRecipeTypes.init();
    }

    /**
     * Used to register your own new machines.
     * Call init() from your Machine class(es) here
     *
     * @param event
     */
    private void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        FTMachines.init();
    }

    /**
     * Used to register your own new sounds
     * Call init from your Sound class(es) here
     *
     * @param event
     */
    public void registerSounds(GTCEuAPI.RegisterEvent<ResourceLocation, SoundEntry> event) {
        // CustomSounds.init();
    }
}
