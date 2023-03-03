package fe.fastforwardkt

import getBuiltInFastForwardJson
import kotlin.test.Test
import kotlin.test.assertEquals

class GetRuleRedirectTest {
    @Test
    fun testGetRuleRedirectTest(){
        val obj = loadFastForwardRuleJson(getBuiltInFastForwardJson()!!)
        assertEquals("https://ninachuba.bio.to/glas-album", getRuleRedirect("https://www.youtube.com/redirect?event=video_description&redir_token=TOKEN_LEL&q=https%3A%2F%2Fninachuba.bio.to%2Fglas-album&v=D3RG2O03wsI", obj, debugPrint = true))
    }
}
