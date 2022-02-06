/*
 * Copyright 2022 Alexengrig Dev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.alexengrig.myim.mono.security.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MatchingFieldsValuesValidator
        implements ConstraintValidator<MatchingFieldsValues, Object> {

    private String field;
    private String matchField;

    @Override
    public void initialize(MatchingFieldsValues constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.matchField = constraintAnnotation.matchField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = isValid(value);
        if (!isValid) {
            initConstraintViolations(context);
        }
        return isValid;
    }

    private boolean isValid(Object value) {
        BeanWrapperImpl bean = new BeanWrapperImpl(value);
        Object fieldValue = bean.getPropertyValue(field);
        Object matchFieldValue = bean.getPropertyValue(matchField);
        return Objects.equals(fieldValue, matchFieldValue);
    }

    private void initConstraintViolations(ConstraintValidatorContext context) {
        String template = context.getDefaultConstraintMessageTemplate();
        String message = template
                .replace(MatchingFieldsValues.FIELD_PLACEHOLDER, field)
                .replace(MatchingFieldsValues.MATCH_FIELD_PLACEHOLDER, matchField);
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(matchField)
                .addConstraintViolation();
    }

}
