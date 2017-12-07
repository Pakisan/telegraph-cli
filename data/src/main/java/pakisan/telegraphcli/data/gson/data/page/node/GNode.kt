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

package pakisan.telegraphcli.data.gson.data.page.node

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import pakisan.telegraphcli.data.gson.NodeDeserializer
import pakisan.telegraphcli.data.gson.NodeListDeserializer
import pakisan.telegraphcli.data.gson.NodeSerializer
import pakisan.telegraphcli.data.page.node.Node

/**
 * Serialize [Node] to json or Deserialize it from last.
 */
object GNode {

    private val listOfNodes = object: TypeToken<List<Node>>(){}.type
    private val gson = GsonBuilder()
            .registerTypeAdapter(Node::class.java, NodeDeserializer())
            .registerTypeAdapter(Node::class.java, NodeSerializer())
            .registerTypeAdapter(listOfNodes, NodeListDeserializer())
            .create()
    private val prettyGson = GsonBuilder()
            .registerTypeAdapter(Node::class.java, NodeDeserializer())
            .registerTypeAdapter(Node::class.java, NodeSerializer())
            .registerTypeAdapter(listOfNodes, NodeListDeserializer())
            .setPrettyPrinting()
            .create()

    /**
     * Deserialize [Node] from JSON.
     *
     * @param json [Node] as JSON.
     * @return [Node]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun node(json: String): Node {
        return gson.fromJson(json, Node::class.java)
    }

    /**
     * Serialize [Node] to JSON.
     *
     * @param node [Node] to serialize to JSON.
     * @return [Node] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun node(node: Node, pretty: Boolean = false): String {
        return if(pretty) {
            prettyGson.toJson(node, Node::class.java)
        } else {
            gson.toJson(node, Node::class.java)
        }
    }

    /**
     * Serialize [Node] to JSON.
     *
     * @param nodes [Node] to serialize to JSON.
     * @return [Node] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun nodes(nodes: List<Node>, pretty: Boolean = false): String {
        return if(pretty) {
            prettyGson.toJson(nodes, listOfNodes)
        } else {
            gson.toJson(nodes, listOfNodes)
        }
    }

    /**
     * Deserialize [Node] from JSON.
     *
     * @param json [Node] as JSON.
     * @return [Node]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun nodes(json: String): List<Node> {
        return gson.fromJson(json, listOfNodes)
    }

}