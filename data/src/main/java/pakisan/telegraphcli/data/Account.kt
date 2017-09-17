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

package pakisan.telegraphcli.data

import com.google.gson.annotations.SerializedName

/**
 * This object represents a Telegraph account.
 *
 * @param shortName Account name, helps users with several accounts remember which they are
 * currently using. Displayed to the user above the "Edit/Publish" button on Telegra.ph,
 * other users don't see this name.
 * @param authorName Default author name used when creating new articles.
 * @param authorUrl Profile link, opened when users click on the author's name below the title.
 * Can be any link, not necessarily to a Telegram profile or channel.
 * @param accessToken Optional. Only returned by the createAccount and revokeAccessToken method.
 * Access token of the Telegraph account.
 * @param authUrl Optional. URL to authorize a browser on telegra.ph and connect it to a Telegraph account.
 * This URL is valid for only one use and for 5 minutes only.
 * @param pageCount Optional. Number of pages belonging to the Telegraph account.
 */
data class Account(
        @SerializedName("short_name") val shortName: String,
        @SerializedName("author_name") val authorName: String,
        @SerializedName("author_url") val authorUrl: String,
        @SerializedName("access_token") val accessToken: String? = null,
        @SerializedName("auth_url") val authUrl: String? = null,
        @SerializedName("page_count") val pageCount: Int = -1
)