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

package pakisan.telegraphcli.data.test.page.node

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Before
import org.junit.Test
import pakisan.telegraphcli.data.gson.NodeDeserializer
import pakisan.telegraphcli.data.gson.NodeSerializer
import pakisan.telegraphcli.data.gson.data.page.node.GNodeElement
import pakisan.telegraphcli.data.page.node.Node
import pakisan.telegraphcli.data.page.node.NodeElement
import pakisan.telegraphcli.data.page.node.Text
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class NodeElementTest {

    private val listOfNodeElementsType = object: TypeToken<List<NodeElement>>(){}.type
    lateinit var nodeElement: NodeElement
    lateinit var nodeElements: List<NodeElement>

    @Before
    fun prepareNodeElement() {
        nodeElement = NodeElement(
                "p",
                children = listOf(
                        Node(Text("text content")),
                        Node(NodeElement(
                                "a",
                                mapOf(
                                        Pair("href", "https://m.habrahabr.ru/post/335876/"),
                                        Pair("target", "_blank")
                                ),
                                listOf(
                                        Node(Text("https://m.habrahabr.ru/post/335876/"))
                                )
                        ))
                )
        )
    }

    @Before
    fun prepareNodeElements() {
        nodeElements = listOf(nodeElement, nodeElement)
    }

    @Test
    fun nodeElementDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val nodeElementAsJson = FileReader(
                NodeElementTest::class.java.getResource("/nodeElement/nodeElement.json").path)
        val nodeElementFromJson = gson.fromJson(nodeElementAsJson, NodeElement::class.java)

        assertEquals(nodeElementFromJson, nodeElement, "malformed NodeElement.")
    }

    @Test
    fun nodeElementsDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val nodeElementsAsJson = FileReader(
                NodeElementTest::class.java.getResource("/nodeElement/nodeElements.json").path)
        val nodeElementsFromJson: List<NodeElement> = gson.fromJson(nodeElementsAsJson, listOfNodeElementsType)

        assertEquals(nodeElementsFromJson, nodeElements, "malformed NodeElement.")
    }

    @Test
    fun GNodeElementDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                NodeElementTest::class.java.getResource("/nodeElement/nodeElement.json").path))
        val nodeElementAsJson = String(jsonAsBytes)
        val nodeElementFromJson = gson.fromJson(nodeElementAsJson, NodeElement::class.java)
        val nodeElement = GNodeElement.nodeElement(nodeElementAsJson)


        assertEquals(nodeElementFromJson, nodeElement, "malformed NodeElement.")
    }

    @Test
    fun GNodeElementsDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                NodeElementTest::class.java.getResource("/nodeElement/nodeElements.json").path))
        val nodeElementsAsJson = String(jsonAsBytes)
        val nodeElementsFromJson: List<NodeElement> = gson.fromJson(nodeElementsAsJson, listOfNodeElementsType)
        val nodeElements: List<NodeElement> = GNodeElement.nodeElements(nodeElementsAsJson)


        assertEquals(nodeElementsFromJson, nodeElements, "malformed NodeElement.")
    }

    @Test
    fun GNodeElementSerialization() {
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                NodeElementTest::class.java.getResource("/nodeElement/nodeElement.json").path))
        val nodeElementAsJson = String(jsonAsBytes)

        assertEquals(nodeElementAsJson, GNodeElement.nodeElement(nodeElement, true), "malformed JSON.")
    }

    @Test
    fun GNodeElementsSerialization() {
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                NodeElementTest::class.java.getResource("/nodeElement/nodeElements.json").path))
        val nodeElementsAsJson = String(jsonAsBytes)

        assertEquals(nodeElementsAsJson, GNodeElement.nodeElements(nodeElements, true), "malformed JSON.")
    }

    @Test
    fun nodeElementSerialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeSerializer())
                .setPrettyPrinting()
                .create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                NodeElementTest::class.java.getResource("/nodeElement/nodeElement.json").path))
        val nodeElementAsJson = String(jsonAsBytes)

        assertEquals(nodeElementAsJson, gson.toJson(nodeElement), "malformed JSON.")
    }

    @Test
    fun nodeElementsSerialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeSerializer())
                .setPrettyPrinting()
                .create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                NodeElementTest::class.java.getResource("/nodeElement/nodeElements.json").path))
        val nodeElementsAsJson = String(jsonAsBytes)

        assertEquals(nodeElementsAsJson, gson.toJson(nodeElements), "malformed JSON.")
    }
}