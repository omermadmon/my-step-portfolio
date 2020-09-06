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
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Servlet responsible for creating new comments and add them to datastore. */
@WebServlet("/new-comment")
public class NewCommentServlet extends HttpServlet {
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // Collect comment attributes.
    String firstName = request.getParameter("fname");
    String lastName = request.getParameter("lname");
    long timestamp = System.currentTimeMillis();
    String commentText = request.getParameter("comment-text");

    // Collect email.
    String email = null;
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
        email = userService.getCurrentUser().getEmail();
    }
    
    // Create new comment entity.
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("fname", firstName);
    commentEntity.setProperty("lname", lastName);
    commentEntity.setProperty("timestamp", timestamp);
    commentEntity.setProperty("text", commentText);
    commentEntity.setProperty("email", email);

    // Add comment to datastore.
    datastore.put(commentEntity);

    response.sendRedirect("/comments.html");
  }
}