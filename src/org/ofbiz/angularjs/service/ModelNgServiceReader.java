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
package org.ofbiz.angularjs.service;

import java.util.Map;

import org.ofbiz.angularjs.model.AbstractModelNg;
import org.ofbiz.angularjs.model.AbstractModelNgReader;
import org.ofbiz.base.config.ResourceHandler;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilXml;
import org.w3c.dom.Element;

@SuppressWarnings("serial")
public class ModelNgServiceReader extends AbstractModelNgReader {

    public static final String module = ModelNgServiceReader.class.getName();

    protected ModelNgServiceReader(ResourceHandler handler) {
        super("ng-service", handler);
    }
    
    public static Map<String, ModelNgService> getModelNgServiceMap(ResourceHandler handler) {
        ModelNgServiceReader reader = new ModelNgServiceReader(handler);
        return UtilGenerics.cast(reader.getModelNgs());
    }

    @Override
    protected AbstractModelNg createModelNg(Element element,
            String resourceLocation) {
        ModelNgService ngService = new ModelNgService();
        ngService.name = UtilXml.checkEmpty(element.getAttribute("name")).intern();
        ngService.location = UtilXml.checkEmpty(element.getAttribute("location")).intern();
        ngService.invoke = UtilXml.checkEmpty(element.getAttribute("invoke")).intern();
        return ngService;
    }
}
