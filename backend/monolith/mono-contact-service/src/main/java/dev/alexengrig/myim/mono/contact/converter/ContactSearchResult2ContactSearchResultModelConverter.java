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

package dev.alexengrig.myim.mono.contact.converter;

import dev.alexengrig.myim.mono.contact.config.MapStructConfiguration;
import dev.alexengrig.myim.mono.contact.domain.condition.ContactSearchResult;
import dev.alexengrig.myim.mono.contact.model.condition.ContactSearchResultModel;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MapStructConfiguration.class)
public interface ContactSearchResult2ContactSearchResultModelConverter
        extends Converter<ContactSearchResult, ContactSearchResultModel> {

    @Override
    ContactSearchResultModel convert(ContactSearchResult source);

}
