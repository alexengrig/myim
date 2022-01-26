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

import { useContext } from 'react'
import PropTypes from 'prop-types'
import { ApplicationContext } from '../contexts'

const Author = ({ value: { id, name } }) => {
  const { userId } = useContext(ApplicationContext)
  return userId === id ?
    <strong>{name}</strong> :
    <span>{name}</span>
}

const ChatMessageItem = ({ id, text, author }) => {
  return (
    <div>
      <Author value={author}/>: {text}
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
}

ChatMessageItem.propTypes = ChatMessageItemPropTypes

export default ChatMessageItem