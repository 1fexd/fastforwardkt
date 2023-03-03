package fe.fastforwardkt

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.*

fun String.decodeBase64() = String(Base64.getDecoder().decode(this))
fun String.decodeUrl() = URLDecoder.decode(this, StandardCharsets.UTF_8)
fun String.decodeHex(): String {
    val list = mutableListOf<Int>()
    for (i in 0 until this.length - 1 step 2) {
        list.add(this.substring(i, 2).toInt(16))
    }

    return list.map { Character.toChars(it) }.joinToString()
}

fun String.querySplit(): Map<String, String> {
    return this.split("&").mapNotNull {
        with(it.split("=")) {
            if (this.size == 2) {
                this[0] to this[1]
            } else null
        }
    }.toMap()
}
