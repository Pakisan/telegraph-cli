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
import pakisan.telegraphcli.data.gson.data.GResponse
import pakisan.telegraphcli.data.page.Page
import pakisan.telegraphcli.data.response.Response
import pakisan.telegraphcli.data.response.Result
import pakisan.telegraphcli.requests.Telegraph

object Page {

    /**
     * Use this method to get a Telegraph page. Returns a Page object on success.
     *
     * @param path Required. Path to the Telegraph page (in the format Title-12-31, i.e. everything
     * that comes after http://telegra.ph/).
     * @param returnContent If true, content field will be returned in Page object.
     *
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return [Response] with [Page].
     */
    @Synchronized
    fun getPage(path: String, returnContent: Boolean = false): Response<Page> {
        val response: HttpResponse<JsonNode> = Unirest.get(
                Telegraph.getPage(path, returnContent)).asJson()
        return GResponse.response(response.body.toString(), object: TypeToken<Response<Page>>(){}.type)
    }

    /**
     * Use this method to create a new Telegraph page. On success, returns a Page object.
     *
     * @param accessToken Required. Access token of the Telegraph account.
     * @param title Required. Page title.
     * @param authorName Author name, displayed below the article's title.
     * @param authorUrl Profile link, opened when users click on the author's name below the title.
     * Can be any link, not necessarily to a Telegram profile or channel.
     * @param content Required. Content of the page.
     * @param returnContent If true, a content field will be returned in the Page object
     * (see: http://telegra.ph/api#Content-format).
     *
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return [Response] with [Page].
     */
    @Synchronized
    fun createPage(accessToken: String, title: String, authorName: String = "",
                   authorUrl: String = "", content: String, returnContent: Boolean = false): Response<Page> {
        val response: HttpResponse<JsonNode> = Unirest.get(
                Telegraph.createPage(accessToken, title, authorName, authorUrl, content, returnContent)).asJson()
        return GResponse.response(response.body.toString(), object: TypeToken<Response<Page>>(){}.type)
    }

    /**
     * Use this method to edit an existing Telegraph page. On success, returns a Page object.
     *
     * @param accessToken Required. Access token of the Telegraph account.
     * @param path Required. Path to the page.
     * @param title Required. Page title.
     * @param content Required. Content of the page.
     * @param authorName Author name, displayed below the article's title.
     * @param authorUrl Profile link, opened when users click on the author's name below the title.
     * Can be any link, not necessarily to a Telegram profile or channel.
     * @param returnContent If true, a content field will be returned in the Page object.
     *
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return [Response] with [Page].
     */
    @Synchronized
    fun editPage(accessToken: String, path: String, title: String, content: String,
                 authorName: String = "", authorUrl: String = "", returnContent: Boolean = false): Response<Page> {
        val response: HttpResponse<JsonNode> = Unirest.get(
                Telegraph.editPage(accessToken, path, title, authorName, authorUrl, content, returnContent)).asJson()
        return GResponse.response(response.body.toString(), object: TypeToken<Response<Page>>(){}.type)
    }

    /**
     * Use this method to get a list of pages belonging to a Telegraph account. Returns a PageList
     * object, sorted by most recently created pages first.
     *
     * @param accessToken Required. Access token of the Telegraph account.
     * @param offset Sequential number of the first page to be returned.
     * @param limit Limits the number of pages to be retrieved.
     *
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return [Response] with [Result].
     */
    @Synchronized
    fun getPageList(accessToken: String, offset: Int = 0, limit: Int = 50): Response<Result> {
        val response: HttpResponse<JsonNode> = Unirest.get(
                Telegraph.getPageList(accessToken, offset, limit)).asJson()
        return GResponse.response(response.body.toString(), object: TypeToken<Response<Result>>(){}.type)
    }

}