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

import PropTypes from 'prop-types'
import { ChatMessageInput, ChatMessageList, NoChatMessageList } from './index'
import { useEffect, useState } from 'react'
import { CSRF_HEADER_NAME, getCsrfToken } from '../utils/csrf'

const ChatBody = ({ messages, error, onSend, onTextUpdate, onRemove }) => {
  if (messages) {
    return (
      <>
        {messages.length ?
          <ChatMessageList
            value={messages}
            onTextUpdate={onTextUpdate}
            onRemove={onRemove}
          /> :
          <NoChatMessageList/>}
        <ChatMessageInput onSend={onSend}/>
      </>
    )
  } else if (error) {
    return <div>Messages loading error: {error}</div>
  } else {
    return <div>Messages loading...</div>
  }
}

const Chat = ({ value: { id, name }, onSend = () => {} }) => {
  const [messages, setMessages] = useState(null)
  const [error, setError] = useState(null)
  const handleMessagesFetch = () => {
    fetch(`http://localhost:8080/api/v1/sender/chats/${id}/messages`, {
      headers: {
        'Accept': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
    })
      .then(response => response.json())
      .then(data => data.values)
      .then((messages = []) => {
        setMessages(messages.map(({ id, chatId, text, authorId, authorName }) => ({
          id,
          text: text,
          author: {
            id: authorId,
            name: authorName
          }
        })))
      })
      .catch(error => setError(error))
  }
  const handleMessageSend = message => {
    onSend(id, message)
      .then(handleMessagesFetch)
  }
  const handleTextUpdate = (messageId, text) => {
    fetch(`http://localhost:8080/api/v1/manager/chats/${id}/messages/${messageId}`, {
      method: 'PATCH',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'text/plain',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
      body: text
    })
      .then(response => response.json())
      .then(chatMessage => {
        console.debug('Updated chat message text', chatMessage)
        handleMessagesFetch()
      })
      .catch(error => {
        console.error('Error of chat message text updating', error)
      })
  }
  const handleRemove = messageId => {
    fetch(`http://localhost:8080/api/v1/manager/chats/${id}/messages/${messageId}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
    })
      .then(response => response.json())
      .then(chatMessage => {
        console.debug('Removed chat message', chatMessage)
        handleMessagesFetch()
      })
      .catch(error => {
        console.error('Error of chat message removing', error)
      })
  }
  useEffect(() => {
    handleMessagesFetch()
  }, [id])
  return (
    <div>
      <h3>{name}</h3>
      <ChatBody
        messages={messages}
        error={error}
        onSend={handleMessageSend}
        onTextUpdate={handleTextUpdate}
        onRemove={handleRemove}
      />
    </div>
  )
}

export const ChatPropTypes = {
  value: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
  }).isRequired,
  onSend: PropTypes.func
}

Chat.propTypes = ChatPropTypes

export default Chat
