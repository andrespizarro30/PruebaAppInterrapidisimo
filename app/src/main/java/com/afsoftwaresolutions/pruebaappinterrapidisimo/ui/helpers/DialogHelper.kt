package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.helpers

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogHelper {
    fun showDialog(context: Context, dialogTitle: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(dialogTitle)
            .setMessage(message)
            .setPositiveButton(context.getString(android.R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .show()
    }
}