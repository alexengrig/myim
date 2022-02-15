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

package dev.alexengrig.myim.mono.security.listener;

import dev.alexengrig.myim.mono.security.domain.ApplicationUserDetails;
import dev.alexengrig.myim.mono.security.service.ApplicationUserDetailsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultUserCreationOnApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ApplicationUserDetailsManager userDetailsManager;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent ignore) {
        if (!userDetailsManager.userExists("admin")) {
            userDetailsManager.createUser(ApplicationUserDetails.builder()
                    .username("admin")
                    .password("admin")
                    .build());
        }
        log.info("Default user: admin/admin");
    }

}
