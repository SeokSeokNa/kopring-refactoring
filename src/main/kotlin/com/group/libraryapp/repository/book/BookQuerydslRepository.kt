package com.group.libraryapp.repository.book

import com.group.libraryapp.domain.book.QBook.book
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class BookQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun getStats(): List<BookStatResponse> {
        //특정 클래스로 반환할때 생성자를 호출하며 특정 컬럼 몇개만 가져오기 위해 Projections 클래스 사용( 몇개 컬럼만 가져온다 할때 Projection 이라고 표현함)
        return queryFactory.select(Projections.constructor(
            BookStatResponse::class.java, //생성자를 호출할 특정 클래스
            book.type, //컬럼1
            book.id.count()//컬럼2
        ))
            .from(book)
            .groupBy(book.type)
            .fetch()
    }
}