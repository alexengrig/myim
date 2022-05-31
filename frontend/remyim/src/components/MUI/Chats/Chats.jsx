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

import {useCallback, useEffect, useState} from 'react'
import {Typography} from '@mui/material'
import {useEnvContext} from '../../../contexts'
import {ChatList, NoChatList} from './index'

const initialState = {
  loading: false,
  page: 0,
  size: 15,
  chats: null,
  error: null
}

const Chats = props => {
  const {baseUrl, csrfHeader, csrfToken} = useEnvContext()
  const [{loading, page, size, chats, error}, setState] = useState(initialState)
  const handleChatsFetch = useCallback(() => {
    if (loading) {
      return
    }
    setState(prevState => ({
      ...prevState,
      loading: true
    }))
    fetch(`${baseUrl}/api/v1/sender/chats?page=${page}&size=${size}`, {
      headers: {
        'Accept': 'application/json',
        [csrfHeader]: csrfToken,
      },
    })
        .then(response => response.json())
        .then(data => data.values)
        .then((newChats = []) => {
          setState(prevState => {
                const {page, chats} = prevState
                if (newChats.length > 0) {
                  return {
                    ...prevState,
                    loading: false,
                    page: page + 1,
                    chats: chats ? [...chats, ...newChats] : newChats
                  }
                } else {
                  return {
                    ...prevState,
                    loading: false,
                    chats: chats || []
                  }
                }
              }
          )
        })
        .catch(error => {
          setState(prevState => ({
            ...prevState,
            error,
            loading: false
          }))
        })
  }, [baseUrl, csrfHeader, csrfToken, loading, page, size, setState])
  useEffect(() => {
    handleChatsFetch()
  }, [])
  if (chats) {
    if (chats.length > 0) {
      return (
          <ChatList
              value={chats}
              loading={loading}
              onLoadMore={handleChatsFetch}
          />
      )
    } else {
      return <NoChatList/>
    }
  } else if (error) {
    return <Typography>Chats loading error: {error}</Typography>
  } else {
    return <Typography>Chats loading...</Typography>
  }
}

export default Chats
