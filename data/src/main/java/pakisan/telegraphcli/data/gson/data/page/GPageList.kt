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

package pakisan.telegraphcli.data.gson.data.page

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pakisan.telegraphcli.data.page.PageList

/**
 * Serialize [PageList] to json or Deserialize it from last.
 */
object GPageList {

    val listOfPageLists = object: TypeToken<List<PageList>>(){}.type
    val gson = Gson()

    /**
     * Deserialize [PageList] from JSON.
     *
     * @param json [PageList] as JSON.
     * @return [PageList]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun pageList(json: String): PageList {
        return gson.fromJson(json, PageList::class.java)
    }

    /**
     * Serialize [PageList] to JSON.
     *
     * @param page [PageList] to serialize to JSON.
     * @return [PageList] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun pageList(page: PageList): String {
        return gson.toJson(page)
    }

    /**
     * Deserialize List of [PageList] from JSON.
     *
     * @param json [PageList] as JSON.
     * @return [PageList]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun pageLists(json: String): PageList {
        return gson.fromJson(json, listOfPageLists)
    }

    /**
     * Serialize List of [PageList] to JSON.
     *
     * @param pageLists [PageList] to serialize to JSON.
     * @return [PageList] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun pageLists(pageLists: List<PageList>): String {
        return gson.toJson(pageLists, listOfPageLists)
    }
    
}