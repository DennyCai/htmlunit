/*
 * Copyright (c) 2002-2015 Gargoyle Software Inc.
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
package com.gargoylesoftware.htmlunit.javascript.host.dom;

import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.CHROME;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.IE;
import net.sourceforge.htmlunit.corejs.javascript.Context;
import net.sourceforge.htmlunit.corejs.javascript.Function;
import net.sourceforge.htmlunit.corejs.javascript.NativeArray;
import net.sourceforge.htmlunit.corejs.javascript.NativeObject;
import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
import net.sourceforge.htmlunit.corejs.javascript.TopLevel;

import com.gargoylesoftware.htmlunit.html.CharacterDataChangeEvent;
import com.gargoylesoftware.htmlunit.html.CharacterDataChangeListener;
import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxFunction;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;

/**
 * A JavaScript object for {@code MutationObserver}.
 *
 * @version $Revision$
 * @author Ahmed Ashour
 */
@JsxClass(browsers = { @WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(value = IE, minVersion = 11) })
public class MutationObserver extends SimpleScriptable implements CharacterDataChangeListener {

    private Function function_;
    private Node node_;
    private boolean attaributes_;
    private boolean childList_;
    private boolean characterData_;
    private boolean characterDataOldValue_;
    private boolean subtree_;

    /**
     * Creates an instance.
     */
    public MutationObserver() {
    }

    /**
     * Creates an instance.
     */
    @JsxConstructor
    public MutationObserver(final Function function) {
        function_ = function;
    }

    /**
     * Registers the {@link MutationObserver} instance to receive notifications of DOM mutations on the specified node.
     * @param node the node
     * @param options the options
     */
    @JsxFunction
    public void observe(final Node node, final NativeObject options) {
        node_ = node;
        attaributes_ = Boolean.TRUE.equals(options.get("attributes"));
        childList_ = Boolean.TRUE.equals(options.get("childList"));
        characterData_ = Boolean.TRUE.equals(options.get("characterData"));
        characterDataOldValue_ = Boolean.TRUE.equals(options.get("characterDataOldValue"));
        subtree_ = Boolean.TRUE.equals(options.get("subtree"));
        if (characterData_) {
            node.getDomNodeOrDie().addCharacterDataChangeListener(this);
        }
    }

    /**
     * Stops the MutationObserver instance from receiving notifications of DOM mutations.
     */
    @JsxFunction
    public void disconnect() {
        if (characterData_) {
            node_.getDomNodeOrDie().removeCharacterDataChangeListener(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void characterDataChanged(final CharacterDataChangeEvent event) {
        final MutationRecord mutationRecord = new MutationRecord();
        final Scriptable scope = getParentScope();
        mutationRecord.setParentScope(scope);
        mutationRecord.setPrototype(getPrototype(mutationRecord.getClass()));

        mutationRecord.setTarget(event.getCharacterData().getScriptObject());
        mutationRecord.setOldValue(event.getOldValue());

        final NativeArray array = new NativeArray(new Object[] {mutationRecord});
        ScriptRuntime.setBuiltinProtoAndParent(array, scope, TopLevel.Builtins.Array);
        function_.call(Context.getCurrentContext(), scope, this, new Object[] {array});
    }
}
