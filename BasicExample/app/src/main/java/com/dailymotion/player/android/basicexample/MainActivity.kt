package com.dailymotion.player.android.basicexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.player.android.sdk.PlayerView
import com.dailymotion.player.android.sdk.webview.error.PlayerError

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playerId = "xhysi"
        val videoId = "x84sh87"
        val playlistId = "x79dlo"

        val containerView = findViewById<FrameLayout>(R.id.playerContainerView)
        findViewById<TextView>(R.id.playerIdTextView)?.text = resources.getString(R.string.player_id_string_format, playerId)
        findViewById<TextView>(R.id.videoIdTextView)?.text = resources.getString(R.string.video_id_string_format, videoId)
        findViewById<TextView>(R.id.playlistIdTextView)?.text = resources.getString(R.string.playlist_id_string_format, playlistId)

        Log.d("dmsample-basic", "Creating dailymotion player with playerId=$playerId, videoId=$videoId, playlistId=$playlistId")
        Dailymotion.createPlayer(
            context = this,
            playerId = playerId,
            videoId = videoId,
            playlistId = playlistId,
            playerSetupListener = object : Dailymotion.PlayerSetupListener {
                override fun onPlayerSetupSuccess(player: PlayerView) {
                    Log.d("dmsample-basic", "Successfully created dailymotion player")

                    val lp = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    containerView.addView(player, lp)

                    Log.d("dmsample-basic", "Added dailymotion player to view hierarchy")
                }
                override fun onPlayerSetupFailed(error: PlayerError) {
                    Log.e("dmsample-basic", "Error while creating dailymotion player: ${error.message}")
                }
            }
        )
    }
}