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

package dev.alexengrig.myim.mono.store.repository;

import dev.alexengrig.myim.mono.store.entity.ChatMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, String> {

    @Query("""
            select e from ChatMessageEntity e
            where (':chatIds' = '()' or e.chat.id in :chatIds)
              and (':authorIds' = '()' or e.author.id in :authorIds)
            """)
    Page<ChatMessageEntity> search(
            Pageable pageable,
            @Param("chatIds") Set<String> chatIds,
            @Param("authorIds") Set<String> authorIds);

}
