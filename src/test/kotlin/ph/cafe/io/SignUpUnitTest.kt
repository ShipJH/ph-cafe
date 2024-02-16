package ph.cafe.io

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import ph.cafe.io.common.Constants.EMPTY

internal class SignUpUnitTest: BehaviorSpec({

    // @NotBlank 설정하여 발생하지 않음.
    Given("핸드폰 번호 비었을 경우") {
        val emptyPhoneNumber = ""
        val nullPhoneNumber = null
        When("핸드폰 번호가 빈값 을 넣으면.") {
            Then("IllegalArgumentException 에러가 발생한다.") {
                assertThrows<IllegalArgumentException> { phoneNumberDegree(emptyPhoneNumber) }
            }
        }

        When("핸드폰 번호에 null 을 넣으면.") {
            Then("IllegalArgumentException 에러가 발생한다.") {
                assertThrows<IllegalArgumentException> { phoneNumberDegree(nullPhoneNumber) }
            }
        }
    }
    
    Given("핸드폰 번호에 - 가 포함되어 있을 경우") {
        val phoneNumber = "010-1234-5678"
        When("핸드폰 번호에 - 를 넣으면") {
            Then("true 를 반환한다.") {
                phoneNumberDegree(phoneNumber) shouldBe true
            }
        }
    }
    
    Given("핸드폰 번호에 - 가 포함되어 있지 않을 경우") {
        val phoneNumber = "01012345678"
        When("번호만 넣으면") {
            Then("true 를 반환한다.") {
                phoneNumberDegree(phoneNumber) shouldBe true
            }
        }
    }
    
    Given("핸드폰 번호에 다른 문자가 포함되어 있을 경우") {
        val phoneNumber = "010-1234-568a"
        When("번호 뒤에 영문 a를 넣으면") {
            Then("false 를 반환한다.") {
                phoneNumberDegree(phoneNumber) shouldBe false
            }
        }
    }
    
    Given("핸드폰 번호 앞자리에 유효한 번호가 아닌 다른 번호가 포함되어 있을 경우") {
        val phoneNumber = "111-1234-5678"
        When("앞자리 111을 넣으면") {
            Then("false 를 반환한다.") {
                phoneNumberDegree(phoneNumber) shouldBe false
            }
        }
    }



    // @NotBlank 설정하여 발생하지 않음.
    Given("패스워드가 비었을 경우") {
        val emptyPw = ""
        val nullPw = null
        When("패스워드가 빈값 을 넣으면.") {
            Then("IllegalArgumentException 에러가 발생한다.") {
                assertThrows<IllegalArgumentException> { passwordPasswordDegree(emptyPw) }
            }
        }

        When("패스워드에 null 을 넣으면.") {
            Then("IllegalArgumentException 에러가 발생한다.") {
                assertThrows<IllegalArgumentException> { passwordPasswordDegree(nullPw) }
            }
        }
    }

    Given("패스워드 대문자만") {
        val pw = "ABCD"
        When("패스워드가 대문자만 충족할 경우") {
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 쉬움이다") {
                passwordPasswordDegree.first shouldBe PasswordDegree.EASY
            }
            Then("1가지 충족") {
                passwordPasswordDegree.second shouldBe 1
            }
        }
    }

    Given("패스워드 소문자만") {
        val pw = "abcd"
        When("패스워드가 소문자만 충족할 경우") {
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 쉬움이다") {
                passwordPasswordDegree.first shouldBe PasswordDegree.EASY
            }
            Then("1가지 충족") {
                passwordPasswordDegree.second shouldBe 1
            }
        }
    }

    Given("패스워드 숫자만") {
        val pw = "1234"
        When("패스워드가 숫자만 충족할 경우") {
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 쉬움이다.") {
                passwordPasswordDegree(pw).first shouldBe PasswordDegree.EASY
            }
            Then("1가지 충족") {
                passwordPasswordDegree.second shouldBe 1
            }
        }
    }

    Given("패스워드 특수문자만") {
        val pw = "!@#$"
        When("패스워드가 특수문자만 충족할 경우") {
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 쉬움이다.") {
                passwordPasswordDegree(pw).first shouldBe PasswordDegree.EASY
            }
            Then("1가지 충족") {
                passwordPasswordDegree.second shouldBe 1
            }
        }
    }

    Given("2가지 조건 충족") {
        var pw = EMPTY
        When("길이, 특수문자만 충족") {
            pw = "~@!@!@!@!@!"
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 보통이다.") {
                passwordPasswordDegree.first shouldBe PasswordDegree.NORMAL
            }
            Then("2가지 충족") {
                passwordPasswordDegree.second shouldBe 2
            }
        }

        When("특수문자, 대문자만 충족") {
            pw = "!@#@ABC"
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 보통이다.") {
                passwordPasswordDegree.first shouldBe PasswordDegree.NORMAL
            }
            Then("2가지 충족") {
                passwordPasswordDegree.second shouldBe 2
            }
        }

        When("대문자, 소문자만 충족") {
            pw = "ABCDabd"
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 보통이다.") {
                passwordPasswordDegree.first shouldBe PasswordDegree.NORMAL
            }
            Then("2가지 충족") {
                passwordPasswordDegree.second shouldBe 2
            }
        }

        When("숫자, 대문자 충족") {
            pw = "1234ABD"
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 보통이다.") {
                passwordPasswordDegree.first shouldBe PasswordDegree.NORMAL
            }
            Then("2가지 충족") {
                passwordPasswordDegree.second shouldBe 2
            }
        }

        // ...
    }

    Given("3가지 조건 충족") {
        var pw = EMPTY
        When("길이,숫자,대문자 충족") {
            pw = "1234ABCD"
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 보통이다.") {
                passwordPasswordDegree.first shouldBe PasswordDegree.NORMAL
            }
            Then("3가지 충족") {
                passwordPasswordDegree.second shouldBe 3
            }
        }

        When("길이,대문자,특수문자 충족") {
            pw = "VSDSABCD!"
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 보통이다.") {
                passwordPasswordDegree.first shouldBe PasswordDegree.NORMAL
            }
            Then("3가지 충족") {
                passwordPasswordDegree.second shouldBe 3
            }
        }
        // ...
    }

    Given("4가지 충족") {
        var pw = EMPTY
        When("길이,숫자,대문자,특수문자 충족") {
            pw = "1234ABCD!"
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 강함이다.") {
                passwordPasswordDegree.first shouldBe PasswordDegree.STRONG
            }
            Then("4가지 충족") {
                passwordPasswordDegree.second shouldBe 4
            }
        }

        When("숫자,대문자,소문자,특수문자 충족") {
            pw ="Ab1!"
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 강함이다.") {
                passwordPasswordDegree.first shouldBe PasswordDegree.STRONG
            }
            Then("4가지 충족") {
                passwordPasswordDegree.second shouldBe 4
            }
        }
    }

    Given("모든 조건 충족 (5가지)") {
        val pw = "abCD12#$"
        When("모든 조건 충족") {
            val passwordPasswordDegree = passwordPasswordDegree(pw)
            Then("패스워드의 강도는 매우 강함이다.") {
                passwordPasswordDegree.first shouldBe PasswordDegree.STRONG
            }
            Then("5가지 충족") {
                passwordPasswordDegree.second shouldBe 5
            }
        }
    }

    Given("0가지 충족") {
        val pw = "åå"
        When("0가지 충족") {
            Then("0가지 충족시 에러 발생") {
                assertThrows<IllegalArgumentException> { passwordPasswordDegree(pw) }
            }
        }
    }

})

