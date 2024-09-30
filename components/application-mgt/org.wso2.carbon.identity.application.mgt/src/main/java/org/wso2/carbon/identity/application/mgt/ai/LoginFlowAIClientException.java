/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.application.mgt.ai;

/**
 * Client Exception class for BrandingAI service.
 */
public class LoginFlowAIClientException extends Exception {

    private String errorCode;
    private LoginFlowAIManagerImpl.HttpResponseWrapper loginFlowAIResponse;

    public LoginFlowAIClientException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }

    public LoginFlowAIClientException(LoginFlowAIManagerImpl.HttpResponseWrapper httpResponseWrapper,
                                      String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
        this.loginFlowAIResponse = httpResponseWrapper;
    }

    public LoginFlowAIClientException(String message, Throwable cause) {

        super(cause);
    }

    public LoginFlowAIClientException(String message, String errorCode, Throwable cause) {

        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {

        return errorCode;
    }

    public LoginFlowAIManagerImpl.HttpResponseWrapper getLoginFlowAIResponse() {

        return loginFlowAIResponse;
    }
}
