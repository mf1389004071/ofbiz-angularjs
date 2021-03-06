/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.ofbiz.angularjs.javascript;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.ofbiz.base.util.StringUtil;
import org.ofbiz.base.util.UtilValidate;

public class JavaScriptPackage {

    public final static String module = JavaScriptPackage.class.getName();

    protected String packageName = null;
    protected JavaScriptPackage parent = null;
    protected List<JavaScriptPackage> children = new LinkedList<JavaScriptPackage>();
    protected String name = null;
    protected Map<String, JavaScriptClass> javaScriptClasses = new HashMap<String, JavaScriptClass>();

    public JavaScriptPackage(String packageName) {
        this.packageName = packageName;
        List<String> packageTokens = StringUtil.split(packageName, ".");
        if (UtilValidate.isNotEmpty(packageTokens)) {
            this.name = packageTokens.remove(packageTokens.size() - 1);
            String parentPackagePath = StringUtil.join(packageTokens, ".");
            this.parent = JavaScriptFactory
                    .getJavaScriptPackage(parentPackagePath);

            if (UtilValidate.isNotEmpty(this.parent)) {
                this.parent.getChildren().add(this);
            }

            if (packageName.indexOf(".") < 0) {
                JavaScriptFactory.addRootJavaScriptPackage(this);
            }
        } else {
            this.name = packageName;
            JavaScriptFactory.addRootJavaScriptPackage(this);
        }
    }

    public void addJavaScriptClass(String className, Function classFunction,
            Context context) {
        JavaScriptClass javaScriptClass = new JavaScriptClass(this, className,
                classFunction, context);
        javaScriptClasses.put(className, javaScriptClass);
    }

    public List<JavaScriptPackage> getChildren() {
        return children;
    }

    public JavaScriptClass getJavaScriptClass(String className) {
        JavaScriptClass javaScriptClass = javaScriptClasses.get(className);
        return javaScriptClass;
    }

    public String getPackagePath() {
        return packageName;
    }

    public String rawString() {
        String rawString = "";
        if (UtilValidate.isEmpty(parent)) {
            rawString += "var " + name + " = {";
        } else {
            rawString = name + ": {\n";
        }

        // render sub packages
        for (JavaScriptPackage javaScriptPackage : getChildren()) {
            rawString += javaScriptPackage.rawString();
        }

        // render classes
        for (JavaScriptClass javaScriptClass : javaScriptClasses.values()) {
            rawString += javaScriptClass.rawString();
        }

        rawString += "\n},\n";
        return rawString;
    }
}
