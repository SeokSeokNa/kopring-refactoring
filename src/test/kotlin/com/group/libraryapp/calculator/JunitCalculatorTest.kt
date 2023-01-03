package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JunitCalculatorTest {

    @Test
    fun addTest() {
        //given
        val calculator =  Calculator(5)

        //when
        calculator.add(3)

        //then
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minusTest() {
        //given
        val calculator =  Calculator(5)

        //when
        calculator.minus(3)

        //then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun multiplyTest() {
        //given
        val calculator =  Calculator(5)

        //when
        calculator.multiply(3)

        //then
        assertThat(calculator.number).isEqualTo(15)
    }

    @Test
    fun divideTest() {
        //given
        val calculator =  Calculator(5)

        //when
        calculator.divide(2)

        //then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun divideExceptionTest() {
        //given
        val calculator =  Calculator(5)

        //when & then
//        val message = assertThrows<IllegalArgumentException> { //assertThrows 를 이용해 <> 안에 결과로 기대되는 익셉션을 넣어줘서 검증
//            calculator.divide(0) //익셉션이 발생하기 위한 코드
//        }.message // 익셉션의 메세지 가져오기
//
//        assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")

        /* apply 를 이용하기 */
        assertThrows<IllegalArgumentException> { //assertThrows 를 이용해 <> 안에 결과로 기대되는 익셉션을 넣어줘서 검증
            calculator.divide(0) //익셉션이 발생하기 위한 코드
        }.apply {
            assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
        }


    }
}