package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import javax.persistence.*

@Entity
class User constructor( // "constructor" 를 명시적으로 쓰게되면 해당 엔티티를 생성하는 곳 만 빠르게 추적할 수 있다.!!!

    var name: String,

    val age: Int?,

    //cascade 파라미터는 배열로 되어있는데 코틀린에서는 배열을 넣을때 "[]" 로 하여 넣어야한다.
    @OneToMany(mappedBy = "user" , cascade = [CascadeType.ALL] , orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
) {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory(this , book.name , false))
    }

    fun returnBook(bookName: String) {
        this.userLoanHistories.first {history -> history.bookName == bookName}.doReturn()
    }
}