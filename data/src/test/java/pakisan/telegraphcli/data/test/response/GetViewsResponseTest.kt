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

package pakisan.telegraphcli.data.test.response

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test
import pakisan.telegraphcli.data.gson.data.GResponse
import pakisan.telegraphcli.data.page.PageViews
import pakisan.telegraphcli.data.response.Response
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class GetViewsResponseTest {

    private val viewsResponseType = object: TypeToken<Response<PageViews>>(){}.type

    @Test
    fun getViewsResponseDeserializationTest() {
        val gson = Gson()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                GetViewsResponseTest::class.java.getResource("/response/getViewsResponse.json").path))
        val pageViewsResponseAsJson = String(jsonAsBytes)
        val pageViewsResponseFromJson: Response<PageViews> = gson.fromJson(pageViewsResponseAsJson,
                viewsResponseType)
        val pageViewsResponse: Response<PageViews> = GResponse
                .response(pageViewsResponseAsJson, viewsResponseType)

        assertEquals(pageViewsResponseFromJson, pageViewsResponse, "malformed getViews response.")
    }


    @Test
    fun getViewsResponseSerialization() {
        val gson = Gson()
        val json = FileReader(GetViewsResponseTest::class.java.getResource("/response/getViewsResponse.json").path)
        val getViewsResponse = gson.toJson(gson.fromJson<Response<PageViews>>(json, viewsResponseType))
        val gGetViewsResponse = Response(true, result = PageViews(40))

        assertEquals(getViewsResponse, GResponse.response(gGetViewsResponse), "malformed getViews JSON.")
    }

}