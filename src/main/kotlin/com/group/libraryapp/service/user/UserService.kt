package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {

    //트랜젝션 어노테이션을 사용하려면 함수가 상속가능한 형태여야 하는데 코틀린에서는 기본적으로
    //함수 , 클래스 모두 final 즉, 상속 불가능한 형태이기때문에 build.gradle에 플러그인을 추가해주었다.
    //해당 플러그인 없이 하려면 open 키워드를 메소드마다 붙혀서 상속가능하게 열어줘야한다(코드가 더러워짐)
    @Transactional
    fun saveUser(request: UserCreateRequest) {
        val newUser = User(request.name , request.age)
        userRepository.save(newUser)
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<UserResponse> {
        return userRepository.findAll()
            .map { user -> UserResponse(user) } //여러가지 방법이 있지만 이렇게 명시하는게 좋음
//            .map { UserResponse(it) }
//            .map(::UserResponse) //코틀린에서 생성자를 부를때 "::클래스" 이렇게쓴다.(자바에서는 클래스::new)
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest) {
        val user = userRepository.findById(request.id).orElseThrow(::IllegalArgumentException)
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val user = userRepository.findByName(name).orElseThrow(::IllegalArgumentException)
        userRepository.delete(user)
    }
}