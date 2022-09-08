package com.news_app_nandhakumar.newsapp.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.news_app_nandhakumar.newsapp.R
import com.news_app_nandhakumar.newsapp.databinding.CustomLoaderLayoutBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class Loader @Inject constructor(@ActivityContext var context: Context) {
    private val dialog = Dialog(context)
    private var binding: CustomLoaderLayoutBinding? = null

    init {
        binding = CustomLoaderLayoutBinding.inflate(LayoutInflater.from(context), null, false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(binding!!.root)
        dialog.window!!.attributes.windowAnimations = R.style.dialogAnimation
        dialog.setCanceledOnTouchOutside(false)
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        if (dialog.isShowing)
            dialog.dismiss()
    }

}