package dev.thornium.flatcore.api;

import net.minecraftforge.fluids.FluidStack;

import static com.gregtechceu.gtceu.api.GTValues.L;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

@SuppressWarnings("unused")
public class FTValues {
  /**
   * List of ME cable ID prefixes.
   */
  public static final String[] ME_CABLE_TYPES = { "glass", "covered", "covered_dense", "smart", "smart_dense" };

  /**
   * Ordered list of cable insulation, from worst to best.
   */
  public static final FluidStack[] CABLE_INSULATION = {
    Rubber.getFluid(L),
    SiliconeRubber.getFluid(L / 2),
    StyreneButadieneRubber.getFluid(L / 4)
  };
}
