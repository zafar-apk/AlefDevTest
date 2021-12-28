package abdullaev.zafar.aleftestapp.presenter.imagesFragment

import abdullaev.zafar.aleftestapp.data.model.Resource
import abdullaev.zafar.aleftestapp.domain.repo.interfaces.ImagesRepo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val repo: ImagesRepo
) : ViewModel() {

    private val _images = MutableLiveData<Resource<List<String>>>(Resource.Loading)
    val images: LiveData<Resource<List<String>>> = _images

    init {
        loadPictures()
    }

    fun loadPictures() {
        _images.value = Resource.Loading

        viewModelScope.launch {
            _images.postValue(repo.loadImages())
        }
    }

}