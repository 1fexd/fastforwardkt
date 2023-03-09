package fe.fastforwardkt

import java.net.URI

class UriKt(str: String) {
    private val uri = URI(str)

    val host: String?
        get() = uri.host
    val path: String?
        get() = uri.path
    val query: String?
        get() = uri.query

    val splitQuery: MutableMap<String, String> by lazy {
        query?.split("&")?.mapNotNull {
            with(it.split("=")) {
                if (this.size == 2) {
                    this[0] to this[1]
                } else null
            }
        }?.toMap()?.toMutableMap() ?: mutableMapOf()
    }

    val fragment: String?
        get() = uri.fragment
}
