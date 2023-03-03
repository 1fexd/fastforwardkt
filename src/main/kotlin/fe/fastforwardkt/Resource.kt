package fe.fastforwardkt

import java.io.InputStream
object Resource
fun getBuiltInJson(name: String = "rules.json"): InputStream? = Resource::class.java.classLoader.getResourceAsStream(name)
