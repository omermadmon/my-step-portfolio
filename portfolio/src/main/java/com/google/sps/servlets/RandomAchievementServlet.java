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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.gson.Gson;
import com.google.sps.data.Achievement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*; 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for retrieving a random achievement to be displayed on index page. */
@WebServlet("/random-achievement")
public class RandomAchievementServlet extends HttpServlet {
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // Create query for counting all achievements.
    Query query = new Query("Achievement");
    PreparedQuery results = datastore.prepare(query);

    // Generate random achievement serial.
    int randomAchievementSerial = (int) (Math.random() * results.countEntities());

    // Create query for retrieving achievement from serial.
    Query randomAchievementQuery = new Query("Achievement")
                  .setFilter(new FilterPredicate("serial", FilterOperator.EQUAL, randomAchievementSerial));

    // Prepare query.
    PreparedQuery pq = datastore.prepare(randomAchievementQuery);
    
    /** Return the first result found in the index that matches the query. 
        'serial' property is not a key, but is unique. */
    Entity entity = pq.asSingleEntity();

    if (entity != null) {
        // Create an achievement from the random entity.
        Achievement randomAchievement = new Achievement(entity);

        // Transform comments list to JSON string.
        Gson gson = new Gson();
        String json = gson.toJson(randomAchievement);

        // Send the JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println(json);
    } else {
        // Send a null JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println("null");
    }
    
  }
}
