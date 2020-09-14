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

/** Class responsible for representing my FM achievements. */
public final class Achievement {

  private final long id;
  private final String title;
  private final String club;
  private final String geo;
  private final String text;

  /**
  * Used whenever an achievement object has to be created manually.
  * @param  id  a unique serial id number.
  * @param  title  full club name (e.g "Maccabi Haifa FC").
  * @param  club  lowercase short club name (e.g "mhfc"), used to retrieve the corresponding image to be displayed on a Google Maps map.
  * @param  geo  a JSON string with properties of "latitude" and "longitude", for displaying the achievement as a marker on a Google Maps map.
  * @param  text  achievement description.
  */
  public Achievement(long id, String title, String club, 
                 String geo, String text) {
    this.id = id;
    this.title = title;
    this.club = club;
    this.geo = geo;
    this.text = text;
  }

  /**
  * Used whenever an achievement object is constructed out of a data store entity.
  * @param  entity  a datastore entity.
  */
  public Achievement(Entity entity) {
    this.id = entity.getKey().getId();
    this.title = (String) entity.getProperty("title");
    this.club = (String) entity.getProperty("club");
    this.geo = (String) entity.getProperty("geo");
    this.text = (String) entity.getProperty("text");
  }
}