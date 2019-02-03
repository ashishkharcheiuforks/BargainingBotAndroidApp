package com.example.shounak.bargainingbot.internal

import kotlinx.coroutines.*

/**
 * Created by Shounak on 02-Feb-19
 */

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}