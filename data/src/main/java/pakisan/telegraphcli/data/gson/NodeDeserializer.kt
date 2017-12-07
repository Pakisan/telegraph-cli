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

package pakisan.telegraphcli.data.gson

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import pakisan.telegraphcli.data.page.node.Node
import pakisan.telegraphcli.data.page.node.NodeElement
import pakisan.telegraphcli.data.page.node.Text
import java.lang.reflect.Type

/**
 * Deserialize telegra.ph [pakisan.telegraphcli.data.page.Page] into
 * [Node] with String or [NodeElement] value.
 */
class NodeDeserializer : JsonDeserializer<Node> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?,
                             context: JsonDeserializationContext?): Node? {
        return when(json) {
            is JsonPrimitive -> Node(Text(json.asString))
            is JsonObject -> {
                val map = object: TypeToken<Map<String, String>>(){}.type
                val list = object: TypeToken<List<Node>>(){}.type

                val jsonObject = json.getAsJsonObject()
                val nodeElement = NodeElement(
                        jsonObject.get("tag").asString,
                        context!!.deserialize(jsonObject.get("attrs"), map),
                        context.deserialize(jsonObject.get("children"), list)
                )
                Node(nodeElement)
            }
            else -> {
                null
            }
        }
    }
}