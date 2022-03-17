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
import { Chat, ChatList, Logout, NoChat, NoChatList } from './components'
import { useUserContext } from './contexts'
import { CSRF_HEADER_NAME, getCsrfToken } from './utils/csrf'

const initMessagesByChatId = {
  '1': [
    { id: '10', text: 'Message #10', author: { id: '2', name: 'User #2' } },
    { id: '11', text: 'Message #11', author: { id: '1', name: 'User #1' } },
  ],
  '2': [
    { id: '20', text: 'Message #20', author: { id: '1', name: 'User #1' } },
    { id: '21', text: 'Message #21', author: { id: '3', name: 'User #3' } },
  ],
  '3': [
    { id: '30', text: 'Message #30', author: { id: '3', name: 'User #3' } },
    { id: '31', text: 'Message #31', author: { id: '2', name: 'User #2' } },
  ],
  '4': [],
}

const MyimApplication = () => {
  const [chats, setChats] = useState([])
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
  const [messagesByChatId, setMessagesByChatId] = useState(initMessagesByChatId)
  const handleSend = (chatId, text) => {
    const newMessage = {
      text,
      author: {
        id: user.id,
        name: user.name,
      },
    }
    setChat({
      ...chat,
      messages: [...chat.messages, newMessage],
    })
    setMessagesByChatId({
      ...messagesByChatId,
      [chatId]: [...messagesByChatId[chatId], newMessage],
    })
  }
  const user = useUserContext()
  const handleChatsFetch = () => {
    fetch('http://localhost:8080/api/v1/sender/chats', {
      headers: {
        'Accept': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
    })
      .then(response => response.json())
      .then(data => data.values)
      .then((chats = []) => {
        setChats(chats.map(({ id, name }) => ({ id, name })))
      })
  }
  useEffect(handleChatsFetch, [])
  return (
    <>
      <div>
        <h1>myim | {user.name}</h1>
        {(chats && chats.length) ?
          <ChatList
            value={chats}
            selectedId={chat && chat.id}
            onChatClick={handleChatFetch}
          /> :
          <NoChatList/>}
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
