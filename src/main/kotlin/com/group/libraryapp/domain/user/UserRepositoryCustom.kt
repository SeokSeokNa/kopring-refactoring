package com.group.libraryapp.domain.user

interface UserRepositoryCustom {

    fun findAllWithHiostories(): List<User>

}