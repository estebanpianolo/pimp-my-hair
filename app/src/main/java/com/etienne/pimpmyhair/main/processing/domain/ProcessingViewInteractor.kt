package com.etienne.pimpmyhair.main.processing.domain

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.etienne.libraries.archi.nucleus.*
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import com.etienne.pimpmyhair.main.processing.domain.PhotoLibraryLauncher.Companion.PhotoLibraryRequestCode
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import java.io.ByteArrayOutputStream


class ProcessingViewInteractor(
    private val applicationState: ApplicationState,
    private val photoLibraryLauncher: PhotoLibraryLauncher,
    private val contentResolver: ContentResolver,
    private val computationScheduler: Scheduler
) :
    NucleusInteractorImpl<ProcessingState>(ProcessingState.Idle) {
    override val reducerConfigurator: NucleusReducerConfigurator<ProcessingState> = {
        PickPhoto::class changesBoth {
            Pair(
                ProcessingState.PickingPhoto,
                OpenPhotoLibrary(photoLibraryLauncher, PhotoLibraryRequestCode)
            )
        }
        PhotoPickedUp::class changesBoth {
            Pair(
                ProcessingState.FormattingData(it.imageUri),
                FormatData(it.imageUri, contentResolver, computationScheduler)
            )
        }
        NoPhotoPickedUp::class changesBoth {
            Pair(
                ProcessingState.Idle,
                simpleCommand {
                    applicationState.hideProcessingScreen()
                }
            )
        }
        DataFormatted::class changesBoth {
            Pair(
                ProcessingState.SendingData(it.bitmap),
                SendData(it.encodedImage)
            )
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == PhotoLibraryRequestCode) {
            val imageUri = data?.data
            actions.onNext(
                if (resultCode == AppCompatActivity.RESULT_OK && imageUri != null) {
                    PhotoPickedUp(imageUri)
                } else
                    NoPhotoPickedUp
            )
            return true
        }
        return false
    }
}

class OpenPhotoLibrary(
    private val photoLibraryLauncher: PhotoLibraryLauncher,
    private val photoLibraryRequestCode: Int
) :
    Command {
    override fun execute(): Single<Action> {
        return Single.fromCallable {
            photoLibraryLauncher.showPhotoLibrary(photoLibraryRequestCode)
            DoNothing
        }
    }
}


class FormatData(
    private val imageUri: Uri,
    private val contentResolver: ContentResolver,
    private val computationScheduler: Scheduler
) :
    Command {
    override fun execute(): Single<Action> = Single.fromCallable {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        DataFormatted(
            Base64.encodeToString(
                ByteArrayOutputStream().run {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
                    toByteArray()
                }, Base64.DEFAULT
            ),
            bitmap
        ) as Action
    }.subscribeOn(computationScheduler)

}

sealed class ProcessingState {
    object PickingPhoto : ProcessingState()
    data class FormattingData(val imageUri: Uri) : ProcessingState()
    data class SendingData(val image: Bitmap) : ProcessingState()
    data class FormattingResult(val image: Bitmap) : ProcessingState()
    object Idle : ProcessingState()
}

object PickPhoto : Action
data class PhotoPickedUp(val imageUri: Uri) : Action
object NoPhotoPickedUp : Action
data class DataFormatted(val encodedImage: String, val bitmap: Bitmap) : Action
data class ResultReceived(val encodedResult: String) : Action
data class ResultFormatted(val resultImage: Bitmap) : Action

interface PhotoLibraryLauncher {
    fun showPhotoLibrary(requestCode: Int)

    companion object {
        const val PhotoLibraryRequestCode = 100
    }
}