fun phoneNumberDegree(phoneNumber: String?): Boolean {
    if(phoneNumber.isNullOrEmpty()) {
        throw IllegalArgumentException("핸드폰 번호가 비었습니다.")
    }

    val valid = "^01(?:0|1|[6-9])-?(\\d{3}|\\d{4})-?(\\d{4})$"
    return phoneNumber.matches(Regex(valid)) ?: false
}


fun passwordPasswordDegree(pw: String?): Pair<PasswordDegree, Int> {
    if(pw.isNullOrEmpty()) {
        throw IllegalArgumentException("패스워드가 비었습니다.")
    }

    return when (passwordMatches(pw)) {
        0 -> throw IllegalArgumentException("패스워드는 8자리이상, 대문자, 소문자, 숫자, 특수문자 중 1개 이상이 포함되어야 합니다.")
        1 -> PasswordDegree.EASY to passwordMatches(pw)
        2, 3 -> PasswordDegree.NORMAL to passwordMatches(pw)
        4, 5 -> PasswordDegree.STRONG to passwordMatches(pw)
        else -> throw Exception("패스워드 규칙의 로직 이상.")
    }

}

fun passwordMatches(pw: String): Int {
    var matches = 0
    if(pw.length >= 8) matches++
    if(pw.contains(Regex("[!@#\$%^&*(),.?\":{}|<>]"))) matches++
    if(pw.contains(Regex("[A-Z]"))) matches++
    if(pw.contains(Regex("[a-z]"))) matches++
    if(pw.contains(Regex("[0-9]"))) matches++
    return matches
}

enum class PasswordDegree {
    EASY, NORMAL, STRONG
}