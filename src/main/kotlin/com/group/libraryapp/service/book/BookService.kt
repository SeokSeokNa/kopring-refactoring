package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @Transactional
    fun saveBook(request: BookRequest) {
        val book = Book(request.name , request.type)
        bookRepository.save(book)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest) {
        val book = bookRepository.findByName(request.bookName) ?: fail()
        if (userLoanHistoryRepository.findByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED) != null) {
            throw IllegalArgumentException("진작 대출되어 있는 책입니다")
        }

        val user = userRepository.findByName(request.userName) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest) {
        val user = userRepository.findByName(request.userName) ?: fail()
        user.returnBook(request.bookName)
    }

    //현재 대출중인 책의 개수
    @Transactional(readOnly = true)
    fun countLoanedBook(): Int {
        return userLoanHistoryRepository.findAllByStatus(UserLoanStatus.LOANED).size
    }

    //분야별 책 권수 통계
    @Transactional(readOnly = true)
    fun getBookStatistics(): List<BookStatResponse> {
        return bookRepository.findAll() // List<Book> 형태를 가져옴
            .groupBy { book -> book.type } //Map<BookType , List<Book>> 형태로 바뀜
            .map { (type , books) -> BookStatResponse(type , books.size) } // List<BookStatResponse> 형태가 됨



//        val results = mutableListOf<BookStatResponse>() // 반환할 빈 리스트 미리 만들어두기
//        val books = bookRepository.findAll() // 등록되어 있는 책 모두 가져오기
//
//        //책 리스트 루프 돌리며 통계 리스트 데이터 만들기
//        for (book in books) {
//          val targetResult = results.firstOrNull { dto -> book.type == dto.type } //통계 리스트안에 해당하는 분야가 있는지 체크
//            if (targetResult == null) { //없으면 분야 넣어주기(분야 , 개수 1개)
//                results.add(BookStatResponse(book.type, 1))
//            } else {
//                targetResult.plusOne() // 있다면 개수 1개 늘려주기
//            }
//        }
//
//        // 코틀린 문법을 이용하여 리펙토링 해보기
//        for (book in books) {
//            results.firstOrNull { dto -> book.type == dto.type }?.plusOne() // safety call 을 이용해 "firstOrNull" 결과가 null 이 아닐경우 만 plusOne() 함수 실행
//                ?: results.add(BookStatResponse(book.type, 1)) //앨리스 연산자를 이용하여 앞에 결과가 null일 경우 results.add 실행 하여 통계 데이터 넣기
//        }
//
//        return results
    }


}