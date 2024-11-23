package m.ify.computersciencebouira.Helpers

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import m.ify.computersciencebouira.R

class ImageLoader {
    fun loadImageToView(url: String, imageView: ImageView) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.book)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }

    fun loadAvatar(url: String, imageView: ImageView) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.avatar)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }
}
