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

package pakisan.telegraphcli.api.unit

import com.google.gson.reflect.TypeToken
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import pakisan.telegraphcli.data.Account
import pakisan.telegraphcli.data.gson.data.GAccount
import pakisan.telegraphcli.data.gson.data.GResponse
import pakisan.telegraphcli.data.response.Response
import pakisan.telegraphcli.requests.Telegraph

object Account {

    /**
     * Use this method to create a new Telegraph account. Most users only need one account, but
     * this can be useful for channel administrators who would like to keep individual author
     * names and profile links for each of their channels. On success, returns an Account object
     * with the regular fields and an additional access_token field.
     *
     * @param shortName Required. Account name, helps users with several accounts remember which
     * they are currently using. Displayed to the user above the "Edit/Publish" button on Telegra.ph,
     * other users don't see this name.
     * @param authorName author_name Default author name used when creating new articles.
     * @param authorUrl Default profile link, opened when users click on the author's name below the title.
     * Can be any link, not necessarily to a Telegram profile or channel.
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return [Response] with [Account].
     */
    @Synchronized
    fun createAccount(shortName: String, authorName: String = "", authorUrl: String = ""): Response<Account> {
        val response: HttpResponse<JsonNode> = Unirest.get(
                Telegraph.createAccount(shortName, authorName, authorUrl)).asJson()
        return GResponse.response(response.body.toString(),
                object: TypeToken<Response<Account>>(){}.type)
    }

    /**
     * Use this method to update information about a Telegraph account. Pass only the parameters
     * that you want to edit. On success, returns an Account object with the default fields.
     *
     * @param accessToken Required. Access token of the Telegraph account.
     * @param shortName New account name.
     * @param authorName New default author name used when creating new articles.
     * @param authorUrl New default profile link, opened when users click on the author's name
     * below the title. Can be any link, not necessarily to a Telegram profile or channel.
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return [Response] with [Account].
     */
    @Synchronized
    fun editAccountInfo(accessToken: String, shortName: String, authorName: String = "",
                        authorUrl: String = ""): Response<Account> {
        val response: HttpResponse<JsonNode> = Unirest.get(
                Telegraph.editAccountInfo(accessToken, shortName, authorName, authorUrl)).asJson()
        return GResponse.response(response.body.toString(),
                object: TypeToken<Response<Account>>(){}.type)
    }

    /**
     * Use this method to revoke access_token and generate a new one, for example, if the user would
     * like to reset all connected sessions, or you have reasons to believe the token was compromised.
     * On success, returns an Account object with new access_token and auth_url fields.
     *
     * @param accessToken Required. Access token of the Telegraph account.
     *
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return [Response] with [Account].
     */
    @Synchronized
    fun revokeAccessToken(accessToken: String): Response<Account> {
        val response: HttpResponse<JsonNode> = Unirest.get(
                Telegraph.revokeAccessToken(accessToken)).asJson()
        return GResponse.response(response.body.toString(),
                object: TypeToken<Response<Account>>(){}.type)
    }

}