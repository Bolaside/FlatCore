package dev.thornium.flatcore.common.machine

import com.google.common.base.Supplier
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability
import com.gregtechceu.gtceu.api.gui.GuiTextures
import com.gregtechceu.gtceu.api.gui.editor.EditableMachineUI
import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfigurator
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfiguratorButton
import com.gregtechceu.gtceu.api.item.tool.GTToolType
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.TickableSubscription
import com.gregtechceu.gtceu.api.machine.feature.IAutoOutputBoth
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine
import com.gregtechceu.gtceu.api.machine.feature.IHasCircuitSlot
import com.gregtechceu.gtceu.api.machine.feature.ICleanroomProvider
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife
import com.gregtechceu.gtceu.api.machine.feature.IMufflableMachine
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine
import com.gregtechceu.gtceu.api.machine.fancyconfigurator.CircuitFancyConfigurator
import com.gregtechceu.gtceu.api.machine.trait.IRecipeHandlerTrait
import com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic
import com.gregtechceu.gtceu.api.recipe.GTRecipeType
import com.gregtechceu.gtceu.api.recipe.RecipeCondition
import com.gregtechceu.gtceu.api.recipe.ui.GTRecipeTypeUI
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour
import com.gregtechceu.gtceu.config.ConfigHolder
import com.gregtechceu.gtceu.utils.GTTransferUtils
import com.google.common.collect.Table
import com.google.common.collect.Tables
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture
import com.lowdragmc.lowdraglib.gui.util.ClickData
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup
import com.lowdragmc.lowdraglib.syncdata.ISubscription
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import net.minecraft.Util
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.TickTask
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import java.util.EnumMap
import java.util.LinkedHashMap
import java.util.function.BiConsumer
import java.util.function.BiFunction
import java.util.function.BooleanSupplier
import javax.annotation.ParametersAreNonnullByDefault

// I don't know how to format this
// no, I'm not putting a blank line
/**
 * A singleblock workable machine with no tier and no energy container: recipes run on
 * item/fluid I/O alone, EU is neither accepted nor displayed (Jade shows no energy bar).
 */
@ParametersAreNonnullByDefault
class TierlessWorkableMachine(holder: IMachineBlockEntity, tankCapacity: Int) : MetaMachine(holder), IRecipeLogicMachine, IMachineLife, IMufflableMachine, IAutoOutputBoth, IHasCircuitSlot, IFancyUIMachine {
    @field:Persisted
    @field:DescSynced
    private var recipeLogic: RecipeLogic

    private val recipeTypes: Array<GTRecipeType> = definition.recipeTypes

    @field:Persisted
    private var activeRecipeType: Int = 0

    @field:Persisted
    private var importItems: NotifiableItemStackHandler

    @field:Persisted
    private var exportItems: NotifiableItemStackHandler

    @field:Persisted
    private var importFluids: NotifiableFluidTank

    @field:Persisted
    private var exportFluids: NotifiableFluidTank

    private val capabilitiesProxy: MutableMap<IO, MutableList<RecipeHandlerList>> = EnumMap(IO::class.java)

    private val capabilitiesFlat: MutableMap<IO, MutableMap<RecipeCapability<*>, MutableList<IRecipeHandler<*>>>> =
        EnumMap(IO::class.java)

    @field:Persisted
    @field:DescSynced
    private var muffled = false
    private var previouslyMuffled = true

    private var cleanroom: ICleanroomProvider? = null

    @field:Persisted
    @field:DescSynced
    @field:RequireRerender
    private var outputFacingItems: Direction

    @field:Persisted
    @field:DescSynced
    @field:RequireRerender
    private var outputFacingFluids: Direction

    @field:Persisted
    @field:DescSynced
    @field:RequireRerender
    private var autoOutputItems: Boolean = false

    @field:Persisted
    @field:DescSynced
    @field:RequireRerender
    private var autoOutputFluids: Boolean = false

    @field:Persisted
    private var allowInputFromOutputSideItems: Boolean = false

    @field:Persisted
    private var allowInputFromOutputSideFluids: Boolean = false

    @field:Persisted
    private var circuitInventory: NotifiableItemStackHandler

    private var autoOutputSubs: TickableSubscription? = null
    private var exportItemSubs: ISubscription? = null
    private var exportFluidSubs: ISubscription? = null
    private val traitSubscriptions = ArrayList<ISubscription>()

