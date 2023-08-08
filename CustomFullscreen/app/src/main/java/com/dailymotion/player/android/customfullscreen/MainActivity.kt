package com.dailymotion.player.android.customfullscreen

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.DialogFragment
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.player.android.sdk.LogLevel
import com.dailymotion.player.android.sdk.PlayerView
import com.dailymotion.player.android.sdk.ads.DailymotionAds
import com.dailymotion.player.android.sdk.listeners.PlayerListener
import com.dailymotion.player.android.sdk.webview.error.PlayerError


class MainActivity : AppCompatActivity() {

    private var dmPlayer: PlayerView? = null

    private var screenHeight: Int = 0
    private var screenWidth: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logTag = "dmsample-${Dailymotion.version()}"
        val playerId = "xhysi"
        val videoId = "x84sh87"
        val playlistId = "x79dlo"

        val fullscreenGroupVisibility = findViewById<Group>(R.id.fullscreenGroup)
        val containerView = findViewById<FrameLayout>(R.id.playerContainerView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        findViewById<TextView>(R.id.playerIdTextView)?.text =
            resources.getString(R.string.player_id_string_format, playerId)
        findViewById<TextView>(R.id.videoIdTextView)?.text =
            resources.getString(R.string.video_id_string_format, videoId)
        findViewById<TextView>(R.id.playlistIdTextView)?.text =
            resources.getString(R.string.playlist_id_string_format, playlistId)
        findViewById<TextView>(R.id.sdkVersionTextView)?.text =
            resources.getString(R.string.sdk_version_string_format, Dailymotion.version())
        findViewById<TextView>(R.id.adsVersionTextView)?.text =
            resources.getString(R.string.ads_version_string_format, DailymotionAds.version())

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels
        screenWidth = displayMetrics.widthPixels

        Dailymotion.setLogLevel(LogLevel.All)
        DailymotionAds.setLogLevel(com.dailymotion.player.android.sdk.ads.LogLevel.All)

        if (dmPlayer == null) {
            Log.d(
                logTag,
                "Creating dailymotion player with playerId=$playerId, videoId=$videoId, playlistId=$playlistId"
            )
            Dailymotion.createPlayer(
                context = this,
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
                        Log.d(logTag, "Enter fullscreen")

                        containerView.layoutParams.height = LayoutParams.MATCH_PARENT
                        containerView.layoutParams.width = LayoutParams.MATCH_PARENT
                        fullscreenGroupVisibility?.visibility = View.GONE
                        dmPlayer?.notifyFullscreenChanged()
                    }

                    override fun onFullscreenExit(playerView: PlayerView) {
                        super.onFullscreenExit(playerView)
                        Log.d(logTag, "Exit fullscreen")

                        containerView.layoutParams.height = this@MainActivity.resources.getDimension(R.dimen.dm_player_height).toInt()
                        containerView.layoutParams.width = LayoutParams.MATCH_PARENT
                        fullscreenGroupVisibility?.visibility = View.VISIBLE
                        dmPlayer?.notifyFullscreenChanged()
                    }
                })
        }
    }
}