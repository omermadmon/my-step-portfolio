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

// Achievements Generator.
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

// Write all achievements to a table.
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

// Favourite Clubs Generator.
function getRandomFavouriteClubs() {
  fetch('/data').then(response => response.text()).then((club) => {
    document.getElementById('favourite-club-container').innerText = club;
  });
}

/** Fetches from comments servlet and applies 
    writeCommentsToList on JSON string containing comments */ 
function getComments() {
    fetch('/comments').then(response => response.json())
    .then((commentsJSON) => writeCommentsToList(commentsJSON));
}

/** Gets comments (in JSON string format),
    and displays them in a list on comments.html*/
function writeCommentsToList(comments){

    // Creates comments list and fill it with JSON content.
    const commentsList = document.getElementById('comments-list');
    commentsList.innerHTML = '';
    Object.entries(comments).forEach(([author, comment]) => {
        var listItemText = '<strong>' + author + 
        ' said: </strong><br>' + comment;
        commentsList.appendChild(createListElement(listItemText));
    });
}

/** Creates an <li> element containing text. Taken from: 
step/walkthroughs/week-3-server/examples/server-stats/
    src/main/webapp/script.js */
function createListElement(htmlCode) {
  const liElement = document.createElement('li');
  liElement.innerHTML = htmlCode;
  return liElement;
}