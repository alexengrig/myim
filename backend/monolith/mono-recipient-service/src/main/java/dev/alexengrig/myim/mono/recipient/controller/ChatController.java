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

package dev.alexengrig.myim.mono.recipient.controller;

import dev.alexengrig.myim.mono.recipient.domain.ChatMessage;
import dev.alexengrig.myim.mono.recipient.domain.ChatMessageStatus;
import dev.alexengrig.myim.mono.recipient.payload.ChatMessageRequest;
import dev.alexengrig.myim.mono.recipient.payload.ChatMessageStatusResponse;
import dev.alexengrig.myim.mono.recipient.payload.MessageRequest;
import dev.alexengrig.myim.mono.recipient.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/recipient/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ConversionService conversionService;

    @PostMapping("/{chatId}/message")
    public ResponseEntity<ChatMessageStatusResponse> createMessage(
            @PathVariable String chatId,
            @RequestBody MessageRequest messageRequest) {
        ChatMessageRequest request = messageRequest.withChatId(chatId);
        log.info("Message creation request: {}", request);
        ChatMessage message = conversionService.convert(request, ChatMessage.class);
        ChatMessageStatus status = chatService.sendMessage(message);
        ChatMessageStatusResponse response = conversionService.convert(status, ChatMessageStatusResponse.class);
        log.info("Message creation response: {}", response);
        return ResponseEntity.ok(response);
    }

}
