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

import {createContext, useContext, useEffect, useState} from 'react'
import {useEnvContext} from './index'

const defaultUser = {
  id: null,
  name: null,
  username: null,
}

const UserContext = createContext(defaultUser)

export const UserContextProvider = ({children}) => {
  const { baseUrl, csrfHeader, csrfToken } = useEnvContext()
  const [user, setUser] = useState(null)
  const [error, setError] = useState(null)
  useEffect(() => {
    fetch(`${baseUrl}/api/v1/users/current`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        [csrfHeader]: csrfToken,
      },
    })
        .then(response => response.json())
        .then(({id, name, username}) => {
          setUser({id, name, username})
        })
      .catch(error => {
        setError(error)
      })
  }, [baseUrl])
  if (user) {
    return <UserContext.Provider value={user} children={children}/>
  } else if (error) {
    return <div>Authentication error: {error}</div>
  } else {
    return <div>Authentication execution...</div>
  }
}

export const useUserContext = () => useContext(UserContext)
