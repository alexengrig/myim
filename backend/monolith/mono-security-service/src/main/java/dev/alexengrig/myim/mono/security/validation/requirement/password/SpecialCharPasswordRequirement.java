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

package dev.alexengrig.myim.mono.security.validation.requirement.password;

import dev.alexengrig.myim.mono.security.validation.requirement.AnyCodePointRequirement;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(5)
@Component
public final class SpecialCharPasswordRequirement
        extends AnyCodePointRequirement<CharSequence, PasswordRequirementException>
        implements PasswordRequirement {

    @Override
    protected boolean match(int codePoint) {
        return !Character.isLetterOrDigit(codePoint);
    }

    @Override
    protected PasswordRequirementException createException(CharSequence value) {
        return new PasswordRequirementException("Password must contain at least one special character");
    }

}
