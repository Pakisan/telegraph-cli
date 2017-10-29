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

package pakisan.telegraphcli.data.test.response.account

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test
import pakisan.telegraphcli.data.Account
import pakisan.telegraphcli.data.gson.NodeDeserializer
import pakisan.telegraphcli.data.gson.NodeSerializer
import pakisan.telegraphcli.data.gson.data.GResponse
import pakisan.telegraphcli.data.page.Page
import pakisan.telegraphcli.data.page.node.Node
import pakisan.telegraphcli.data.page.node.NodeElement
import pakisan.telegraphcli.data.response.Response
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class CreateAccountResponseTest {

    private val createAccountResponseType = object: TypeToken<Response<Account>>(){}.type

    val account = Account(
            "Sandbox",
            "Anonymous",
            "",
            "ca90f944adb2b8f7f4f9d07c4cf6aae72af4461cc252cbcfad4a2d9aeabd",
            "https://edit.telegra.ph/auth/XjB2sC7FZYSzFu3h61HigkGFqjNJLxpGR0HhDOVAKN",
            0
    )

    @Test
    fun createAccountResponseDeserializationTest() {
        val gson = Gson()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                CreateAccountResponseTest::class.java.getResource("/response/account/createAccountResponse.json").path))
        val createAccountResponseAsJson = String(jsonAsBytes)
        val createAccountResponseFromJson: Response<Page> = gson.fromJson(createAccountResponseAsJson,
                createAccountResponseType)
        val createAccountResponse: Response<Page> = GResponse.response(createAccountResponseAsJson, createAccountResponseType)

        println(createAccountResponseFromJson)
        println(createAccountResponse)

        assertEquals(createAccountResponseFromJson, createAccountResponse, "malformed createAccount response.")
    }


    @Test
    fun createAccountResponseSerialization() {
        val gson = Gson()
        val json = FileReader(CreateAccountResponseTest::class.java.getResource("/response/account/createAccountResponse.json").path)
        val createAccountResponse = gson.toJson(gson.fromJson<Response<Page>>(json, createAccountResponseType))
        val gCreateAccountResponse = Response(true, result = account)

        println(createAccountResponse)
        println(GResponse.response(gCreateAccountResponse))

        assertEquals(createAccountResponse, GResponse.response(gCreateAccountResponse), "malformed createAccount JSON.")
    }

}