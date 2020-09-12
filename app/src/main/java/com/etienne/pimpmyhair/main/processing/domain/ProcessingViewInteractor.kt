package com.etienne.pimpmyhair.main.processing.domain

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.etienne.libraries.archi.nucleus.Action
import com.etienne.libraries.archi.nucleus.NucleusInteractorImpl
import com.etienne.libraries.archi.nucleus.NucleusReducerConfigurator
import com.etienne.libraries.archi.nucleus.simpleCommand
import com.etienne.pimpmyhair.domain.ResultHistoryInteractor
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import com.etienne.pimpmyhair.main.processing.domain.PhotoLibraryLauncher.Companion.PhotoLibraryRequestCode
import io.reactivex.rxjava3.core.Scheduler


class ProcessingViewInteractor(
    private val applicationState: ApplicationState,
    private val photoLibraryLauncher: PhotoLibraryLauncher,
    private val resultHistoryInteractor: ResultHistoryInteractor,
    private val contentResolver: ContentResolver,
    private val processingRepository: ProcessingRepository,
    private val computationScheduler: Scheduler,
    private val ioScheduler: Scheduler,
    private val mainScheduler: Scheduler
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
                ProcessingState.FormattingData,
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
                ProcessingState.SendingData,
                SendData(it.bitmap, it.encodedImage, processingRepository, ioScheduler)
            )
        }
        ResultReceived::class changesBoth {
            Pair(
                ProcessingState.FormattingResult,
                FormatResult(it.bitmap, it.encodedResult, computationScheduler)
            )
        }
        ResultFormatted::class changesBoth {
            Pair(
                ProcessingState.Idle,
                PublishResult(
                    it.originalImage,
                    it.resultImage,
                    applicationState,
                    resultHistoryInteractor,
                    mainScheduler
                )
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

sealed class ProcessingState {
    object PickingPhoto : ProcessingState()
    object FormattingData : ProcessingState()
    object SendingData : ProcessingState()
    object FormattingResult : ProcessingState()
    object Idle : ProcessingState()
}

object PickPhoto : Action
data class PhotoPickedUp(val imageUri: Uri) : Action
object NoPhotoPickedUp : Action
data class DataFormatted(val encodedImage: String, val bitmap: Bitmap) : Action
data class ResultReceived(val bitmap: Bitmap, val encodedResult: String) : Action
data class ResultFormatted(val originalImage: Bitmap, val resultImage: Bitmap) : Action
object ProcessingError : Action

interface PhotoLibraryLauncher {
    fun showPhotoLibrary(requestCode: Int)

    companion object {
        const val PhotoLibraryRequestCode = 100
    }
}
