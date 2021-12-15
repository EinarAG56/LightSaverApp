package com.example.lightsaver.di

import androidx.lifecycle.ViewModel
import com.example.lightsaver.BaseActivity
import com.example.lightsaver.BaseFragment
import com.example.lightsaver.ui.*
import com.example.lightsaver.viewmodel.MainViewModel
import com.example.lightsaver.viewmodel.MessageViewModel
import com.example.lightsaver.viewmodel.TransmitViewModel

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class AndroidBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransmitViewModel::class)
    abstract fun bindsTransmitViewModel(viewModel: TransmitViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindsMessageViewModel(viewModel: MessageViewModel): ViewModel

    @ContributesAndroidInjector
    internal abstract fun baseActivity(): BaseActivity

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun logActivity(): LogActivity

    @ContributesAndroidInjector
    internal abstract fun settingsActivity(): SettingsActivity

    @ContributesAndroidInjector
    internal abstract fun mainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun transmitFragment(): TransmitFragment

    @ContributesAndroidInjector
    internal abstract fun lightFragment(): LightFragment

    @ContributesAndroidInjector
    internal abstract fun baseFragment(): BaseFragment

    @ContributesAndroidInjector
    internal abstract fun logFragment(): LogFragment
}