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


// Create a key-value object for storing clubs achievements.
var achievements =
    {
      'mhfc' : 'In FM2013, I led Maccabi Haifa to the \
                European Champions Cup semi-final!',
      'juve' : 'The first time I was playing FM was back in 2006, \
                when Juventus was one of the greatest teams in europe. \
                \nLeading this club to the European Champions Cup final \
                was my first FM experience.',
      'tott' : 'Tottenham Hotspur is one of the many great football clubs \
                of the coty of London, but they never managed to be \
                considered as the greatest club of the city. My FM challenge \
                was changing this fact...',
      'inter' : 'After 4 years of managing Hapoel Ramat-Gan with huge success, \
                 I got a (virtual) offer from Inter. \nIn half a season I \
                 managed to raise from 10th place all the way up to the \
                 honorable 2nd place in the italian football league, the Seria A.',
      'hrg' : 'This one is my favourite one!\nIn 4 years (in the game, of course) I \
               have qualified to the first israeli leauge, won the israeli national \
               cup, and also won Athletico Madrid in the European Champions Cup!'
    }; 

/** Achievements Generator. */
function addRandomAchievement() {

  // Pick a random club.
  const clubs = Object.keys(achievements);
  const club = clubs[Math.floor(Math.random() * clubs.length)];
  const imgUrl = 'images/' + club + '-logo.png';

  // Get the corresponding achievement text.
  const achievement = achievements[club];

  // Add image to the page.
  document.getElementById('achievement-image').src = imgUrl;

  // Add text to the page.
  document.getElementById('achievement-container').innerText = achievement;
}

/** Display all achievements in a table. */
function displayAllAchievements(){
    
    // Get all clubs.
    const clubs = Object.keys(achievements);    

    // Create a table for holding achievements and clubs' names.
    var table = document.getElementById("achievements-table");

    // Foreach club insert a new row with the corresponding achievement.
    for(let i = 0; i < clubs.length; ++i){
        var row = table.insertRow(-1);
        var imgUrl = 'images/' + clubs[i] + '-logo.png';
        row.insertCell(0).innerHTML = "<img src=" + imgUrl + " alt='club-logo'>";
        row.insertCell(1).innerHTML = achievements[clubs[i]];
    }
}

/** Favourite Clubs Generator. */
function getRandomFavouriteClubs() {
  fetch('/data').then(response => response.text()).then((club) => {
    document.getElementById('favourite-club-container').innerText = club;
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
function writeCommentsToList(comments){

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
function formatName(firstName, lastName){
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
function deleteCommentFromDataStore(commentID){
   const params = new URLSearchParams();
   params.append('id', commentID);
   fetch('/delete-comment', {method: 'POST', body: params});
}