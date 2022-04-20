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

import { useEffect, useState } from 'react'
import { Chat, Chats, Contacts, Logout, NoChat } from './components'
import { useEnvContext, useUserContext } from './contexts'

const MyimApplication = () => {
  const { baseUrl, csrfHeader, csrfToken } = useEnvContext()
  const user = useUserContext()
  const [chatId, setChatId] = useState(null)
  const [chat, setChat] = useState(null)
  const handleSend = (chatId, text) => {
    return fetch(`${baseUrl}/api/v1/recipient/chats/${chatId}/messages`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken,
      },
      body: text
    })
      .then(response => response.json())
      .then(({ chatId, description, type }) => {
        console.log(chatId, description, type)
      })
  }
  useEffect(() => {
    const handleChatFetch = () => {
      fetch(`${baseUrl}/api/v1/sender/chats/${chatId}`, {
        headers: {
          'Accept': 'application/json',
          [csrfHeader]: csrfToken,
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
    if (chatId) {
      handleChatFetch()
    }
  }, [baseUrl, chatId])
  return (
    <>
      <div>
        <h1>myim | {user.name}</h1>
        <Chats
          selected={chatId}
          onClick={setChatId}
        />
        {chat ?
          <Chat
            value={chat}
            onSend={handleSend}
          /> :
          <NoChat/>}
        <Contacts/>
      </div>
      <Logout/>
    </>
  )
}

export default MyimApplication
