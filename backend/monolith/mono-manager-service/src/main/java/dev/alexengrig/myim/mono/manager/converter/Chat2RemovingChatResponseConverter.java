
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

package dev.alexengrig.myim.mono.manager.converter;

import dev.alexengrig.myim.mono.domain.Chat;
import dev.alexengrig.myim.mono.manager.config.MapStructConfiguration;
import dev.alexengrig.myim.mono.manager.payload.RemovingChatResponse;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MapStructConfiguration.class)
public interface Chat2RemovingChatResponseConverter
        extends Converter<Chat, RemovingChatResponse> {

    @Override
    RemovingChatResponse convert(Chat source);

}
