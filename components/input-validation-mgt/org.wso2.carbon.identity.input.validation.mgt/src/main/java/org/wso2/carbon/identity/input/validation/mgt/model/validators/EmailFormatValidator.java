/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
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

package org.wso2.carbon.identity.input.validation.mgt.model.validators;

import org.wso2.carbon.identity.input.validation.mgt.exceptions.InputValidationMgtClientException;
import org.wso2.carbon.identity.input.validation.mgt.model.Property;
import org.wso2.carbon.identity.input.validation.mgt.model.ValidationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.wso2.carbon.identity.input.validation.mgt.utils.Constants.Configs.DEFAULT_EMAIL_REGEX_PATTERN;
import static org.wso2.carbon.identity.input.validation.mgt.utils.Constants.Configs.ENABLE;
import static org.wso2.carbon.identity.input.validation.mgt.utils.Constants.Configs.USERNAME;
import static org.wso2.carbon.identity.input.validation.mgt.utils.Constants.ErrorMessages.ERROR_VALIDATION_EMAIL_FORMAT_MISMATCH;

/**
 * Email format validator.
 */
public class EmailFormatValidator extends AbstractRulesValidator {

    private final List<String> allowedFields = new ArrayList<String>() {{
        add(USERNAME);
    }};

    @Override
    public boolean isAllowedField(String field) {

        return allowedFields.contains(field);
    }

    /**
     * Validate the string against the validation criteria.
     *
     * @param context   Validation Context.
     * @return boolean
     * @throws InputValidationMgtClientException Error when string does not satisfy the validation criteria
     */
    @Override
    public boolean validate(ValidationContext context) throws InputValidationMgtClientException {

        String value = context.getValue();
        String field = context.getField();
        Map<String, String> attributesMap = context.getProperties();
        String emailRegEx = DEFAULT_EMAIL_REGEX_PATTERN;

        // Check whether value satisfies the email format criteria.
        if (attributesMap.containsKey(ENABLE)) {
            if (Boolean.parseBoolean(attributesMap.get(ENABLE)) && value != null && !value.matches(emailRegEx)) {
                throw new InputValidationMgtClientException(ERROR_VALIDATION_EMAIL_FORMAT_MISMATCH.getCode(),
                        ERROR_VALIDATION_EMAIL_FORMAT_MISMATCH.getMessage(),
                        String.format(ERROR_VALIDATION_EMAIL_FORMAT_MISMATCH.getDescription(), field, emailRegEx));
            }
        }

        return true;
    }

    /**
     * Get list of supported properties for the validator.
     *
     * @return  List<Property>
     */
    @Override
    public List<Property> getConfigurationProperties() {

        List<Property> configProperties = new ArrayList<>();
        int parameterCount = 0;

        Property enable = new Property();
        enable.setName(ENABLE);
        enable.setDisplayName("Email validation");
        enable.setDescription("Validate whether the field value is in the email format.");
        enable.setType("boolean");
        enable.setDisplayOrder(++parameterCount);
        configProperties.add(enable);

        return configProperties;
    }

    /**
     * Validate the configuration values of the properties for the validator.
     *
     * @param context   Validation Context.
     * @return  boolean
     */
    @Override
    public boolean validateProps(ValidationContext context) throws InputValidationMgtClientException {

        Map<String, String> properties = context.getProperties();
        validatePropertyName(properties, this.getClass().getSimpleName(), context.getTenantDomain());
        if (properties.get(ENABLE) != null && !validateBoolean(properties.get(ENABLE),
                ENABLE, context.getTenantDomain())) {
            properties.remove(ENABLE);
        }
        return true;
    }
}
