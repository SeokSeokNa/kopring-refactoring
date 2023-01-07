package com.group.libraryapp.domain.book

import com.group.libraryapp.dto.book.response.BookStatResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface BookRepository : JpaRepository<Book , Long> {

    fun findByName(bookName: String): Book?

    //JPQL 을 이용하여 DTO 객체로 바로 반환하기 위해서는 new 키워드와 해당 DTO 의 패키지를 모두 써줘야함
    @Query("SELECT NEW com.group.libraryapp.dto.book.response.BookStatResponse(b.type , COUNT(b.id)) FROM Book b GROUP BY b.type")
    fun getStats(): List<BookStatResponse>
}