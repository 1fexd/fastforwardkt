package fe.fastforwardkt

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.*

fun String.decodeBase64() = kotlin.runCatching { String(Base64.getDecoder().decode(this)) }.getOrNull()
fun String.decodeUrl() = kotlin.runCatching { URLDecoder.decode(this, StandardCharsets.UTF_8) }.getOrNull()
fun String.decodeHex(): String {
    val list = mutableListOf<Int>()
    for (i in 0 until this.length - 1 step 2) {
        list.add(this.substring(i, 2).toInt(16))
    }

    return list.map { Character.toChars(it) }.joinToString()
}
