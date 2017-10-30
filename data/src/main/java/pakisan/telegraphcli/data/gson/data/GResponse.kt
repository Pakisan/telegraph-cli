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

package pakisan.telegraphcli.data.gson.data

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import pakisan.telegraphcli.data.gson.NodeDeserializer
import pakisan.telegraphcli.data.gson.NodeSerializer
import pakisan.telegraphcli.data.page.node.Node
import pakisan.telegraphcli.data.response.Response
import java.lang.reflect.Type

/**
 * Serialize [Response] to json or Deserialize it from last.
 */
object GResponse {

    val gson = GsonBuilder()
            .registerTypeAdapter(Node::class.java, NodeDeserializer())
            .registerTypeAdapter(Node::class.java, NodeSerializer())
            .create()

    /**
     * Deserialize [Response] from JSON.
     *
     * @param json [Response] as JSON.
     * @return [Response]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun <DataType> response(json: String, typeToken: Type): Response<DataType> {
        return gson.fromJson(json, typeToken)
    }

    /**
     * Serialize [Response] to JSON.
     *
     * @param response [Response] to serialize to JSON.
     * @return [Response] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun <DataType> response(response: Response<DataType>): String {
        val viewsResponseType = object: TypeToken<Response<DataType>>(){}.type
        return gson.toJson(response, viewsResponseType)
    }

}