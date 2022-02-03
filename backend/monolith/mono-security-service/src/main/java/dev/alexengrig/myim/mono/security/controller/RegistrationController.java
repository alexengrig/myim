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

package dev.alexengrig.myim.mono.security.controller;

import dev.alexengrig.myim.mono.security.exception.UserAlreadyExistsException;
import dev.alexengrig.myim.mono.security.payload.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserDetailsManager userDetailsManager;
    private final ConversionService conversionService;

    @GetMapping
    public String page(Model model) {
        model.addAttribute("user", new UserRegistrationRequest());
        return "registration";
    }

    @PostMapping
    public String register(@ModelAttribute("user") UserRegistrationRequest request, Model model) {
        log.info("Register: {}", request);
        UserDetails user = conversionService.convert(request, UserDetails.class);
        try {
            userDetailsManager.createUser(user);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }

}