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

import dev.alexengrig.myim.mono.security.validation.requirement.password.PasswordRequirement;
import dev.alexengrig.myim.mono.security.validation.requirement.password.PasswordRequirementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VerifiedPasswordValidator
        implements ConstraintValidator<VerifiedPassword, CharSequence> {

    private final List<PasswordRequirement> requirements;

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        boolean isValid = true;
        context.disableDefaultConstraintViolation();
        for (PasswordRequirement requirement : requirements) {
            try {
                requirement.satisfy(value);
            } catch (PasswordRequirementException e) {
                addConstraintViolation(context, e);
                isValid = false;
            }
        }
        return isValid;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, PasswordRequirementException e) {
        String text = e.getText();
        String template = context.getDefaultConstraintMessageTemplate();
        String message = template.replace(VerifiedPassword.TEXT_PLACEHOLDER, text);
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

}
