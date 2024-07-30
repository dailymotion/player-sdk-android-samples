package com.dailymotion.sample.player.sdk.eventsstate

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.player.android.sdk.PlayerView
import com.dailymotion.player.android.sdk.listeners.AdListener
import com.dailymotion.player.android.sdk.listeners.PlayerListener
import com.dailymotion.player.android.sdk.listeners.VideoListener
import com.dailymotion.player.android.sdk.webview.error.PlayerError
import com.dailymotion.player.android.sdk.webview.events.PlayerEvent
import com.dailymotion.sample.player.sdk.MainActivity
import com.dailymotion.sample.player.sdk.R
import com.google.android.material.tabs.TabLayout


class PlayerEventsStateFragment: Fragment() {

    companion object {
        private const val TAG = "PlayerEventsState"
    }

    private var videoContainer: FrameLayout? = null
    private var tabLayout: TabLayout? = null
    private var eventsGroup: Group? = null
    private var playerEventsTextView: TextView? = null
    private var clearButton: Button? = null
    private var stateGroup: Group? = null
    private var playerStateTextView: TextView? = null
    private var playerView: PlayerView? = null
    private var refreshButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player_events_state, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoContainer = view.findViewById(R.id.videoContainer)
        tabLayout = view.findViewById(R.id.logTabLayout)

        eventsGroup = view.findViewById(R.id.eventsGroup)
        playerEventsTextView = view.findViewById(R.id.playerEventsTextView)
        playerEventsTextView?.movementMethod = ScrollingMovementMethod()
        clearButton = view.findViewById(R.id.clearButton)

        stateGroup = view.findViewById(R.id.stateGroup)
        playerStateTextView = view.findViewById(R.id.playerStateTextView)
        playerStateTextView?.movementMethod = ScrollingMovementMethod()
        refreshButton = view.findViewById(R.id.refreshButton)

        initializeToolbar()
        initializeLogsTextView()

