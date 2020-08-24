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

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import com.google.gson.Gson;

/** Servlet that returns some hard coded comments,
    to be displayed on comments page as a list.*/
@WebServlet("/comments")
public class CommentsServlet extends HttpServlet {

  // HashMap fpr storing hard coded comments.
  private HashMap<String, String> comments;
  
  @Override
  public void init() {
    comments = new HashMap<String, String>();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // Transform HashMap to JSON string.
    Gson gson = new Gson();
    String json = gson.toJson(comments);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // Write comment from request to comments HashMap.
    String firstName = request.getParameter("fname");
    String lastName = request.getParameter("lname");
    String commentText = request.getParameter("comment-text");

    String name = formatName(firstName, lastName);
    comments.put(name, commentText);

    // Redirect back to the comments HTML page.
    response.sendRedirect("/comments.html");
  }

  // convert name format: Omer Madmon => O. Madmon
  private String formatName(String firstName, String lastName){
      if (firstName == null && lastName == null){
          return "Anonymous";
      } else if (firstName == null){
          return lastName;
      } else if (lastName == null){
          return firstName;
      }
      return firstName.charAt(0) + ". " + lastName;
  }
}
