package com.dailymotion.player.android.customfullscreen

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.player.android.sdk.LogLevel
import com.dailymotion.player.android.sdk.PlayerView
import com.dailymotion.player.android.sdk.ads.DailymotionAds
import com.dailymotion.player.android.sdk.listeners.PlayerListener
import com.dailymotion.player.android.sdk.webview.error.PlayerError

class MainFragment: Fragment() {

    private var dmPlayer: PlayerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logTag = "dmsample-${Dailymotion.version()}"
        val playerId = "xhysi"
        val videoId = "x84sh87"
        val playlistId = "x79dlo"

        val containerView = view.findViewById<FrameLayout>(R.id.playerContainerView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        view.findViewById<TextView>(R.id.playerIdTextView)?.text =
            resources.getString(R.string.player_id_string_format, playerId)
        view.findViewById<TextView>(R.id.videoIdTextView)?.text =
            resources.getString(R.string.video_id_string_format, videoId)
        view.findViewById<TextView>(R.id.playlistIdTextView)?.text =
            resources.getString(R.string.playlist_id_string_format, playlistId)
        view.findViewById<TextView>(R.id.sdkVersionTextView)?.text =
            resources.getString(R.string.sdk_version_string_format, Dailymotion.version())
        view.findViewById<TextView>(R.id.adsVersionTextView)?.text =
            resources.getString(R.string.ads_version_string_format, DailymotionAds.version())

        Dailymotion.setLogLevel(LogLevel.All)
        DailymotionAds.setLogLevel(com.dailymotion.player.android.sdk.ads.LogLevel.All)

        if (dmPlayer == null) {
            Log.d(
                logTag,
                "Creating dailymotion player with playerId=$playerId, videoId=$videoId, playlistId=$playlistId"
            )
            Dailymotion.createPlayer(
                context = view.context,
                playerId = playerId,
                videoId = videoId,
                playlistId = playlistId,
                playerSetupListener = object : Dailymotion.PlayerSetupListener {
                    override fun onPlayerSetupSuccess(player: PlayerView) {
                        Log.d(logTag, "Successfully created dailymotion player")

                        progressBar.visibility = View.GONE
                        val lp = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT
                        )
                        containerView.addView(player, lp)
                        Log.d(logTag, "Added dailymotion player to view hierarchy")

                        dmPlayer = player
                    }

                    override fun onPlayerSetupFailed(error: PlayerError) {
                        Log.e(logTag, "Error while creating dailymotion player: ${error.message}")
                    }
                },
                playerListener = object : PlayerListener {
                    override fun onFullscreenRequested(playerDialogFragment: DialogFragment) {
                        super.onFullscreenRequested(playerDialogFragment)

                        this@MainFragment.findNavController()
                            .navigate(MainFragmentDirections.actionMainFragmentToFullscreenPlayerWebViewFragment())
                    }
                })
        }
    }
}