package fe.fastforwardkt

fun getBuiltInJson() = object {}::class.java.getResourceAsStream("rules.json")!!