    init {
        importItems = createImportItemHandler()
        exportItems = createExportItemHandler()
        importFluids = createImportFluidHandler(tankCapacity)
        exportFluids = createExportFluidHandler(tankCapacity)
        circuitInventory = createCircuitItemHandler()
        outputFacingItems = if (hasFrontFacing()) frontFacing.opposite else Direction.UP
        outputFacingFluids = outputFacingItems
        recipeLogic = createRecipeLogic()
    }

    override fun getFieldHolder(): ManagedFieldHolder = MANAGED_FIELD_HOLDER

    // disambiguates the IUIMachine default from MetaMachine's own implementation
    @Suppress("EmptyMethod")
    override fun isRemote(): Boolean = super<MetaMachine>.isRemote()

    private fun createImportItemHandler(): NotifiableItemStackHandler =
        NotifiableItemStackHandler(this, getRecipeType().getMaxInputs(ItemRecipeCapability.CAP), IO.IN)

    private fun createExportItemHandler(): NotifiableItemStackHandler =
        NotifiableItemStackHandler(this, getRecipeType().getMaxOutputs(ItemRecipeCapability.CAP), IO.OUT)

    private fun createImportFluidHandler(tankCapacity: Int): NotifiableFluidTank =
        NotifiableFluidTank(this, getRecipeType().getMaxInputs(FluidRecipeCapability.CAP), tankCapacity, IO.IN)

    private fun createExportFluidHandler(tankCapacity: Int): NotifiableFluidTank =
        NotifiableFluidTank(this, getRecipeType().getMaxOutputs(FluidRecipeCapability.CAP), tankCapacity, IO.OUT)

    private fun createCircuitItemHandler(): NotifiableItemStackHandler =
        NotifiableItemStackHandler(this, 1, IO.IN, IO.NONE)
            .setFilter(IntCircuitBehaviour::isIntegratedCircuit)

    private fun createRecipeLogic(): RecipeLogic = RecipeLogic(this)

    override fun onLoad() {
        super.onLoad()
        val ioTraits = EnumMap<IO, MutableList<IRecipeHandler<*>>>(IO::class.java)

        for (trait in traits) {
            if (trait is IRecipeHandlerTrait<*>) {
                ioTraits.computeIfAbsent(trait.handlerIO) { ArrayList() }.add(trait)
            }
        }

        for ((io, handlers) in ioTraits) {
            val handlerList = RecipeHandlerList.of(io, handlers)
            addHandlerList(handlerList)
            traitSubscriptions.add(handlerList.subscribe { recipeLogic.updateTickSubscription() })
        }

        if (!isRemote) {
            (level as? ServerLevel)?.server?.tell(TickTask(0, ::updateAutoOutputSubscription))
            exportItemSubs = exportItems.addChangedListener(::updateAutoOutputSubscription)
            exportFluidSubs = exportFluids.addChangedListener(::updateAutoOutputSubscription)
        }
    }

    override fun onUnload() {
        super.onUnload()
        exportItemSubs?.unsubscribe()
        exportItemSubs = null
        exportFluidSubs?.unsubscribe()
        exportFluidSubs = null
        traitSubscriptions.forEach(ISubscription::unsubscribe)
        traitSubscriptions.clear()
        capabilitiesProxy.clear()
        capabilitiesFlat.clear()
        recipeLogic.inValid()
    }

    override fun clientTick() {
        super.clientTick()
        if (previouslyMuffled != isMuffled()) {
            previouslyMuffled = isMuffled()
            recipeLogic.updateSound()
        }
    }

    override fun hasAutoOutputFluid(): Boolean = exportFluids.tanks > 0

    override fun hasAutoOutputItem(): Boolean = exportItems.slots > 0

    override fun getOutputFacingFluids(): Direction? = outputFacingFluids.takeIf { hasAutoOutputFluid() }

    override fun getOutputFacingItems(): Direction? = outputFacingItems.takeIf { hasAutoOutputItem() }

    override fun isAutoOutputItems(): Boolean = autoOutputItems

    override fun setAutoOutputItems(allow: Boolean) {
        if (hasAutoOutputItem()) {
            autoOutputItems = allow
            updateAutoOutputSubscription()
        }
    }

    override fun isAutoOutputFluids(): Boolean = autoOutputFluids

    override fun setAutoOutputFluids(allow: Boolean) {
        if (hasAutoOutputFluid()) {
            autoOutputFluids = allow
            updateAutoOutputSubscription()
        }
    }

