package ph.cafe.io.common

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

object Constants {
    const val DASH = "-"
    const val UBAR = "_"
    const val VBAR = "|"
    const val COMMA = ","
    const val EMPTY = ""
    const val SPACE = " "
    const val LEFT_SQUARE_BRAKETS = "["
    const val RIGHT_SQUARE_BRAKETS = "]"


    val DEFAULT_LOCALDATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S][Z]")
    val SIMPLE_LOCALDATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val LOCALDATETIME_FORMATTER = DateTimeFormatterBuilder()
        .appendOptional(DEFAULT_LOCALDATETIME_FORMATTER)
        .appendOptional(SIMPLE_LOCALDATETIME_FORMATTER)
        .appendOptional(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        .toFormatter();

}