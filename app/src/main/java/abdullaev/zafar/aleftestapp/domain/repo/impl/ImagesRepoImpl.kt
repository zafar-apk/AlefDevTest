package abdullaev.zafar.aleftestapp.domain.repo.impl

import abdullaev.zafar.aleftestapp.data.model.Resource
import abdullaev.zafar.aleftestapp.data.remote.ImagesApi
import abdullaev.zafar.aleftestapp.domain.repo.interfaces.ImagesRepo
import javax.inject.Inject

class ImagesRepoImpl @Inject constructor(
    private val api: ImagesApi
) : ImagesRepo {

    override suspend fun loadImages(): Resource<List<String>> {
        return try {
            Resource.Success(data = api.loadImages())
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "Unknown error")
        }
    }

}