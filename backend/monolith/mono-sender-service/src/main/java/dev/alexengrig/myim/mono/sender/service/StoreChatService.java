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

package dev.alexengrig.myim.mono.sender.service;

import dev.alexengrig.myim.mono.domain.Chat;
import dev.alexengrig.myim.mono.domain.ChatMessage;
import dev.alexengrig.myim.mono.domain.condition.ChatMessageSearchParams;
import dev.alexengrig.myim.mono.domain.condition.ChatMessageSearchResult;
import dev.alexengrig.myim.mono.domain.condition.ChatSearchParams;
import dev.alexengrig.myim.mono.domain.condition.ChatSearchResult;
import dev.alexengrig.myim.mono.store.entity.ChatEntity;
import dev.alexengrig.myim.mono.store.entity.ChatMessageEntity;
import dev.alexengrig.myim.mono.store.repository.ChatMessageRepository;
import dev.alexengrig.myim.mono.store.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("storeChatSenderService")
@RequiredArgsConstructor
public class StoreChatService implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository messageRepository;
    private final ConversionService conversionService;

    @Override
    public Chat getChatById(String id) {
        ChatEntity entity = chatRepository.getById(id);
        return conversionService.convert(entity, Chat.class);
    }

    @Override
    public ChatSearchResult searchChats(ChatSearchParams params) {
        PageRequest pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<ChatEntity> page = chatRepository.findAll(pageable);
        List<Chat> domains = page.getContent().stream()
                .map(entity -> conversionService.convert(entity, Chat.class))
                .collect(Collectors.toList());
        return ChatSearchResult.builder()
                .values(domains)
                .total(page.getTotalElements())
                .build();
    }

    @Override
    public ChatMessageSearchResult searchMessages(ChatMessageSearchParams params) {
        PageRequest pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<ChatMessageEntity> page = messageRepository.search(
                pageable,
                params.getChatIds(),
                params.getAuthorIds());
        List<ChatMessage> domains = page.getContent().stream()
                .map(entity -> conversionService.convert(entity, ChatMessage.class))
                .collect(Collectors.toList());
        return ChatMessageSearchResult.builder()
                .values(domains)
                .total(page.getTotalElements())
                .build();
    }

}
