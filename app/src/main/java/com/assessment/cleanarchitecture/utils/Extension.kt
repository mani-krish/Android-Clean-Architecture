package com.assessment.cleanarchitecture.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.assessment.cleanarchitecture.R
import com.google.android.material.snackbar.Snackbar

/*Inflate the dialog for failure*/
fun Activity.showErrorDialog() {
    AlertDialog.Builder(this).setTitle(getString(R.string.title_alert))
        .setMessage(getString(R.string.message_general_error))
        .setNegativeButton(R.string.cancel) { dialog, id ->
            dialog.cancel()
            finish()
        }.create().show()
}

fun Activity.showDialogExit() {
    AlertDialog.Builder(this)
        .setMessage(getString(R.string.message_app_exit))
        .setPositiveButton(R.string.yes) { dialog, id ->
            dialog.cancel()
            finish()
        }.setNegativeButton(R.string.no) { dialog, id ->
            dialog.cancel()
        }.create().show()
}

/*Inflate the dialog for internet connectivity issue*/
fun Activity.showDialog() {
    AlertDialog.Builder(this).setTitle(getString(R.string.title_alert))
        .setMessage(getString(R.string.message_internet_error))
        .setPositiveButton(R.string.ok) { dialog, id ->
            dialog.cancel()
            finish()
        }.create().show()
}

fun Context.getString(stringResId: Int): String = resources.getString(stringResId)

/*Extension method to show the toast*/
fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

/*Extension method to show the snackBar*/
fun View.showSnackBar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).show()
}

/*Extension method to make the view gone*/
fun View.gone() {
    visibility = View.GONE
}

/*Extension method to make the view visible*/
fun View.visible() {
    visibility = View.VISIBLE
}

/*Extension method to make the view invisible*/
fun View.invisible() {
    visibility = View.INVISIBLE
}

val Context.isInternetAvailable: Boolean
    get() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return run {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
    }