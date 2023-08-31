package com.dailymotion.sample.player.sdk.eventsstate

import com.dailymotion.player.android.sdk.webview.events.PlayerEvent

object PlayerStateHelper {

    fun mapPlayerStateToString(
        playerState: PlayerEvent.PlayerState
    ): String {

        val adCompanionString = extractAdCompanionString(playerState = playerState)
        val playerErrorString = extractPlayerErrorString(playerState = playerState)

        return "\tadAdvertiserName=${playerState.adAdvertiserName}\n" +
                "\tadCompanion=$adCompanionString\n" +
                "\tadCreativeAdId=${playerState.adCreativeAdId}\n" +
                "\tadCreativeId=${playerState.adCreativeId}\n" +
                "\tadDescription=${playerState.adDescription}\n" +
                "\tadDuration=${playerState.adDuration}\n" +
                "\tadEndedReason=${playerState.adEndedReason}\n" +
                "\tadError=${playerState.adError}\n" +
                "\tadId=${playerState.adId}\n" +
                "\tadIsPlaying=${playerState.adIsPlaying}\n" +
                "\tadIsSkippable=${playerState.adIsSkippable}\n" +
                "\tadPosition=${playerState.adPosition}\n" +
                "\tadSkipOffset=${playerState.adSkipOffset}\n" +
                "\tadTime=${playerState.adTime}\n" +
                "\tadTitle=${playerState.adTitle}\n" +
                "\tplayerAreControlsEnabled=${playerState.playerAreControlsEnabled}\n" +
                "\tplayerError=${playerErrorString}\n" +
                "\tplayerIsAlertDialogDisplayed=${playerState.playerIsAlertDialogDisplayed}\n" +
                "\tplayerIsBuffering=${playerState.playerIsBuffering}\n" +
                "\tplayerPlaybackSpeed=${playerState.playerPlaybackSpeed}\n" +
                "\tplayerIsCriticalPathReady=${playerState.playerIsCriticalPathReady}\n" +
                "\tplayerIsMuted=${playerState.playerIsMuted}\n" +
                "\tplayerIsNavigationEnabled=${playerState.playerIsNavigationEnabled}\n" +
                "\tplayerIsPlaybackAllowed=${playerState.playerIsPlaybackAllowed}\n" +
                "\tplayerIsPlaying=${playerState.playerIsPlaying}\n" +
                "\tplayerIsReplayScreen=${playerState.playerIsReplayScreen}\n" +
                "\tplayerIsStartScreen=${playerState.playerIsStartScreen}\n" +
                "\tplayerNextVideo=${playerState.playerNextVideo}\n" +
                "\tplayerPlaybackPermissionReason=${playerState.playerPlaybackPermissionReason}\n" +
                "\tplayerPrevVideo=${playerState.playerPrevVideo}\n" +
                "\tplayerVolume=${playerState.playerVolume}\n" +
                "\tplayerAspectRatio=${playerState.playerAspectRatio}\n" +
                "\tplayerPresentationMode=${playerState.playerPresentationMode}\n" +
                "\tplayerScaleMode=${playerState.playerScaleMode}\n" +
                "\tvideoCreatedTime=${playerState.videoCreatedTime}\n" +
                "\tvideoDuration=${playerState.videoDuration}\n" +
                "\tvideoId=${playerState.videoId}\n" +
                "\tvideoIsCreatedForKids=${playerState.videoIsCreatedForKids}\n" +
                "\tvideoIsPasswordRequired=${playerState.videoIsPasswordRequired}\n" +
                "\tvideoOwnerId=${playerState.videoOwnerId}\n" +
                "\tvideoOwnerScreenname=${playerState.videoOwnerScreenName}\n" +
                "\tvideoOwnerUsername=${playerState.videoOwnerUsername}\n" +
                "\tvideoQuality=${playerState.videoQuality}\n" +
                "\tvideoQualitiesList=${playerState.videoQualitiesList?.joinToString()}\n" +
                "\tvideoSubtitles=${playerState.videoSubtitles}\n" +
                "\tvideoSubtitlesList=${playerState.videoSubtitlesList?.joinToString()}\n" +
                "\tvideoTime=${playerState.videoTime}\n" +
                "\tvideoTitle=${playerState.videoTitle}\n"
    }

    private fun extractAdCompanionString(playerState: PlayerEvent.PlayerState): String {
        val adCompanion = playerState.adCompanion
        val adCompanionString = if (adCompanion == null) {
            "null"
        } else {
            "{\n" +
            "\t\tid=${adCompanion.id}\n" +
            "\t\tadId=${adCompanion.adId}\n" +
            "\t\tsequence=${adCompanion.sequence}\n" +
            "\t\tapiFramework=${adCompanion.apiFramework}\n" +
            "\t\ttype=${adCompanion.type}\n" +
            "\t\tadCompanionRequired=${adCompanion.adCompanionRequired}\n" +
            "\t}"
        }
        return adCompanionString
    }

    private fun extractPlayerErrorString(playerState: PlayerEvent.PlayerState): String {
        val playerError = playerState.playerError
        val playerErrorString = if (playerError == null) {
            "null"
        } else {
            "{\n" +
            "\t\ttitle=${playerError.title}\n" +
            "\t\tmessage=${playerError.message}\n" +
            "\t}"
        }
        return playerErrorString
    }
}