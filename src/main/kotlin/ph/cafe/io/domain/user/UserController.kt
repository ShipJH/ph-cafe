package ph.cafe.io.domain.user

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ph.cafe.io.common.ResponseDto
import ph.cafe.io.domain.user.model.UserDto

@RestController
class UserController(
    private val userService: UserService
) {


    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid request: UserDto.SignUpRequest): ResponseDto.Response {
        return userService.signUp(request)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody @Valid request: UserDto.LoginRequest): ResponseDto.Response {
        return userService.signIn(request)
    }

    @PostMapping("/sign-out")
    fun signOut(): ResponseDto.Response {
        return userService.signOut()
    }

    @PostMapping("/reissue")
    fun reissue(@RequestBody @Valid request: UserDto.ReissueRequest): ResponseDto.Response {
        return userService.reissue(request)
    }

    @GetMapping("testUser")
    fun testUser(): String {
        return "OK~~~~"
    }


}