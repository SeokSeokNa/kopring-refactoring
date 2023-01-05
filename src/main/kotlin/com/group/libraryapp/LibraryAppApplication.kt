package com.group.libraryapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryAppApplication

//코틀린에서는 함수를 클래스 최상단에 만들경우 해당 함수는 static으로 감지됨!!!
fun main(args: Array<String>) {
    //스프링이 코틀린을 대응하기 위해 SpringBootApplication의 확장함수를 사용할 수 있게 했는데
    // 해당함수가 runApplication 이다.
    runApplication<LibraryAppApplication>(*args) // "*"는 코틀린 문법으로 배열을 가변인자(a,b,c..) 처럼 사용할 수 있게 해주는것이다.
}