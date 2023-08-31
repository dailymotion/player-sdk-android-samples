package com.dailymotion.sample.player.sdk.screenselection.adapter

import com.dailymotion.sample.player.sdk.screenselection.adapter.ScreenSelectionItem

data class SectionText(override val text: String): ScreenSelectionItem {
    override fun displayText(): String {
        return text
    }
}