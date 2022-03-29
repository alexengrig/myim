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

package dev.alexengrig.myim.mono.manager.service;

import dev.alexengrig.myim.mono.domain.ChatMessage;
import dev.alexengrig.myim.mono.store.entity.ChatMessageEntity;
import dev.alexengrig.myim.mono.store.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreChatMessageManagerService implements ChatMessageManagerService {

    private final ChatMessageRepository chatMessageRepository;
    private final ConversionService conversionService;

    @Override
    public ChatMessage updateTextById(String id, String text) {
        ChatMessageEntity entity = chatMessageRepository.getById(id);
        ChatMessage message = conversionService.convert(entity, ChatMessage.class);
        message.setText(text);
        ChatMessageEntity updatedEntity = conversionService.convert(message, ChatMessageEntity.class);
        updatedEntity.setStatuses(entity.getStatuses());
        chatMessageRepository.save(updatedEntity);
        return message;
    }

    @Override
    public ChatMessage removeById(String id) {
        ChatMessageEntity entity = chatMessageRepository.getById(id);
        ChatMessage message = conversionService.convert(entity, ChatMessage.class);
        chatMessageRepository.deleteById(id);
        return message;
    }

}
