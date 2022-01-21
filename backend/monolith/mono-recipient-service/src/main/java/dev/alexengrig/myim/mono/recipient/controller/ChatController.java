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

import dev.alexengrig.myim.mono.recipient.payload.ChatMessageRequest;
import dev.alexengrig.myim.mono.recipient.payload.MessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/recipient/chat")
public class ChatController {

    @PostMapping("/{chatId}/message")
    public void createMessage(
            @PathVariable String chatId,
            @RequestBody MessageRequest messageRequest) {
        ChatMessageRequest request = messageRequest.withChatId(chatId);
        log.info("Create message: {}", request);
    }

}