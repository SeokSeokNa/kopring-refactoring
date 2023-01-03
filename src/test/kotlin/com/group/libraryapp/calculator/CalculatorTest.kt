package com.group.libraryapp.calculator

import java.lang.IllegalStateException

fun main() {
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
    calculatorTest.minusTest()
    calculatorTest.multiplyTest()
    calculatorTest.divideTest()
    calculatorTest.divideExceptionTest()
}

class CalculatorTest {

    fun addTest() {
        //given
        val calculator = Calculator(5)
        //when
        calculator.add(3)

        //then
        //Calculator가 data 클래스 일 경우 equals 메소드가 자동구현 되어있기에 값 비교가 가능함
//        val expectedCalculator = Calculator(8)
//        if (calculator != expectedCalculator) {
//            throw  IllegalArgumentException()
//        }

        //Calculator가 data 클래스 아닐 경우 테스트 방법(custom getter 를 이용해 필드에 접근)
        if (calculator.number != 8) {
            throw  IllegalArgumentException()
        }
    }


    fun minusTest() {
        //given
        val calculator = Calculator(5)
        //when
        calculator.minus(3)
        //then
        if (calculator.number != 2) {
            throw  IllegalArgumentException()
        }
    }

    fun multiplyTest() {
        //given
        val calculator = Calculator(5)
        //when
        calculator.multiply(3)
        //then
        if (calculator.number != 15) {
            throw  IllegalArgumentException()
        }
    }

    fun divideTest() {
        //given
        val calculator = Calculator(5)
        //when
        calculator.divide(2)
        //then
        if (calculator.number != 2) {
            throw  IllegalArgumentException()
        }
    }

    fun divideExceptionTest() {
        //given
        val calculator = Calculator(5)

        //when
        try {
            calculator.divide(0)
        } catch (e: IllegalArgumentException) {
            if (e.message != "0으로 나눌 수 없습니다.") {
                throw IllegalStateException("메세지가 다릅니다.")
            }
            //테스트 성공!!
            return
        } catch (e: Exception) {
            throw IllegalStateException()
        }

        throw IllegalStateException("기대하는 예외가 발생하지 않았습니다.")


    }
}