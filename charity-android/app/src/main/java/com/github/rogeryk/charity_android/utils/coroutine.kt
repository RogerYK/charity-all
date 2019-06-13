package com.github.rogeryk.charity_android.utils

import android.app.Activity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@UseExperimental(ExperimentalCoroutinesApi::class)
val coroutineScope = MainScope()


 fun Activity.launch(
         context: CoroutineContext = EmptyCoroutineContext,
         start: CoroutineStart = CoroutineStart.DEFAULT,
         block: suspend CoroutineScope.() -> Unit
): Job {
     return coroutineScope.launch(context, start, block)
}
