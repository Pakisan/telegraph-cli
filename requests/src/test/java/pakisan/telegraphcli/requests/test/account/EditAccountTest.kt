
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
import java.net.URLEncoder
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EditAccountTest {

    @Test
    fun editAccountWithAuthorName() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/editAccountInfo")
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&short_name=${URLEncoder.encode("new short name", Telegraph.URL_ENCODING)}")
                .append("&author_name=${URLEncoder.encode("new author name", Telegraph.URL_ENCODING)}")

        val endpoint = Telegraph.editAccountInfo("b968da509bb......0989a5ec3b32997d55286c657e...",
                "new short name", "new author name")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed editAccountInfo endpoint.")
    }

    @Test
    fun editAccountWithAuthorUrl() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/editAccountInfo")
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&short_name=${URLEncoder.encode("new short name", Telegraph.URL_ENCODING)}")
                .append("&author_url=https://t.me")

        val endpoint = Telegraph.editAccountInfo("b968da509bb......0989a5ec3b32997d55286c657e...",
                "new short name", authorUrl =  "https://t.me")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed editAccountInfo endpoint.")
    }

    @Test
    fun editAccount() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/editAccountInfo")
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&short_name=${URLEncoder.encode("new short name", Telegraph.URL_ENCODING)}")
                .append("&author_name=${URLEncoder.encode("new author name", Telegraph.URL_ENCODING)}")
                .append("&author_url=https://t.me")

        val endpoint = Telegraph.editAccountInfo("b968da509bb......0989a5ec3b32997d55286c657e...",
                "new short name", "new author name", "https://t.me")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed editAccountInfo endpoint.")
    }

    @Test
    fun failAccountEditingByAccessToken() {
        assertFailsWith<IllegalArgumentException>("accessToken must not be empty or blank") {
            Telegraph.editAccountInfo("",
                    "new short name", "new author name", "https://t.me")
        }
    }

    @Test
    fun failAccountEditingByShortName() {
        assertFailsWith<IllegalArgumentException>("shortName must not be empty or blank") {
            Telegraph.editAccountInfo("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "     ", "new author name", "https://t.me")
        }
    }

    @Test
    fun failAccountEditingByTooLongAuthorName() {
        val authorName = StringBuilder()
        authorName.append("Very l")
        for(x in 1..Telegraph.AUTHOR_NAME_MAX_LENGTH) {
            authorName.append("o")
        }
        authorName.append("g name")

        assertFailsWith<IllegalArgumentException>("authorName must be equals or shorter " +
                "than ${Telegraph.AUTHOR_NAME_MAX_LENGTH}.") {
            Telegraph.editAccountInfo("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "new short name", authorName.toString(), "https://t.me")
        }
    }

    @Test
    fun failAccountEditingByTooLongAuthorUrl() {
        val authorUrl = StringBuilder()
        authorUrl.append("Very l")
        for(x in 1..Telegraph.AUTHOR_URL_MAX_LENGTH) {
            authorUrl.append("o")
        }
        authorUrl.append("g url")

        assertFailsWith<IllegalArgumentException>("authorUrl must be equals or shorter " +
                "than ${Telegraph.AUTHOR_URL_MAX_LENGTH}.") {
            Telegraph.editAccountInfo("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "new short name", "new author name", authorUrl.toString())
        }
    }

}