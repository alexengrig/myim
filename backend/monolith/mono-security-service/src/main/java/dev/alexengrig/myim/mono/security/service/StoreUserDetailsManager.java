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

import dev.alexengrig.myim.mono.security.domain.ApplicationUserDetails;
import dev.alexengrig.myim.mono.security.exception.UserAlreadyExistsException;
import dev.alexengrig.myim.mono.store.entity.AuthorEntity;
import dev.alexengrig.myim.mono.store.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreUserDetailsManager implements ApplicationUserDetailsManager {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConversionService conversionService;

    @Override
    public ApplicationUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (ApplicationUserDetails) auth.getPrincipal();
    }

    @Override
    public ApplicationUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthorEntity entity = authorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return conversionService.convert(entity, ApplicationUserDetails.class);
    }

    @Override
    public void createUser(ApplicationUserDetails user) {
        requireUserNotExist(user);
        AuthorEntity entity = conversionService.convert(user, AuthorEntity.class);
        encodePassword(entity);
        authorRepository.save(entity);
    }

    @Override
    public void updateUser(ApplicationUserDetails user) {
        requireUserExists(user.getUsername());
        AuthorEntity entity = conversionService.convert(user, AuthorEntity.class);
        authorRepository.save(entity);
    }

    @Override
    public void deleteUser(String username) {
        requireUserExists(username);
        authorRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        ApplicationUserDetails user = getCurrentUser();
        requireUserExists(user.getUsername());
        if (!Objects.equals(user.getPassword(), oldPassword)) {
            throw new IllegalArgumentException("Invalid old password");
        }
        ApplicationUserDetails userWithNewPassword = user.withPassword(newPassword);
        AuthorEntity entity = conversionService.convert(userWithNewPassword, AuthorEntity.class);
        encodePassword(entity);
        authorRepository.save(entity);
    }

    @Override
    public boolean userExists(String username) {
        return authorRepository.existsByUsername(username);
    }

    private void encodePassword(AuthorEntity entity) {
        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);
    }

    private void requireUserExists(String username) {
        if (!userExists(username)) {
            throw new UsernameNotFoundException(username);
        }
    }

    private void requireUserNotExist(ApplicationUserDetails user) {
        if (userExists(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
    }

}
