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

import {ListItemText, Typography} from '@mui/material'
import {useUserContext} from '../../../contexts'
import {useMemo} from 'react'

const ChatMessageListItem = props => {
  const {id: currentUserId} = useUserContext()
  const {id, chatId, text, authorId, authorName} = props
  const sx = useMemo(() => {
    console.log(currentUserId, authorId)
    if (authorId === currentUserId) {
      return {textAlign: 'end'}
    }
  }, [currentUserId, authorId])
  return (
      <>
        <ListItemText
            sx={sx}
            primary={<Typography variant="body1">{authorName}</Typography>}
            secondary={<Typography variant="body2">{text}</Typography>}
        />
      </>
  )
}

export default ChatMessageListItem
