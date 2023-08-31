package com.dailymotion.sample.player.sdk.vertical

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.dailymotion.sample.player.sdk.MainActivity
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.player.android.sdk.Orientation
import com.dailymotion.player.android.sdk.PlayerParameters
import com.dailymotion.player.android.sdk.PlayerView
import com.dailymotion.player.android.sdk.listeners.PlayerListener
import com.dailymotion.player.android.sdk.webview.error.PlayerError
import com.dailymotion.sample.player.sdk.R

class VerticalPlayerFragment: Fragment() {

    companion object {
        private const val TAG = "VerticalPlayer"
    }

    private var videoContainer: FrameLayout? = null
    private var playerView: PlayerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vertical_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolbar()

        videoContainer = view.findViewById(R.id.videoContainer)

        Log.d(TAG, "Creating dailymotion player")
        Dailymotion.createPlayer(context = view.context,
            playerId = "xix5w",
            videoId = "x8nphw7",
            playerParameters = PlayerParameters(
                defaultFullscreenOrientation = Orientation.Portrait
            ),
            playerSetupListener = object : Dailymotion.PlayerSetupListener {
                override fun onPlayerSetupSuccess(player: PlayerView) {
                    Log.d(TAG, "Successfully created dailymotion player")
                    videoContainer?.addView(player, LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                    ))
                }

                override fun onPlayerSetupFailed(error: PlayerError) {
                    Log.d(TAG, "Error while creating dailymotion player: ${error.message}")
                }
            },
            playerListener = object : PlayerListener {
                override fun onFullscreenRequested(playerDialogFragment: DialogFragment) {
                    super.onFullscreenRequested(playerDialogFragment)
                    Log.d(TAG, "Entering fullscreen")

                    playerDialogFragment.show(this@VerticalPlayerFragment.parentFragmentManager, "dmPlayerFullscreenFragment")
                }

                override fun onFullscreenExit(playerView: PlayerView) {
                    super.onFullscreenExit(playerView)
                    Log.d(TAG, "Exiting fullscreen")
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
            a.setTitle(R.string.vertical_screen_title)
            a.showToolbarBackButton(showBackButton = true)
        }
    }
    private fun destroyPlayer() {
        playerView?.destroy()
        playerView = null
    }
}