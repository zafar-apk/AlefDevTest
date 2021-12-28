package abdullaev.zafar.aleftestapp.data.remote

import retrofit2.http.GET

interface ImagesApi {

    @GET("list.php")
    suspend fun loadImages(): List<String>

}