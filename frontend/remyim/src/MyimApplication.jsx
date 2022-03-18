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

import { useState } from 'react'
import { Chat, Chats, Logout, NoChat } from './components'
import { useUserContext } from './contexts'
import { CSRF_HEADER_NAME, getCsrfToken } from './utils/csrf'

const MyimApplication = () => {
  const user = useUserContext()
  const [chat, setChat] = useState(null)
  const handleChatFetch = chatId => {
    fetch(`http://localhost:8080/api/v1/sender/chats/${chatId}`, {
      headers: {
        'Accept': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
    })
      .then(response => response.json())
      .then(({ id, name }) => {
        setChat({
          id: id,
          name: name,
        })
      })
  }
  const handleSend = (chatId, text) => {
    const newMessage = { text }
    fetch(`http://localhost:8080/api/v1/recipient/chats/${chatId}/messages`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
      body: JSON.stringify(newMessage)
    })
      .then(response => response.json())
      .then(({ chatId, description, type }) => {
        console.log(chatId, description, type)
      })
  }
  return (
    <>
      <div>
        <h1>myim | {user.name}</h1>
        <Chats
          selected={chat}
          onClick={handleChatFetch}
        />
        {chat ?
          <Chat
            value={chat}
            onSend={handleSend}
          /> :
          <NoChat/>}
      </div>
      <Logout/>
    </>
  )
}

export default MyimApplication
