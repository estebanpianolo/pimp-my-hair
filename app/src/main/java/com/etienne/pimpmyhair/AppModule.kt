package com.etienne.pimpmyhair

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Scope


@Module(
    includes = []
)
class AppModule(application: Application) {

    private val context: Context = application.applicationContext

    @Provides
    @ApplicationScope
    @ApplicationContext
    internal fun provideApplicationContext(): Context {
        return context
    }

    @ApplicationScope
    @IOScheduler
    @Provides
    fun provideRxIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @ApplicationScope
    @MainScheduler
    @Provides
    fun provideRxMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
    
    @Provides
    @ApplicationScope
    fun provideAppInjector(appComponent: AppComponent): AppInjector {
        return AppInjectorImpl(appComponent)
    }
}

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope
