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

package dev.alexengrig.myim.mono.recipient.converter;

import dev.alexengrig.myim.mono.domain.Chat;
import dev.alexengrig.myim.mono.domain.ChatMessage;
import dev.alexengrig.myim.mono.domain.ChatMessageStatus;
import dev.alexengrig.myim.mono.domain.MessageStatusType;
import dev.alexengrig.myim.mono.recipient.payload.ChatMessageStatusResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ChatMessageStatus2ChatMessageStatusResponseConverterImpl.class)
class ChatMessageStatus2ChatMessageStatusResponseConverterTest {

    @Autowired
    ChatMessageStatus2ChatMessageStatusResponseConverter converter;

    @Test
    void should_convert() {
        ChatMessageStatus source = ChatMessageStatus.builder()
                .description("test-description")
                .message(ChatMessage.builder()
                        .chat(Chat.builder()
                                .id("test-chat-id")
                                .build())
                        .build())
                .type(MessageStatusType.SENT)
                .build();
        ChatMessageStatusResponse actual = converter.convert(source);
        ChatMessageStatusResponse expected = ChatMessageStatusResponse.builder()
                .description("test-description")
                .type("SENT")
                .chatId("test-chat-id")
                .build();
        assertEquals(expected, actual, "ChatMessageStatusResponse");
    }

}