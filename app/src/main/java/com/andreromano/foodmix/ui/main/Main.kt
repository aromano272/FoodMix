package com.andreromano.foodmix.ui.main

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    GlobalScope.launch {
        delay(1000L)
        println("bla")
    }
    
    runBlocking {
        println("hello")
        delay(2000L)
    }


}