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

package dev.alexengrig.myim.mono.manager.controller;

import dev.alexengrig.myim.mono.domain.Chat;
import dev.alexengrig.myim.mono.domain.ChatMessage;
import dev.alexengrig.myim.mono.manager.payload.CreatingChatRequest;
import dev.alexengrig.myim.mono.manager.payload.CreatingChatResponse;
import dev.alexengrig.myim.mono.manager.payload.RemovingChatMessageResponse;
import dev.alexengrig.myim.mono.manager.payload.RemovingChatResponse;
import dev.alexengrig.myim.mono.manager.payload.UpdatingChatMessageResponse;
import dev.alexengrig.myim.mono.manager.payload.UpdatingChatRequest;
import dev.alexengrig.myim.mono.manager.payload.UpdatingChatResponse;
import dev.alexengrig.myim.mono.manager.service.ChatManagerService;
import dev.alexengrig.myim.mono.manager.service.ChatMessageManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manager/chats")
@RequiredArgsConstructor
public class ChatManagerController {

    private final ChatManagerService chatManagerService;
    private final ChatMessageManagerService chatMessageManagerService;
    private final ConversionService conversionService;

    @PostMapping
    public ResponseEntity<CreatingChatResponse> createChat(
            @RequestBody CreatingChatRequest request) {
        Chat chat = conversionService.convert(request, Chat.class);
        Chat result = chatManagerService.create(chat);
        CreatingChatResponse response = conversionService.convert(result, CreatingChatResponse.class);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{chatId}")
    public ResponseEntity<UpdatingChatResponse> updateChat(
            @PathVariable String chatId,
            @RequestBody UpdatingChatRequest request) {
        Chat chat = conversionService.convert(request, Chat.class);
        chat.setId(chatId);
        Chat result = chatManagerService.update(chat);
        UpdatingChatResponse response = conversionService.convert(result, UpdatingChatResponse.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<RemovingChatResponse> removeChat(
            @PathVariable String chatId) {
        Chat result = chatManagerService.removeById(chatId);
        RemovingChatResponse response = conversionService.convert(result, RemovingChatResponse.class);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{chatId}/messages/{messageId}")
    public ResponseEntity<UpdatingChatMessageResponse> updateMessage(
            @PathVariable String chatId,
            @PathVariable String messageId,
            @RequestBody String text) {
        ChatMessage result = chatMessageManagerService.updateTextById(messageId, text);
        UpdatingChatMessageResponse response = conversionService.convert(result, UpdatingChatMessageResponse.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{chatId}/messages/{messageId}")
    public ResponseEntity<RemovingChatMessageResponse> removeMessage(
            @PathVariable String chatId,
            @PathVariable String messageId) {
        ChatMessage result = chatMessageManagerService.removeById(messageId);
        RemovingChatMessageResponse response = conversionService.convert(result, RemovingChatMessageResponse.class);
        return ResponseEntity.ok(response);
    }

}
