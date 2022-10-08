package com.th3pl4gu3.mes.ui.utils.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.ui.MesActivity

/**
 * Get the NavController in disregards
 * of the current user's activity
 **/
inline val AppCompatActivity.navController get() = findNavController(R.id.Navigation_Host)

/*
* MES Toolbar configuration
*/
fun AppCompatActivity.mesToolBarConfiguration(toolbar: MaterialToolbar) {
    /* Set the default action bar to our custom material toolbar */
    setSupportActionBar(toolbar)

    /*
    * Remove the default left title on the toolbar
    * We will provide our own title centered in the middle
    */
    supportActionBar?.setDisplayShowTitleEnabled(false)
}

fun Fragment.requireMesToolBar(): MaterialToolbar =
    requireMesActivity().findViewById(R.id.Toolbar_Main)

fun MaterialToolbar.hide() {
    this.visibility = View.GONE
}

fun MaterialToolbar.show() {
    this.visibility = View.VISIBLE
}


/**
 * Returns the MesActivity in particular
 */
fun Fragment.requireMesActivity() = requireActivity() as MesActivity


/**
 * Phone Call Permission Extensions
 */
fun AppCompatActivity.triggerCallPhoneRequestPermissionLogic(requestCode: Int) {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.CALL_PHONE),
        requestCode
    )
}

fun AppCompatActivity.phoneCallPermissionApproved(): Boolean =
    PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.CALL_PHONE
    )

fun AppCompatActivity.shouldShowCallPhoneRequestPermissionRationale() =
    shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)


/**
 * Navigation Architecture component made easy
 */
fun AppCompatActivity.navigateTo(directions: NavDirections) {
    findNavController(R.id.Navigation_Host).navigate(directions)
}

fun Fragment.navigateTo(directions: NavDirections) {
    findNavController().navigate(directions)
}

fun Fragment.pop() {
    findNavController().popBackStack()
}


/*
* Hides soft keyboard
*/
fun AppCompatActivity.hideSoftKeyboard() {
    val inputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}