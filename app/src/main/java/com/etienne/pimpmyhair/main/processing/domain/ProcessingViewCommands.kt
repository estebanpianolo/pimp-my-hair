package com.etienne.pimpmyhair.main.processing.domain

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import com.etienne.libraries.archi.nucleus.Action
import com.etienne.libraries.archi.nucleus.Command
import com.etienne.libraries.archi.nucleus.DoNothing
import com.etienne.pimpmyhair.domain.Result
import com.etienne.pimpmyhair.domain.ResultHistoryInteractor
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import java.io.ByteArrayOutputStream


class OpenPhotoLibrary(
    private val photoLibraryLauncher: PhotoLibraryLauncher,
    private val photoLibraryRequestCode: Int
) : Command {
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
) : Command {
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

class SendData(
    private val bitmap: Bitmap,
    private val encodedImage: String,
    private val processingRepository: ProcessingRepository,
    private val ioScheduler: Scheduler
) : Command {
    override fun execute(): Single<Action> =
        processingRepository.sendData(encodedImage)
            .map { ResultReceived(bitmap, it) as Action }
            .onErrorResumeNext { Single.just(ProcessingError as Action) }
            .subscribeOn(ioScheduler)
}

class FormatResult(
    private val bitmap: Bitmap,
    private val encodedResult: String,
    private val computationScheduler: Scheduler
) : Command {
    override fun execute(): Single<Action> = Single.fromCallable {
        Base64.decode(encodedResult, Base64.DEFAULT).run {
            ResultFormatted(bitmap, BitmapFactory.decodeByteArray(this, 0, size)) as Action
        }
    }.subscribeOn(computationScheduler)

}

class PublishResult(
    private val originalImage: Bitmap,
    private val processedImage: Bitmap,
    private val applicationState: ApplicationState,
    private val resultHistoryInteractor: ResultHistoryInteractor,
    private val mainScheduler: Scheduler
) : Command {
    override fun execute(): Single<Action> {
        return Single.fromCallable {
            val result = Result(originalImage, processedImage)
            applicationState.showResultScreen(result)
            resultHistoryInteractor.addResult(result)
            DoNothing as Action
        }.subscribeOn(mainScheduler)
    }

}

