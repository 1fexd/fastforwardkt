package fe.fastforwardkt

import FastForwardResource
import com.google.gson.JsonObject
import fe.gson.extensions.parseReaderAs
import java.io.InputStream

object FastForwardLoader {
    fun loadBuiltInFastForwardRules() = loadFastForwardJson(FastForwardResource.getBuiltInFastForwardJson()!!)

    fun loadFastForwardJson(inputStream: InputStream) = inputStream.use { parseReaderAs<JsonObject>(it.reader()) } }
