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
import pakisan.telegraphcli.data.gson.data.page.GPageViews
import pakisan.telegraphcli.data.page.PageViews
import pakisan.telegraphcli.data.test.AccountTest
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class PageViewsTest {

    @Test
    fun pageViewsSerialization() {
        val gson = Gson()
        val pageViewsAsJson = FileReader(AccountTest::class.java.getResource("/pageViews.json").path)
        val pageViewsFromJson = gson.fromJson(pageViewsAsJson, PageViews::class.java)
        val account = PageViews(50)

        assertEquals(pageViewsFromJson, account, "malformed PageViews.")
    }

    @Test
    fun pageViewsDeserialization() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                PageViewsTest::class.java.getResource("/pageViews.json").path))
        val pageViewsAsJson = String(jsonAsBytes)
        val pageViews = PageViews(50)

        assertEquals(pageViewsAsJson, gson.toJson(pageViews), "malformed JSON.")
    }

    @Test
    fun gPageViewsSerialization() {
        val gson = Gson()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                PageViewsTest::class.java.getResource("/pageViews.json").path))
        val pageViewsAsJson = String(jsonAsBytes)
        val pageViewsFromJson = gson.fromJson(pageViewsAsJson, PageViews::class.java)
        val pageViews = GPageViews.pageViews(pageViewsAsJson)


        assertEquals(pageViewsFromJson, pageViews, "malformed PageViews.")
    }


    @Test
    fun gPageViewsDeserialization() {
        val gson = Gson()
        val json = FileReader(PageViewsTest::class.java.getResource("/pageViews.json").path)
        val pageViewsAsJson = gson.toJson(gson.fromJson(json, PageViews::class.java))
        val pageViews = PageViews(50)

        assertEquals(pageViewsAsJson, GPageViews.pageViews(pageViews), "malformed JSON.")
    }

}