package com.andreromano.foodmix.database

interface TransactionRunner {
    suspend fun run(block: suspend () -> Unit)
}