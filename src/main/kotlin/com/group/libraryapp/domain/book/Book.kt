package com.group.libraryapp.domain.book

import javax.persistence.*

@Entity
class Book(
    val name: String,

    //JPA 사용시 Enum 필드는 DB에 들어갈때 기본적으로 숫자로 들어가게 된다.
    //이렇게 되면 Enum에 정의한 값의 순서가 변경되면 꼬이게 되며 추가나 삭제할때 제한된다, 그렇기 때문에 꼭 String값을 사용할 것!
    @Enumerated(EnumType.STRING)
    val type: BookType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
    }

    // 해당 도메인에 필드가 하나 추가되면 영향받는 곳 중 하나인 테스트코드에서 최대한 유지보수가 편하게 하기 위해 테스트코드 만을 위한 정적팩토리 메소드를 만든다.
    // 해당 메소드를 사용하게 되면 테스트 코드에서 해당 메소드만 사용하여 객체를 생성하게 되어 크게 변경할 부분이 없어진다.
    // Object Mother 패턴 이라고 불리운다. ( 간단하게 test object를 만들어주는 class 라고 보면됨)
    // 해당 패턴으로 만들어진 객체는 test fixture 라고 부른다.
    companion object {
        fun fixture(
            name: String = "책 이름",
            type: BookType = BookType.COMPUTER,
            id: Long? = null
        ): Book {
            return Book(
                name = name,
                type = type,
                id = id,
            )
        }

    }
}