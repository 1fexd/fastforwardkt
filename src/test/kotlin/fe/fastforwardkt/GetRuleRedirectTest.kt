package fe.fastforwardkt

import fe.fastforwardkt.FastForward.getRuleRedirect
import kotlin.test.Test
import kotlin.test.assertEquals

class GetRuleRedirectTest {
    @Test
    fun testGetRuleRedirectTest() {
        assertEquals(
            "https://ninachuba.bio.to/glas-album",
            getRuleRedirect(
                "https://www.youtube.com/redirect?event=video_description&redir_token=TOKEN_LEL&q=https%3A%2F%2Fninachuba.bio.to%2Fglas-album&v=D3RG2O03wsI",
                System.out
            )
        )

        assertEquals(
            null,
            getRuleRedirect(
                "https://www.humblebundle.com/games/humble-heroines-warriors-dreamers-and-god-slayers?partner=caschysblog",
                System.out
            )
        )
    }
}
