package com.stuempke.luzuatest.util

inline fun <T, R> Result<List<T>>.mapList(transform: (T) -> R): Result<List<R>> =
    this.map { list -> list.map(transform) }