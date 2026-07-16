package dev.thornium.flatcore.registry

import dev.thornium.flatcore.common.data.FTBlocks
import dev.thornium.flatcore.common.data.FTCreativeTabs
import dev.thornium.flatcore.common.data.FTItems

object StaticContentInitializer {
    fun initialize() {
        FTCreativeTabs.init()
        FTBlocks.init()
        FTItems.init()
    }
}
