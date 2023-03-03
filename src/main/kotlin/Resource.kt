import java.io.InputStream
object Resource
fun getBuiltInFastForwardJson(name: String = "/rules.json"): InputStream? = Resource::class.java.getResourceAsStream(name)
