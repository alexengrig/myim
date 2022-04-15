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

package dev.alexengrig.myim.mono.listener;

import dev.alexengrig.myim.mono.domain.Chat;
import dev.alexengrig.myim.mono.manager.service.ChatManagerService;
import dev.alexengrig.myim.mono.recipient.service.ChatService;
import dev.alexengrig.myim.mono.security.domain.ApplicationUserDetails;
import dev.alexengrig.myim.mono.security.service.ApplicationUserAuthenticationService;
import dev.alexengrig.myim.mono.security.service.ApplicationUserDetailsManager;
import dev.alexengrig.myim.mono.store.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Component
@Profile("development")
@RequiredArgsConstructor
public class DefaultValuesCreationOnApplicationReadyListener
        implements ApplicationListener<ApplicationReadyEvent> {

    private static final int FIRST_USER_NUMBER = 0;
    private static final int LAST_USER_NUMBER = 9;
    private static final String USERNAME_PREFIX = "user";

    private static final int FIRST_CHAT_NUMBER = 0;
    private static final int LAST_CHAT_NUMBER = 9;
    private static final String CHAT_NAME_PREFIX = "Chat#";

    private final ApplicationUserDetailsManager userManager;
    private final ApplicationUserAuthenticationService authService;

    private final ChatRepository chatRepository;

    private final ChatManagerService chatManagerService;
    private final ChatService chatService;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUsersIfNeed();
        createDefaultChatsIfNeed();
    }

    private Stream<String> getUsernames() {
        return IntStream.rangeClosed(FIRST_USER_NUMBER, LAST_USER_NUMBER)
                .mapToObj(i -> USERNAME_PREFIX + i);
    }

    private void createDefaultUsersIfNeed() {
        getUsernames().forEach(username -> {
            if (!userManager.userExists(username)) {
                userManager.createUser(ApplicationUserDetails.builder()
                        .username(username)
                        .password(username)
                        .build());
            }
        });
        log.info("Development users: {}X/{}X (X = [{}-{}])",
                USERNAME_PREFIX, USERNAME_PREFIX, FIRST_USER_NUMBER, LAST_USER_NUMBER);
    }

    private void createDefaultChatsIfNeed() {
        if (chatRepository.count() == 0) {
            List<Chat> newChats = IntStream.rangeClosed(FIRST_CHAT_NUMBER, LAST_CHAT_NUMBER)
                    .mapToObj(i -> CHAT_NAME_PREFIX + i)
                    .map(name -> Chat.builder()
                            .name(name)
                            .build())
                    .collect(Collectors.toList());
            List<Chat> chats = chatManagerService.createAll(newChats);
            log.info("Development chats: {}X (X = [{}-{}])",
                    CHAT_NAME_PREFIX, FIRST_CHAT_NUMBER, LAST_CHAT_NUMBER);
            getUsernames().forEach(username -> {
                authService.authenticateByUsername(username);
                chats.forEach(chat ->
                        chatService.sendMessage(chat.getId(), "Message by " + username + " to " + chat.getName()));
            });
        }
    }

}
