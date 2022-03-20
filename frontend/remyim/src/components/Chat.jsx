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

const ChatBody = ({ messages, error, onSend }) => {
  if (messages) {
    return (
      <>
        {messages.length ?
          <ChatMessageList value={messages}/> :
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
        setMessages(messages.map(({ chatId, text, authorId, authorName }) => ({
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