        Log.d(TAG, "Creating dailymotion player")
        Dailymotion.createPlayer(context = view.context,
            playerId = "xix5w",
            videoId = "x8nlohg",
            playerSetupListener = object : Dailymotion.PlayerSetupListener {
                override fun onPlayerSetupSuccess(player: PlayerView) {
                    this@PlayerEventsStateFragment.playerView = player
                    writeToPlayerEventLog("onPlayerSetupSuccess")

                    Log.d(TAG, "Successfully created dailymotion player")
                    videoContainer?.addView(player, LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                    ))
                }

                override fun onPlayerSetupFailed(error: PlayerError) {
                    writeToPlayerEventLog("onPlayerSetupFailed")

                    Log.d(TAG, "Error while creating dailymotion player: ${error.message}")
                }
            },
            playerListener = object : PlayerListener {
                override fun onFullscreenRequested(playerDialogFragment: DialogFragment) {
                    super.onFullscreenRequested(playerDialogFragment)
                    writeToPlayerEventLog("onFullscreenRequested")

                    playerDialogFragment.show(this@PlayerEventsStateFragment.parentFragmentManager, "dmPlayerFullscreenFragment")
                }

                override fun onFullscreenExit(playerView: PlayerView) {
                    super.onFullscreenExit(playerView)
                    writeToPlayerEventLog("onFullscreenExit")
                }

                override fun onPlayerCriticalPathReady(playerView: PlayerView) {
                    super.onPlayerCriticalPathReady(playerView)
                    writeToPlayerEventLog("onPlayerCriticalPathReady")
                }

                override fun onPlayerEnd(playerView: PlayerView) {
                    super.onPlayerEnd(playerView)
                    writeToPlayerEventLog("onPlayerEnd")
                }

                override fun onPlayerError(playerView: PlayerView, error: PlayerError) {
                    super.onPlayerError(playerView, error)
                    writeToPlayerEventLog("onPlayerError")
                }

                override fun onPlayerPlaybackPermission(
                    playerView: PlayerView,
                    event: PlayerEvent.PlaybackPermission
                ) {
                    super.onPlayerPlaybackPermission(playerView, event)
                    writeToPlayerEventLog("onPlayerPlaybackPermission")
                }

                override fun onPlayerScaleModeChange(playerView: PlayerView, scaleMode: String) {
                    super.onPlayerScaleModeChange(playerView, scaleMode)
                    writeToPlayerEventLog("onPlayerScaleModeChange")
                }

                override fun onPlayerStart(playerView: PlayerView) {
                    super.onPlayerStart(playerView)
                    writeToPlayerEventLog("onPlayerStart")
                }

                override fun onPlayerVideoChange(
                    playerView: PlayerView,
                    event: PlayerEvent.VideoChange
                ) {
                    super.onPlayerVideoChange(playerView, event)
                    writeToPlayerEventLog("onPlayerVideoChange")
                }

                override fun onPlayerVolumeChange(
                    playerView: PlayerView,
                    volume: Long,
                    muted: Boolean
                ) {
                    super.onPlayerVolumeChange(playerView, volume, muted)
                    writeToPlayerEventLog("onPlayerVolumeChange")
                }
            },
            videoListener = object : VideoListener {
                override fun onVideoBuffering(playerView: PlayerView) {
                    super.onVideoBuffering(playerView)
                    writeToPlayerEventLog("onVideoBuffering")
                }

                override fun onVideoDurationChange(playerEvent: PlayerView, duration: Long) {
                    super.onVideoDurationChange(playerEvent, duration)
                    writeToPlayerEventLog("onVideoDurationChange")
                }

                override fun onVideoEnd(playerView: PlayerView) {
                    super.onVideoEnd(playerView)
                    writeToPlayerEventLog("onVideoEnd")
                }

                override fun onVideoPause(playerView: PlayerView) {
                    super.onVideoPause(playerView)
                    writeToPlayerEventLog("onVideoPause")
                }

                override fun onVideoPlay(playerView: PlayerView) {
                    super.onVideoPlay(playerView)
                    writeToPlayerEventLog("onVideoPlay")
                }

                override fun onVideoPlaying(playerView: PlayerView) {
                    super.onVideoPlaying(playerView)
                    writeToPlayerEventLog("onVideoPlaying")
                }

                override fun onVideoProgress(playerEvent: PlayerView, time: Long) {
                    super.onVideoProgress(playerEvent, time)
                    writeToPlayerEventLog("onVideoProgress")
                }

                override fun onVideoQualitiesReady(
                    playerView: PlayerView,
                    qualityList: List<String>
                ) {
                    super.onVideoQualitiesReady(playerView, qualityList)
                    writeToPlayerEventLog("onVideoQualitiesReady")
                }

                override fun onVideoQualityChange(playerView: PlayerView, quality: String) {
                    super.onVideoQualityChange(playerView, quality)
                    writeToPlayerEventLog("onVideoQualityChange")
                }

                override fun onVideoSeekEnd(playerView: PlayerView, time: Long) {
                    super.onVideoSeekEnd(playerView, time)
                    writeToPlayerEventLog("onVideoSeekEnd")
                }

                override fun onVideoSeekStart(playerView: PlayerView, time: Long) {
                    super.onVideoSeekStart(playerView, time)
                    writeToPlayerEventLog("onVideoSeekStart")
                }

                override fun onVideoStart(playerView: PlayerView) {
                    super.onVideoStart(playerView)
                    writeToPlayerEventLog("onVideoStart")
                }

                override fun onVideoSubtitlesChange(playerView: PlayerView, subtitle: String) {
                    super.onVideoSubtitlesChange(playerView, subtitle)
                    writeToPlayerEventLog("onVideoSubtitlesChange")
                }

                override fun onVideoSubtitlesReady(
                    playerView: PlayerView,
                    subtitleList: List<String>
                ) {
                    super.onVideoSubtitlesReady(playerView, subtitleList)
                    writeToPlayerEventLog("onVideoSubtitlesReady")
                }

                override fun onVideoTimeChange(playerView: PlayerView, time: Long) {
                    super.onVideoTimeChange(playerView, time)
                }
            },
            adListener = object : AdListener {
                override fun onAdClick(playerView: PlayerView) {
                    super.onAdClick(playerView)
                    writeToPlayerEventLog("onAdClick")
                }

                override fun onAdCompanionsReady(playerView: PlayerView) {
                    super.onAdCompanionsReady(playerView)
                    writeToPlayerEventLog("onAdCompanionsReady")
                }

                override fun onAdDurationChange(playerView: PlayerView, duration: Long) {
                    super.onAdDurationChange(playerView, duration)
                    writeToPlayerEventLog("onAdDurationChange")
                }

                override fun onAdEnd(
                    playerView: PlayerView,
                    adEnd: PlayerEvent.AdEnd
                ) {
                    super.onAdEnd(playerView, adEnd)
                    writeToPlayerEventLog("onAdEnd")
                }

                override fun onAdImpression(playerView: PlayerView) {
                    super.onAdImpression(playerView)
                    writeToPlayerEventLog("onAdImpression")
                }

                override fun onAdLoaded(
                    playerView: PlayerView,
                    adLoaded: PlayerEvent.AdLoaded
                ) {
                    super.onAdLoaded(playerView, adLoaded)
                    writeToPlayerEventLog("onAdLoaded")
                }

                override fun onAdPause(playerView: PlayerView) {
                    super.onAdPause(playerView)
                    writeToPlayerEventLog("onAdPause")
                }

                override fun onAdPlay(playerView: PlayerView) {
                    super.onAdPlay(playerView)
                    writeToPlayerEventLog("onAdPlay")
                }

                override fun onAdStart(playerView: PlayerView, type: String, position: String) {
                    super.onAdStart(playerView, type, position)
                    writeToPlayerEventLog("onAdStart")
                }

                override fun onAdTimeChange(playerView: PlayerView, time: Double) {
                    super.onAdTimeChange(playerView, time)
                    writeToPlayerEventLog("onAdTimeChange")
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyPlayer()
    }

    private fun initializeToolbar() {
        (activity as? MainActivity?)?.let { a ->
            a.setTitle(R.string.player_events_state_title)
            a.showToolbarBackButton(showBackButton = true)
        }
    }

    private fun initializeLogsTextView() {
        showPlayerEventsLog()
        tabLayout?.let { t ->
            t.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (t.selectedTabPosition) {
                        0 -> showPlayerEventsLog()
                        1 -> {
                            showPlayerStateLog()
                            logPlayerState()
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // do nothing
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // do nothing
                }
            })
            t.getTabAt(0)?.select()
        }

        clearButton?.setOnClickListener {
            playerEventsTextView?.text = ""
        }

        refreshButton?.setOnClickListener {
            logPlayerState()
        }
    }

    private fun showPlayerEventsLog() {
        eventsGroup?.visibility = View.VISIBLE
        stateGroup?.visibility = View.GONE
    }

    private fun showPlayerStateLog() {
        eventsGroup?.visibility = View.GONE
        stateGroup?.visibility = View.VISIBLE
    }

    private fun logPlayerState() {
        playerView?.getState(object : PlayerView.PlayerStateCallback {
            override fun onPlayerStateReceived(
                playerView: PlayerView,
                playerState: PlayerEvent.PlayerState
            ) {
                val playerStateString = PlayerStateHelper.mapPlayerStateToString(playerState)
                writeToPlayerState(playerStateString)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun writeToPlayerEventLog(text: String) {
        playerEventsTextView?.let { textView ->
            textView.text = textView.text.toString() + "$text\n"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun writeToPlayerState(text: String) {
        playerStateTextView?.let { textView ->
            textView.text = "$text\n"
        }
    }

    private fun destroyPlayer() {
        playerView?.destroy()
        playerView = null
    }
}