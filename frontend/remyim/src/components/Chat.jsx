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

const Chat = ({
  value: { id, name, messages = [] },
  onSend = () => {}
}) => {
  const handleMessageSend = message => {
    onSend(id, message)
  }
  return (
    <div>
      <h3>{name}</h3>
      {(messages && messages.length) ?
        <ChatMessageList value={messages}/> :
        <NoChatMessageList/>}
      <ChatMessageInput onSend={handleMessageSend}/>
    </div>
  )
}

export const ChatPropTypes = {
  value: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    messages: PropTypes.array,
  }).isRequired,
  onSend: PropTypes.func
}

Chat.propTypes = ChatPropTypes

export default Chat
