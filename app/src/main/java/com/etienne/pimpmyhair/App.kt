package com.etienne.pimpmyhair

import android.app.Activity
import com.etienne.libraries.archi.view.Application
import dagger.android.AndroidInjector

import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityLifecycleCallbacks: com.etienne.pimpmyhair.ActivityLifecycleCallbacks

    @Inject
    lateinit var injector: AppInjector

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    val appComponent: AppComponent
        get() = injector.appComponent

    val isRootActivity: Boolean
        get() = activityLifecycleCallbacks.isRootActivity()

    override val isAppInitialized: () -> Boolean = { MainActivity.isInitialized }

    override fun buildAppDependencies() {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return activityInjector
    }
}
