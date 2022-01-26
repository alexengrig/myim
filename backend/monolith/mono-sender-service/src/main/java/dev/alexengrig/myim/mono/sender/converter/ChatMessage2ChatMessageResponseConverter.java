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

import dev.alexengrig.myim.mono.sender.domain.ChatMessage;
import dev.alexengrig.myim.mono.sender.payload.ChatMessageResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChatMessage2ChatMessageResponseConverter
        implements Converter<ChatMessage, ChatMessageResponse> {

    @Override
    public ChatMessageResponse convert(ChatMessage source) {
        return ChatMessageResponse.builder()
                .text(source.getText())
                .chatId(source.getChatId())
                .authorId(source.getAuthor().getId())
                .authorName(source.getAuthor().getName())
                .build();
    }

}
