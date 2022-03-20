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

import dev.alexengrig.myim.mono.domain.ChatMessageStatus;
import dev.alexengrig.myim.mono.domain.MessageStatusType;
import dev.alexengrig.myim.mono.security.service.ApplicationUserDetailsManager;
import dev.alexengrig.myim.mono.store.entity.AuthorEntity;
import dev.alexengrig.myim.mono.store.entity.ChatEntity;
import dev.alexengrig.myim.mono.store.entity.ChatMessageEntity;
import dev.alexengrig.myim.mono.store.entity.ChatMessageStatusEntity;
import dev.alexengrig.myim.mono.store.repository.AuthorRepository;
import dev.alexengrig.myim.mono.store.repository.ChatMessageRepository;
import dev.alexengrig.myim.mono.store.repository.ChatMessageStatusRepository;
import dev.alexengrig.myim.mono.store.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service("storeChatRecipientService")
@RequiredArgsConstructor
public class StoreChatService implements ChatService {

    private final AuthorRepository authorRepository;
    private final ChatRepository chatRepository;
    private final ChatMessageRepository messageRepository;
    private final ChatMessageStatusRepository statusRepository;
    private final ApplicationUserDetailsManager userDetailsManager;
    private final ConversionService conversionService;

    @Override
    public ChatMessageStatus sendMessage(String chatId, String text) {
        ChatEntity chat = chatRepository.getById(chatId);
        String username = userDetailsManager.getCurrentUsername();
        AuthorEntity author = authorRepository.getByUsername(username);
        ChatMessageEntity message = messageRepository.save(ChatMessageEntity.builder()
                .chat(chat)
                .author(author)
                .text(text)
                .build());
        ChatMessageStatusEntity status = statusRepository.save(ChatMessageStatusEntity.builder()
                .message(message)
                .description("Message saved")
                .type(MessageStatusType.SENT.name())
                .build());
        return conversionService.convert(status, ChatMessageStatus.class);
    }

}
