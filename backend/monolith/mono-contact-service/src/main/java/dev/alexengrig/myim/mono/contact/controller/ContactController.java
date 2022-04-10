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

package dev.alexengrig.myim.mono.contact.controller;

import dev.alexengrig.myim.mono.contact.domain.Contact;
import dev.alexengrig.myim.mono.contact.domain.condition.ContactSearchParams;
import dev.alexengrig.myim.mono.contact.domain.condition.ContactSearchResult;
import dev.alexengrig.myim.mono.contact.model.ContactModel;
import dev.alexengrig.myim.mono.contact.model.condition.ContactSearchResultModel;
import dev.alexengrig.myim.mono.contact.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    private static final String DEFAULT_CONTACTS_PAGE = "0";
    private static final String DEFAULT_CONTACTS_SIZE = "10";

    private final ContactService contactService;
    private final ContactLinker contactLinker;
    private final ConversionService conversionService;

    @GetMapping
    public ResponseEntity<ContactSearchResultModel> getContacts(
            @RequestParam(defaultValue = DEFAULT_CONTACTS_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_CONTACTS_SIZE) int size) {
        log.info("Contacts getting request: page={}, size={}", page, size);
        ContactSearchParams params = ContactSearchParams.builder()
                .page(page)
                .size(size)
                .build();
        ContactSearchResult result = contactService.searchContacts(params);
        ContactSearchResultModel response = conversionService.convert(result, ContactSearchResultModel.class);
        contactLinker.linkByGetContacts(response, page, size);
        log.info("Contacts getting response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<ContactModel> getContact(@PathVariable String contactId) {
        Optional<Contact> optionalContact = contactService.findById(contactId);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            ContactModel response = conversionService.convert(contact, ContactModel.class);
            contactLinker.linkByGetContact(response, contactId);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

}
