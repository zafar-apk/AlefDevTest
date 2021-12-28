package abdullaev.zafar.aleftestapp.domain.repo.interfaces

import abdullaev.zafar.aleftestapp.data.model.Resource

interface ImagesRepo {

    suspend fun loadImages(): Resource<List<String>>

}