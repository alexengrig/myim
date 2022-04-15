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

package dev.alexengrig.myim.mono.security.service;

import dev.alexengrig.myim.mono.store.entity.AuthorEntity;
import dev.alexengrig.myim.mono.store.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreAuthenticationService implements ApplicationUserAuthenticationService {

    private final AuthorRepository authorRepository;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public void authenticateByUsername(String username) {
        AuthorEntity author = authorRepository.getByUsername(username);
        var token = new UsernamePasswordAuthenticationToken(author.getUsername(), author.getPassword());
        Authentication authenticate = authenticationProvider.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

}
