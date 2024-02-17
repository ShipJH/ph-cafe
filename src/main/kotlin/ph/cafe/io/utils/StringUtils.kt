package ph.cafe.io.utils

import ph.cafe.io.common.BaseEnum
import ph.cafe.io.common.Constants.EMPTY
import ph.cafe.io.common.Constants.KOREAN_INITIAL_ARRAY
import ph.cafe.io.common.Constants.SPACE

object StringUtils {

    fun makeInitial(str: String): String {
        if(isEnglish(str)) return str

        val korBegin = 44032
        val initialBase = 588
        val initial = KOREAN_INITIAL_ARRAY
        val result = StringBuilder()
        for (char in str) {
            if (char in '가'..'힣') {
                val unicode = char.code
                val initialIndex = (unicode - korBegin) / initialBase
                result.append(initial[initialIndex])
            }
        }
        return if(result.toString() == EMPTY) str else result.toString()
    }

    private fun isKoreanName(str: String): Boolean {
        return str.replace(SPACE, EMPTY).all { it in '가'..'힣' }
    }

    private fun isEnglish(str: String): Boolean {
        return str.replace(SPACE, EMPTY).all { it in 'a'..'z' || it in 'A'..'Z' }
    }

    private fun isInitialConsonant(str: String): Boolean {
        val initialConsonants = KOREAN_INITIAL_ARRAY
        for (char in str.replace(SPACE, EMPTY)) {
            if (char !in initialConsonants) {
                return false
            }
        }
        return true
    }

    fun languageCheck(str: String): BaseEnum.Language? {
        return if (isInitialConsonant(str)) {
          BaseEnum.Language.KOREAN_INITIAL
        } else if (isKoreanName(str)) {
           BaseEnum.Language.KOREAN
        } else if (isEnglish(str)) {
           BaseEnum.Language.ENGLISH
        } else {
           null
        }
    }

}