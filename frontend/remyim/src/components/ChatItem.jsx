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

const ChatItem = ({ id, name, selected, onClick = () => {} }) => {
  const handleClick = () => {
    onClick(id)
  }
  return (
    <div onClick={handleClick}>
      {selected ?
        <strong>{name}</strong> :
        <span>{name}</span>}
    </div>
  )
}

export const ChatItemPropTypes = {
  id: PropTypes.string,
  name: PropTypes.string,
  selected: PropTypes.bool,
  onClick: PropTypes.func
}

ChatItem.propTypes = ChatItemPropTypes

export default ChatItem
