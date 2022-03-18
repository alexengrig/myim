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
import { ChatList, NoChatList } from './index'

const Chats = ({ selected, onClick = () => {} }) => {
  const selectedId = selected && selected.id
  const [chats, setChats] = useState(null)
  const [error, setError] = useState(null)
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
        setChats(chats.map(({ id, name }) => ({
          id: id,
          name: name
        })))
      })
      .catch(error => setError(error))
  }
  useEffect(() => {
    handleChatsFetch()
  }, [])
  if (chats) {
    return (
      <>
        {chats.length ?
          <ChatList
            value={chats}
            selected={selectedId}
            onClick={onClick}
          /> :
          <NoChatList/>}
      </>
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
