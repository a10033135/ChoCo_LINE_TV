package idv.fan.choco.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import idv.fan.choco.R

object DialogFactory {
    fun createPopDialog(context: Context, message: String, listener: DialogInterface.OnClickListener): AlertDialog {
        val dialog = AlertDialog.Builder(context)
            .setMessage(message)
            .setNegativeButton(context.getString(R.string.btn_confirm), listener)
            .setCancelable(false)
        return dialog.create()
    }
}