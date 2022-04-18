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

import { useEffect, useRef } from 'react'

const useInterval = (callback, delay, deps = [], cleanup) => {
  const callbackRef = useRef()
  const cleanupRef = useRef()
  useEffect(() => {
    callbackRef.current = callback
    cleanupRef.current = cleanup
  })
  useEffect(() => {
    const run = () => {
      callbackRef.current()
    }
    const clean = () => {
      if (cleanupRef.current instanceof Function) {
        cleanupRef.current()
      }
    }
    const intervalId = setInterval(run, delay)
    return () => {
      clearInterval(intervalId)
      clean()
    }
  }, [delay, ...deps])
}

export default useInterval
