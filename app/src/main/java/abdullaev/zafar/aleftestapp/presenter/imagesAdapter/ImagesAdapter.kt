package abdullaev.zafar.aleftestapp.presenter.imagesAdapter

import abdullaev.zafar.aleftestapp.R
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImagesAdapter(
    private val itemSize: Int,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    private var mData = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.image_item, parent, false)
        return ImagesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val url = mData.getOrNull(position)

        holder.imageView.layoutParams =
            ViewGroup.LayoutParams(itemSize, itemSize)

        Glide.with(holder.itemView.context)
            .load(url)
            .error(android.R.drawable.stat_notify_error)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView)

        holder.itemView.setOnClickListener { mData.getOrNull(position)?.let(onItemClicked) }
    }

    override fun getItemCount() = mData.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<String>) {
        mData = data
        notifyDataSetChanged()
    }

    inner class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_image_view)
    }

}