package ph.cafe.io.common

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

object Constants {
    const val DASH = "-"
    const val EMPTY = ""
    const val SPACE = " "
    const val COMMA = ","

    val DEFAULT_LOCALDATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S][Z]")
    val SIMPLE_LOCALDATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val LOCALDATETIME_FORMATTER = DateTimeFormatterBuilder()
        .appendOptional(DEFAULT_LOCALDATETIME_FORMATTER)
        .appendOptional(SIMPLE_LOCALDATETIME_FORMATTER)
        .appendOptional(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        .toFormatter();

    val KOREAN_INITIAL_ARRAY = arrayOf('ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
}