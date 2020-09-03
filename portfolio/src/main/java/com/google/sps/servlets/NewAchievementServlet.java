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

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** [TODO: REMOVE] Servlet responsible for creating new dummy achievementS and add them to datastore (for testing). */
@WebServlet("/new-achievement")
public class NewAchievementServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Count existing achievement.
    Query query = new Query("Achievement");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    
    // Create new achievement entity.
    Entity achievementEntity = new Entity("Achievement");
    int serial = results.countEntities();
    String achievementText = request.getParameter("text") + "\nSerial number: " + serial;
    achievementEntity.setProperty("title", "Maccabi Haifa FC");
    achievementEntity.setProperty("club", "mhfc");
    achievementEntity.setProperty("geo", "{ \"latitude\": 32.794, \"longitude\": 34.9896 }");
    achievementEntity.setProperty("text", achievementText);
    achievementEntity.setProperty("serial", serial);

    // Add achievement to datastore.
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(achievementEntity);

    response.sendRedirect("/achievements.html");
  }
}