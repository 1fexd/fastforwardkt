package fe.fastforwardkt

import java.io.InputStream

fun getBuiltInJson(name: String = "/rules.json"): InputStream? = object {}::class.java.getResourceAsStream(name)
