package com.example.fibonacciassignment.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fibonacciassignment.domain.Fibonacci
import com.example.fibonacciassignment.util.FibonacciUtil.getFibonacciList

class MainViewModel : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    init {
        loadRequireData()
    }

    private fun loadRequireData() {
        val fibonacciList = getFibonacciList()

        uiState = uiState.update(mainList = fibonacciList)
    }

    fun onAddedItem(fibonacci: Fibonacci) {
        val mainList = uiState.mainList
            .toMutableList().apply { remove(fibonacci) }
        val newSubList = when (fibonacci.shape) {
            Fibonacci.Shape.Circle -> uiState.circleFibonacciList

            Fibonacci.Shape.Square -> uiState.squareFibonacciList

            Fibonacci.Shape.Cross -> uiState.crossFibonacciList
        }.toMutableList()
        val addIndex = newSubList.indexOfFirst { it.index > fibonacci.index }

        if (addIndex >= 0) {
            newSubList.add(addIndex, fibonacci)
        } else {
            newSubList.add(fibonacci)
        }

        uiState = when (fibonacci.shape) {
            Fibonacci.Shape.Circle -> uiState.update(circleFibonacciList = newSubList)

            Fibonacci.Shape.Square -> uiState.update(squareFibonacciList = newSubList)

            Fibonacci.Shape.Cross -> uiState.update(crossFibonacciList = newSubList)
        }

        uiState = uiState.update(
            mainList = mainList,
            subList = newSubList.toList(),
            addedFibonacci = fibonacci
        )
    }

    fun onPoppedItem(fibonacci: Fibonacci, ) {
        val mainList = uiState.mainList.toMutableList()
        val addIndex = mainList.indexOfFirst { it.index > fibonacci.index }

        if (addIndex >= 0) {
            mainList.add(addIndex, fibonacci)
        } else {
            mainList.add(fibonacci)
        }

        val newSubList = when (fibonacci.shape) {
            Fibonacci.Shape.Circle -> uiState.circleFibonacciList

            Fibonacci.Shape.Square -> uiState.squareFibonacciList

            Fibonacci.Shape.Cross -> uiState.crossFibonacciList
        }.toMutableList()

        newSubList.remove(fibonacci)

        uiState = when (fibonacci.shape) {
            Fibonacci.Shape.Circle -> uiState.update(circleFibonacciList = newSubList)

            Fibonacci.Shape.Square -> uiState.update(squareFibonacciList = newSubList)

            Fibonacci.Shape.Cross -> uiState.update(crossFibonacciList = newSubList)
        }

        uiState = uiState.update(
            mainList = mainList,
            subList = newSubList.toList(),
            poppedFibonacci = fibonacci
        )
    }

}