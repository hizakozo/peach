package com.example.peachapi.domain

interface FCC<T> : Iterable<T> {
    val list: List<T>
    override fun iterator(): Iterator<T> = list.iterator()
    fun isEmpty(): Boolean = list.isEmpty()
    fun count(): Int = list.size
}