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

package dev.alexengrig.myim.mono.security.payload;

import dev.alexengrig.myim.mono.security.validation.MatchingFieldsValues;
import dev.alexengrig.myim.mono.security.validation.password.VerifiedPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MatchingFieldsValues(
        field = "password",
        matchField = "confirmPassword",
        message = "Confirm password doesn't match!")
public class UserRegistrationRequest {

    @NotBlank(message = "Username must not be blank!")
    private String username;

    @VerifiedPassword
    @NotBlank(message = "Password must not be blank!")
    private String password;
    private String confirmPassword;

}
