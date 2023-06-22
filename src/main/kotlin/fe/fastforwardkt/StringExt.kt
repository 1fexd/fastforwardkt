package fe.fastforwardkt

fun String.substringNullable(startIndex: Int, endIndex: Int): String? {
    return runCatching { this.substring(startIndex, endIndex) }.getOrNull()
}

fun String.substringNullable(startIndex: Int): String? {
    return runCatching { this.substring(startIndex) }.getOrNull()
}
