package com.group.libraryapp.domain.user.loanhistory

import com.group.libraryapp.domain.user.User
import javax.persistence.*

@Entity
class UserLoanHistory(

    @ManyToOne
    val user: User,

    val bookName: String,

    //추가 요구사항 -> 반납여부가 아닌 책의 대출 상태로 변경해달라
    //대출 상태 관련 필드(기본적으로 UserLoanHistory 객체가 생성된다는건 책을 대출한다는 뜻이니 기본값으로 LOANED 를 써줌)
    @Enumerated(EnumType.STRING)
    var status: UserLoanStatus = UserLoanStatus.LOANED,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
) {

    val isReturn: Boolean
      get() = this.status == UserLoanStatus.RETURNED

    fun doReturn() {
        this.status = UserLoanStatus.RETURNED
    }

    companion object {
        fun fixture(
            user: User,
            bookName: String = "이상한 나라의 엘리스",
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null
        ): UserLoanHistory {
            return UserLoanHistory(
                user = user,
                bookName = bookName,
                status = status,
                id = id
            )
        }
    }
}