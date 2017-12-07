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

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test
import pakisan.telegraphcli.data.gson.NodeDeserializer
import pakisan.telegraphcli.data.gson.NodeSerializer
import pakisan.telegraphcli.data.gson.data.GResponse
import pakisan.telegraphcli.data.page.Page
import pakisan.telegraphcli.data.page.node.Node
import pakisan.telegraphcli.data.page.node.NodeElement
import pakisan.telegraphcli.data.page.node.Text
import pakisan.telegraphcli.data.response.Response
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class GetPageResponseTest {

    private val pageResponseType = object: TypeToken<Response<Page>>(){}.type

    val page = Page(
            "Sample-Page-12-15",
            "http://telegra.ph/Sample-Page-12-15",
            "Sample Page",
            "Hello, world!",
            "Anonymous",
            content = listOf(
                    Node(NodeElement(
                            "p",
                            children = listOf(Node(Text("Hello, world!")))
                    ))
            ),
            views = 501,
            canEdit = false
    )

    @Test
    fun getPageResponseDeserializationTest() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .registerTypeAdapter(Node::class.java, NodeSerializer())
                .create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                GetPageResponseTest::class.java.getResource("/response/page/getPageResponse.json").path))
        val pageResponseAsJson = String(jsonAsBytes)
        val pageResponseFromJson: Response<Page> = gson.fromJson(pageResponseAsJson,
                pageResponseType)
        val pageResponse: Response<Page> = GResponse.response(pageResponseAsJson, pageResponseType)

        assertEquals(pageResponseFromJson, pageResponse, "malformed page response.")
    }


    @Test
    fun getPageResponseSerialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .registerTypeAdapter(Node::class.java, NodeSerializer())
                .create()
        val json = FileReader(GetPageResponseTest::class.java.getResource("/response/page/getPageResponse.json").path)
        val pageResponse = gson.toJson(gson.fromJson<Response<Page>>(json, pageResponseType))
        val gPageResponse = Response(true, result = page)

        assertEquals(pageResponse, GResponse.response(gPageResponse), "malformed page JSON.")
    }

}