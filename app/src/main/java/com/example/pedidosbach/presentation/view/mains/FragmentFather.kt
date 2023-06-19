package com.example.pedidosbach.presentation.view.mains

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pedidosbach.R
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.time.LocalDateTime
import java.util.Calendar
import java.util.TimeZone

open class FragmentFather : Fragment() {
    val buttonColor = "background_button"
    val buttonColorNull = "background_button_null"

    open fun pathColorButton(uri: String, ctx: Context): Drawable? {
        val context = requireActivity().applicationContext
        val gradientResource =
            resources.getIdentifier(uri, "drawable", context.packageName)
        return ContextCompat.getDrawable(ctx, gradientResource)
    }

    open fun opaqueImage(context: Context, image: ImageView) {
        image.setColorFilter(
            ContextCompat.getColor(context, R.color.colorBlackTransparent),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    open fun getFileExtension(uri: Uri, context: Context): String? {
        val cR = context.contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    fun getDateTime(): Calendar {
        val zonaHorariaLocal = TimeZone.getDefault()
        return Calendar.getInstance(zonaHorariaLocal)
    }

    fun getDate(): String{
        val day= getDateTime().get(Calendar.DATE)
        val mount = getDateTime().get(Calendar.MONTH) + 1
        val year = getDateTime().get(Calendar.YEAR)
        return "$day/$mount/$year"
    }

    fun getTime(): String{
        val hour = getDateTime().get(Calendar.HOUR_OF_DAY)
        val minute = getDateTime().get(Calendar.MINUTE)
        return "$hour:$minute"
    }

    open fun configSwipeRefresh(swipe: SwipeRefreshLayout, context: Context) {
        swipe.setColorSchemeColors(ContextCompat.getColor(context, R.color.white))
        swipe.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(context, R.color.orange)
        )
    }

    fun showSnackbarPay(viewFrag: View, message: String, background: Int, text: Int) {
        try {
            val snackbar = Snackbar.make(viewFrag, message, Snackbar.LENGTH_LONG)
            val snackbarView = snackbar.view
            val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView

            snackbarView.setBackgroundColor(resources.getColor(background))
            snackbar.setTextColor(resources.getColor(text))
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackbar.show()
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }
    }
}