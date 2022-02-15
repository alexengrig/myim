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

import dev.alexengrig.myim.mono.security.domain.ApplicationUserDetails;
import dev.alexengrig.myim.mono.store.entity.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AuthorEntity2ApplicationUserDetailsConverterImpl.class)
class AuthorEntity2ApplicationUserDetailsConverterTest {

    @Autowired
    AuthorEntity2ApplicationUserDetailsConverter converter;

    @Test
    void should_converter() {
        AuthorEntity source = AuthorEntity.builder()
                .username("test-username")
                .password("test-password")
                .build();
        ApplicationUserDetails actual = converter.convert(source);
        ApplicationUserDetails expected = ApplicationUserDetails.builder()
                .username("test-username")
                .password("test-password")
                .authorities(Collections.emptyList())
                .build();
        assertEquals(expected, actual, "ApplicationUserDetails");
    }

}