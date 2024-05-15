package com.example.fibonacciassignment.presentation

import android.util.Log
import com.example.fibonacciassignment.domain.Fibonacci

data class MainUiState(
    val mainList: List<Fibonacci> = listOf(),
    val subList: List<Fibonacci> = listOf(),
    val circleFibonacciList: List<Fibonacci> = listOf(),
    val squareFibonacciList: List<Fibonacci> = listOf(),
    val crossFibonacciList: List<Fibonacci> = listOf(),
    val addedFibonacci: Fibonacci? = null,
    val poppedFibonacci: Fibonacci? = null
) {

    val addedFibonacciIndex: Int?
        get() {
            val index = addedFibonacci?.let {
                subList.indexOf(it)
            } ?: -1

            return if (index == -1) null
            else index
        }

    val poppedFibonacciIndex: Int?
        get() {
            val index = poppedFibonacci?.let {
                mainList.indexOf(it)
            } ?: -1

            return if (index == -1) null
            else index
        }

    fun update(
        mainList: List<Fibonacci> = this.mainList,
        subList: List<Fibonacci> = this.subList,
        circleFibonacciList: List<Fibonacci> = this.circleFibonacciList,
        squareFibonacciList: List<Fibonacci> = this.squareFibonacciList,
        crossFibonacciList: List<Fibonacci> = this.crossFibonacciList,
        addedFibonacci: Fibonacci? = this.addedFibonacci,
        poppedFibonacci: Fibonacci? = this.poppedFibonacci,
    ): MainUiState = MainUiState(
        mainList = mainList,
        subList = subList,
        circleFibonacciList = circleFibonacciList,
        squareFibonacciList = squareFibonacciList,
        crossFibonacciList = crossFibonacciList,
        addedFibonacci = addedFibonacci,
        poppedFibonacci = poppedFibonacci
    )
}