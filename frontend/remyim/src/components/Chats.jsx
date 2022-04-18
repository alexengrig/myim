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
import { useEffect, useState } from 'react'
import { CSRF_HEADER_NAME, getCsrfToken } from '../utils/csrf'
import { ChatCreating, ChatList, NoChatList } from './index'

const Chats = ({ selected: selectedId, onClick = () => {} }) => {
  const [page, setPage] = useState(0)
  const [size, setSize] = useState(8)
  const [chats, setChats] = useState(null)
  const [error, setError] = useState(null)
  const handlePrevPageGo = () => {
    const prevPage = page - 1
    setPage(prevPage < 0 ? 0 : prevPage)
  }
  const handleNextPageGo = () => {
    setPage(page + 1)
  }
  const handleChatsFetch = () => {
    fetch(`http://localhost:8080/api/v1/sender/chats?page=${page}&size=${size}`, {
      headers: {
        'Accept': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
    })
      .then(response => response.json())
      .then(data => data.values)
      .then((chats = []) => {
        if (chats.length > 0) {
          setChats(chats.map(({ id, name }) => ({
            id: id,
            name: name
          })))
        } else {
          handlePrevPageGo()
        }
      })
      .catch(error => setError(error))
  }
  const handleChatRemove = chatId => {
    fetch(`http://localhost:8080/api/v1/manager/chats/${chatId}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      }
    })
      .then(response => response.json())
      .then(chat => {
        console.debug('Removed chat', chat)
        handleChatsFetch()
      })
      .catch(error => {
        console.error('Error of chat removing', error)
      })
  }
  const handleChatUpdate = (chatId, chat) => {
    fetch(`http://localhost:8080/api/v1/manager/chats/${chatId}`, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
      body: JSON.stringify(chat)
    })
      .then(response => response.json())
      .then(chat => {
        console.debug('Updated chat', chat)
        handleChatsFetch()
      })
      .catch(error => {
        console.error('Error of chat updating', error)
      })
  }
  useEffect(() => {
    handleChatsFetch()
  }, [size, page])
  if (chats) {
    return (
      <div>
        <h2>Chats</h2>
        {chats.length ?
          <>
            <ChatList
              value={chats}
              selected={selectedId}
              onClick={onClick}
              onUpdate={handleChatUpdate}
              onRemove={handleChatRemove}
            />
            <div>
              <button onClick={handlePrevPageGo} disabled={page <= 0}>Previous</button>
              <button onClick={handleNextPageGo}>Next</button>
              <button onClick={handleChatsFetch}>Update</button>
            </div>
          </> :
          <NoChatList/>}
        <ChatCreating onCreate={handleChatsFetch}/>
      </div>
    )
  } else if (error) {
    return <div>Chats loading error: {error}</div>
  } else {
    return <div>Chats loading...</div>
  }
}

export const ChatsPropTypes = {
  selected: PropTypes.shape({
      id: PropTypes.string.isRequired
    }
  ),
  onClick: PropTypes.func
}

Chats.propTypes = ChatsPropTypes

export default Chats
