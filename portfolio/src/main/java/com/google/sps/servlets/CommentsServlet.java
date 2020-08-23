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
import java.util.ArrayList;
import java.util.List;
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

    // Write initial hard coded comments.
    comments = new HashMap<String, String>();
    comments.put("G. Buffon", "Awesome!");
    comments.put("Y. Katan", "Wonderful!");
    comments.put("A. Benado", "Amazing!");
    comments.put("A. Del-Piero", "Extraordinary!");

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
}
