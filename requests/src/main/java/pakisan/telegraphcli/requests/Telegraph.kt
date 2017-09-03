
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

package pakisan.telegraphcli.requests

import java.net.URLEncoder
import java.nio.charset.Charset

/**
 * Generates endpoints for telegra.ph api.
 * http://telegra.ph/api
 */
object Telegraph {
    private val endpointRoot = "https://api.telegra.ph/"

    const val SHORT_NAME_MAX_LENGTH = 32
    const val AUTHOR_NAME_MAX_LENGTH = 128
    const val AUTHOR_URL_MAX_LENGTH = 512

    const val MAX_PAGE_TITLE_LENGTH = 256
    const val MAX_CONTENT_SIZE_KB = 64 * 1024

    const val MIN_YEAR = 2000
    const val MAX_YEAR = 2100

    const val URL_ENCODING = "UTF-8"

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
     * @return String, which contains url to endpoint with given parameters.
     */
    @Synchronized
    fun createAccount(shortName: String, authorName: String = "", authorUrl: String = ""): String {
        validateAccountParams(shortName, authorName, authorUrl)
        val endpoint = StringBuilder()
        endpoint.append("${endpointRoot}createAccount?")
                .append("short_name=${URLEncoder.encode(shortName, URL_ENCODING)}")
        if (authorName.isNotBlank()) {
            endpoint.append("&author_name=${URLEncoder.encode(authorName, URL_ENCODING)}")
        }
        if (authorUrl.isNotBlank()) {
            endpoint.append("&author_url=$authorUrl")
        }

        return endpoint.toString()
    }

    @Synchronized
    private fun validateAccountParams(shortName: String, authorName: String, authorUrl: String) {
        if (shortName.isBlank() || shortName.length > SHORT_NAME_MAX_LENGTH) {
            throw IllegalArgumentException("shortName can't be empty or blank and must be " +
                    "shorter than $SHORT_NAME_MAX_LENGTH symbols")
        }
        if (authorName.length > AUTHOR_NAME_MAX_LENGTH) {
            throw IllegalArgumentException("authorName must be " +
                    "shorter than $AUTHOR_NAME_MAX_LENGTH symbols")
        }
        if (authorUrl.length > AUTHOR_URL_MAX_LENGTH) {
            throw IllegalArgumentException("authorName must be " +
                    "shorter than $AUTHOR_URL_MAX_LENGTH symbols")
        }
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
     * @return String, which contains url to endpoint with given parameters.
     */
    @Synchronized
    fun editAccountInfo(accessToken: String, shortName: String, authorName: String = "",
                                authorUrl: String = ""): String {
        validateAccountParams(shortName, authorName, authorUrl)
        validateToken(accessToken)
        val endpoint = StringBuilder()
        endpoint.append("${endpointRoot}editAccountInfo?")
                .append("access_token=${URLEncoder.encode(accessToken, "UTF-8")}")
        if (shortName.isNotBlank()) {
            endpoint.append("&short_name=${URLEncoder.encode(shortName, URL_ENCODING)}")
        }
        if (authorName.isNotBlank()) {
            endpoint.append("&author_name=${URLEncoder.encode(authorName, URL_ENCODING)}")
        }
        if (authorUrl.isNotBlank()) {
            endpoint.append("&author_url=$authorUrl")
        }

        return endpoint.toString()

    }

    @Synchronized
    private fun validateToken(accessToken: String) {
        if (accessToken.isBlank()) {
            throw IllegalArgumentException("accessToken can't be blank")
        }
    }

    /**
     * Use this method to get information about a Telegraph account. Returns an Account object on success.
     *
     * @param accessToken Required. Access token of the Telegraph account.
     * @param fields (Array of String, default = [“short_name”,“author_name”,“author_url”]) List of account
     * fields to return. Available fields: short_name, author_name, author_url, auth_url, page_count.
     *
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return String, which contains url to endpoint with given parameters.
     */
    @Synchronized
    fun getAccountInfo(accessToken: String, fields: Array<String> = arrayOf()): String {
        validateToken(accessToken)
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}getAccountInfo?")
                .append("access_token=${URLEncoder.encode(accessToken, URL_ENCODING)}")

        if (fields.isNotEmpty()) {
            val handledFields = mutableListOf<String>()
            fields.forEach { handledFields.add("\"${URLEncoder.encode(it, URL_ENCODING)}\"") }
            endpoint.append("&fields=")
                    .append(handledFields.joinToString(",", "[", "]"))
        }

        return endpoint.toString()
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
     * @return String, which contains url to endpoint with given parameters.
     */
    @Synchronized
    fun revokeAccessToken(accessToken: String): String {
        validateToken(accessToken)
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}revokeAccessToken?")
                .append("access_token=${URLEncoder.encode(accessToken, URL_ENCODING)}")

        return endpoint.toString()
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
     * @return String, which contains url to endpoint with given parameters.
     */
    @Synchronized
    fun createPage(accessToken: String, title: String, authorName: String = "",
                   authorUrl: String = "", content: String, returnContent: Boolean = false): String {
        validateToken(accessToken)
        validateAccountParams("plug", authorName, authorUrl)
        validatePageParams(title, content, "plug")
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}createPage?")
                .append("access_token=${URLEncoder.encode(accessToken, URL_ENCODING)}")
                .append("&title=${URLEncoder.encode(title, URL_ENCODING)}")
        if (authorName.isNotBlank()) {
            endpoint.append("&author_name=${URLEncoder.encode(authorName, URL_ENCODING)}")
        }
        if (authorUrl.isNotBlank()) {
            endpoint.append("&author_url=$authorUrl")
        }
        endpoint.append("&content=$content")
                .append("&return_content=$returnContent")

        return endpoint.toString()
    }

