package com.github.rogeryk.charity_android.ui.login

/**
 * User details post authentication that is exposed castTo the UI
 */
data class LoggedInUserView(
        val displayName: String
        //... other data fields that may be accessible castTo the UI
)
