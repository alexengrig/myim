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

import { useEffect, useState } from 'react'
import { useEnvContext } from '../contexts'

const Contacts = () => {
  const { baseUrl, csrfHeader, csrfToken } = useEnvContext()
  const [href, setHref] = useState(`${baseUrl}/api/v1/contacts?size=2`)
  const [prevHref, setPrevHref] = useState(null)
  const [nextHref, setNextHref] = useState(null)
  const [contacts, setContacts] = useState(null)
  const [error, setError] = useState(null)
  const onPrev = () => {
    setHref(prevHref)
  }
  const onNext = () => {
    setHref(nextHref)
  }
  const handleContactsFetch = () => {
    fetch(href, {
      headers: {
        'Accept': 'application/json',
        [csrfHeader]: csrfToken,
      },
    })
      .then(response => response.json())
      .then(({ values: contacts = [], _links: { next, prev } }) => {
        setContacts(contacts.map(({ id, name }) => ({ id, name })))
        if (prev && prev.href) {
          setPrevHref(prev.href)
        } else {
          setPrevHref(null)
        }
        if (next && next.href) {
          setNextHref(next.href)
        } else {
          setNextHref(null)
        }
      })
      .catch(error => setError(error))
  }
  useEffect(() => {
    handleContactsFetch()
  }, [href])
  if (contacts) {
    return (
      <div>
        <h2>Contacts</h2>
        {contacts && contacts.map((contact, index) => {
          return (
            <div key={index}>{contact.name}</div>
          )
        })}
        {prevHref && <button onClick={onPrev}>Previous</button>}
        {nextHref && <button onClick={onNext}>Next</button>}
      </div>
    )
  } else if (error) {
    return <div>Contacts loading error: {error}</div>
  } else {
    return <div>Contacts loading...</div>
  }
}

export const ContactsPropTypes = {}

Contacts.propTypes = ContactsPropTypes

export default Contacts
