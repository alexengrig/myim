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

import dev.alexengrig.myim.mono.contact.model.ContactModel;
import dev.alexengrig.myim.mono.contact.model.condition.ContactSearchResultModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ContactLinker {

    public void linkByGetContacts(ContactSearchResultModel response, int page, int size) {
        ContactController controller = controller();
        response.add(linkTo(controller.getContacts(page, size)).withSelfRel());
        response.addIf(page - 1 >= 0, () ->
                linkTo(controller.getContacts(page - 1, size)).withRel(IanaLinkRelations.PREV));
        response.addIf(response.getTotal() > (long) (page + 1) * size, () ->
                linkTo(controller.getContacts(page + 1, size)).withRel(IanaLinkRelations.NEXT));
        response.getValues().forEach(contact -> linkByGetContact(contact, contact.getId()));
    }

    public void linkByGetContact(ContactModel response, String contactId) {
        ContactController controller = controller();
        response.add(linkTo(controller.getContact(contactId)).withSelfRel());
    }

    private ContactController controller() {
        return methodOn(ContactController.class);
    }

}
