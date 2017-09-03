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

package pakisan.telegraphcli.requests.test.account

import org.junit.Test
import pakisan.telegraphcli.requests.Telegraph
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetAccountInfoTest {

    @Test
    fun getAccountInfoWithAccessToken() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/getAccountInfo")
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")

        val endpoint = Telegraph.getAccountInfo("b968da509bb......0989a5ec3b32997d55286c657e...")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed getAccountInfo endpoint.")
    }

    @Test
    fun getAccountInfo() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/getAccountInfo")
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&fields=[\"short_name\",\"page_count\"]")

        val endpoint = Telegraph.getAccountInfo("b968da509bb......0989a5ec3b32997d55286c657e...",
                arrayOf("short_name", "page_count"))

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed getAccountInfo endpoint.")
    }

    @Test
    fun failGettingAccountInfoByAccessToken() {
        assertFailsWith<IllegalArgumentException>("empty or blank accessToken must fail creation.") {
            Telegraph.getAccountInfo("")
        }
    }

}