# telegraph-cli: Data

Based on http://telegra.ph/api#Available-types
Is actual to 13.11.2017
> This module holds telegra.ph data types and helper tools to work with them.

Main goal is to implement http://telegra.ph/api and provide common lightweight implementation for both platforms - desktop and andriod.

## Page:
``` kotlin
package pakisan.telegraphcli.data.page

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
```

## Page list:
``` kotlin
package pakisan.telegraphcli.data.page

/**
 * This object represents a list of Telegraph articles belonging to an account.
 * Most recently created articles first.
 *
 * @param totalCount Total number of pages belonging to the target Telegraph account.
 * @param pages List of requested pages of the target Telegraph account.
 */
data class PageList(
        @SerializedName("total_count") val totalCount: Int,
        val pages: List<Page>
)
```

## Page views:
``` kotlin
package pakisan.telegraphcli.data.page

/**
 * This object represents the number of page views for a Telegraph article.
 *
 * @param views Number of page views for the target page.
 */
data class PageViews(
        val views: Int
)
```

## Node:
``` kotlin
package pakisan.telegraphcli.data.page.node

/**
 * This abstract object represents a DOM Node. It can be a String which represents a DOM text
 * node or a NodeElement object.
 */
data class Node (
        val value: Any
)
```

## Node element:
``` kotlin
package pakisan.telegraphcli.data.page.node

/**
 * This object represents a DOM element node.
 *
 * @param tag Name of the DOM element. Available tags: a, aside, b, blockquote, br, code, em,
 * figcaption, figure, h3, h4, hr, i, iframe, img, li, ol, p, pre, s, strong, u, ul, video.
 * @param attrs Optional. Map of attributes of the DOM element. Key of object represents name
 * of attribute, value represents value of attribute. Available attributes: href, src.
 * @param children Optional. List of child nodes for the DOM element.
 */
data class NodeElement(
        val tag: String,
        val attrs: Map<String, String>? = null,
        val children: List<Node>? = null
)
```

dependencies:
- [gson](https://github.com/google/gson) - Apache License 2.0
- [JUnit](http://junit.org/junit4/) - Eclipse Public License Version 1.0 

licence:
 MIT