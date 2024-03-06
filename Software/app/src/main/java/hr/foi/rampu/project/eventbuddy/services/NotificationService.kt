package hr.foi.rampu.project.eventbuddy.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.app.Notification
import androidx.core.app.NotificationCompat
import hr.foi.rampu.project.eventbuddy.R

const val NOTIFICATION_ID = 1111
class NotificationService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val prihvacen = intent.getBooleanExtra("prihvacen", false)
        val upozorenja = intent.getIntExtra("upozorenja", 0)

        if (prihvacen) {
            val notification = buildNotification("Prihvaćeni ste kao organizator!!", "Zahtjev za organizatora")
            startForeground(NOTIFICATION_ID, notification)
        }

        if (upozorenja == 1) {
            val contentText ="Dobili ste prvo upozorenje, vaš događaj je sakriven. Ukoliko dobite drugo upozorenje gubite ulogu organizatora."
            val contentTitle ="Prvo pozorenje"
            val notification = buildNotification(contentText, contentTitle)
            startForeground(NOTIFICATION_ID, notification)
        }
        if (upozorenja == 2) {
            val contentText ="Dobili ste drugo upozorenje. Oduzeta vam je uloga organizatora"
            val contentTitle ="Drugo upozorenje"
            val notification = buildNotification(contentText, contentTitle)
            startForeground(NOTIFICATION_ID, notification)
        }
        return START_NOT_STICKY
    }

    private fun buildNotification(contentText: String, setContentTitle: String): Notification {
        return NotificationCompat.Builder(applicationContext, "prihvacen-channel")
            .setContentTitle(setContentTitle)
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setSmallIcon(R.drawable.baseline_supervisor_account_24)
            .setOnlyAlertOnce(true)
            .build()
    }

    override fun onBind(intent: Intent): IBinder? = null
}