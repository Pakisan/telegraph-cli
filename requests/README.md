# telegraph-cli: Requests

Based on http://telegra.ph/api#Available-methods 
Is actual to 13.11.2017
> This module allows to get string representation of telegra.ph api endpoint with given parameters
> which will be url encoded and validated.

Main goal is to implement http://telegra.ph/api and provide common lightweight implementation for both platforms - desktop and andriod.

## Account:
``` kotlin
package pakisan.telegraphcli.requests

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
    fun createAccount(shortName: String, authorName: String = "", authorUrl: String = ""): String

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
                                authorUrl: String = ""): String

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
    fun getAccountInfo(accessToken: String, fields: Array<String> = arrayOf()): String

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
    fun revokeAccessToken(accessToken: String): String
```

## Page:
``` kotlin
package pakisan.telegraphcli.requests

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
                   authorUrl: String = "", content: String, returnContent: Boolean = false): String

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
                 authorName: String = "", authorUrl: String = "", returnContent: Boolean = false): String

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
    fun getPage(path: String, returnContent: Boolean = false): String

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
    fun getPageList(accessToken: String, offset: Int = 0, limit: Int = 50): String

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
    fun getViews(path: String, year: Int, month: Int, day: Int, hour: Int): String

    @Synchronized
    fun getViews(path: String, hour: Int): String

    @Synchronized
    fun getViews(path: String, day: Int, hour: Int): String

    @Synchronized
    fun getViews(path: String, month: Int, day: Int, hour: Int): String
```

dependencies:
- [JUnit](http://junit.org/junit4/) - Eclipse Public License Version 1.0 

Licence:
MIT