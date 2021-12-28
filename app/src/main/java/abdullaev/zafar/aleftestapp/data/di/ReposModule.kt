package abdullaev.zafar.aleftestapp.data.di

import abdullaev.zafar.aleftestapp.domain.repo.impl.ImagesRepoImpl
import abdullaev.zafar.aleftestapp.domain.repo.interfaces.ImagesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ReposModule {

    @Binds
    fun bindImagesRepo(impl: ImagesRepoImpl): ImagesRepo

}