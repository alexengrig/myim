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
import { Chat, ChatList, NoChat, NoChatList } from './components'

const initChats = [
  { id: '1', name: 'ChatItem #1' },
  { id: '2', name: 'ChatItem #2' },
  { id: '3', name: 'ChatItem #3' },
  { id: '4', name: 'ChatItem #4' },
]

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
  '4': []
}

const MyimApplication = () => {
  const [chats] = useState(initChats)
  const [chat, setChat] = useState(null)
  const [messagesByChatId, setMessagesByChatId] = useState(initMessagesByChatId)
  const handleChatClick = chatId => {
    const newChat = chats.find(chat => chat.id === chatId)
    setChat({
      id: newChat.id,
      name: newChat.name,
      messages: messagesByChatId[newChat.id]
    })
  }
  const handleSend = (chatId, text) => {
    const newMessage = {
      text,
      author: {
        id: '1',
        name: 'User #1'
      }
    }
    setChat({
      ...chat,
      messages: [...chat.messages, newMessage]
    })
    setMessagesByChatId({
      ...messagesByChatId,
      [chatId]: [...messagesByChatId[chatId], newMessage]
    })
  }
  return (
    <div>
      <h1>myim</h1>
      {(chats && chats.length) ?
        <ChatList
          value={chats}
          selectedId={chat && chat.id}
          onChatClick={handleChatClick}
        /> :
        <NoChatList/>}
      {chat ?
        <Chat
          value={chat}
          onSend={handleSend}
        /> :
        <NoChat/>}
    </div>
  )
}

export default MyimApplication
