package abdullaev.zafar.aleftestapp.presenter

import abdullaev.zafar.aleftestapp.R
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.snackbar.Snackbar

const val KEY_IMAGE_URL = "image_url"

sealed class DeviceType {
    object Phone : DeviceType()
    object Tablet : DeviceType()
}

fun Fragment.getDeviceType(): DeviceType {
    val widthDp = toDp(getWidth())
    val isPortrait = isPortrait()

    return when {
        isPortrait && widthDp < 600f -> DeviceType.Phone
        !isPortrait && widthDp < 480F -> DeviceType.Phone
        else -> DeviceType.Tablet
    }
}

private fun Fragment.isPortrait() =
    resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

private fun Fragment.getWidth() = getMetrics().bounds.width()


fun Fragment.showSnackbar(onRetry: () -> Unit): LifecycleEventObserver? {
    view?.let { parentView ->
        val snackbar = Snackbar.make(
            parentView,
            getString(R.string.error_message),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.retry) { onRetry() }

        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                snackbar.dismiss()
            }
        }

        lifecycle.addObserver(lifecycleObserver)
        snackbar.show()
        return lifecycleObserver
    }
    return null
}

fun Fragment.getSpanCount() = when (getDeviceType()) {
    DeviceType.Phone -> 2
    DeviceType.Tablet -> 3
}

private fun Fragment.getMetrics() = WindowMetricsCalculator.getOrCreate()
    .computeCurrentWindowMetrics(requireActivity())

fun Fragment.getItemSize(spanCount: Int): Int {
    val itemWidth = getWidth() / spanCount
    val spaceCount = if (isPortrait()) spanCount else spanCount + 1
    val margins = resources.getDimensionPixelSize(R.dimen.spacing) * spaceCount
    return itemWidth - margins
}

private fun Fragment.toDp(v: Int) = v / resources.displayMetrics.density