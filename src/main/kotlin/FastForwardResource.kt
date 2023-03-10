import java.io.InputStream

object FastForwardResource {
    fun getBuiltInFastForwardJson(name: String = "fastforward_rules.json"): InputStream? =
        FastForwardResource::class.java.getResourceAsStream(name)
}
