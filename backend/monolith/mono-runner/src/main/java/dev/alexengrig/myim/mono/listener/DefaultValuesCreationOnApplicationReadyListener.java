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

package dev.alexengrig.myim.mono.listener;

import dev.alexengrig.myim.mono.security.domain.ApplicationUserDetails;
import dev.alexengrig.myim.mono.security.service.ApplicationUserDetailsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Component
@Profile("development")
@RequiredArgsConstructor
public class DefaultValuesCreationOnApplicationReadyListener
        implements ApplicationListener<ApplicationReadyEvent> {

    private static final int FIRST_USER_NUMBER = 0;
    private static final int LAST_USER_NUMBER = 9;
    private static final String USERNAME_PREFIX = "user";

    private final ApplicationUserDetailsManager userManager;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUsersIfNeed();
    }

    private Stream<String> getUsernames() {
        return IntStream.rangeClosed(FIRST_USER_NUMBER, LAST_USER_NUMBER)
                .mapToObj(i -> USERNAME_PREFIX + i);
    }

    private void createDefaultUsersIfNeed() {
        getUsernames().forEach(username -> {
            if (!userManager.userExists(username)) {
                userManager.createUser(ApplicationUserDetails.builder()
                        .username(username)
                        .password(username)
                        .build());
            }
        });
        log.info("Development users: userX/userX (X = [0-9])");
    }

}
