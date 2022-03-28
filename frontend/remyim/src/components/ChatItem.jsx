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

import { useState } from 'react'
import PropTypes from 'prop-types'
import { ChatEditingButton, ChatRemovingButton } from './index'

const ChatName = props => {
  const { value, selected, editable, onClick, onChange } = props
  if (editable) {
    return (
      <input
        value={value}
        onChange={e => onChange(e.target.value)}
      />
    )
  }
  const Component = props => selected
    ? <strong {...props}/>
    : <span {...props}/>
  return (
    <Component onClick={onClick}>
      {value}
    </Component>
  )
}

const ChatItem = props => {
  const { id, name, selected, onClick = () => {}, onRename, onRemove } = props
  const [editable, setEditable] = useState(false)
  const [newName, setNewName] = useState(name)
  const handleEdit = () => {
    if (editable) {
      onRename(newName)
    }
    setEditable(!editable)
  }
  const handleRemove = () => {
    onRemove(id)
  }
  return (
    <div>
      <ChatName
        value={editable ? newName : name}
        selected={selected}
        editable={editable}
        onClick={onClick}
        onChange={setNewName}
      />
      <ChatEditingButton onClick={handleEdit}/>
      <ChatRemovingButton onClick={handleRemove}/>
    </div>
  )
}

export const ChatItemPropTypes = {
  id: PropTypes.string,
  name: PropTypes.string,
  selected: PropTypes.bool,
  onClick: PropTypes.func,
  onRename: PropTypes.func,
  onRemove: PropTypes.func,
}

ChatItem.propTypes = ChatItemPropTypes

export default ChatItem
