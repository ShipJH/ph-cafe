package ph.cafe.io.domain.user

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ph.cafe.io.domain.user.model.UserDto

@RestController
class UserController(
    private val userService: UserService
) {


    @PostMapping("/signUp")
    fun signUp(@RequestBody @Valid request: UserDto.SignUpRequest) {
        userService.signUp(request)
    }


}