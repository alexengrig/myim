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

import dev.alexengrig.myim.mono.domain.ChatMessage;
import dev.alexengrig.myim.mono.recipient.config.MapStructConfiguration;
import dev.alexengrig.myim.mono.recipient.payload.ChatMessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MapStructConfiguration.class)
public interface ChatMessageRequest2ChatMessageConverter
        extends Converter<ChatMessageRequest, ChatMessage> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "chat", source = "chatId")
    @Override
    ChatMessage convert(ChatMessageRequest source);

}
