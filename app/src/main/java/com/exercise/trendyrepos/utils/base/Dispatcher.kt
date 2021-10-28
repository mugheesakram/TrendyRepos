package com.exercise.trendyrepos.utils.base

sealed class Dispatcher {
    object Main : Dispatcher()
    object Background : Dispatcher()
    object LongOperation : Dispatcher()
}
