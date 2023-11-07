package com.dailymotion.sample.player.sdk.screenselection.adapter

data class ItemText(override val text: String): ScreenSelectionItem {
    override fun displayText(): String {
        return text
    }
}