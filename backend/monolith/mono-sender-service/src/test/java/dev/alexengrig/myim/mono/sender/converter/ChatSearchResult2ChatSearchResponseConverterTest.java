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
import dev.alexengrig.myim.mono.domain.condition.ChatSearchResult;
import dev.alexengrig.myim.mono.sender.payload.ChatResponse;
import dev.alexengrig.myim.mono.sender.payload.ChatSearchResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest(classes = ChatSearchResult2ChatSearchResponseConverterImpl.class)
class ChatSearchResult2ChatSearchResponseConverterTest {

    @Autowired
    ChatSearchResult2ChatSearchResponseConverter converter;

    @Test
    void should_convert() {
        ChatSearchResult source = ChatSearchResult.builder()
                .values(Collections.singletonList(
                        Chat.builder()
                                .id("test-chat-id")
                                .name("test-chat-name")
                                .build()))
                .total(1000)
                .build();
        ChatSearchResponse actual = converter.convert(source);
        ChatSearchResponse expected = ChatSearchResponse.builder()
                .values(Collections.singletonList(
                        ChatResponse.builder()
                                .id("test-chat-id")
                                .name("test-chat-name")
                                .build()))
                .total(1000)
                .build();
        Assertions.assertEquals(expected, actual, "ChatSearchResponse");
    }

}