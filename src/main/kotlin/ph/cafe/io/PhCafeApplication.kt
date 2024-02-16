package ph.cafe.io

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhCafeApplication

fun main(args: Array<String>) {
    runApplication<PhCafeApplication>(*args)
}
