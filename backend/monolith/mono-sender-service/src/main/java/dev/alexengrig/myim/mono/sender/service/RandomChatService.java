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

import dev.alexengrig.myim.mono.sender.domain.ChatMessage;
import dev.alexengrig.myim.mono.sender.domain.ChatMessageSearchParams;
import dev.alexengrig.myim.mono.sender.domain.ChatMessageSearchResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RandomChatService implements ChatService {

    private static final int MIN_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 10;
    private static final char FIRST_LETTER = 'a';
    private static final char LAST_LETTER = 'z';

    private final Random random = new Random();

    @Override
    public ChatMessageSearchResult searchMessages(ChatMessageSearchParams params) {
        String chatId = params.getChatId();
        List<ChatMessage> values = IntStream.rangeClosed(0, params.getSize())
                .mapToObj(this::randomText)
                .map(text -> ChatMessage.builder()
                        .chatId(chatId)
                        .text(text)
                        .build())
                .collect(Collectors.toList());
        return ChatMessageSearchResult.builder()
                .params(params)
                .values(values)
                .total(params.getSize())
                .build();
    }

    private String randomText(int serial) {
        String randomWord = randomWord();
        return "Message #" + serial + ": " + randomWord;
    }

    private String randomWord() {
        int size = random.nextInt(MIN_WORD_LENGTH, MAX_WORD_LENGTH);
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) {
            bytes[i] = (byte) random.nextInt(FIRST_LETTER, LAST_LETTER);
        }
        return new String(bytes);
    }

}
