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

import {AppBar as MuiAppBar, Grid, Toolbar, Typography} from '@mui/material'
import {useChatContext} from '../../../contexts'

const AppBar = props => {
  const {height} = props
  const {name: chatName} = useChatContext()
  return (
      <MuiAppBar position="static" sx={{height: height}}>
        <Toolbar>
          <Grid container>
            <Grid item xs={4}>
              <Typography>MYIM</Typography>
            </Grid>
            <Grid item xs={8}>
              {chatName && <Typography>{chatName}</Typography>}
            </Grid>
          </Grid>
        </Toolbar>
      </MuiAppBar>
  )
}

export default AppBar
