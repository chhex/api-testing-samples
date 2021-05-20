package com.apgsga.apitestingsample

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CalculatorTests() {

    @Test
    fun  `Assert some Calculations`() {
        val calculator = Calculator()
        var calculation = calculator.calculate("1+1")
        Assertions.assertThat(calculation.compareTo(2))
        println("Result : ${calculation}")
        calculation = calculator.calculate("1*1")
        Assertions.assertThat(calculation.compareTo(1))
        println("Result : ${calculation}")
        calculation = calculator.calculate("2*2")
        Assertions.assertThat(calculation.compareTo(4))
        println("Result : ${calculation}")




    }

}