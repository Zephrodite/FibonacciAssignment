package com.example.fibonacciassignment.util

import com.example.fibonacciassignment.domain.Fibonacci
import kotlin.random.Random

object FibonacciUtil {

    private const val TERMS_OF_FIBONACCI = 40

    private fun getAssociatedShape(): Fibonacci.Shape {
        val shapes = Fibonacci.Shape.entries.toTypedArray()
        val randomIndex = Random.nextInt(shapes.size)

        return shapes[randomIndex]
    }

    private fun getFibonacciNumber(number: Int): Int {
        if (number <= 1) {
            return number
        }

        return getFibonacciNumber(number - 1) + getFibonacciNumber(number - 2)
    }

    fun getFibonacciList(terms: Int = TERMS_OF_FIBONACCI): List<Fibonacci> {
        val fibonacciList = mutableListOf<Fibonacci>()

        for (i in 0..terms) {
            val fibonacciNumber = getFibonacciNumber(i)

            fibonacciList.add(
                Fibonacci(
                    index = i,
                    number = fibonacciNumber,
                    shape = getAssociatedShape()
                )
            )
        }

        return fibonacciList.toList()
    }

}