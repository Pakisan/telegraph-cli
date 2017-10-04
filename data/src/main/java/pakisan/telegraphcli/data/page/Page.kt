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

package pakisan.telegraphcli.data.page

import com.google.gson.annotations.SerializedName
import pakisan.telegraphcli.data.page.node.Node

/**
 * This object represents a page on Telegraph.
 *
 * @param path Path to the page.
 * @param url URL of the page.
 * @param title Title of the page.
 * @param description Description of the page.
 * @param authorName Optional. Name of the author, displayed below the title.
 * @param authorUrl Optional. Profile link, opened when users click on the author's name below the title.
 * Can be any link, not necessarily to a Telegram profile or channel.
 * @param imageUrl Optional. Image URL of the page.
 * @param content Optional. List of [Node], which represents content of the page.
 * @param views Number of page views for the page.
 * @param canEdit Optional. Only returned if access_token passed. True, if the
 * target Telegraph account can edit the page.
 */
data class Page(
        val path: String,
        val url: String,
        val title: String,
        val description: String,
        @SerializedName("author_name") val authorName: String? = null,
        @SerializedName("author_url") val authorUrl: String? = null,
        @SerializedName("image_url") val imageUrl: String? = null,
        val content: List<Node>? = null,
        val views: Int,
        @SerializedName("can_edit") val canEdit: Boolean = false
)