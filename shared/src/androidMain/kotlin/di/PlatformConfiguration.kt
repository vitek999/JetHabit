package di

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileWriter
import java.net.URLConnection

actual class PlatformConfiguration constructor(
    val applicationContext: Context,
    val activityContext: Context,
    actual val appName: String,
    val applicationId: String,
) {
    actual val platform: Platform
        get() = Platform.Android

    actual fun exportDataToFile(data: String, fileName: String) {

        val file = File(activityContext.cacheDir, fileName)
        //clear
        FileWriter(file).close()

        val fw = FileWriter(file)
        fw.write(data)
        fw.flush()
        fw.close()
        println("SUCCESS")

        activityContext.startActivity(
            Intent.createChooser(
                Intent(
                    Intent.ACTION_SEND
                ).apply {
                    type = URLConnection.guessContentTypeFromName(file.name)
                    putExtra(
                        Intent.EXTRA_STREAM, FileProvider.getUriForFile(
                            activityContext,
                            applicationId,
                            file
                        )
                    )
                    putExtra(
                        Intent.EXTRA_SUBJECT, fileName
                    )
                }, "Share File")
            )

    }
}