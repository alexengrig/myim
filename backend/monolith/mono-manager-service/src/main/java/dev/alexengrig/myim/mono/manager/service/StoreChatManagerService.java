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

import dev.alexengrig.myim.mono.domain.Chat;
import dev.alexengrig.myim.mono.store.entity.ChatEntity;
import dev.alexengrig.myim.mono.store.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreChatManagerService implements ChatManagerService {

    private final ChatRepository chatRepository;
    private final ConversionService conversionService;

    @Override
    public Chat create(Chat chat) {
        ChatEntity entity = conversionService.convert(chat, ChatEntity.class);
        ChatEntity savedEntity = chatRepository.save(entity);
        return conversionService.convert(savedEntity, Chat.class);
    }

    @Override
    public List<Chat> createAll(List<Chat> chats) {
        List<ChatEntity> entities = chats.stream()
                .map(chat -> conversionService.convert(chat, ChatEntity.class))
                .collect(Collectors.toList());
        List<ChatEntity> savedEntities = chatRepository.saveAll(entities);
        return savedEntities.stream()
                .map(entity -> conversionService.convert(entity, Chat.class))
                .collect(Collectors.toList());
    }

    @Override
    public Chat update(Chat chat) {
        ChatEntity originalEntity = chatRepository.getById(chat.getId());
        Chat oldChat = conversionService.convert(originalEntity, Chat.class);
        BeanUtils.copyProperties(chat, oldChat, Chat.class);
        ChatEntity updatedEntity = conversionService.convert(oldChat, ChatEntity.class);
        ChatEntity savedEntity = chatRepository.save(updatedEntity);
        return conversionService.convert(savedEntity, Chat.class);
    }

    @Override
    public Chat removeById(String id) {
        ChatEntity entity = chatRepository.getById(id);
        Chat chat = conversionService.convert(entity, Chat.class);
        chatRepository.deleteById(id);
        return chat;
    }

}
