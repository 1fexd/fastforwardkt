import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.io.InputStream

fun loadJson(inputStream: InputStream) = loadJson(inputStream.use { JsonParser.parseReader(it.reader()) })
fun loadJson(text: String) = loadJson(JsonParser.parseString(text))

private fun loadJson(element: JsonElement) = element.asJsonObject.getAsJsonObject("providers")
