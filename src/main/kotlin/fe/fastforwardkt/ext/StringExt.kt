package fe.fastforwardkt.ext

import java.lang.Exception

fun String.substringNullable(startIndex: Int, endIndex: Int): String? {
    return try {
        this.substring(startIndex, endIndex)
    } catch (e: Exception) {
        null
    }
}

fun String.substringNullable(startIndex: Int): String? {
    return try {
        this.substring(startIndex)
    } catch (e: Exception) {
        null
    }
}
