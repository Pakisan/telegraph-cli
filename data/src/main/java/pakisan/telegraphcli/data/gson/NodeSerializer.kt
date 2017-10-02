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
import pakisan.telegraphcli.data.page.node.Node
import pakisan.telegraphcli.data.page.node.NodeElement
import java.lang.reflect.Type

/**
 * Serialize telegra.ph [pakisan.telegraphcli.data.page.Page] into
 * [Node] with String or [NodeElement] value json.
 */
class NodeSerializer : JsonSerializer<Node> {

    override fun serialize(node: Node?, typeOfSrc: Type?,
                           context: JsonSerializationContext?): JsonElement? {
        if(node != null) {
            when(node.value) {
                is String -> {
                    return JsonPrimitive(node.value)
                }
                is NodeElement -> {
                    val jsonObject = JsonObject()
                    jsonObject.add("tag", JsonPrimitive(node.value.tag))
                    jsonObject.add("attrs", context!!.serialize(node.value.attrs))
                    jsonObject.add("children", context.serialize(node.value.children))

                    return jsonObject
                }
                else -> {
                    return null
                }
            }
        } else {
            return null
        }
    }
}