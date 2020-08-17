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
      'mhfc' : 'to be done.',
      'juve' : 'to be done.',
      'tott' : 'to be done.',
      'inter' : 'to be done.',
      'hrg' : 'to be done.'
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