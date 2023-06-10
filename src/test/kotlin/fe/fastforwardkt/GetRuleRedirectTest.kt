package fe.fastforwardkt

import FastForwardResource.getBuiltInFastForwardJson
import kotlin.test.Test
import kotlin.test.assertEquals

class GetRuleRedirectTest {
    @Test
    fun testGetRuleRedirectTest() {
        val obj = FastForwardLoader.loadFastForwardJson(getBuiltInFastForwardJson()!!)
        assertEquals(
            "https://ninachuba.bio.to/glas-album",
            getRuleRedirect(
                "https://www.youtube.com/redirect?event=video_description&redir_token=TOKEN_LEL&q=https%3A%2F%2Fninachuba.bio.to%2Fglas-album&v=D3RG2O03wsI",
                obj,
                debugPrint = true
            )
        )
        assertEquals(
            null,
            getRuleRedirect(
                "https://www.humblebundle.com/games/humble-heroines-warriors-dreamers-and-god-slayers?partner=caschysblog",
                obj,
                debugPrint = true
            )
        )
    }
}
