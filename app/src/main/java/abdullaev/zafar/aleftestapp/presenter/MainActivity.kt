package abdullaev.zafar.aleftestapp.presenter

import abdullaev.zafar.aleftestapp.R
import abdullaev.zafar.aleftestapp.presenter.imagesFragment.ImagesFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}