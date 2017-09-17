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
import pakisan.telegraphcli.data.page.PageViews

/**
 * Serialize [PageViews] to json or Deserialize it from last.
 */
object GPageViews {

    val gson = Gson()

    /**
     * Deserialize [PageViews] from JSON.
     *
     * @param json [PageViews] as JSON.
     * @return [PageViews]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun pageViews(json: String): PageViews {
        return gson.fromJson(json, PageViews::class.java)
    }

    /**
     * Serialize [PageViews] to JSON.
     *
     * @param pageViews [PageViews] to serialize to JSON.
     * @return [PageViews] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun pageViews(pageViews: PageViews): String {
        return gson.toJson(pageViews)
    }

}