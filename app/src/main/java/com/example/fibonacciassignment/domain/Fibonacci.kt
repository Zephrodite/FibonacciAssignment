package com.example.fibonacciassignment.domain

data class Fibonacci(
    val index: Int,
    val number: Int,
    val shape: Shape
) {

    enum class Shape {
        Circle,
        Square,
        Cross
    }

    companion object {
        fun fibonacciListExample(): List<Fibonacci> {
            return listOf(
                Fibonacci(index = 0, number = 0, shape = Shape.Circle),
                Fibonacci(index = 1, number = 1, shape = Shape.Square),
                Fibonacci(index = 2, number = 1, shape = Shape.Square),
                Fibonacci(index = 3, number = 2, shape = Shape.Cross),
                Fibonacci(index = 4, number = 3, shape = Shape.Circle),
                Fibonacci(index = 5, number = 5, shape = Shape.Cross),
            )
        }
    }

}