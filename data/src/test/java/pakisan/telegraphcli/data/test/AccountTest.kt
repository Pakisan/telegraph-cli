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

package pakisan.telegraphcli.data.test

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Test
import pakisan.telegraphcli.data.Account
import pakisan.telegraphcli.data.gson.data.GAccount
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AccountTest {

    @Test
    fun accountRequiredFields() {
        val gson = Gson()
        val accountAsJson = FileReader(AccountTest::class
                .java
                .getResource("/account/accountWithRequiredFields.json").path)
        val account = gson.fromJson(accountAsJson, Account::class.java)
        assertNotNull(account.shortName, "shortName is required and must not be NULL.")
        assertNotNull(account.authorName, "authorName is required and must not be NULL.")
        assertNotNull(account.authorUrl, "authorUrl is required and must not be NULL.")
    }

    @Test
    fun accountDeserialization() {
        val gson = Gson()
        val accountAsJson = FileReader(AccountTest::class.java.getResource("/account/account.json").path)
        val accountFromJson = gson.fromJson(accountAsJson, Account::class.java)
        val account = Account(
                "Author short name",
                "Author name",
                "Author URL",
                "Access token",
                "Auth URL",
                50
        )

        assertEquals(accountFromJson, account, "malformed Account.")
    }

    @Test
    fun accountSerialization() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                AccountTest::class.java.getResource("/account/account.json").path))
        val accountAsJson = String(jsonAsBytes)
        val account = Account(
                "Author short name",
                "Author name",
                "Author URL",
                "Access token",
                "Auth URL",
                50
        )

        assertEquals(accountAsJson, gson.toJson(account), "malformed JSON.")
    }

    @Test
    fun gAccountDeserialization() {
        val gson = Gson()
        val jsonAsBytes = Files.readAllBytes(Paths.get(
                AccountTest::class.java.getResource("/account/account.json").path))
        val accountAsJson = String(jsonAsBytes)
        val accountFromJson = gson.fromJson(accountAsJson, Account::class.java)
        val account = GAccount.account(accountAsJson)


        assertEquals(accountFromJson, account, "malformed Account.")
    }


    @Test
    fun gAccountSerialization() {
        val gson = Gson()
        val json = FileReader(AccountTest::class.java.getResource("/account/account.json").path)
        val accountAsJson = gson.toJson(gson.fromJson(json, Account::class.java))
        val account = Account(
                "Author short name",
                "Author name",
                "Author URL",
                "Access token",
                "Auth URL",
                50
        )

        assertEquals(accountAsJson, GAccount.account(account), "malformed JSON.")
    }

}