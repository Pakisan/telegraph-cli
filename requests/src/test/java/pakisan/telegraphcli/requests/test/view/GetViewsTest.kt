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

package pakisan.telegraphcli.requests.test.view

import org.junit.Test
import pakisan.telegraphcli.requests.Telegraph
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetViewsTest {
    @Test
    fun getViewsByYearMonthDayHour() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/getViews/Sample-Page-12-15")
                .append("?year=2016")
                .append("&month=10")
                .append("&day=14")
                .append("&hour=12")

        val endpoint = Telegraph.getViews("Sample-Page-12-15",2016, 10, 14, 12)

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed getViews endpoint.")
    }

    @Test
    fun getViewsByMonthDayHour() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/getViews/Sample-Page-12-15")
                .append("?month=10")
                .append("&day=14")
                .append("&hour=12")

        val endpoint = Telegraph.getViews("Sample-Page-12-15", 10, 14, 12)

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed getViews endpoint.")
    }

    @Test
    fun getViewsByDayHour() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/getViews/Sample-Page-12-15")
                .append("?day=14")
                .append("&hour=12")

        val endpoint = Telegraph.getViews("Sample-Page-12-15", 14, 12)

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed getViews endpoint.")
    }

    @Test
    fun getViewsByHour() {
        val expectedEndpoint = StringBuilder()
        expectedEndpoint.append("https://api.telegra.ph/getViews/Sample-Page-12-15")
                .append("?hour=12")

        val endpoint = Telegraph.getViews("Sample-Page-12-15",  12)

        assertEquals(expectedEndpoint.toString(), endpoint, "malformed getViews endpoint.")
    }

    @Test
    fun failGettingViewsByPath() {
        assertFailsWith<IllegalArgumentException>("Path can't be empty or blank.") {
            Telegraph.getViews(" ",2016,10,15,15)
        }
    }

    @Test
    fun failGettingViewsByIncorrectYear() {
        assertFailsWith<IllegalArgumentException>("year must be in range " +
                "of ${Telegraph.MIN_YEAR} and ${Telegraph.MAX_YEAR}.") {
            Telegraph.getViews("Sample-Page-12-15",3000,15,15,15)
        }
    }

    @Test
    fun failGettingViewsByIncorrectMonth() {
        assertFailsWith<IllegalArgumentException>("month must be between 1-12.") {
            Telegraph.getViews("Sample-Page-12-15",2016,-15,15,15)
        }
    }

    @Test
    fun failGettingViewsByIncorrectDay() {
        assertFailsWith<IllegalArgumentException>("day must be between 1-31.") {
            Telegraph.getViews("Sample-Page-12-15",2016,5,0,15)
        }
    }

    @Test
    fun failGettingViewsByIncorrectHour() {
        assertFailsWith<IllegalArgumentException>("hour must be between 0-24.") {
            Telegraph.getViews("Sample-Page-12-15",2016,5,10,32)
        }
    }
}