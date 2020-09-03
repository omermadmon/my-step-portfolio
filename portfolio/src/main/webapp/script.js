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


/** Achievements Generator. */
function generateRandomAchievement() {

  fetch("/random-achievement")
    .then(response => response.json())
    .then((achievement) => {

        if (achievement == null) {
            document.getElementById('achievement-container').innerText = "Sorry, there are no achievements at the moment :(";
        }
        
        else {
            const club = achievement.club;
            const text = achievement.text;
            const imgUrl = 'images/' + club + '-logo.png';

            // Add image to the page.
            document.getElementById('achievement-image').src = imgUrl;

            // Add text to the page.
            document.getElementById('achievement-container').innerText = text;
        }
        

    });
}

/** Fetch achievements from servlet,
    and display them in a table on achievements.html. */ 
function displayAchievements() {
    fetch("/achievements")
    .then(response => response.json())
    .then((achievementsJSON) => writeAchievementsToTable(achievementsJSON));
}

/** Get achievements (in JSON string format),
    and display them in a table on achievements.html. */
function writeAchievementsToTable(achievementsJSON) {
 
    // Create a table for holding achievements and clubs' names.
    var table = document.getElementById("achievements-table");

    // Foreach achievement insert a new row.
    achievementsJSON.forEach((achievement) => {
        var row = table.insertRow(-1);
        var imgUrl = 'images/' + achievement.club + '-logo.png';
        row.insertCell(0).innerHTML = "<img src=" + imgUrl + " alt='club-logo'>";
        row.insertCell(1).innerHTML = achievement.text;
    });
}

/** Create a map and adds it to the page. */
function createMap() {

  // Init map.
  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 45, lng: 0}, zoom: 3.7});

  // Create markers foreach achievements.
  fetch("/achievements")
    .then(response => response.json())
    .then((achievementsJSON) => createMarkers(achievementsJSON, map));
}  

/** Get achievements as JSON string and the map,
    and display all achievements on the map as markers.*/
function createMarkers(achievementsJSON, map) {
    
  achievementsJSON.forEach((achievement) => {
        
        // Create content window.
        const infowindow = new google.maps.InfoWindow({
            content: "<img src=images/" + achievement.club + 
                     "-logo.png alt='club-logo' class='club-icon'>" 
                     + "<br><br>" + achievement.text
        });

        // parse coordinates.
        var coordinates = JSON.parse(achievement.geo);

        // Create marker.
        const clubMarker = new google.maps.Marker({
        position: {lat: coordinates.latitude, lng: coordinates.longitude},
        map: map,
        title: achievement.club
        });

        // Attach content window to marker.
        clubMarker.addListener("click", () => {
        infowindow.open(map, clubMarker);
        });
    });

}

/** Load all components in achievements page. */
function initAchievementsPage() {
    displayAchievements();
    createMap();
}

/** Favourite Clubs Generator. */
function getRandomFavouriteClubs() {
  fetch('/data').then(response => response.text()).then((club) => {
    document.getElementById('favourite-club-container').innerText = club;
  });
}

/** Load all components in comments page. */
function initCommentsPage() {
    userAuthentication();
    displayComments();
}

/** Fetch autentication log-in/log-out url. */
function userAuthentication() {
    fetch('/authentication')
    .then(response => response.text())
    .then((authenticationMessage) => {
    document.getElementById('authentication-message').innerHTML = authenticationMessage;
  });
}

/** Fetch comments from servlet,
    and display them in a list on comments.html. */ 
function displayComments() {

    // Get maximum number of comments to display.
    const commentsLimit = document.getElementById('limit').value;

    fetch('/list-comments?limit='+commentsLimit).then(response => response.json())
    .then((commentsJSON) => writeCommentsToList(commentsJSON));
}

/** Get comments (in JSON string format),
    and display them in a list on comments.html. */
function writeCommentsToList(comments) {

    // Creates comments list and fill it with JSON content.
    const commentsList = document.getElementById('comments-list');
    commentsList.innerHTML = '';
    comments.forEach((comment) => {

        // Create list element and add it to the comments list.
        var author = formatName(comment.firstName, comment.lastName);
        var listItemText = '<strong>' + author + 
        ' said: </strong><br>' + comment.text + '<br><br>';
        var liElement = createListElement(listItemText);
        commentsList.appendChild(liElement);

        // Create delete button for this comment.
        var deleteButtonElement = document.createElement('button');
        deleteButtonElement.innerText = 'Delete';
        deleteButtonElement.addEventListener('click', () => {
            var sure = confirm("Are you sure?")
            if(sure) {
                // Tell the server to delete the comment from datastore:
                deleteCommentFromDataStore(comment.id);

                // Remove the comment from DOM:
                liElement.remove();

                // Reload comments without the deleted comment:
                displayComments();
            }
        });

        liElement.appendChild(deleteButtonElement);
    });
}

/** Convert name format: Omer Madmon => O. Madmon. */
function formatName(firstName, lastName) {
    if (firstName == "" && lastName == ""){
        return "Anonymous";
    } else if (firstName == ""){
        return lastName;
    } else if (lastName == ""){
        return firstName;
    } else {
        return firstName.charAt(0) + ". " + lastName;
    }    
}

/** Create a list element for a comment. */
function createListElement(htmlCode) {
  const liElement = document.createElement('li');
  liElement.innerHTML = htmlCode;
  return liElement;
}

/** Tell the server to delete a comment from datastore. */
function deleteCommentFromDataStore(commentID) {
   const params = new URLSearchParams();
   params.append('id', commentID);
   fetch('/delete-comment', {method: 'POST', body: params});
}