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
import dev.alexengrig.myim.mono.security.domain.ApplicationUserRole;
import dev.alexengrig.myim.mono.security.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class InMapUserDetailsService implements UserDetailsManager {

    private final PasswordEncoder passwordEncoder;

    private Map<String, ApplicationUserDetails> userByUsername;

    @PostConstruct
    private void init() {
        userByUsername = Stream.of(
                        ApplicationUserDetails.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("admin"))
                                .authority(ApplicationUserRole.builder()
                                        .authority("ADMIN")
                                        .build())
                                .authority(ApplicationUserRole.builder()
                                        .authority("USER")
                                        .build())
                                .build(),
                        ApplicationUserDetails.builder()
                                .username("user")
                                .password(passwordEncoder.encode("user"))
                                .authority(ApplicationUserRole.builder()
                                        .authority("USER")
                                        .build())
                                .build(),
                        ApplicationUserDetails.builder()
                                .username("guest")
                                .password(passwordEncoder.encode("guest"))
                                .authority(ApplicationUserRole.builder()
                                        .authority("GUEST")
                                        .build())
                                .build())
                .collect(Collectors.toMap(ApplicationUserDetails::getUsername, Function.identity()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        ApplicationUserDetails user = userByUsername.get(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        log.info("Loaded user: {}", user);
        return user;
    }

    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        userByUsername.put(user.getUsername(), ((ApplicationUserDetails) user).withPassword(encodedPassword));
    }

    @Override
    public void updateUser(UserDetails user) {
        String username = user.getUsername();
        if (userByUsername.containsKey(username)) {
            userByUsername.put(username, (ApplicationUserDetails) user);
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    @Override
    public void deleteUser(String username) {
        ApplicationUserDetails removedUser = userByUsername.remove(username);
        if (removedUser == null) {
            throw new UsernameNotFoundException(username);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ApplicationUserDetails user = (ApplicationUserDetails) auth.getPrincipal();
        String newEncodedPassword = passwordEncoder.encode(newPassword);
        userByUsername.put(user.getUsername(), user.withPassword(newEncodedPassword));
    }

    @Override
    public boolean userExists(String username) {
        return userByUsername.containsKey(username);
    }

}
