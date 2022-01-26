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
import { Chat, ChatList, NoChat, NoChatList } from './components'
import { ApplicationContext } from './contexts'

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
  const [chats, setChats] = useState([])
  const [chat, setChat] = useState(null)
  const [messagesByChatId, setMessagesByChatId] = useState(initMessagesByChatId)
  const handleChatMessagesFetch = chatId => {
    fetch(`http://localhost:8080/api/v1/sender/chats/${chatId}/messages`, {
      headers: {
        'Accept': 'application/json'
      }
    })
      .then(response => response.json())
      .then(data => data.values)
      .then((messages = []) => {
        const chat = chats.find(chat => chat.id === chatId)
        setChat({
          id: chat.id,
          name: chat.name,
          messages: messages.map(({ id, text, authorId, authorName }) => ({
            id: id,
            text: text,
            author: {
              id: authorId,
              name: authorName
            }
          }))
        })
      })
  }
  const handleSend = (chatId, text) => {
    const newMessage = {
      text,
      author: {
        id: user.id,
        name: user.name
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
  const [user] = useState({ id: '1', name: 'User #1' })
  const context = { userId: user.id }
  const handleChatsFetch = () => {
    fetch('http://localhost:8080/api/v1/sender/chats', {
      headers: {
        'Accept': 'application/json'
      }
    })
      .then(response => response.json())
      .then(data => data.values)
      .then((chats = []) => setChats(chats.map(({ id, name }) => ({ id, name }))))
  }
  useEffect(handleChatsFetch, [])
  return (
    <ApplicationContext.Provider value={context}>
      <div>
        <h1>myim | {user.name}</h1>
        {(chats && chats.length) ?
          <ChatList
            value={chats}
            selectedId={chat && chat.id}
            onChatClick={handleChatMessagesFetch}
          /> :
          <NoChatList/>}
        {chat ?
          <Chat
            value={chat}
            onSend={handleSend}
          /> :
          <NoChat/>}
      </div>
    </ApplicationContext.Provider>
  )
}

export default MyimApplication
