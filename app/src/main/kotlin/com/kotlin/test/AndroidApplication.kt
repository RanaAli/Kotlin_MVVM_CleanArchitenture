
package com.kotlin.test

import android.app.Application
import com.kotlin.test.core.di.ApplicationComponent
import com.kotlin.test.core.di.ApplicationModule
import com.kotlin.test.core.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary

class AndroidApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
