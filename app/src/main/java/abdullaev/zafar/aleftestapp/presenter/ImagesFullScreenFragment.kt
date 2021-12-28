package abdullaev.zafar.aleftestapp.presenter

import abdullaev.zafar.aleftestapp.R
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jsibbold.zoomage.ZoomageView

class ImagesFullScreenFragment : Fragment(R.layout.fragment_images_fullscreen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()

        arguments?.getString(KEY_IMAGE_URL, null)?.let { url ->
            val imageView = view.findViewById<ZoomageView>(R.id.full_screen_image)
            Glide.with(view)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(android.R.drawable.stat_notify_error)
                .into(imageView)
        }

        val closeButton = view.findViewById<ImageButton>(R.id.close_button)

        closeButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDetach() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.show()
        super.onDetach()
    }

}