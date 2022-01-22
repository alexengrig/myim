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

import dev.alexengrig.myim.mono.recipient.domain.ChatMessageStatus;
import dev.alexengrig.myim.mono.recipient.payload.ChatMessageStatusResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageStatus2ChatMessageStatusResponseConverter
        implements Converter<ChatMessageStatus, ChatMessageStatusResponse> {

    @Override
    public ChatMessageStatusResponse convert(ChatMessageStatus source) {
        return ChatMessageStatusResponse.builder()
                .description(source.getDescription())
                .type(source.getType())
                .chatId(source.getChatId())
                .build();
    }

}
