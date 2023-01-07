package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor( //bean 주입을 받을때 생성자에 @Autowired를 붙히면 필드에서는 @Autowired생략 가능
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val userLoanHiRepository: UserLoanHistoryRepository
) {


    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }
    @Test
    @DisplayName("유저 저장이 정상 동작한다.")
    fun saveUserTest() {
        //given
        val request = UserCreateRequest("나원석" , null)

        //when
        userService.saveUser(request)

        //then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("나원석")
        assertThat(results[0].age).isNull()
    }


    @Test
    @DisplayName("유저 조회가 정상 동작한다.")
    fun getUsersTest() {
        //given
        userRepository.saveAll(listOf(
            User("A", 20),
            User("B", null)
        ))

        //when
        val results = userService.getUsers()

        //then
        assertThat(results).hasSize(2)
        assertThat(results).extracting("name") // ["A" , "B"] 형태로 만들어줌
            .containsExactlyInAnyOrder("A" , "B")

        assertThat(results).extracting("age").containsExactlyInAnyOrder(20 , null)
    }


    @Test
    @DisplayName("유저 업데이트가 정상 동작한다.")
    fun updateUserNametest() {
        //given
        val savedUser = userRepository.save(User("A", null))
        val request = UserUpdateRequest(savedUser.id!! , "B")

        //when
        userService.updateUserName(request)

        //then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    @DisplayName("유저 삭제가 정상 동작한다.")
    fun deleteUsertest() {
        //given
        userRepository.save(User("A", null))

        //when
        userService.deleteUser("A")

        //then
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 없는 유저도  응답에 포함된다.")
    fun getUserLoanHistoriesTest1() {
        //given
        userRepository.save(User("A" , null))

        //when
        val results = userService.getUserLoanHistories()


        //then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 동작한다.")
    fun getUserLoanHistoriesTest2() {
        //given
        val savedUser = userRepository.save(User("A", null))
        userLoanHiRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser , "책1" , UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser , "책2" , UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser , "책3" , UserLoanStatus.RETURNED),
        ))

        //when
        val results = userService.getUserLoanHistories()


        //then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name").containsExactlyInAnyOrder("책1","책2","책3")
        assertThat(results[0].books).extracting("isReturn").containsExactlyInAnyOrder(false,false,true)
    }


    //테스트 할 부분을 합치지 말고 단위별로 하나씩 하는게 좋다, 고로 이 방법은 추천하지 않음!!
    //복잡한 테스트 1개보다 작은 테스트 N개로 하는게 좋음
    //테스트 코드가 복잡해질 뿐 아니라 테스트 중간에 실패할 경우 뒤에 테스트코드가 정상적인지 알 수 없음
//    @Test
//    @DisplayName("위 두경우를 같이 테스트하기 , 단 이 방법은 크게 추천하지 않는다.")
//    fun getUserLoanHistoriesTest3() {
//        //given
//        val savedUsers = userRepository.saveAll(listOf(
//            User("A", null),
//            User("B", null),
//        ))
//        userLoanHiRepository.saveAll(listOf(
//            UserLoanHistory.fixture(savedUsers[0] , "책1" , UserLoanStatus.LOANED),
//            UserLoanHistory.fixture(savedUsers[0] , "책2" , UserLoanStatus.LOANED),
//            UserLoanHistory.fixture(savedUsers[0] , "책3" , UserLoanStatus.RETURNED),
//        ))
//
//        //when
//        val results = userService.getUserLoanHistories()
//
//
//        //then
//        assertThat(results).hasSize(2)
//        val userAResult = results.first {it.name == "A"} //A 유저 테스트
//
//        assertThat(userAResult.books).hasSize(3)
//        assertThat(userAResult.books).extracting("name").containsExactlyInAnyOrder("책1","책2","책3")
//        assertThat(userAResult.books).extracting("isReturn").containsExactlyInAnyOrder(false,false,true)
//
//        val userBResult = results.first {it.name == "B"} //B 유저 테스트
//        assertThat(userBResult.books).isEmpty()
//    }



}