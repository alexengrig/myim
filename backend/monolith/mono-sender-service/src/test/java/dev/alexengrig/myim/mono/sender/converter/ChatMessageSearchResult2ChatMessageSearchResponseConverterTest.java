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

package dev.alexengrig.myim.mono.sender.converter;

import dev.alexengrig.myim.mono.domain.Author;
import dev.alexengrig.myim.mono.domain.Chat;
import dev.alexengrig.myim.mono.domain.ChatMessage;
import dev.alexengrig.myim.mono.domain.condition.ChatMessageSearchResult;
import dev.alexengrig.myim.mono.sender.config.SenderConversionServiceAdapter;
import dev.alexengrig.myim.mono.sender.payload.ChatMessageResponse;
import dev.alexengrig.myim.mono.sender.payload.ChatMessageSearchResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ChatMessageSearchResult2ChatMessageSearchResponseConverterTest.Config.class)
class ChatMessageSearchResult2ChatMessageSearchResponseConverterTest {

    @Autowired
    ChatMessageSearchResult2ChatMessageSearchResponseConverter converter;

    @Test
    void should_convert() {
        ChatMessageSearchResult source = ChatMessageSearchResult.builder()
                .values(Collections.singletonList(
                        ChatMessage.builder()
                                .text("test-text")
                                .chat(Chat.builder()
                                        .id("test-chat-id")
                                        .name("test-chat-name")
                                        .build())
                                .author(Author.builder()
                                        .id("test-author-id")
                                        .name("test-author-name")
                                        .build())
                                .build()))
                .total(1000)
                .build();
        ChatMessageSearchResponse actual = converter.convert(source);
        ChatMessageSearchResponse expected = ChatMessageSearchResponse.builder()
                .values(Collections.singletonList(
                        ChatMessageResponse.builder()
                                .text("test-text")
                                .chatId("test-chat-id")
                                .authorId("test-author-id")
                                .authorName("test-author-name")
                                .build()))
                .total(1000)
                .build();
        assertEquals(expected, actual, "ChatMessageSearchResponse");
    }

    static class Config {

        @Bean
        ChatMessage2ChatMessageResponseConverter chatMessage2ChatMessageResponseConverter() {
            return new ChatMessage2ChatMessageResponseConverterImpl();
        }

        @Bean
        ConversionService conversionService(ChatMessage2ChatMessageResponseConverter converter) {
            DefaultConversionService service = new DefaultConversionService();
            service.addConverter(converter);
            return service;
        }

        @Bean
        SenderConversionServiceAdapter adapter(ConversionService conversionService) {
            return new SenderConversionServiceAdapter(conversionService);
        }

        @Bean
        @SneakyThrows(NoSuchFieldException.class)
        ChatMessageSearchResult2ChatMessageSearchResponseConverter converter(SenderConversionServiceAdapter adapter) {
            var converter = new ChatMessageSearchResult2ChatMessageSearchResponseConverterImpl();
            var converterType = ChatMessageSearchResult2ChatMessageSearchResponseConverterImpl.class;
            Field field = converterType.getDeclaredField("senderConversionServiceAdapter");
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, converter, adapter);
            return converter;
        }

    }

}