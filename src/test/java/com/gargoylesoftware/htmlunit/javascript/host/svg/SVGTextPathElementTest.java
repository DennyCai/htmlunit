/*
 * Copyright (c) 2002-2020 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.javascript.host.svg;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlPageTest;

/**
 * Tests for {@link SVGTextPathElement}.
 *
 * @author Ronald Brill
 */
@RunWith(BrowserRunner.class)
public class SVGTextPathElementTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "[object SVGTextPathElement]",
            FF = "function SVGTextPathElement() {\n    [native code]\n}",
            FF68 = "function SVGTextPathElement() {\n    [native code]\n}",
            FF60 = "function SVGTextPathElement() {\n    [native code]\n}",
            CHROME = "function SVGTextPathElement() { [native code] }")
    public void simpleScriptable() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    alert(window.SVGTextPathElement);\n"
            + "  }\n"
            + "</script>\n"
            + "</head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"[object SVGTextPathElement]", "true"})
    public void getComputedTextLengthAvailable() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    if (window.SVGPathElement) {\n"
            + "      var text = document.getElementById('myId');\n"
            + "      alert(text);\n"
            + "      alert(text.getComputedTextLength() > 0);\n"
            + "    }\n"
            + "  }\n"
            + "</script>\n"
            + "</head><body onload='test()'>\n"
            + "<svg width='100%' height='100%' viewBox='0 0 400 400' xmlns='http://www.w3.org/2000/svg'>\n"
            + "  <defs>\n"
            + "    <path id='myTextPath' d='M 50,100 Q 150,50 250,100' />\n"
            + "  </defs>\n"
            + "  <text fill='steelblue' font-size='20'>\n"
            + "    <textPath id='myId' xlink:href='#myTextPath'>Heho</textPath>\n"
            + "  </text>\n"
            + "</svg>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"[object SVGTextPathElement]", "43.3"},
            IE = {"[object SVGTextPathElement]", "49.3"})
    @NotYetImplemented
    public void getComputedTextLength() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    if (window.SVGTextElement) {\n"
            + "      var text = document.getElementById('myId');\n"
            + "      alert(text);\n"
            + "      var length = text.getComputedTextLength();\n"
            + "      alert(length.toFixed(1));\n"
            + "    }\n"
            + "  }\n"
            + "</script>\n"
            + "</head><body onload='test()'>\n"
            + "<svg width='100%' height='100%' viewBox='0 0 400 400' xmlns='http://www.w3.org/2000/svg'>\n"
            + "  <defs>\n"
            + "    <path id='myTextPath' d='M 50,100 Q 150,50 250,100' />\n"
            + "  </defs>\n"
            + "  <text fill='steelblue' font-size='20'>\n"
            + "    <textPath id='myId' xlink:href='#myTextPath'>Heho</textPath>\n"
            + "  </text>\n"
            + "</svg>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }
}
