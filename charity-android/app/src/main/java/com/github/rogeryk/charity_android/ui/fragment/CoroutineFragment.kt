package com.github.rogeryk.charity_android.ui.fragment

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class CoroutineFragment : Fragment(), CoroutineScope by CoroutineScope(Dispatchers.Main)