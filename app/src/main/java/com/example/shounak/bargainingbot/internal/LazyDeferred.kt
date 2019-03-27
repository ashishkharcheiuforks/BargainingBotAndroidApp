package com.example.shounak.bargainingbot.internal

import kotlinx.coroutines.*

/**
 * Function to call a deferred value lazily
 */

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}