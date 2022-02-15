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

import dev.alexengrig.myim.mono.sender.domain.Author;
import dev.alexengrig.myim.mono.sender.domain.ChatMessage;
import dev.alexengrig.myim.mono.sender.payload.ChatMessageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ChatMessage2ChatMessageResponseConverterImpl.class})
class ChatMessage2ChatMessageResponseConverterTest {

    @Autowired
    ChatMessage2ChatMessageResponseConverter converter;

    @Test
    void should_convert() {
        ChatMessage source = ChatMessage.builder()
                .text("test-text")
                .chatId("test-chat-id")
                .author(Author.builder()
                        .id("test-author-id")
                        .name("test-author-name")
                        .build())
                .build();
        ChatMessageResponse actual = converter.convert(source);
        ChatMessageResponse expected = ChatMessageResponse.builder()
                .text("test-text")
                .chatId("test-chat-id")
                .authorId("test-author-id")
                .authorName("test-author-name")
                .build();
        assertEquals(expected, actual, "ChatMessageResponse");
    }

}