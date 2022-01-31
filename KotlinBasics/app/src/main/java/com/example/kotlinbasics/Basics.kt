package com.example.kotlinbasics

fun main() {
    var brandon = Person("Brandon", "Redmond")
}

class Person (firstName: String, lastName: String) {

    init {
        println("Person Created")
    }
}