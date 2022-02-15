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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ApplicationUserDetails2AuthorEntityConverterImpl.class)
class ApplicationUserDetails2AuthorEntityConverterTest {

    @Autowired
    ApplicationUserDetails2AuthorEntityConverter converter;

    @Test
    void should_converter() {
        ApplicationUserDetails source = ApplicationUserDetails.builder()
                .username("test-username")
                .password("test-password")
                .authorities(Collections.emptyList())
                .build();
        AuthorEntity actual = converter.convert(source);
        AuthorEntity expected = AuthorEntity.builder()
                .username("test-username")
                .password("test-password")
                .name("test-username")
                .build();
        assertAuthorEntityEquals(expected, actual);
    }

    private void assertAuthorEntityEquals(AuthorEntity expected, AuthorEntity actual) {
        assertNotNull(expected, "Expected must not be null");
        assertNotNull(actual, "Actual must not be null");
        assertEquals(expected.getId(), actual.getId(), "AuthorEntity.Id");
        assertEquals(expected.getUsername(), actual.getUsername(), "AuthorEntity.Username");
        assertEquals(expected.getPassword(), actual.getPassword(), "AuthorEntity.Password");
        assertEquals(expected.getName(), actual.getName(), "AuthorEntity.Name");
    }

}