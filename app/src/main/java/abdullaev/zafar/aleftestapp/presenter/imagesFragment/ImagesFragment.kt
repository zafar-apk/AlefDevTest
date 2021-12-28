package abdullaev.zafar.aleftestapp.presenter.imagesFragment

import abdullaev.zafar.aleftestapp.R
import abdullaev.zafar.aleftestapp.data.model.Resource
import abdullaev.zafar.aleftestapp.presenter.KEY_IMAGE_URL
import abdullaev.zafar.aleftestapp.presenter.getItemSize
import abdullaev.zafar.aleftestapp.presenter.getSpanCount
import abdullaev.zafar.aleftestapp.presenter.imagesAdapter.ImagesAdapter
import abdullaev.zafar.aleftestapp.presenter.imagesAdapter.SpacesItemDecoration
import abdullaev.zafar.aleftestapp.presenter.showSnackbar
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagesFragment : Fragment(R.layout.fragment_images) {

    private val viewModel: ImagesViewModel by viewModels()

    private var swipeToRefreshView: SwipeRefreshLayout? = null
    private var imagesRecyclerView: RecyclerView? = null
    private var snackBarEventObserver: LifecycleEventObserver? = null

    private val spanCount by lazy { getSpanCount() }

    private val imagesAdapter by lazy {
        ImagesAdapter(getItemSize(spanCount), ::openFullScreen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.init()
        setupRecyclerView()
        observeResult()
    }

    private fun View.init() {
        swipeToRefreshView = findViewById(R.id.swipe_to_refresh_layout)
        imagesRecyclerView = findViewById(R.id.images_recycler_view)

        swipeToRefreshView?.setOnRefreshListener(
            viewModel::loadPictures.also {
                swipeToRefreshView?.isRefreshing = true
            }
        )
    }

    private fun setupRecyclerView() {
        imagesRecyclerView?.apply {
            adapter = imagesAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            val margins = resources.getDimensionPixelSize(R.dimen.spacing)
            val spacesItemDecoration = SpacesItemDecoration(margins, spanCount)
            addItemDecoration(spacesItemDecoration)
        }
    }

    private fun observeResult() {
        viewModel.images.observe(this) { resource ->
            when (resource) {
                is Resource.Error -> onError()
                Resource.Loading -> swipeToRefreshView?.isRefreshing = true
                is Resource.Success -> onSuccess(resource)
            }
        }
    }

    private fun openFullScreen(url: String) {
        findNavController()
            .navigate(R.id.imagesFullScreenFragment, bundleOf(KEY_IMAGE_URL to url))
    }

    private fun onSuccess(resource: Resource.Success<List<String>>) {
        swipeToRefreshView?.isRefreshing = false
        resource.data.let(imagesAdapter::submitList)
    }

    private fun onError() {
        swipeToRefreshView?.isRefreshing = false
        snackBarEventObserver = showSnackbar(viewModel::loadPictures)
    }

    override fun onDestroyView() {
        snackBarEventObserver?.let(lifecycle::removeObserver)
        super.onDestroyView()
    }

}