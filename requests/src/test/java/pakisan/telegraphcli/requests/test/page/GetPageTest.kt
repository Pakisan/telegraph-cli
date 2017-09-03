/*
 * Copyright 2017 pakisan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package pakisan.telegraphcli.requests.test.page

import org.junit.Test
import pakisan.telegraphcli.requests.Telegraph
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetPageTest {
    @Test
    fun getPageWithPath() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/getPage")
                .append("/Sample-Page-12-15")
                .append("?return_content=false")

        val endpoint = Telegraph.getPage("Sample-Page-12-15")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed getPage endpoint.")
    }

    @Test
    fun getPage() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/getPage")
                .append("/Sample-Page-12-15")
                .append("?return_content=true")

        val endpoint = Telegraph.getPage("Sample-Page-12-15", true)

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed getPage endpoint.")
    }

    @Test
    fun failGettingPageByPath() {
        assertFailsWith<IllegalArgumentException>("empty or blank accessToken must fail creation.") {
            Telegraph.getPage("")
        }
    }
}