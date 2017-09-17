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
import org.junit.Test
import pakisan.telegraphcli.data.gson.data.page.GPageList
import pakisan.telegraphcli.data.page.Page
import pakisan.telegraphcli.data.page.PageList
import pakisan.telegraphcli.data.test.AccountTest
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class PageListTest {

    @Test
    fun pageListSerialization() {
        val gson = Gson()
        val pageListAsJson = FileReader(AccountTest::class.java.getResource("/pageList/singlePageList.json").path)
        val pageListFromJson = gson.fromJson(pageListAsJson, PageList::class.java)
        val pageList = PageList(1, listOf(Page(
                "Test-Page",
                "http://telegra.ph/Test-Page",
                "Test page title",
                "Test page description",
                imageUrl = "",
                views = 0,
                canEdit = true
        )))

        assertEquals(pageListFromJson, pageList, "malformed PageList.")
    }

    @Test
    fun pageListDeserialization() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                PageListTest::class.java.getResource("/pageList/singlePageList.json").path))
        val pageListAsJson = String(jsonAsBytes)
        val pageList = PageList(1, listOf(Page(
                "Test-Page",
                "http://telegra.ph/Test-Page",
                "Test page title",
                "Test page description",
                imageUrl = "",
                views = 0,
                canEdit = true
        )))

        assertEquals(pageListAsJson, gson.toJson(pageList), "malformed JSON.")
    }

    @Test
    fun gPageListSerialization() {
        val gson = Gson()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                PageListTest::class.java.getResource("/pageList/pageList.json").path))
        val pageListAsJson = String(jsonAsBytes)
        val pageListFromJson = gson.fromJson(pageListAsJson, PageList::class.java)
        val pageList = GPageList.pageList(pageListAsJson)


        assertEquals(pageListFromJson, pageList, "malformed PageList.")
    }


    @Test
    fun gPageListDeserialization() {
        val gson = Gson()
        val json = FileReader(PageListTest::class.java.getResource("/pageList/singlePageList.json").path)
        val pageListAsJson = gson.toJson(gson.fromJson(json, PageList::class.java))
        val pageList = PageList(1, listOf(Page(
                "Test-Page",
                "http://telegra.ph/Test-Page",
                "Test page title",
                "Test page description",
                imageUrl = "",
                views = 0,
                canEdit = true
        )))

        assertEquals(pageListAsJson, GPageList.pageList(pageList), "malformed JSON.")
    }

}