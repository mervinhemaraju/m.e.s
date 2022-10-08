package com.th3pl4gu3.mes.ui

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.th3pl4gu3.mes.NavigationDrawerMainDirections
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.databinding.ActivityMesBinding
import com.th3pl4gu3.mes.ui.utils.helpers.Global
import com.th3pl4gu3.mes.ui.utils.extensions.*
import com.th3pl4gu3.mes.ui.utils.listeners.PhoneNumberListener
import java.util.*

class MesActivity : AppCompatActivity(), PhoneNumberListener {

    private val mBinding: ActivityMesBinding by contentView(R.layout.activity_mes)
    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private var mNetworkCallbackRegistered = false

    private var mPendingServiceRedirection: Service? = null

    companion object {
        private const val REQUEST_CODE_PHONE_CALL_PERMISSION = 34
    }

    // The network callback to update network connectivity
    private val mNetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            // Sets a global variable to true
            Global.isNetworkConnected = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            // Sets a global variable to false
            Global.isNetworkConnected = false
        }
    }

    /*
    * Fragments listed here are
    * eligible for opening the navigation drawer
    * All other fragments not listed here will get the
    * back button instead of the hamburger menu icon
    * ONLY list fragments that can open the drawer menu here
    */
    private val mNavigationFragments = setOf(
        R.id.Fragment_Emergencies,
        R.id.Fragment_All_Services
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        /* Re set the style to the main theme app */
        setTheme(R.style.Mes_Theme)
        super.onCreate(savedInstanceState)

        // Custom toolbar configuration
        mesToolBarConfiguration(mBinding.ToolbarMain)

        // Setup the JetPack Navigation UI
        navigationUISetup()

        // Check internet connection callback
        registerNetworkCallback()
    }

    override fun onResume() {
        super.onResume()

        /*
         * If network callback not registered (This will happen if user sends app to the background)
         * then register it again
        */
        if (!mNetworkCallbackRegistered) {
            registerNetworkCallback()
        }
    }

    override fun onPause() {
        super.onPause()

        unregisterNetworkCallback()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(
            item
        )

    override fun onSupportNavigateUp() = navController.navigateUp(mAppBarConfiguration)

    override fun onPhoneNumberClicked(service: Service) {
        /**
         * All phone call intents reply to this function
         * as single point of contact.
         * It will handle all permission request and phone call intents
         */
        // Using irrelevant number for testing purpose
        if (phoneCallPermissionApproved()) {
            navigateTo(NavigationDrawerMainDirections.actionGlobalFragmentPreCall(service))
        } else {
            // Put the phone call in pending state until permission approved
            mPendingServiceRedirection = service
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PHONE_CALL_PERMISSION -> when (PackageManager.PERMISSION_GRANTED) {
                grantResults[0] ->
                    // Permission was granted.
                    if (mPendingServiceRedirection != null) {
                        navigateTo(
                            NavigationDrawerMainDirections.actionGlobalFragmentPreCall(
                                mPendingServiceRedirection!!
                            )
                        )
                    }
                else -> {
                    // Permission denied.
                    if (shouldShowCallPhoneRequestPermissionRationale()) {
                        alertDialogPermissionRequestExplanation()
                    } else {
                        mBinding.LayoutCoordinatorMain.snack(getString(R.string.message_info_permission_denied)) {
                            action(getString(R.string.action_retry)) {
                                alertDialogExplicitPermissionRequest()
                            }
                        }
                    }
                }
            }
        }
    }

    /*
    * Private functions that are
    * called within the MES Activity
    * itself.
    */
    private fun navigationUISetup() {
        //Fetch the Nav Controller
        with(navController) {
            //Setup the App Bar Configuration
            mAppBarConfiguration = AppBarConfiguration(mNavigationFragments, mBinding.DrawerMain)

            //Use Navigation UI to setup the app bar config and navigation view
            NavigationUI.setupActionBarWithNavController(
                this@MesActivity,
                this,
                mAppBarConfiguration
            )
            NavigationUI.setupWithNavController(mBinding.NavigationView, this)

            //Add on change destination listener to navigation controller to handle fab visibility
            navigationDestinationChangeListener(this)
        }
    }

    private fun navigationDestinationChangeListener(navController: NavController) {
        navController.addOnDestinationChangedListener { _, nd, _ ->

            // Update the toolbar title
            mBinding.ToolbarMainTitle.text = nd.label

            // Update Soft Keyboard visibility
            // Only all services should be able to show a keyboard
            if (nd.id != R.id.Fragment_All_Services) {
                this.hideSoftKeyboard()
            }

            // Update Drawer Lock Mode
            when (nd.id) {
                R.id.Fragment_Emergencies,
                R.id.Fragment_All_Services -> {
                    mBinding.DrawerMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                else -> {
                    mBinding.DrawerMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }
    }

    private fun registerNetworkCallback() {
        try {
            // Get the connectivity manager
            with(this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
                // Register the network callback
                registerNetworkCallback(NetworkRequest.Builder().build(), mNetworkCallback)
            }

            // By default set network to false
            // If network is available, it will be automatically set
            // in the callback
            Global.isNetworkConnected = false

            // Says that the registration has been done
            mNetworkCallbackRegistered = true
        } catch (e: Exception) {
            Global.isNetworkConnected = false
        }
    }

    private fun unregisterNetworkCallback() {

        // Get the connectivity manager & unregister the network callback
        with(this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
            unregisterNetworkCallback(mNetworkCallback)
        }

        // Specifies that the registration has been undone
        mNetworkCallbackRegistered = false

    }

    private fun requestPermission() {
        // Provide an additional rationale to the user if the permission was not granted
        // and the user would benefit from additional context for the use of the permission.
        if (shouldShowCallPhoneRequestPermissionRationale()) {
            alertDialogPermissionRequestExplanation()
        } else {
            // Request permission
            triggerCallPhoneRequestPermissionLogic(
                REQUEST_CODE_PHONE_CALL_PERMISSION
            )
        }
    }

    // Alert Dialog Boxes
    private fun alertDialogPermissionRequestExplanation() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.title_permission_call_info))
            .setMessage(getString(R.string.message_permission_call_info))
            .setNeutralButton(getString(R.string.action_ask_me_later)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.action_allow)) { _, _ ->
                // Request permission
                triggerCallPhoneRequestPermissionLogic(
                    REQUEST_CODE_PHONE_CALL_PERMISSION
                )
            }
            .show()
    }

    private fun alertDialogExplicitPermissionRequest() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.title_permission_call_explicit))
            .setMessage(getString(R.string.message_permission_call_explicit))
            .setNegativeButton(getString(R.string.action_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.action_proceed)) { _, _ ->
                // Build intent that displays the App settings screen.
                startActivity(requireAppSettingsIntent())
            }
            .show()
    }
}