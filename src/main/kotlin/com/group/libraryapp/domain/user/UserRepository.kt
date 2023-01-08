package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User , Long> , UserRepositoryCustom {

    fun findByName(name: String): User?


//    /*
//        N+1 문제 해결하기
//        기존 findAll 메소드 사용시 유저 전체조회 쿼리 1번 + 조회된 유저수 만큼 history 내역 N번 쿼리 가 나가 N+1 문제가 생겼다.
//        JPQL의 Fetch Join 을 이용하여 sql의 join을 사용하여 한번의 쿼리만 나가 N+1 문제를 해결한다!
//
//        "FETCH" 를 사용해줘야 User 도메인에 userLoanHistories 필드안에 조인한 값을 넣어줄 수 있다
//        Fetch를 사용하지 않으면 결국 Lazy Fetching 이 발생하여 해당 필드에 접근시 쿼리가 한번 더 나가게 된다(history를 조회하는 쿼리 , N+1 문제 결국 또 발생!!)
//     */
//    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userLoanHistories")
//    fun findAllWithHistories(): List<User>
}