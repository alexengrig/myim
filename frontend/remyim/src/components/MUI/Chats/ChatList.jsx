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

import {forwardRef, useMemo} from 'react'
import {Virtuoso} from 'react-virtuoso'
import {List, ListItem, Typography} from '@mui/material'
import {useChatContext} from '../../../contexts'
import {ChatListItem} from './index'

const ChatList = props => {
  const {id: selectedChatId, set: setSelectedChat} = useChatContext()
  const {value: chats, loading, onLoadMore} = props
  const context = useMemo(() => ({loading}), [loading])
  const handleItemRender = (index, chat) => {
    return (
        <ChatListItem
            key={index}
            selectedId={selectedChatId}
            onClick={() => setSelectedChat(chat)}
            {...chat}
        />
    )
  }
  const handleMoreLoad = atBottom => {
    if (atBottom) {
      onLoadMore()
    }
  }
  return (
      <Virtuoso
          components={MuiComponents}
          context={context}
          data={chats}
          itemContent={handleItemRender}
          atBottomStateChange={handleMoreLoad}
      />
  )
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
            style={{...style, margin: 0, padding: 0}}
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
  })
}

export default ChatList
