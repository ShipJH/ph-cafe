package ph.cafe.io.utils

import ph.cafe.io.exception.ExceptionCode
import ph.cafe.io.exception.PasswordException
import ph.cafe.io.exception.PhoneNumberException

object ValidUtils {

    fun phoneNumberValidCheck(phoneNumber: String?) {
        val valid = "^01(?:0|1|[6-9])-?(\\d{3}|\\d{4})-?(\\d{4})$"

        if(phoneNumber == null) throw PhoneNumberException(ExceptionCode.PHONE_NUMBER_EMPTY)
        if(!phoneNumber.matches(Regex(valid))) throw PhoneNumberException(ExceptionCode.PHONE_NUMBER_NOT_VALID)
    }

    fun passwordValidCheck(pw: String?) {
        if(pw == null) throw PasswordException(ExceptionCode.PASSWORD_EMPTY)
        if(pw.length < 8) throw PasswordException(ExceptionCode.PASSWORD_LENGTH_NOT_VALID)
        if(!pw.contains(Regex("[0-9]"))) throw PasswordException(ExceptionCode.PASSWORD_NUMBER_NOT_VALID)
        if(!pw.contains(Regex("[A-Z]"))) throw PasswordException(ExceptionCode.PASSWORD_STRING_UPPER_NOT_VALID)
        if(!pw.contains(Regex("[a-z]"))) throw PasswordException(ExceptionCode.PASSWORD_STRING_LOWER_NOT_VALID)
        if(!pw.contains(Regex("([!@#\$%^&*(),.?\":{}|<>])"))) throw PasswordException(ExceptionCode.PASSWORD_SPECIAL_CHARACTER_NOT_VALID)
    }

    fun passwordValidCheck(pw: String?, pw2: String?) {
        if(pw == null || pw2 == null) throw PasswordException(ExceptionCode.PASSWORD_EMPTY)
        if(pw != pw2) throw PasswordException(ExceptionCode.NOT_MATCH_PASSWORD)
        passwordValidCheck(pw)
    }

}