    @Synchronized
    private fun validatePageParams(title: String, content: String, path: String) {
        if (title.isBlank() || title.length > MAX_PAGE_TITLE_LENGTH) {
            throw IllegalArgumentException("title can't be blank and must be shorter " +
                    "than $MAX_PAGE_TITLE_LENGTH")
        }
        if (content.isBlank() || ((content.toByteArray(
                Charset.forName(URL_ENCODING)).size * 1024) > MAX_CONTENT_SIZE_KB)) {
            throw IllegalArgumentException("content can't be blank and must be less " +
                    "than $MAX_CONTENT_SIZE_KB kb")
        }
        if (path.isBlank()) {
            throw IllegalArgumentException("path can't be blank")
        }
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
     * @return String, which contains url to endpoint with given parameters.
     */
    @Synchronized
    fun editPage(accessToken: String, path: String, title: String, content: String,
                 authorName: String = "", authorUrl: String = "", returnContent: Boolean = false): String {
        validateToken(accessToken)
        validatePageParams(title, content, path)
        validateAccountParams("plug", authorName, authorUrl)
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}editPage/$path")
                .append("?access_token=${URLEncoder.encode(accessToken, URL_ENCODING)}")
                .append("&title=${URLEncoder.encode(title, URL_ENCODING)}")
        if (authorName.isNotBlank()) {
            endpoint.append("&author_name=${URLEncoder.encode(authorName, URL_ENCODING)}")
        }
        if (authorUrl.isNotBlank()) {
            endpoint.append("&author_url=$authorUrl")
        }
        endpoint.append("&content=$content")
                .append("&return_content=$returnContent")

        return endpoint.toString()
    }

    /**
     * Use this method to get a Telegraph page. Returns a Page object on success.
     *
     * @param path Required. Path to the Telegraph page (in the format Title-12-31, i.e. everything
     * that comes after http://telegra.ph/).
     * @param returnContent If true, content field will be returned in Page object.
     *
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return String, which contains url to endpoint with given parameters.
     */
    @Synchronized
    fun getPage(path: String, returnContent: Boolean = false): String {
        validatePageParams("plug", "plug", path)
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}getPage/$path")
                .append("?return_content=$returnContent")

        return endpoint.toString()
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
     * @return String, which contains url to endpoint with given parameters.
     */
    @Synchronized
    fun getPageList(accessToken: String, offset: Int = 0, limit: Int = 50): String {
        validateToken(accessToken)
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}getPageList")
                .append("?access_token=$accessToken")
                .append("&offset=$offset")
                .append("&limit=$limit")

        return endpoint.toString()
    }

    /**
     * Use this method to get the number of views for a Telegraph article. Returns a PageViews object
     * on success. By default, the total number of page views will be returned.
     *
     * @param path Required. Path to the Telegraph page (in the format Title-12-31, where 12 is the
     * month and 31 the day the article was first published).
     * @param year The number of page views for the requested year will be returned.
     * @param month The number of page views for the requested month will be returned.
     * @param day The number of page views for the requested day will be returned.
     * @param hour If passed, the number of page views for the requested hour will be returned.
     *
     * @throws IllegalArgumentException in case when fields are incorrect.
     *
     * @return String, which contains url to endpoint with given parameters.
     */
    @Synchronized
    fun getViews(path: String, year: Int, month: Int, day: Int, hour: Int): String {
        validatePageParams("plug", "plug", path)
        validateTimeUnits(year, month, day, hour)
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}getViews/$path")
                .append("?year=$year")
                .append("&month=$month")
                .append("&day=$day")
                .append("&hour=$hour")

        return endpoint.toString()
    }

    @Synchronized
    fun getViews(path: String, hour: Int): String {
        validatePageParams("plug", "plug", path)
        validateTimeUnits(null, null, null, hour)
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}getViews/$path")
                .append("?hour=$hour")

        return endpoint.toString()
    }

    @Synchronized
    fun getViews(path: String, day: Int, hour: Int): String {
        validatePageParams("plug", "plug", path)
        validateTimeUnits(null, null, day, hour)
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}getViews/$path")
                .append("?day=$day")
                .append("&hour=$hour")

        return endpoint.toString()
    }

    @Synchronized
    fun getViews(path: String, month: Int, day: Int, hour: Int): String {
        validatePageParams("plug", "plug", path)
        validateTimeUnits(null, month, day, hour)
        val endpoint = StringBuilder()

        endpoint.append("${endpointRoot}getViews/$path")
                .append("?month=$month")
                .append("&day=$day")
                .append("&hour=$hour")

        return endpoint.toString()
    }

    @Synchronized
    private fun validateYear(year: Int) {
        if (year !in 2000..21000) {
            throw IllegalArgumentException("year must be between $MIN_YEAR and $MAX_YEAR")
        }
    }

    @Synchronized
    private fun validateMonth(month: Int) {
        if (month !in 1..12) {
            throw IllegalArgumentException("month must be between 1 and 12")
        }
    }

    @Synchronized
    private fun validateDay(day: Int) {
        if (day !in 1..31) {
            throw IllegalArgumentException("day must be between 1 and 12")
        }
    }

    @Synchronized
    private fun validateHour(hour: Int) {
        if (hour !in 0..24) {
            throw IllegalArgumentException("hour must be between 0 and 24")
        }
    }

    @Synchronized
    private fun validateTimeUnits(year: Int? = null, month: Int? = null,
                                  day: Int? = null, hour: Int) {
        if(year != null) {
            validateYear(year)
        }
        if(month != null) {
            validateMonth(month)
        }
        if(day != null) {
            validateDay(day)
        }
        validateHour(hour)
    }

}