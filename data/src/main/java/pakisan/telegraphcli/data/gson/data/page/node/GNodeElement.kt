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
import pakisan.telegraphcli.data.gson.NodeSerializer
import pakisan.telegraphcli.data.page.node.Node
import pakisan.telegraphcli.data.page.node.NodeElement

/**
 * Serialize [NodeElement] to json or Deserialize it from last.
 */
object GNodeElement {


    private val listOfNodeElementsType = object: TypeToken<List<NodeElement>>(){}.type
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
     * Deserialize [NodeElement] from JSON.
     *
     * @param json [NodeElement] as JSON.
     * @return [NodeElement]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun nodeElement(json: String): NodeElement {
        return gson.fromJson(json, NodeElement::class.java)
    }

    /**
     * Serialize [NodeElement] to JSON.
     *
     * @param nodeElement [NodeElement] to serialize to JSON.
     * @return [NodeElement] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun nodeElement(nodeElement: NodeElement, pretty: Boolean = false): String {
        return if(pretty) {
            prettyGson.toJson(nodeElement, NodeElement::class.java)
        } else {
            gson.toJson(nodeElement, NodeElement::class.java)
        }
    }

    /**
     * Deserialize List of [NodeElement] from JSON.
     *
     * @param json [NodeElement] as JSON.
     * @return [NodeElement]
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun nodeElements(json: String): List<NodeElement> {
        return gson.fromJson(json, listOfNodeElementsType)
    }

    /**
     * Serialize List of [NodeElement] to JSON.
     *
     * @param nodeElements [NodeElement] to serialize to JSON.
     * @return [NodeElement] as JSON.
     * @throws [com.google.gson.JsonSyntaxException] in case when JSON was malformed.
     */
    @Synchronized
    fun nodeElements(nodeElements: List<NodeElement>, pretty: Boolean = false): String {
        return if(pretty) {
            prettyGson.toJson(nodeElements, listOfNodeElementsType)
        } else {
            gson.toJson(nodeElements, listOfNodeElementsType)
        }
    }

}