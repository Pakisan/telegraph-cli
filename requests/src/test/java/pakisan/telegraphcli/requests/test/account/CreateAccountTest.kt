
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

class CreateAccountTest {

    @Test
    fun createAccountWithAuthorName() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING
        expectedEndpoint.append("https://api.telegra.ph/createAccount")
                .append("?short_name=${URLEncoder.encode("Sandbox", encoding)}")
                .append("&author_name=${URLEncoder.encode("Anonymous", encoding)}")

        val endpoint = Telegraph.createAccount("Sandbox", "Anonymous")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createAccount endpoint.")
    }

    @Test
    fun createAccountWithAuthorUrl() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/createAccount")
                .append("?short_name=${URLEncoder.encode("Sandbox", Telegraph.URL_ENCODING)}")
                .append("&author_url=https://t.me/test")

        val endpoint = Telegraph.createAccount("Sandbox", authorUrl = "https://t.me/test")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createAccount endpoint.")
    }

    @Test
    fun createAccount() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING
        expectedEndpoint.append("https://api.telegra.ph/createAccount")
                .append("?short_name=${URLEncoder.encode("Sandbox", encoding)}")
                .append("&author_name=${URLEncoder.encode("Jimmy Hart", encoding)}")
                .append("&author_url=https://t.me/test")

        val endpoint = Telegraph.createAccount("Sandbox","Jimmy Hart","https://t.me/test")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createAccount endpoint.")
    }

    @Test
    fun failAccountCreationByShortName() {
        assertFailsWith<IllegalArgumentException>("empty or blank shortName must fail creation.") {
            Telegraph.createAccount("")
        }
    }

    @Test
    fun failAccountCreationByTooLongShortName() {
        val shortName = StringBuilder()
        shortName.append("Very l")
        for(x in 1..Telegraph.SHORT_NAME_MAX_LENGTH) {
            shortName.append("o")
        }
        shortName.append("g short name")

        assertFailsWith<IllegalArgumentException>("authorName must be equals or shorter " +
                "than ${Telegraph.SHORT_NAME_MAX_LENGTH}.") {
            Telegraph.createAccount(shortName.toString())
        }
    }

    @Test
    fun failAccountCreationByTooLongAuthorName() {
        val authorName = StringBuilder()
        authorName.append("Very l")
        for(x in 1..Telegraph.AUTHOR_NAME_MAX_LENGTH) {
            authorName.append("o")
        }
        authorName.append("g name")

        assertFailsWith<IllegalArgumentException>("authorName must be equals or shorter " +
                "than ${Telegraph.AUTHOR_NAME_MAX_LENGTH}.") {
            Telegraph.createAccount("shortName", authorName = authorName.toString())
        }
    }

    @Test
    fun failAccountCreationByTooLongAuthorUrl() {
        val authorUrl = StringBuilder()
        authorUrl.append("Very l")
        for(x in 1..Telegraph.AUTHOR_URL_MAX_LENGTH) {
            authorUrl.append("o")
        }
        authorUrl.append("g url")

        assertFailsWith<IllegalArgumentException>("authorUrl must be equals or shorter " +
                "than ${Telegraph.AUTHOR_URL_MAX_LENGTH}.") {
            Telegraph.createAccount("shortName", authorUrl = authorUrl.toString())
        }
    }

}