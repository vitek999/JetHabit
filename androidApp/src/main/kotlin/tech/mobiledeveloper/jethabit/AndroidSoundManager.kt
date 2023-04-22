package tech.mobiledeveloper.jethabit

import android.content.Context
import android.media.RingtoneManager
import utils.SoundManager

class AndroidSoundManager(private val applicationContext: Context) : SoundManager {
    override fun play() {
        val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(applicationContext, notificationUri)
        r.play()
    }

}