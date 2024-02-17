package ph.cafe.io

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhCafeApplication

fun main(args: Array<String>) {
//
//    println(StringUtils.isInitialConsonant("가나다"))
//    println(StringUtils.isInitialConsonant("ㅇㅇㅅ"))
//    println(StringUtils.isInitialConsonant("ㅇㅇㅅ ㅇㅁㄹㅋㄴ"))

    runApplication<PhCafeApplication>(*args)
}
