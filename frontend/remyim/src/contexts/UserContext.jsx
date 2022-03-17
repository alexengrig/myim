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

import { createContext, useContext, useEffect, useState } from 'react'
import { CSRF_HEADER_NAME, getCsrfToken } from '../utils/csrf'

const defaultUser = {
  id: null,
  name: null,
}

const UserContext = createContext(defaultUser)

export const UserContextProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [error, setError] = useState(null)
  useEffect(() => {
    fetch('http://localhost:8080/api/v1/users/current', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        [CSRF_HEADER_NAME]: getCsrfToken(),
      },
    }).
      then(response => response.json()).
      then(({ id, name }) => {
        setUser({ id, name })
      }).
      catch(error => {
        setError(error)
      })
  }, [])
  if (user) {
    return <UserContext.Provider value={user} children={children}/>
  } else if (error) {
    return <div>Authentication error: {error}</div>
  } else {
    return <div>Authentication execution...</div>
  }
}

export const useUserContext = () => useContext(UserContext)
