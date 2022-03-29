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
import { useUserContext } from '../contexts'
import { useState } from 'react'

const Author = ({ value: { id, name } }) => {
  const { id: userId } = useUserContext()
  return userId === id ?
    <strong>{name}: </strong> :
    <span>{name}: </span>
}

const Text = ({ value, editable, onChange }) => {
  if (editable) {
    return (
      <input
        value={value}
        onChange={e => onChange(e.target.value)}
      />
    )
  }
  return (
    <span>{value}</span>
  )
}

const EditButton = ({ onClick }) => {
  return (
    <button onClick={onClick}>
      Edit
    </button>
  )
}

const RemoveButton = ({ onClick }) => {
  return (
    <button onClick={onClick}>
      Remove
    </button>
  )
}

const ChatMessageItem = ({ id, text, author, onTextUpdate, onRemove }) => {
  const [editable, setEditable] = useState(false)
  const [newText, setNewText] = useState(text)
  const handleEdit = () => {
    if (editable) {
      onTextUpdate(id, newText)
    }
    setEditable(!editable)
  }
  const handleRemove = () => {
    onRemove(id)
  }
  return (
    <div>
      <Author value={author}/>
      <Text
        value={editable ? newText : text}
        editable={editable}
        onChange={setNewText}
      />
      <EditButton onClick={handleEdit}/>
      <RemoveButton onClick={handleRemove}/>
    </div>
  )
}

export const ChatMessageItemPropTypes = {
  id: PropTypes.string,
  text: PropTypes.string,
  author: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
  }),
  onTextUpdate: PropTypes.func,
  onRemove: PropTypes.func,
}

ChatMessageItem.propTypes = ChatMessageItemPropTypes

export default ChatMessageItem
