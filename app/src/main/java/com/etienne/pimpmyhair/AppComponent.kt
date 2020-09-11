package com.etienne.pimpmyhair

import android.content.Context
import com.etienne.pimpmyhair.main.MainComponent
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.reactivex.rxjava3.core.Scheduler

import javax.inject.Qualifier

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
    ]
)
interface AppComponent {

    @get:ApplicationContext
    val applicationContext: Context


    @get:MainScheduler
    val mainScheduler: Scheduler

    @get:IOScheduler
    val ioScheduler: Scheduler

    fun provideAppInjector(): AppInjector

    fun inject(app: App)

    fun plus(module: MainComponent.Module): MainComponent
}

@Qualifier
@kotlin.annotation.MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@kotlin.annotation.MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class MainScheduler

@Qualifier
@kotlin.annotation.MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class IOScheduler
