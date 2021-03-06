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
import { ChatMessageItem } from './index'

const ChatMessageList = ({ value: messages = [], onTextUpdate, onRemove }) => (
  <ul>
    {messages.map((message, index) => {
      return (
        <ChatMessageItem
          key={index}
          onTextUpdate={onTextUpdate}
          onRemove={onRemove}
          {...message}
        />
      )
    })}
  </ul>
)

export const ChatMessageListPropTypes = {
  value: PropTypes.arrayOf(PropTypes.object),
  onTextUpdate: PropTypes.func,
  onRemove: PropTypes.func,
}

ChatMessageList.propTypes = ChatMessageListPropTypes

export default ChatMessageList
