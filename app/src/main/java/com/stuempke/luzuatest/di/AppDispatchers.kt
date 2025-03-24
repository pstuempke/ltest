package com.stuempke.luzuatest.di

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher
)