    override fun isAllowInputFromOutputSideItems(): Boolean = allowInputFromOutputSideItems

    override fun setAllowInputFromOutputSideItems(allow: Boolean) {
        allowInputFromOutputSideItems = allow
    }

    override fun isAllowInputFromOutputSideFluids(): Boolean = allowInputFromOutputSideFluids

    override fun setAllowInputFromOutputSideFluids(allow: Boolean) {
        allowInputFromOutputSideFluids = allow
    }

    override fun setOutputFacingFluids(outputFacing: Direction?) {
        if (hasAutoOutputFluid() && outputFacing != null) {
            outputFacingFluids = outputFacing
            updateAutoOutputSubscription()
        }
    }

    override fun setOutputFacingItems(outputFacing: Direction?) {
        if (hasAutoOutputItem() && outputFacing != null) {
            outputFacingItems = outputFacing
            updateAutoOutputSubscription()
        }
    }

    override fun onNeighborChanged(block: Block, fromPos: BlockPos, isMoving: Boolean) {
        super.onNeighborChanged(block, fromPos, isMoving)
        updateAutoOutputSubscription()
    }

    private fun updateAutoOutputSubscription() {
        val itemFacing = outputFacingItems.takeIf { hasAutoOutputItem() }
        val fluidFacing = outputFacingFluids.takeIf { hasAutoOutputFluid() }
        val itemReady = isAutoOutputItems && itemFacing != null && !exportItems.isEmpty &&
            GTTransferUtils.hasAdjacentItemHandler(level, pos, itemFacing)
        val fluidReady = isAutoOutputFluids && fluidFacing != null && !exportFluids.isEmpty &&
            GTTransferUtils.hasAdjacentFluidHandler(level, pos, fluidFacing)

        if (itemReady || fluidReady) {
            autoOutputSubs = subscribeServerTick(autoOutputSubs, ::autoOutput)
        } else {
            autoOutputSubs?.unsubscribe()
            autoOutputSubs = null
        }
    }

    private fun autoOutput() {
        if (offsetTimer % 5 == 0L) {
            if (isAutoOutputItems && hasAutoOutputItem()) {
                exportItems.exportToNearby(outputFacingItems)
            }
            if (isAutoOutputFluids && hasAutoOutputFluid()) {
                exportFluids.exportToNearby(outputFacingFluids)
            }
        }
        updateAutoOutputSubscription()
    }

    override fun isFacingValid(facing: Direction): Boolean =
        facing != getOutputFacingItems() && facing != getOutputFacingFluids() && super.isFacingValid(facing)

    override fun onMachineRemoved() {
        super.onMachineRemoved()
        clearInventory(importItems.storage)
        clearInventory(exportItems.storage)

        if (!ConfigHolder.INSTANCE.machines.ghostCircuit) {
            clearInventory(circuitInventory.storage)
        }
    }

    override fun getRecipeLogic(): RecipeLogic = recipeLogic

    override fun getRecipeTypes(): Array<GTRecipeType> = recipeTypes

    override fun getRecipeType(): GTRecipeType = recipeTypes[activeRecipeType]

    override fun getActiveRecipeType(): Int = activeRecipeType

    override fun setActiveRecipeType(type: Int) {
        activeRecipeType = type
    }

    override fun getCleanroom(): ICleanroomProvider? = cleanroom

    override fun setCleanroom(provider: ICleanroomProvider?) {
        cleanroom = provider
    }

    override fun isMuffled(): Boolean = muffled

    override fun setMuffled(isMuffled: Boolean) {
        muffled = isMuffled
    }

    override fun getCircuitInventory(): NotifiableItemStackHandler = circuitInventory

    // property syntax would write the backing field directly, bypassing the
    // guarded setAutoOutput{Items,Fluids} overrides
    @Suppress("UsePropertyAccessSyntax")
    override fun attachConfigurators(configuratorPanel: ConfiguratorPanel) {
        super.attachConfigurators(configuratorPanel)

        if (hasAutoOutputFluid()) {
            configuratorPanel.attachConfigurators(
                createAutoOutputConfigurator(
                    GuiTextures.IO_CONFIG_FLUID_MODES_BUTTON,
                    "gtceu.gui.fluid_auto_output",
                    { isAutoOutputFluids() },
                ) { _, nextState -> setAutoOutputFluids(nextState) },
            )
        }
        if (hasAutoOutputItem()) {
            configuratorPanel.attachConfigurators(
                createAutoOutputConfigurator(
                    GuiTextures.IO_CONFIG_ITEM_MODES_BUTTON,
                    "gtceu.gui.item_auto_output",
                    { isAutoOutputItems() },
                ) { _, nextState -> setAutoOutputItems(nextState) },
            )
        }
        if (isCircuitSlotEnabled) {
            configuratorPanel.attachConfigurators(CircuitFancyConfigurator(circuitInventory.storage))
        }
    }

