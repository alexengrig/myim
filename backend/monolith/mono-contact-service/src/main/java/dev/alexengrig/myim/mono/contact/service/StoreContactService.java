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

package dev.alexengrig.myim.mono.contact.service;

import dev.alexengrig.myim.mono.contact.domain.Contact;
import dev.alexengrig.myim.mono.contact.domain.condition.ContactSearchParams;
import dev.alexengrig.myim.mono.contact.domain.condition.ContactSearchResult;
import dev.alexengrig.myim.mono.store.entity.AuthorEntity;
import dev.alexengrig.myim.mono.store.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreContactService implements ContactService {

    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;

    @Override
    public ContactSearchResult searchContacts(ContactSearchParams params) {
        PageRequest pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<AuthorEntity> page = authorRepository.findAll(pageable);
        List<Contact> domains = page.getContent().stream()
                .map(entity -> conversionService.convert(entity, Contact.class))
                .collect(Collectors.toList());
        return ContactSearchResult.builder()
                .values(domains)
                .total(page.getTotalElements())
                .build();
    }

    @Override
    public Optional<Contact> findById(String contactId) {
        return authorRepository.findByUsername(contactId)
                .map(entity -> conversionService.convert(entity, Contact.class));
    }
}
