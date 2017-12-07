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
class NodeListDeserializer : JsonDeserializer<List<Node>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?,
                             context: JsonDeserializationContext?): List<Node>? {
        return when(json) {
            is JsonArray -> {
                val nodeTypeToken = object : TypeToken<Node>(){}.type
                val nodes: MutableList<Node> = mutableListOf()
                val jsonArrayOfNodes = json.asJsonArray

                jsonArrayOfNodes.forEach {
                    val node: Node = context!!.deserialize(it, nodeTypeToken)
                    nodes.add(node)
                }

                nodes
            }
            else -> {
                emptyList()
            }
        }
    }
}