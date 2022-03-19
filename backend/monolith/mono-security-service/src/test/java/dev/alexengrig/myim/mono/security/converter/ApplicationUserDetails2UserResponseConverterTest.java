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

package dev.alexengrig.myim.mono.security.converter;

import dev.alexengrig.myim.mono.security.domain.ApplicationGrantedAuthority;
import dev.alexengrig.myim.mono.security.domain.ApplicationUserDetails;
import dev.alexengrig.myim.mono.security.payload.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ApplicationUserDetails2UserResponseConverterImpl.class)
class ApplicationUserDetails2UserResponseConverterTest {

    @Autowired
    ApplicationUserDetails2UserResponseConverter converter;

    @Test
    void should_convert() {
        ApplicationUserDetails source = ApplicationUserDetails.builder()
                .username("test-user")
                .password("test-pass")
                .authority(ApplicationGrantedAuthority.builder()
                        .authority("test-authority")
                        .build())
                .build();
        UserResponse actual = converter.convert(source);
        UserResponse expected = UserResponse.builder()
                .id("test-user")
                .name("test-user")
                .build();
        Assertions.assertEquals(expected, actual, "UserResponse");
    }

}