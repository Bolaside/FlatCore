package dev.thornium.flatcore.api

import com.gregtechceu.gtceu.api.GTValues.L
import com.gregtechceu.gtceu.common.data.GTMaterials.*

@Suppress("unused")
object FTValues {
    /** List of ME cable ID prefixes. */
    val ME_CABLE_TYPES = arrayOf("glass", "covered", "covered_dense", "smart", "smart_dense")

    /** Ordered list of cable insulation, from worst to best. */
    val CABLE_INSULATION = arrayOf(
        Rubber.getFluid(L),
        SiliconeRubber.getFluid(L / 2),
        StyreneButadieneRubber.getFluid(L / 4),
    )
}
