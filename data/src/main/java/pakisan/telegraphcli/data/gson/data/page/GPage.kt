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

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import pakisan.telegraphcli.data.gson.NodeDeserializer
import pakisan.telegraphcli.data.gson.NodeSerializer
import pakisan.telegraphcli.data.page.Page
import pakisan.telegraphcli.data.page.node.Node

/**
 * Serialize [Page] to json or Deserialize it from last.
 */
object GPage {

    private val listOfPagesType = object: TypeToken<List<Page>>(){}.type
    private val gson = GsonBuilder()
            .registerTypeAdapter(Node::class.java, NodeDeserializer())
            .registerTypeAdapter(Node::class.java, NodeSerializer())
            .create()
    private val prettyGson = GsonBuilder()
            .registerTypeAdapter(Node::class.java, NodeDeserializer())
            .registerTypeAdapter(Node::class.java, NodeSerializer())
            .setPrettyPrinting()
            .create()

    /**
     * Deserialize [Page] from JSON.
     *
     * @param json [Page] as JSON.
     * @return [Page]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun page(json: String): Page {
        return gson.fromJson(json, Page::class.java)
    }

    /**
     * Serialize [Page] to JSON.
     *
     * @param page [Page] to serialize to JSON.
     * @return [Page] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun page(page: Page, pretty: Boolean = false): String {
        return if(pretty) {
            prettyGson.toJson(page)
        } else {
            gson.toJson(page)
        }
    }

    /**
     * Deserialize List of [Page] from JSON.
     *
     * @param json [Page] as JSON.
     * @return [Page]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun pages(json: String): List<Page> {
        return gson.fromJson(json, listOfPagesType)
    }

    /**
     * Serialize List of [Page] to JSON.
     *
     * @param pages [Page] to serialize to JSON.
     * @return [Page] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun pages(pages: List<Page>, pretty: Boolean = false): String {
        return if(pretty) {
            prettyGson.toJson(pages, listOfPagesType)
        } else {
            gson.toJson(pages, listOfPagesType)
        }
    }

}