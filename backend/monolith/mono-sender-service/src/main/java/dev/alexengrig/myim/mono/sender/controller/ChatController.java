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

package dev.alexengrig.myim.mono.sender.controller;

import dev.alexengrig.myim.mono.domain.Chat;
import dev.alexengrig.myim.mono.domain.condition.ChatMessageSearchParams;
import dev.alexengrig.myim.mono.domain.condition.ChatMessageSearchResult;
import dev.alexengrig.myim.mono.domain.condition.ChatSearchParams;
import dev.alexengrig.myim.mono.domain.condition.ChatSearchResult;
import dev.alexengrig.myim.mono.sender.payload.ChatResponse;
import dev.alexengrig.myim.mono.sender.payload.condition.ChatMessageSearchResponse;
import dev.alexengrig.myim.mono.sender.payload.condition.ChatSearchResponse;
import dev.alexengrig.myim.mono.sender.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("chatSenderController")
@RequestMapping("/api/v1/sender/chats")
@RequiredArgsConstructor
public class ChatController {

    private static final String DEFAULT_CHATS_PAGE = "0";
    private static final String DEFAULT_CHATS_SIZE = "10";

    private static final String DEFAULT_MESSAGES_PAGE = "0";
    private static final String DEFAULT_MESSAGES_SIZE = "10";

    private final ChatService chatService;
    private final ConversionService conversionService;

    @GetMapping
    public ResponseEntity<ChatSearchResponse> getChats(
            @RequestParam(defaultValue = DEFAULT_CHATS_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_CHATS_SIZE) int size) {
        log.info("Chats getting request: page={}, size={}", page, size);
        ChatSearchParams params = ChatSearchParams.builder()
                .page(page)
                .size(size)
                .build();
        ChatSearchResult result = chatService.searchChats(params);
        ChatSearchResponse response = conversionService.convert(result, ChatSearchResponse.class);
        log.info("Chats getting response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponse> getChat(
            @PathVariable String chatId) {
        log.info("Chat getting request: chatId={}", chatId);
        Chat result = chatService.getChatById(chatId);
        ChatResponse response = conversionService.convert(result, ChatResponse.class);
        log.info("Chat getting response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<ChatMessageSearchResponse> getMessages(
            @PathVariable String chatId,
            @RequestParam(defaultValue = DEFAULT_MESSAGES_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_MESSAGES_SIZE) int size) {
        log.info("Messages getting request: chatId={}, page={}, size={}", chatId, page, size);
        ChatMessageSearchParams params = ChatMessageSearchParams.builder()
                .chatId(chatId)
                .page(page)
                .size(size)
                .build();
        ChatMessageSearchResult result = chatService.searchMessages(params);
        ChatMessageSearchResponse response = conversionService.convert(result, ChatMessageSearchResponse.class);
        log.info("Messages getting response: {}", response);
        return ResponseEntity.ok(response);
    }

}
