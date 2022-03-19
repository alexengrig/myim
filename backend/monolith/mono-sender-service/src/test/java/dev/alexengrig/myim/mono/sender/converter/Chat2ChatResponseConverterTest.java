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

package dev.alexengrig.myim.mono.sender.converter;

import dev.alexengrig.myim.mono.domain.Chat;
import dev.alexengrig.myim.mono.sender.payload.ChatResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {Chat2ChatResponseConverterImpl.class})
class Chat2ChatResponseConverterTest {

    @Autowired
    Chat2ChatResponseConverter converter;

    @Test
    void should_convert() {
        Chat source = Chat.builder()
                .id("test-id")
                .name("test-name")
                .build();
        ChatResponse actual = converter.convert(source);
        ChatResponse expected = ChatResponse.builder()
                .id("test-id")
                .name("test-name")
                .build();
        assertEquals(expected, actual, "ChatResponse");
    }

}