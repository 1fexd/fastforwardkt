package fe.fastforwardkt

import java.net.URI

class UriKt(str: String) {
    private val uri = URI(str)

    val host: String? = uri.host
    val path: String? = uri.path
    val query: String? = uri.query
    val fragment: String? = uri.fragment

    val querySplit by lazy {
        query?.split("&")
            ?.asSequence()
            ?.map { it.split("=")
            }?.filter { it.size == 2 }
            ?.associate { it[0] to it[1] }
            ?: mapOf()
    }
}
