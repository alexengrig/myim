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

import {forwardRef, useCallback, useEffect, useMemo, useRef, useState} from 'react'
import {List, ListItem, Typography} from '@mui/material'
import {Virtuoso} from 'react-virtuoso'
import {useChatContext, useEnvContext} from '../../../contexts'
import {ChatMessageListItem, NoChatMessageList} from './index'

const initialState = {
  loading: false,
  page: 0,
  size: 15,
  firstItemIndex: null,
  messages: null,
  error: null
}

const CurrentChat = props => {
  const {baseUrl, csrfHeader, csrfToken} = useEnvContext()
  const {id} = useChatContext()
  const [{
    loading,
    page,
    size,
    firstItemIndex,
    messages,
    error
  }, setState] = useState(initialState)
  const virtuosoRef = useRef(null)
  const context = useMemo(() => ({loading}), [loading])
  const handleMessagesFetch = useCallback(initial => {
    if (loading) {
      return
    }
    setState(prevState => ({
      ...prevState,
      loading: true
    }))
    const targetPage = initial ? initialState.page : page
    const targetSize = initial ? initialState.size : size
    fetch(`${baseUrl}/api/v1/sender/chats/${id}/messages?page=${targetPage}&size=${targetSize}`, {
      headers: {
        'Accept': 'application/json',
        [csrfHeader]: csrfToken,
      },
    })
        .then(response => response.json())
        .then(({values: newMessage = [], total}) => {
          setState(prevState => {
            const state = initial ? initialState : prevState
            const {page, firstItemIndex, messages} = state
            if (newMessage.length > 0) {
              return {
                ...state,
                loading: false,
                page: page + 1,
                firstItemIndex: initial ? total + newMessage.length - 1 : firstItemIndex - newMessage.length,
                messages: messages ? [...newMessage.reverse(), ...messages] : newMessage.reverse()
              }
            } else {
              return {
                ...state,
                loading: false,
                firstItemIndex: initial ? total : firstItemIndex,
                messages: messages || []
              }
            }
          })
        })
        .catch(error => {
          setState(prevState => ({
            ...prevState,
            error
          }))
        })
  }, [baseUrl, csrfHeader, csrfToken, id, loading, page, size])
  const handleMoreLoad = useCallback(atTop => {
    if (atTop) {
      handleMessagesFetch(false)
    }
  }, [handleMessagesFetch])
  const handleItemRender = (index, message) => {
    return (
        <ChatMessageListItem
            key={index}
            {...message}
        />
    )
  }
  useEffect(() => {
    handleMessagesFetch(true)
    if (virtuosoRef.current) {
      virtuosoRef.current.scrollToIndex({index: 'LAST', align: 'end'})
    }
  }, [id])
  if (messages) {
    if (messages.length > 0) {
      return (
          <Virtuoso
              ref={virtuosoRef}
              components={MuiComponents}
              context={context}
              data={messages}
              firstItemIndex={firstItemIndex}
              initialTopMostItemIndex={{index: 'LAST'}}
              itemContent={handleItemRender}
              atTopStateChange={handleMoreLoad}
          />
      )
    } else {
      return <NoChatMessageList/>
    }
  } else if (error) {
    return <Typography>Messages loading error: {error}</Typography>
  } else {
    return <Typography>Messages loading...</Typography>
  }
}

const MuiComponents = {
  List: forwardRef(({style, children, ...props}, listRef) => {
    return (
        <List
            {...props}
            ref={listRef}
            component="div"
            style={{...style, margin: 0, padding: 0}}
        >
          {children}
        </List>
    )
  }),
  Item: forwardRef(({style, children, ...props}, itemRef) => {
    return (
        <ListItem
            {...props}
            ref={itemRef}
            component="div"
            style={{...style}}
        >
          {children}
        </ListItem>
    )
  }),
  Footer: forwardRef(({context: {loading}}, footerRef) => {
    if (loading) {
      return (
          <Typography align="center" justifyContent="center">
            Loading...
          </Typography>
      )
    }
    return null
  }),
  Header: forwardRef(({context: {loading}}, footerRef) => {
    if (loading) {
      return (
          <Typography align="center" justifyContent="center">
            Loading...
          </Typography>
      )
    }
    return null
  })
}

export default CurrentChat
