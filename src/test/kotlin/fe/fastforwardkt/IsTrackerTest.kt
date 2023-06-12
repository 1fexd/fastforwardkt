package fe.fastforwardkt

import kotlin.test.Test
import kotlin.test.assertEquals

class IsTrackerTest {
    @Test
    fun testIsTracker() {
        assertEquals(true, isTracker("https://t.co/coInA0UU8T"))
        assertEquals(true, isTracker("https://x.t.co/coInA0UU8T"))
        assertEquals(true, isTracker("https://rebrand.ly/hallo"))
        assertEquals(true, isTracker("https://shareasale-analytics.com/r.cfm?yeet=test"))
        assertEquals(false, isTracker("https://lol.shareasale-analytics.com/r.cfm?yeet=test"))
        assertEquals(true, isTracker("https://T.CO/coInA0UU8T"))
    }
}