    private fun createAutoOutputConfigurator(
        modesButtonTexture: ResourceTexture,
        tooltipBaseLangKey: String,
        stateSupplier: BooleanSupplier,
        onToggle: BiConsumer<ClickData, Boolean>,
    ): IFancyConfigurator {
        val toggle = IFancyConfiguratorButton.Toggle(
            GuiTextureGroup(
                GuiTextures.TOGGLE_BUTTON_BACK.getSubTexture(0f, 0f, 1f, 0.5f),
                modesButtonTexture.getSubTexture(0f, 1 / 3f, 1f, 1 / 3f),
            ),
            GuiTextureGroup(
                GuiTextures.TOGGLE_BUTTON_BACK.getSubTexture(0f, 0.5f, 1f, 0.5f),
                modesButtonTexture.getSubTexture(0f, 2 / 3f, 1f, 1 / 3f),
            ),
            stateSupplier,
            onToggle,
        )
        toggle.setTooltipsSupplier { enabled ->
            listOf(Component.translatable("$tooltipBaseLangKey.${if (enabled) "enabled" else "disabled"}"))
        }
        return toggle
    }

    override fun sideTips(
        player: Player,
        pos: BlockPos,
        state: BlockState,
        toolTypes: Set<GTToolType>,
        side: Direction,
    ): ResourceTexture? = when {
        GTToolType.WRENCH in toolTypes && !player.isShiftKeyDown && (!hasFrontFacing() || side != frontFacing) ->
            GuiTextures.TOOL_IO_FACING_ROTATION

        GTToolType.SCREWDRIVER in toolTypes && (side == getOutputFacingItems() || side == getOutputFacingFluids()) ->
            GuiTextures.TOOL_ALLOW_INPUT

        else -> super.sideTips(player, pos, state, toolTypes, side)
    }

    override fun getCapabilitiesProxy(): Map<IO, List<RecipeHandlerList>> = capabilitiesProxy

    override fun getCapabilitiesFlat(): Map<IO, Map<RecipeCapability<*>, List<IRecipeHandler<*>>>> = capabilitiesFlat

    companion object {
        @JvmField
        val MANAGED_FIELD_HOLDER = ManagedFieldHolder(
            TierlessWorkableMachine::class.java,
            MetaMachine.MANAGED_FIELD_HOLDER,
        )

        @JvmField
        val EDITABLE_UI_CREATOR: BiFunction<ResourceLocation, GTRecipeType, EditableMachineUI> =
            Util.memoize { path: ResourceLocation, recipeType: GTRecipeType ->
                EditableMachineUI(
                    "simple",
                    path,
                    { recipeType.recipeUI.createEditableUITemplate(false, false).createDefault() },
                    { template, machine -> setupSimpleUI(template, machine) },
                )
            }

        private fun setupSimpleUI(template: WidgetGroup, machine: MetaMachine) {
            if (machine !is TierlessWorkableMachine) return

            val holder = GTRecipeTypeUI.RecipeHolder(
                { machine.recipeLogic.progressPercent },
                machine.createStorages(),
                CompoundTag(),
                emptyList(),
                false,
                false,
            )

            machine.getRecipeType().recipeUI
                .createEditableUITemplate(false, false)
                .setupUI(template, holder)
        }

        @Suppress("UnstableApiUsage")
        private fun TierlessWorkableMachine.createStorages(): Table<IO, RecipeCapability<*>, Any> =
            Tables.newCustomTable<IO, RecipeCapability<*>, Any>(
                EnumMap(IO::class.java),
                ::LinkedHashMap,
            ).also {
                it.put(IO.IN, ItemRecipeCapability.CAP, importItems.storage)
                it.put(IO.OUT, ItemRecipeCapability.CAP, exportItems.storage)
                it.put(IO.IN, FluidRecipeCapability.CAP, importFluids)
                it.put(IO.OUT, FluidRecipeCapability.CAP, exportFluids)
            }
    }
}
