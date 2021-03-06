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

package dev.alexengrig.myim.mono.security.validation.requirement.username;

import dev.alexengrig.myim.mono.security.validation.requirement.LengthRequirement;
import org.springframework.stereotype.Component;

@Component
public final class LengthUsernameRequirement
        extends LengthRequirement<CharSequence, UsernameRequirementException>
        implements UsernameRequirement {

    private static final int MIN_LENGTH = 3;

    public LengthUsernameRequirement() {
        super(MIN_LENGTH);
    }

    @Override
    protected UsernameRequirementException createException(CharSequence ignore) {
        return new UsernameRequirementException("Length of username must be at least " + minLength);
    }

}
