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

package dev.alexengrig.myim.mono.sender.listener;

import dev.alexengrig.myim.mono.store.entity.AuthorEntity;
import dev.alexengrig.myim.mono.store.entity.ChatEntity;
import dev.alexengrig.myim.mono.store.entity.ChatMessageEntity;
import dev.alexengrig.myim.mono.store.repository.AuthorRepository;
import dev.alexengrig.myim.mono.store.repository.ChatMessageRepository;
import dev.alexengrig.myim.mono.store.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DefaultChatMessageCreationOnApplicationReadyListener
        implements ApplicationListener<ApplicationReadyEvent> {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (chatRepository.count() == 0) {
            List<ChatEntity> chats = chatRepository.saveAll(IntStream.rangeClosed(0, 2)
                    .mapToObj(i -> ChatEntity.builder()
                            .name("Chat #" + i)
                            .build())
                    .collect(Collectors.toList()));
            AuthorEntity author = authorRepository.findByUsername("admin")
                    .orElseThrow(() -> new IllegalStateException("No admin author"));
            chats.forEach(chat -> chatMessageRepository.saveAll(IntStream.rangeClosed(0, 3)
                    .mapToObj(i -> ChatMessageEntity.builder()
                            .chat(chat)
                            .author(author)
                            .text(chat.getName() + " | Message #" + i)
                            .build())
                    .collect(Collectors.toList())
            ));
        }
    }

}
