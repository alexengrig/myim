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

package dev.alexengrig.myim.mono.recipient.service;

import dev.alexengrig.myim.mono.domain.ChatMessage;
import dev.alexengrig.myim.mono.domain.ChatMessageStatus;
import dev.alexengrig.myim.mono.domain.MessageStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class LogChatService implements ChatService {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public ChatMessageStatus sendMessage(ChatMessage message) {
        log.info("Send message: {}", message);
        LocalDateTime sentAt = LocalDateTime.now();
        String description = "Message sent at " + dateTimeFormatter.format(sentAt);
        return ChatMessageStatus.builder()
                .message(message)
                .description(description)
                .type(MessageStatusType.SENT)
                .build();
    }

}
