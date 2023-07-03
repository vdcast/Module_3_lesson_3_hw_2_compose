package com.example.module_3_lesson_3_hw_2_compose

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.provider.Settings

class AudioService : Service() {
    private lateinit var player: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (::player.isInitialized) {
            player.stop()
            player.release()
        }

        val audioUri: Uri? = intent?.getParcelableExtra("audio_uri") ?: return START_NOT_STICKY
        player = MediaPlayer.create(this, audioUri)
        player.start()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}