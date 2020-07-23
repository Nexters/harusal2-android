package com.nexters.zzallang.harusal2.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseRepository : CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job
}