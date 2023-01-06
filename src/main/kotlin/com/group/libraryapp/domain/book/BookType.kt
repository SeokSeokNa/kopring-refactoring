package com.group.libraryapp.domain.book
/*
    Type 값을 String으로 관리하지 않고 Enum으로 관리하였을때 장점
    Enum 클래스를 만들어 DB에 어떠한 값들이 들어가있는지 한눈에 파악하고
    String 값을 코드상에서 직접 쓰지 않아 실수를 줄일 수 있게한다.
    따로 검증로직 필요없이 Enum 클래스에 있는 값들로 검증이 가능해진다.(오타 방지가능)

    추가적인 로직이 필요할때 Enum 에 필드를 넣어서 추가적인 로직을 간단히 만들수 있다
    (책 분야별 대출시 점수 배급하는 이벤트가 추가된다 하였을때 Enum 클래스에 점수 필드를 추가하여 해당 분야에 해당하는 값을 로직상에서 편하게 부를수 있음)

    Enum 클래스를 만들 3가지 위치
    1. 해당 enum 값과 관련있는 도메인과의 같은 패키지
    2. 해당 enum 값과 관련있는 도메인 안에
    3. 따로 패키지로 관리
 */
enum class BookType {
    COMPUTER,
    ECONOMY,
    SOCIETY,
    LANGUAGE,
    SCIENCE
    ;
}