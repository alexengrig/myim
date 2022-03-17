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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

public interface ApplicationUserDetailsManager extends UserDetailsManager {

    ApplicationUserDetails getCurrentUser();

    @Override
    ApplicationUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    @Override
    default void createUser(UserDetails user) {
        createUser((ApplicationUserDetails) user);
    }

    void createUser(ApplicationUserDetails user);

    @Override
    default void updateUser(UserDetails user) {
        updateUser((ApplicationUserDetails) user);
    }

    void updateUser(ApplicationUserDetails user);

}
