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
import { useRef } from 'react'
import { CSRF_HEADER_NAME, getCsrfToken } from '../utils/csrf'

const ChatCreating = ({ onCreate }) => {
  const nameInput = useRef(null)
  const handleClick = () => {
    const name = nameInput.current.value
    const newChat = { name }
    fetch('http://localhost:8080/api/v1/manager/chats', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
      body: JSON.stringify(newChat)
    })
      .then(response => response.json())
      .then(chat => {
        console.log(chat)
        nameInput.current.value = ''
        onCreate()
      })
      .catch(error => {
        console.error(error)
      })
  }
  return (
    <>
      <label htmlFor="chat-id">Chat name:</label>
      <input id="chat-id" ref={nameInput}/>
      <button onClick={handleClick}>Create new chat</button>
    </>
  )
}

export const ChatCreatingPropTypes = {
  onCreate: PropTypes.func
}

ChatCreating.propTypes = ChatCreatingPropTypes

export default ChatCreating
