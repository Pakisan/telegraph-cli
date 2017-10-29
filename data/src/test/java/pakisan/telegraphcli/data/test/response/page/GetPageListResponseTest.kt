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

package pakisan.telegraphcli.data.test.response.page

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Before
import org.junit.Test
import pakisan.telegraphcli.data.gson.data.GResponse
import pakisan.telegraphcli.data.page.Page
import pakisan.telegraphcli.data.response.Response
import pakisan.telegraphcli.data.response.Result
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class GetPageListResponseTest {

    private val pageListResponseType = object: TypeToken<Response<Result>>(){}.type

    lateinit var pages: List<Page>

    @Before
    fun preparePages() {
        val page1 = Page(
                "Sample-Page-10-26-16",
                "http://telegra.ph/Sample-Page-10-26-16",
                "Sample Page",
                "Hello, world!",
                "Anonymous",
                views = 0,
                canEdit = true
        )

        val page2 = Page(
                "Sample-Page-10-26-15",
                "http://telegra.ph/Sample-Page-10-26-15",
                "Sample Page",
                "Hello, world!",
                "Anonymous",
                views = 0,
                canEdit = true
        )
        pages = listOf(page1, page2)
    }

    @Test
    fun getPageListResponseDeserializationTest() {
        val gson = Gson()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                GetPageListResponseTest::class.java.getResource("/response/page/getPageListResponse.json").path))
        val pageListResponseAsJson = String(jsonAsBytes)
        val pageListResponseFromJson: Response<Result> = gson.fromJson(pageListResponseAsJson,
                pageListResponseType)
        val pageListResponse: Response<Result> = GResponse.response(pageListResponseAsJson, pageListResponseType)

        assertEquals(pageListResponseFromJson, pageListResponse, "malformed pageList response.")
    }


    @Test
    fun getPageListResponseSerialization() {
        val gson = Gson()
        val json = FileReader(GetPageListResponseTest::class.java.getResource("/response/page/getPageListResponse.json").path)
        val pageListResponse = gson.toJson(gson.fromJson<Response<Result>>(json, pageListResponseType))
        val gPageListResponse = Response(true, result = Result(11969, pages))

        assertEquals(pageListResponse, GResponse.response(gPageListResponse), "malformed pageList JSON.")
    }

}