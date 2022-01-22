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
import dev.alexengrig.myim.mono.sender.domain.ChatMessageSearchResult;
import dev.alexengrig.myim.mono.sender.payload.ChatMessageResponse;
import dev.alexengrig.myim.mono.sender.payload.ChatMessageSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessageSearchResult2ChatMessageSearchResponseConverter
        implements Converter<ChatMessageSearchResult, ChatMessageSearchResponse> {

    private final Converter<ChatMessage, ChatMessageResponse> valueConverter;

    @Override
    public ChatMessageSearchResponse convert(ChatMessageSearchResult source) {
        return ChatMessageSearchResponse.builder()
                .values(source.getValues().stream()
                        .map(valueConverter::convert)
                        .toList())
                .total(source.getTotal())
                .build();
    }

}
