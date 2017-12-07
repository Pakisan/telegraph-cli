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

package pakisan.telegraphcli.data.test.page

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Before
import org.junit.Test
import pakisan.telegraphcli.data.gson.NodeDeserializer
import pakisan.telegraphcli.data.gson.NodeSerializer
import pakisan.telegraphcli.data.gson.data.page.GPage
import pakisan.telegraphcli.data.page.Page
import pakisan.telegraphcli.data.page.node.Node
import pakisan.telegraphcli.data.page.node.NodeElement
import pakisan.telegraphcli.data.page.node.Text
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PageTest {

    lateinit var page: Page
    lateinit var pages: List<Page>
    private val listOfPagesType = object: TypeToken<List<Page>>(){}.type

    @Before
    fun preparePage() {
        page = Page(
                "Test-Page",
                "http://telegra.ph/Test-Page",
                "Test page title",
                "Test page description",
                "Test page author name",
                "Test page author url",
                "Test page image url",
                listOf(Node(
                        NodeElement(
                                "p",
                                children = listOf(
                                        Node(Text("content")),
                                        Node(NodeElement(
                                                "a",
                                                mapOf(
                                                        Pair("href",
                                                                "https://m.habrahabr.ru/post/335876/"),
                                                        Pair("target",
                                                                "_blank")
                                                ),
                                                listOf(
                                                        Node(Text("https://m.habrahabr.ru/post/335876/"))
                                                )
                                        ))
                                )

                        ))),
                10,
                true
        )
    }

    @Before
    fun preparePages() {
        pages = listOf(page, page)
    }

    @Test
    fun pageRequiredFields() {
        val gson = Gson()
        val pageAsJson = FileReader(PageTest::class
                .java
                .getResource("/page/pageWithRequiredFields.json").path)
        val page = gson.fromJson(pageAsJson, Page::class.java)
        assertNotNull(page.path, "path is required and must not be NULL.")
        assertNotNull(page.url, "url is required and must not be NULL.")
        assertNotNull(page.title, "title is required and must not be NULL.")
        assertNotNull(page.description, "description is required and must not be NULL.")
        assertNotNull(page.views, "views is required and must not be NULL.")
    }

    @Test
    fun pageDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val pageAsJson = FileReader(PageTest::class.java.getResource("/page/page.json").path)
        val pageFromJson = gson.fromJson(pageAsJson, Page::class.java)

        assertEquals(pageFromJson, page, "malformed Page.")
    }

    @Test
    fun pagesDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val pagesAsJson = FileReader(PageTest::class.java.getResource("/page/pages.json").path)
        val pagesFromJson: List<Page> = gson.fromJson(pagesAsJson, listOfPagesType)

        assertEquals(pagesFromJson, pages, "malformed Page.")
    }

    @Test
    fun pageSerialization() {
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                PageTest::class.java.getResource("/page/page.json").path))
        val pageAsJson = String(jsonAsBytes)

        assertEquals(pageAsJson, GPage.page(page, true), "malformed JSON.")
    }

    @Test
    fun pagesSerialization() {
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                PageTest::class.java.getResource("/page/pages.json").path))
        val pageAsJson = String(jsonAsBytes)

        assertEquals(pageAsJson, GPage.pages(pages, true), "malformed JSON.")
    }

    @Test
    fun gPageDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                PageTest::class.java.getResource("/page/page.json").path))
        val pageAsJson = String(jsonAsBytes)
        val pageFromJson = gson.fromJson(pageAsJson, Page::class.java)
        val page = GPage.page(pageAsJson)


        assertEquals(pageFromJson, page, "malformed Page.")
    }

    @Test
    fun gPagesDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                PageTest::class.java.getResource("/page/pages.json").path))
        val pagesAsJson = String(jsonAsBytes)
        val pagesFromJson: List<Page> = gson.fromJson(pagesAsJson, listOfPagesType)
        val pages: List<Page> = GPage.pages(pagesAsJson)


        assertEquals(pagesFromJson, pages, "malformed Page.")
    }

    @Test
    fun gPageSerialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeSerializer())
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val json = FileReader(PageTest::class.java.getResource("/page/page.json").path)
        val pageAsJson = gson.toJson(gson.fromJson(json, Page::class.java))

        assertEquals(pageAsJson, GPage.page(page), "malformed JSON.")
    }

    @Test
    fun gPagesSerialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeSerializer())
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val json = FileReader(PageTest::class.java.getResource("/page/pages.json").path)
        val pagesAsJson = gson.toJson(gson.fromJson(json, listOfPagesType), listOfPagesType)

        assertEquals(pagesAsJson, GPage.pages(pages), "malformed JSON.")
    }

}