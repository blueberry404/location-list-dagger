package com.anum.locdagger.helpers

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.annotation.StringRes

fun showAlert(context: Context, title: String, message: String,
              okButton: String, okButtonListener: DialogInterface.OnClickListener,
              cancelButton: String, cancelButtonListener: DialogInterface.OnClickListener) {

    val builder = AlertDialog.Builder(context)
    with(builder) {
        setTitle(title)
        setMessage(message)
        setPositiveButton(okButton, okButtonListener)
        setNegativeButton(cancelButton, cancelButtonListener)
        show()
    }
}

fun showToast(context: Context?, message: String) {
    context?.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }
}

fun showToast(context: Context?, @StringRes message: Int) {
    context?.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }
}
