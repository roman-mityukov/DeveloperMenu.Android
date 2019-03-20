package com.actonica.devmenusample.di

import com.actonica.devmenusample.DevMenuSampleActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindDevMenuSampleActivity(): DevMenuSampleActivity
}