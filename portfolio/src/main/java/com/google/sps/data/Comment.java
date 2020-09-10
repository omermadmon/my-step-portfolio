// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.data;

import com.google.appengine.api.datastore.Entity;

/** Class responsible for representing users' comments on the website. */
public final class Comment {

  private final long id;
  private final String firstName;
  private final String lastName;
  private final long timestamp;
  private final String text;

  /**
  * Used whenever a comment object has to be created manually.
  * @param  id  a unique serial id number.
  * @param  firstName  user's first name.
  * @param  lastName  user's last name.
  * @param  timestamp  a timestamp indicating when the comment entity was created and written to db.
  * @param  text  the comment's content.
  */
  public Comment(long id, String firstName, String lastName, 
                 long timestamp, String text) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.timestamp = timestamp;
    this.text = text;
  }

  /**
  * Used whenever a comment object is constructed out of a datastore entity.
  * @param  entity  a datastore entity.
  */
  public Comment(Entity entity) {
    this.id = entity.getKey().getId();
    this.firstName = (String) entity.getProperty("fname");
    this.lastName = (String) entity.getProperty("lname");
    this.timestamp = (long) entity.getProperty("timestamp");
    this.text = (String) entity.getProperty("text");
  }
}