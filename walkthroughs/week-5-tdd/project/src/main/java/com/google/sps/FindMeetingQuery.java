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

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import java.util.Comparator;

public final class FindMeetingQuery {
  private static final long NUMBER_OF_MINUTES_IN_DAY = 60*24;

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    // if there are no (non-optional) attendees, return {@code TimeRange.WHOLE_DAY} as a list
    if (request.getAttendees().isEmpty()) {
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    // if there duration is longer than a day, return an empty list
    if (request.getDuration() > NUMBER_OF_MINUTES_IN_DAY) {
        return Arrays.asList();
    }

    /**
    LOGIC:

    1. store relevant time ranges in a list
    2. sort list by starting time 
    3. unite all overlapping time ranges ni list
    4. iterate from i=0 to i=NUMBER_OF_MINUTES_IN_DAY and create the results list
    */

    Collection<TimeRange> relevantTimeRanges = FindMeetingQuery.filterIrrelevantTimesRanges(events, request.getAttendees());
    
    
    // TODO: Remove this after implementing the rest of the method.
    throw new UnsupportedOperationException("TODO: Implement this method.");
  }

  /** Filter out events if their attendees are none of the requested attendees. 
      Return only the time range.*/
  private static Collection<TimeRange> filterIrrelevantTimesRanges(Collection<Event> events, Collection<String> attendees) {
      Collection<TimeRange> relevantTimeRanges = new ArrayList<TimeRange>();
      for (Event event : events) {
          if (FindMeetingQuery.isRelevant(event, attendees)) relevantTimeRanges.add(event.getWhen());
      }

      return relevantTimeRanges;
  }

  /** Return true iff at least one of the event's attendees is in the requested attendees. */
  private static boolean isRelevant(Event event, Collection<String> attendees) {
      for (String requestedAttendee : attendees) {
          for (String eventAttendee : event.getAttendees()) {
              if (requestedAttendee.equals(eventAttendee)) return true;
          }
      }

      return false;
  }

}
