package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.service.user.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/user")
    fun saveUser(@RequestBody request: UserCreateRequest) {
        userService.saveUser(request)
    }

    @GetMapping("/user")
    fun getUsers(): List<UserResponse> = userService.getUsers() //코틀린 함수 문법을 이용하여 "=" 으로 값을 리턴할 수 있음



    @PutMapping("/user")
    fun updateUserName(@RequestBody request: UserUpdateRequest) {
        userService.updateUserName(request)
    }

    @DeleteMapping("/user")
    //@RequestParam 의 해당하는 파라미터가 null을 허용할 경우 RequestParam 안에있는 "required" 를 스프링이 자동으로 false로 하게 되어 해당 파라미터는 필수가 아닌 선택이라고 받아들인다.
    // 하지만 deleteUser에 들어가는 파라미터는 null이면 안되기에 non-null 타입으로 사용한다.
    fun deleteUser(@RequestParam name: String) {
        userService.deleteUser(name)
    }
}