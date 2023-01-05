package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?
) {

    //정적 팩토리 방식을 이용한 객체 생성방법(static)
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.id!!, //user entity에는 id가 null일 수 있지만 UserResponse 클래스는 id 필드가 non-null 이기에 "!!" 를 붙혀줌
                name = user.name,
                age = user.age
            )
        }
    }


    //부 생성자를 이용한 객체생성법
//    constructor(user: User): this(
//        id = user.id!!, //user entity에는 id가 null일 수 있지만 UserResponse 클래스는 id 필드가 non-null 이기에 "!!" 를 붙혀줌
//        name = user.name,
//        age = user.age
//    )

}