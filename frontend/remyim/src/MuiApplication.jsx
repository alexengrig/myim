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

import {Grid} from '@mui/material'
import {Chat, Chats} from './components/MUI'
import AppBar from './components/MUI/AppBar/AppBar'
import {MuiThemeContextProvider} from './contexts'

const MuiApplication = props => {
    return (
        <MuiThemeContextProvider>
            <AppBar height="10%"/>
            <Grid container height="90%">
                <Grid item xs={4} height="100%">
                    <Chats/>
                </Grid>
                <Grid item xs={8} height="100%">
                    <Chat/>
                </Grid>
            </Grid>
        </MuiThemeContextProvider>
    )
}

export default MuiApplication
