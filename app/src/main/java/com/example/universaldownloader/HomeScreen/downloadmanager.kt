import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.net.toUri
import java.io.File

fun downloadMergedVideoFromBackend(context: Context, filename: String) {
    val encodedFilename = Uri.encode(filename)
    val downloadUrl = "http://192.168.75.197:8000/video/download-file?filename=$encodedFilename" // Adjust IP if needed
    try {
        val request = DownloadManager.Request(downloadUrl.toUri())
            .setTitle(filename)
            .setDescription("Downloading merged video...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Project One/$filename")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(context, "Download started...", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error starting download: ${e.message}", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }
}


fun downloadAudioToPhone(context: Context, filename: String) {
    try{
        val request = DownloadManager.Request(
            Uri.parse("http://192.168.75.197:8000/audio/file?filename=$filename")  // Adjust IP if needed
        )
            .setTitle(filename)
            .setDescription("Downloading audio")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Project One/$filename")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
        Toast.makeText(context, "Download started...", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error starting download: ${e.message}", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }


}

fun getDownloadedFiles(): List<File> {
    val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Project One/")
    return folder.listFiles()?.filter { it.isFile }?.toList() ?: emptyList()
}

