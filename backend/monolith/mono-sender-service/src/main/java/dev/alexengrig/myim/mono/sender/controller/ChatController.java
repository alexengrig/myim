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

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("chatSenderController")
@RequestMapping("/api/v1/sender/chats")
public class ChatController {

    private static final String DEFAULT_MESSAGES_SIZE = "10";
    private static final String DEFAULT_MESSAGES_OFFSET = "0";

    @GetMapping("/{chatId}/messages")
    public void getMessages(
            @PathVariable String chatId,
            @RequestParam(defaultValue = DEFAULT_MESSAGES_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_MESSAGES_OFFSET) int offset) {
        log.info("Get messages for chatId={} with size={} and offset={}", chatId, size, offset);
    }

}
