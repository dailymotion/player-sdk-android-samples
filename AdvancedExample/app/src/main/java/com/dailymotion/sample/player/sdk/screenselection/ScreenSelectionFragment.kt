package com.dailymotion.sample.player.sdk.screenselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.sample.player.sdk.BuildConfig
import com.dailymotion.sample.player.sdk.MainActivity
import com.dailymotion.sample.player.sdk.R
import com.dailymotion.sample.player.sdk.advertising.AdvertisingScreenFragment
import com.dailymotion.sample.player.sdk.api.PlayerMethodsFragment
import com.dailymotion.sample.player.sdk.basic.BasicEmbedFragment
import com.dailymotion.sample.player.sdk.eventsstate.PlayerEventsStateFragment
import com.dailymotion.sample.player.sdk.live.LivePlayerFragment
import com.dailymotion.sample.player.sdk.screenselection.adapter.ItemText
import com.dailymotion.sample.player.sdk.screenselection.adapter.ScreenSelectionAdapter
import com.dailymotion.sample.player.sdk.screenselection.adapter.ScreenSelectionItem
import com.dailymotion.sample.player.sdk.screenselection.adapter.SectionText
import com.dailymotion.sample.player.sdk.vertical.VerticalPlayerFragment

class ScreenSelectionFragment: Fragment() {

    private lateinit var screenList: List<ScreenSelectionItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeScreenList()
        return inflater.inflate(R.layout.fragment_screen_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()
        initializeRecyclerView()
        initializeVersionText()
    }

    private fun initializeScreenList() {
        this.screenList = listOf(

            SectionText(text = resources.getString(R.string.integration_types)),
            ItemText(text = resources.getString(R.string.basic_embed_title)),
            ItemText(text = resources.getString(R.string.advertising_screen_title)),
            ItemText(text = resources.getString(R.string.vertical_screen_title)),
            ItemText(text = resources.getString(R.string.live_player_title)),

            SectionText(text = resources.getString(R.string.player_api)),
            ItemText(text = resources.getString(R.string.player_events_state_title)),
            ItemText(text = resources.getString(R.string.player_methods_title)),
        )
    }

    private fun initializeRecyclerView() {

        val v = view ?: return
        val recyclerView = v.findViewById<RecyclerView>(R.id.screenEntryRecyclerView)
        recyclerView.adapter = ScreenSelectionAdapter(screenList,
            object : ScreenSelectionAdapter.ItemClickListener {

                override fun onItemClicked(item: ScreenSelectionItem) {
                    when (item.text) {
                        resources.getString(R.string.basic_embed_title) -> {
                            this@ScreenSelectionFragment.parentFragmentManager.commit {
                                setReorderingAllowed(true)
                                addToBackStack(null)
                                replace<BasicEmbedFragment>(R.id.fragment_container_view)
                            }
                        }
                        resources.getString(R.string.advertising_screen_title) -> {
                            this@ScreenSelectionFragment.parentFragmentManager.commit {
                                setReorderingAllowed(true)
                                addToBackStack(null)
                                replace<AdvertisingScreenFragment>(R.id.fragment_container_view)
                            }
                        }
                        resources.getString(R.string.vertical_screen_title) -> {
                            this@ScreenSelectionFragment.parentFragmentManager.commit {
                                setReorderingAllowed(true)
                                addToBackStack(null)
                                replace<VerticalPlayerFragment>(R.id.fragment_container_view)
                            }
                        }
                        resources.getString(R.string.live_player_title) -> {
                            this@ScreenSelectionFragment.parentFragmentManager.commit {
                                setReorderingAllowed(true)
                                addToBackStack(null)
                                replace<LivePlayerFragment>(R.id.fragment_container_view)
                            }
                        }
                        resources.getString(R.string.player_events_state_title) -> {
                            this@ScreenSelectionFragment.parentFragmentManager.commit {
                                setReorderingAllowed(true)
                                addToBackStack(null)
                                replace<PlayerEventsStateFragment>(R.id.fragment_container_view)
                            }
                        }
                        resources.getString(R.string.player_methods_title) -> {
                            this@ScreenSelectionFragment.parentFragmentManager.commit {
                                setReorderingAllowed(true)
                                addToBackStack(null)
                                replace<PlayerMethodsFragment>(R.id.fragment_container_view)
                            }
                        }
                    }
                }
            })
    }

    private fun initializeToolbar() {
        (activity as? MainActivity?)?.let { a ->
            a.setTitle(R.string.screen_selection_title)
            a.showToolbarBackButton(showBackButton = false)
        }
    }

    private fun initializeVersionText() {
        view?.let { v ->

            val appName = v.resources.getString(R.string.app_name)
            val appVersion = BuildConfig.VERSION_NAME
            val sdkName = "SDK"

            /*
            * No need to separate version between DM sdk and DM ads because it need to be in sync to
            * properly work together
            * */
            val sdkVersion = Dailymotion.version()
            val versionText = "$appName: $appVersion\n$sdkName version: $sdkVersion"

            v.findViewById<TextView>(R.id.appPropertiesTextView).text = versionText
        }
    }
}