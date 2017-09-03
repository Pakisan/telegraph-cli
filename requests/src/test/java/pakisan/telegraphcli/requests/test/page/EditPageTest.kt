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

class EditPageTest {
    @Test
    fun editPageWithAuthorName() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING

        expectedEndpoint.append("https://api.telegra.ph/editPage/")
                .append(URLEncoder.encode("Sample-Page-12-15", encoding))
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&title=${URLEncoder.encode("Long title", encoding)}")
                .append("&author_name=${URLEncoder.encode("author name", encoding)}")
                .append("&content=[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")
                .append("&return_content=false")

        val endpoint = Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                "Sample-Page-12-15", "Long title",
                "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                authorName = "author name")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createPage endpoint.")
    }

    @Test
    fun editPageWithAuthorUrl() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING

        expectedEndpoint.append("https://api.telegra.ph/editPage/")
                .append(URLEncoder.encode("Sample-Page-12-15", encoding))
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&title=${URLEncoder.encode("Long title", encoding)}")
                .append("&author_url=https://t.me")
                .append("&content=[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")
                .append("&return_content=false")

        val endpoint = Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                "Sample-Page-12-15", "Long title",
                "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                authorUrl = "https://t.me")

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createPage endpoint.")
    }

    @Test
    fun editPageWithReturnContent() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING

        expectedEndpoint.append("https://api.telegra.ph/editPage/")
                .append(URLEncoder.encode("Sample-Page-12-15", encoding))
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&title=${URLEncoder.encode("Long title", encoding)}")
                .append("&content=[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")
                .append("&return_content=true")

        val endpoint = Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                "Sample-Page-12-15", "Long title",
                "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                returnContent =  true)

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createPage endpoint.")
    }

    @Test
    fun editPage() {
        val expectedEndpoint = StringBuilder()
        val encoding = Telegraph.URL_ENCODING

        expectedEndpoint.append("https://api.telegra.ph/editPage/")
                .append(URLEncoder.encode("Sample-Page-12-15", encoding))
                .append("?access_token=b968da509bb......0989a5ec3b32997d55286c657e...")
                .append("&title=${URLEncoder.encode("Long title", encoding)}")
                .append("&author_name=${URLEncoder.encode("author name", encoding)}")
                .append("&author_url=https://t.me")
                .append("&content=[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]")
                .append("&return_content=true")

        val endpoint = Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                "Sample-Page-12-15", "Long title",
                "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                "author name","https://t.me", true)

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed createPage endpoint.")
    }

    @Test
    fun failPageEditingByAccessToken() {
        assertFailsWith<IllegalArgumentException>("empty or blank accessToken must fail creation.") {
            Telegraph.editPage("",
                    "Sample-Page-12-15", "page title",
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                    "author name","author url", true)
        }
    }

    @Test
    fun failPageEditingByPath() {
        assertFailsWith<IllegalArgumentException>("empty or blank path must fail creation.") {
            Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "", "page title",
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                    "author name","author url", true)
        }
    }

    @Test
    fun failPageEditingByTitle() {
        assertFailsWith<IllegalArgumentException>("empty or blank path must fail creation.") {
            Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "Sample-Page-12-15", " ",
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                    "author name","author url", true)
        }
    }

    @Test
    fun failPageEditingByContent() {
        assertFailsWith<IllegalArgumentException>("empty or blank path must fail creation.") {
            Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "Sample-Page-12-15", "page title",
                    " ",
                    "author name","author url", true)
        }
    }

    @Test
    fun failPageEditingByTooLongTitle() {
        val pageTitle = StringBuilder()
        pageTitle.append("Very l")
        for(x in 1..Telegraph.MAX_PAGE_TITLE_LENGTH) {
            pageTitle.append("o")
        }
        pageTitle.append("g title")

        assertFailsWith<IllegalArgumentException>("title must be equals or shorter " +
                "than ${Telegraph.MAX_PAGE_TITLE_LENGTH}.") {
            Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "Sample-Page-12-15", pageTitle.toString(),
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                    "author name","author url", true)
        }
    }

    @Test
    fun failPageEditingByTooLongAuthorName() {
        val authorName = StringBuilder()
        authorName.append("Very l")
        for(x in 1..Telegraph.AUTHOR_URL_MAX_LENGTH) {
            authorName.append("o")
        }
        authorName.append("g name")

        assertFailsWith<IllegalArgumentException>("authorName must be equals or shorter " +
                "than ${Telegraph.AUTHOR_URL_MAX_LENGTH}.") {
            Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "Sample-Page-12-15","page title",
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                    authorName.toString(),"author url", true)
        }
    }

    @Test
    fun failPageEditingByTooLongAuthorUrl() {
        val authorUrl = StringBuilder()
        authorUrl.append("Very l")
        for(x in 1..Telegraph.AUTHOR_URL_MAX_LENGTH) {
            authorUrl.append("o")
        }
        authorUrl.append("g url")

        assertFailsWith<IllegalArgumentException>("authorUrl must be equals or shorter " +
                "than ${Telegraph.AUTHOR_URL_MAX_LENGTH}.") {
            Telegraph.editPage("b968da509bb......0989a5ec3b32997d55286c657e...",
                    "Sample-Page-12-15","page title",
                    "[{\"tag\":\"p\",\"children\":[\"Hello,+world!\"]}]",
                    "author name",authorUrl.toString(), true)
        }
    }
}