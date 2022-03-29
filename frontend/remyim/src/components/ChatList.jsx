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
import { ChatItem } from './index'

const ChatList = props => {
  const {
    value: chats = [],
    selected: selectedId,
    onClick,
    onUpdate,
    onRemove,
  } = props
  return (
    <ul>
      {chats.map((chat, index) => {
        const handleClick = id => {
          onClick(id)
        }
        const handleRename = (id, name) => {
          return onUpdate(id, { ...chat, name })
        }
        const handleRemove = id => {
          onRemove(id)
        }
        return (
          <ChatItem
            key={index}
            selected={selectedId}
            onClick={handleClick}
            onRename={handleRename}
            onRemove={handleRemove}
            {...chat}
          />
        )
      })}
    </ul>
  )
}

export const ChatListPropTypes = {
  value: PropTypes.arrayOf(PropTypes.object),
  selected: PropTypes.string,
  onClick: PropTypes.func,
  onUpdate: PropTypes.func,
  onRename: PropTypes.func,
}

ChatList.propTypes = ChatListPropTypes

export default ChatList
