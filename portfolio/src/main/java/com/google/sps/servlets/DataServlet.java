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

/** responsible for generating a random favourite club to be displayed on main page.*/
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  // List all of my favourite football clubs.
  private List<String> favouriteClubs;
  
  @Override
  public void init() {
    // Add my favourite clubs to favouriteClubs.
    favouriteClubs = new ArrayList<>();
    favouriteClubs.add("Maccabi Haifa FC");
    favouriteClubs.add("Juventus FC");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Generate a random club to pass to JS.
    String club = favouriteClubs.get((int) 
                  (Math.random() * favouriteClubs.size()));
    response.setContentType("text/html;");
    response.getWriter().println(club);
  }
}
