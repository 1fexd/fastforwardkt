import java.io.InputStream
object FastForwardResource
fun getBuiltInFastForwardJson(name: String = "/rules.json"): InputStream? = FastForwardResource::class.java.getResourceAsStream(name)
