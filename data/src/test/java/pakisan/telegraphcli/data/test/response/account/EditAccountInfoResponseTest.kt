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
import com.google.gson.reflect.TypeToken
import org.junit.Test
import pakisan.telegraphcli.data.Account
import pakisan.telegraphcli.data.gson.data.GResponse
import pakisan.telegraphcli.data.response.Response
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class EditAccountInfoResponseTest {

    private val editAccountInfoResponseType = object: TypeToken<Response<Account>>(){}.type

    val account = Account(
            "Sandbox",
            "Anonymous",
            "",
            "ca90f944adb2b8f7f4f9d07c4cf6aae72af4461cc252cbcfad4a2d9aeabd",
            pageCount = 0
    )

    @Test
    fun editAccountInfoResponseDeserializationTest() {
        val gson = Gson()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                EditAccountInfoResponseTest::class.java.getResource("/response/account/editAccountInfoResponse.json").path))
        val editAccountInfoResponseAsJson = String(jsonAsBytes)
        val editAccountInfoResponseFromJson: Response<Account> = gson.fromJson(editAccountInfoResponseAsJson,
                editAccountInfoResponseType)
        val editAccountInfoResponse: Response<Account> = GResponse.response(editAccountInfoResponseAsJson, editAccountInfoResponseType)

        assertEquals(editAccountInfoResponseFromJson, editAccountInfoResponse, "malformed editAccountInfo response.")
    }


    @Test
    fun editAccountInfoResponseSerialization() {
        val gson = Gson()
        val json = FileReader(EditAccountInfoResponseTest::class.java.getResource("/response/account/editAccountInfoResponse.json").path)
        val editAccountInfoResponse = gson.toJson(gson.fromJson<Response<Account>>(json, editAccountInfoResponseType))
        val gEditAccountInfoResponse = Response(true, result = account)

        assertEquals(editAccountInfoResponse, GResponse.response(gEditAccountInfoResponse), "malformed editAccountInfo JSON.")
    }

}