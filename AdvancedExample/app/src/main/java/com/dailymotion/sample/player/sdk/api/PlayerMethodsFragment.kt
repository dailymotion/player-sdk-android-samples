package com.dailymotion.sample.player.sdk.api

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.dailymotion.sample.player.sdk.MainActivity
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.player.android.sdk.PlayerView
import com.dailymotion.player.android.sdk.listeners.PlayerListener
import com.dailymotion.player.android.sdk.listeners.VideoListener
import com.dailymotion.player.android.sdk.webview.error.PlayerError
import com.dailymotion.player.android.sdk.webview.events.PlayerEvent
import com.dailymotion.sample.player.sdk.R

class PlayerMethodsFragment: Fragment() {

    companion object {
        private const val TAG = "PlayerMethods"

        private const val VIDEO_ID = "x8nlohg"
    }

    private var videoContainer: FrameLayout? = null
    private var playerView: PlayerView? = null

    private var currentVideoTime = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player_methods, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()
        initializeClickListeners()

        videoContainer = view.findViewById(R.id.videoContainer)

        Log.d(TAG, "Creating dailymotion player")
        Dailymotion.createPlayer(context = view.context,
            playerId = "xix5w",
            videoId = VIDEO_ID,
            playerSetupListener = object : Dailymotion.PlayerSetupListener {
                override fun onPlayerSetupSuccess(player: PlayerView) {
                    Log.d(TAG, "Successfully created dailymotion player")
                    videoContainer?.addView(player, LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                    ))

                    this@PlayerMethodsFragment.playerView = player
                }

                override fun onPlayerSetupFailed(error: PlayerError) {
                    Log.d(TAG, "Error while creating dailymotion player: ${error.message}")
                }
            },
            playerListener = object : PlayerListener {
                override fun onFullscreenRequested(playerDialogFragment: DialogFragment) {
                    super.onFullscreenRequested(playerDialogFragment)
                    Log.d(TAG, "Entering fullscreen")

                    playerDialogFragment.show(this@PlayerMethodsFragment.parentFragmentManager, "dmPlayerFullscreenFragment")
                }

                override fun onFullscreenExit(playerView: PlayerView) {
                    super.onFullscreenExit(playerView)
                    Log.d(TAG, "Exiting fullscreen")
                }
            },
            videoListener = object : VideoListener {
                override fun onVideoTimeChange(playerView: PlayerView, time: Long) {
                    super.onVideoTimeChange(playerView, time)
                    currentVideoTime = time
                }

                override fun onVideoSeekEnd(playerView: PlayerView, time: Long) {
                    super.onVideoSeekEnd(playerView, time)
                    currentVideoTime = time
                }

                override fun onVideoEnd(playerView: PlayerView) {
                    super.onVideoEnd(playerView)
                    currentVideoTime = 0
                }

                override fun onVideoStart(playerView: PlayerView) {
                    super.onVideoStart(playerView)
                    currentVideoTime = 0
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
            a.setTitle(R.string.player_methods_title)
            a.showToolbarBackButton(showBackButton = true)
        }
    }

    private fun initializeClickListeners() {
        view?.let { v ->
            v.findViewById<View>(R.id.playImageView).setOnClickListener {
                playerView?.play()
            }
            v.findViewById<View>(R.id.pauseImageView).setOnClickListener {
                playerView?.pause()
            }
            v.findViewById<View>(R.id.seekImageView).setOnClickListener {
                val seconds = 10L
                playerView?.seekTo(seekTo = currentVideoTime + seconds)
                Toast.makeText(context,
                    v.context.getString(R.string.seek_foward_format, seconds.toInt()),
                    Toast.LENGTH_SHORT).show()
            }
            v.findViewById<View>(R.id.loadImageView).setOnClickListener {
                playerView?.loadContent(
                    videoId = VIDEO_ID,
                    startTime = 0
                )
            }
            v.findViewById<View>(R.id.fullscreenImageView).setOnClickListener {
                playerView?.setFullscreen(true)
            }
        }
    }

    private fun destroyPlayer() {
        playerView?.destroy()
        playerView = null
    }
}