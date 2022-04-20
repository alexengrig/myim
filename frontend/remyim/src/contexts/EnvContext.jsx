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
import Cookies from 'js-cookie'

const defaultEnv = {
  baseUrl: null,
  csrfHeader: null,
  csrfToken: null
}

const EnvContext = createContext(defaultEnv)

const CSRF_HEADER_NAME = 'X-XSRF-TOKEN'
const CSRF_COOKIE_NAME = 'XSRF-TOKEN'

export const EnvContextProvider = ({ children }) => {
  const [env, setEnv] = useState(null)
  useEffect(() => {
    setEnv({
      baseUrl: window.location.origin,
      csrfHeader: CSRF_HEADER_NAME,
      csrfToken: Cookies.get(CSRF_COOKIE_NAME)
    })
  }, [window.location.origin, window.document.cookie])
  if (env) {
    return <EnvContext.Provider value={env} children={children}/>
  } else {
    return <div>Environment settings...</div>
  }
}

export const useEnvContext = () => useContext(EnvContext)
