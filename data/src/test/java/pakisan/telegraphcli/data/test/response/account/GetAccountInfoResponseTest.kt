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

class GetAccountInfoResponseTest {

    private val getAccountInfoResponseType = object: TypeToken<Response<Account>>(){}.type

    val account = Account(
            "Sandbox",
            "Anonymous",
            "",
            authUrl = "https://edit.telegra.ph/auth/XjB2sC7FZYSzFu3h61HigkGFqjNJLxpGR0HhDOVAKN",
            pageCount = 0
    )

    @Test
    fun getAccountInfoResponseDeserializationTest() {
        val gson = Gson()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                GetAccountInfoResponseTest::class.java.getResource("/response/account/getAccountInfoResponse.json").path))
        val getAccountInfoResponseAsJson = String(jsonAsBytes)
        val getAccountInfoResponseFromJson: Response<Account> = gson.fromJson(getAccountInfoResponseAsJson,
                getAccountInfoResponseType)
        val getAccountInfoResponse: Response<Account> = GResponse.response(getAccountInfoResponseAsJson, getAccountInfoResponseType)

        assertEquals(getAccountInfoResponseFromJson, getAccountInfoResponse, "malformed getAccountInfo response.")
    }


    @Test
    fun getAccountInfoResponseSerialization() {
        val gson = Gson()
        val json = FileReader(GetAccountInfoResponseTest::class.java.getResource("/response/account/getAccountInfoResponse.json").path)
        val getAccountInfoResponse = gson.toJson(gson.fromJson<Response<Account>>(json, getAccountInfoResponseType))
        val gGetAccountInfoResponse = Response(true, result = account)

        assertEquals(getAccountInfoResponse, GResponse.response(gGetAccountInfoResponse), "malformed getAccountInfo JSON.")
    }

}