package fe.fastforwardkt

import FastForwardResource.getBuiltInFastForwardJson
import kotlin.test.Test
import kotlin.test.assertEquals

class IsTrackerTest {
    @Test
    fun testIsTracker() {
        val obj = FastForwardLoader.loadFastForwardJson(getBuiltInFastForwardJson()!!)
        assertEquals(true, isTracker("https://t.co/coInA0UU8T", obj))
        assertEquals(true, isTracker("https://x.t.co/coInA0UU8T", obj))
        assertEquals(true, isTracker("https://rebrand.ly/hallo", obj))
        assertEquals(true, isTracker("https://shareasale-analytics.com/r.cfm?yeet=test", obj))
        assertEquals(false, isTracker("https://lol.shareasale-analytics.com/r.cfm?yeet=test", obj))
        assertEquals(true, isTracker("https://T.CO/coInA0UU8T", obj))
    }
}
