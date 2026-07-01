package dev.thornium.flatcore.registry

import dev.thornium.flatcore.common.data.FTBlocks
import dev.thornium.flatcore.common.data.FTCreativeTabs
import dev.thornium.flatcore.common.data.FTItems

/**
 * Forces FTCreativeTabs/FTBlocks/FTItems to class-load (and so register their
 * Registrate entries) during mod construction, before any Forge event fires.
 */
object StaticContentInitializer {
    fun initialize() {
        FTCreativeTabs.init()
        FTBlocks.init()
        FTItems.init()
    }
}
