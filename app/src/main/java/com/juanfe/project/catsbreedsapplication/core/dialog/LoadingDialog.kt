package com.juanfe.project.catsbreedsapplication.core.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.juanfe.project.catsbreedsapplication.R


object LoadingDialog {

    private var dialog: AlertDialog? = null
    @SuppressLint("InflateParams")
    fun create(
        context: Context,
    ) {
        if (dialog?.isShowing == true) return
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.loading_dialog, null)
        dialog = AlertDialog.Builder(context, R.style.TransparentDialog).apply {
            setView(dialogView)
            setCancelable(false)
        }.create()
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }


}
