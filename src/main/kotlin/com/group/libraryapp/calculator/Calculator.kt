package com.group.libraryapp.calculator

class Calculator(
    var number: Int // public 하더라도 외부에서 setter 를 지양하는 식으로 하면 코드가 간결해진다.(custom getter 같은 코드가 필요없음
) {

//    val number: Int
//      get() = this._number

    fun add(operand: Int) {
        this.number += operand
    }

    fun minus(operand: Int) {
        this.number -= operand
    }

    fun multiply(operand: Int) {
        this.number *= operand
    }

    fun divide(operand: Int) {
        if (operand == 0) {
            throw  IllegalArgumentException("0으로 나눌 수 없습니다.")
        }
        this.number /= operand
    }

}