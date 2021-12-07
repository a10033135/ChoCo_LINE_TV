package idv.fan.choco.utils

import android.content.Context
import android.text.format.DateFormat
import idv.fan.choco.R
import java.util.*

fun Date.toMovieFormat(context: Context): String {
    return DateFormat.format(context.getString(R.string.movie_date_format), this).toString()
}