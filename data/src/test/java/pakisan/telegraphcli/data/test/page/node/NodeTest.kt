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
import org.junit.Before
import org.junit.Test
import pakisan.telegraphcli.data.gson.NodeDeserializer
import pakisan.telegraphcli.data.gson.NodeSerializer
import pakisan.telegraphcli.data.gson.data.page.node.GNode
import pakisan.telegraphcli.data.page.node.Node
import pakisan.telegraphcli.data.page.node.NodeElement
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class NodeTest {

    lateinit var node:Node

    @Before
    fun prepareNode() {
        node =  Node(NodeElement(
                "p",
                children = listOf(
                        Node("text content"),
                        Node(NodeElement(
                                "a",
                                mapOf(
                                        Pair("href", "https://m.habrahabr.ru/post/335876/"),
                                        Pair("target", "_blank")
                                ),
                                listOf(
                                        Node("https://m.habrahabr.ru/post/335876/")
                                )
                        ))
                )
        ))
    }

    @Test
    fun nodeDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val nodeAsJson = FileReader(NodeTest::class.java.getResource("/node/node.json").path)
        val nodeFromJson = gson.fromJson(nodeAsJson, Node::class.java)

        assertEquals(nodeFromJson, node, "malformed Node.")
    }

    @Test
    fun GNodeDeserialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeDeserializer())
                .create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                NodeTest::class.java.getResource("/node/node.json").path))
        val nodeAsJson = String(jsonAsBytes)
        val nodeFromJson = gson.fromJson(nodeAsJson, Node::class.java)
        val node = GNode.node(nodeAsJson)


        assertEquals(nodeFromJson, node, "malformed Node.")
    }

    @Test
    fun GNodeSerialization() {
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                NodeTest::class.java.getResource("/node/node.json").path))
        val nodeAsJson = String(jsonAsBytes)

        assertEquals(nodeAsJson, GNode.node(node, true), "malformed JSON.")
    }

    @Test
    fun nodeSerialization() {
        val gson = GsonBuilder()
                .registerTypeAdapter(Node::class.java, NodeSerializer())
                .setPrettyPrinting()
                .create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                NodeTest::class.java.getResource("/node/node.json").path))
        val nodeAsJson = String(jsonAsBytes)

        assertEquals(nodeAsJson, gson.toJson(node), "malformed JSON.")
    }
}