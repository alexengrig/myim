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

import { useRef } from 'react'
import PropTypes from 'prop-types'

const isOnlyEnterKey = event => event.charCode === 13 && !event.shiftKey && !event.ctrlKey && !event.altKey

const ChatMessageInput = ({ onSend = () => {} }) => {
  const inputRef = useRef()
  const handleInputKeyPress = event => {
    if (isOnlyEnterKey(event) && inputRef.current.value) {
      const text = inputRef.current.value
      inputRef.current.value = ''
      onSend(text)
    }
  }
  return (
    <input
      ref={inputRef}
      placeholder="Write a message..."
      onKeyPress={handleInputKeyPress}
    />
  )
}

export const ChatMessageInputPropTypes = {
  onSend: PropTypes.func
}

ChatMessageInput.propTypes = ChatMessageInputPropTypes

export default ChatMessageInput
