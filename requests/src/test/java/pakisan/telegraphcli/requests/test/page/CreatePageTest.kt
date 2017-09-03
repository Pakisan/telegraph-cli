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
import java.net.URLEncoder
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreatePageTest {

    @Test
    fun createPageWithAuthorName() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING

        expectedEndpoint.append("https://api.telegra.ph/createPage")
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&title=${URLEncoder.encode("Long title", encoding)}")
                .append("&author_name=${URLEncoder.encode("author name", encoding)}")
                .append("&content=[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")
                .append("&return_content=false")

        val endpoint = Telegraph.createPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                "Long title","author name",
                content = "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createPage endpoint.")
    }

    @Test
    fun createPageWithAuthorUrl() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING

        expectedEndpoint.append("https://api.telegra.ph/createPage")
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&title=${URLEncoder.encode("Long title", encoding)}")
                .append("&author_url=https://t.me")
                .append("&content=[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")
                .append("&return_content=false")

        val endpoint = Telegraph.createPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                "Long title",authorUrl = "https://t.me",
                content = "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createPage endpoint.")
    }

    @Test
    fun createPageWithReturnContent() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING

        expectedEndpoint.append("https://api.telegra.ph/createPage")
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&title=${URLEncoder.encode("Long title", encoding)}")
                .append("&content=[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")
                .append("&return_content=true")

        val endpoint = Telegraph.createPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                "Long title", content = "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                returnContent = true)

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createPage endpoint.")
    }

    @Test
    fun createPage() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING

        expectedEndpoint.append("https://api.telegra.ph/createPage")
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&title=${URLEncoder.encode("Long title", encoding)}")
                .append("&author_name=${URLEncoder.encode("author name", encoding)}")
                .append("&author_url=https://t.me")
                .append("&content=[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")
                .append("&return_content=true")

        val endpoint = Telegraph.createPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                "Long title", "author name", "https://t.me",
                "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]", true)

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createPage endpoint.")
    }

    @Test
    fun failPageCreationByAccessToken() {
        assertFailsWith<IllegalArgumentException>("empty or blank accessToken must fail creation.") {
            Telegraph.createPage("",
                    "Long title", "author name", "https://t.me",
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]", true)
        }
    }

    @Test
    fun failPageCreationByTitle() {
        assertFailsWith<IllegalArgumentException>("empty or blank title must fail creation.") {
            Telegraph.createPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    " ", "author name", "https://t.me",
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]", true)
        }
    }

    @Test
    fun failPageCreationByContent() {
        assertFailsWith<IllegalArgumentException>("empty or blank title must fail creation.") {
            Telegraph.createPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "page title", "author name", "https://t.me",
                    " ", true)
        }
    }

    @Test
    fun failPageCreationByTooLongTitle() {
        val pageTitle = StringBuilder()
        pageTitle.append("Very l")
        for(x in 1..Telegraph.MAX_PAGE_TITLE_LENGTH) {
            pageTitle.append("o")
        }
        pageTitle.append("g title")

        assertFailsWith<IllegalArgumentException>("title must be equals or shorter " +
                "than ${Telegraph.MAX_PAGE_TITLE_LENGTH}.") {
            Telegraph.createPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    pageTitle.toString(), "author name", "https://t.me",
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]", true)
        }
    }

    @Test
    fun failPageCreationByTooLongAuthorName() {
        val authorName = StringBuilder()
        authorName.append("Very l")
        for(x in 1..Telegraph.AUTHOR_NAME_MAX_LENGTH) {
            authorName.append("o")
        }
        authorName.append("g name")

        assertFailsWith<IllegalArgumentException>("title must be equals or shorter " +
                "than ${Telegraph.MAX_PAGE_TITLE_LENGTH}.") {
            Telegraph.createPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "page title", authorName.toString(), "https://t.me",
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]", true)
        }
    }

    @Test
    fun failPageCreationByTooLongAuthorUrl() {
        val authorUrl = StringBuilder()
        authorUrl.append("Very l")
        for(x in 1..Telegraph.AUTHOR_URL_MAX_LENGTH) {
            authorUrl.append("o")
        }
        authorUrl.append("g url")

        assertFailsWith<IllegalArgumentException>("authorUrl must be equals or shorter " +
                "than ${Telegraph.MAX_PAGE_TITLE_LENGTH}.") {
            Telegraph.createPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "page title", "author name", authorUrl.toString(),
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]", true)
        }
    }

}