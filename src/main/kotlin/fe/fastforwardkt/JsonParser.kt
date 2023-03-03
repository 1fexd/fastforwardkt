package fe.fastforwardkt

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.io.InputStream

fun loadFastForwardRuleJson(inputStream: InputStream) = loadFastForwardRuleJson(inputStream.use { JsonParser.parseReader(it.reader()) })
fun loadFastForwardRuleJson(text: String) = loadFastForwardRuleJson(JsonParser.parseString(text))

private fun loadFastForwardRuleJson(element: JsonElement) = element.asJsonObject
