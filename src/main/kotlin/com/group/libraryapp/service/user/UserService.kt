package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.BookHistoryResponse
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.util.fail
import com.group.libraryapp.util.findByIdOrThrow
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
            .map { user -> UserResponse.of(user) } //여러가지 방법이 있지만 이렇게 명시하는게 좋음
//            .map { user -> UserResponse(user) } //여러가지 방법이 있지만 이렇게 명시하는게 좋음
//            .map { UserResponse(it) }
//            .map(::UserResponse) //코틀린에서 생성자를 부를때 "::클래스" 이렇게쓴다.(자바에서는 클래스::new)
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest) {
        //스프링에서 코틀린과의 호환성을 위해 "CrudRepository" 의 확장함수로 만들어둔 findByIdOrNull 을 이용하여 Optional 을 제거함
        //val user = userRepository.findByIdOrNull(request.id) ?: fail()

        //확장함수를 직접만들어 처리하기
        //userRepository 는 "CrudRepository" 를 상속 받고있으니 내가만든 ExceptionUtils.kt 에 정의해둔 "CrudRepository"의 확장함수를 사용할 수 있다.
        // (마치 CrudRepository에 findByIdOrThrow가 있는것 처럼)
        val user = userRepository.findByIdOrThrow(request.id) ?: fail()
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val user = userRepository.findByName(name) ?: fail()
        userRepository.delete(user)
    }

    @Transactional(readOnly = true)
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userRepository.findAllWithHiostories()
            .map(UserLoanHistoryResponse::of) //정적 팩토리 메소드를 이용하여 코드 리펙토링
    }
}