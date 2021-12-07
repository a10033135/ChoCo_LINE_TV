package idv.fan.choco.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import idv.fan.choco.R


fun ImageView.loadUrl(url: String, placeHolder: Int = R.drawable.image_placeholder) {
    if (url.isNotEmpty()) {
        Glide.with(this)
            .load(url)
            .placeholder(placeHolder)
            .into(this)
    } else {
        this.loadUrl(placeHolder)
    }
}

fun ImageView.loadUrl(imageRes: Int, placeHolder: Int = R.drawable.image_placeholder) {
    Glide.with(this)
        .load(imageRes)
        .placeholder(placeHolder)
        .into(this)
}