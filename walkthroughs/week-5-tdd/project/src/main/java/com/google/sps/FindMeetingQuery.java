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

    // if there are no events or no (non-optional) attendees, return {@code TimeRange.WHOLE_DAY} as a list
    if (request.getAttendees().isEmpty() || events.isEmpty()) {
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    // if there duration is longer than a day, return an empty list
    if (request.getDuration() > NUMBER_OF_MINUTES_IN_DAY) {
        return Arrays.asList();
    }

    ArrayList<TimeRange> relevantTimeRanges = FindMeetingQuery.filterIrrelevantTimesRanges(events, request.getAttendees());
    Collections.sort(relevantTimeRanges, TimeRange.ORDER_BY_START);
    return FindMeetingQuery.findAvilableTimesRanges(relevantTimeRanges, request.getDuration());
  }

  /** Filter out events if their attendees are none of the requested attendees. 
      Return only the time range.*/
  private static ArrayList<TimeRange> filterIrrelevantTimesRanges(Collection<Event> events, Collection<String> attendees) {
      ArrayList<TimeRange> relevantTimeRanges = new ArrayList<TimeRange>();
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

  /** Given the occupied slots (sorted by start date) and the meeting duration,
      return a list of time ranges where all meeting attendees are avilable. */
  private static Collection<TimeRange> findAvilableTimesRanges(ArrayList<TimeRange> occupiedSlots, long meetingDuration) {
      
      if (occupiedSlots.isEmpty()) {
          return Arrays.asList(TimeRange.WHOLE_DAY);
      }
      
      ArrayList<TimeRange> result = new ArrayList<TimeRange>();

      // init start and end of the next avilable slot (to add to result).
      int start = TimeRange.START_OF_DAY;
      int end;

      for (TimeRange occupiedSlot : occupiedSlots) {

          end = occupiedSlot.start();
          if (meetingDuration <= end - start) {
              result.add(TimeRange.fromStartEnd(start, end, false));
          }

          // in case where previous event contains the current event.
          if (start <= occupiedSlot.end()) {
              start = occupiedSlot.end();
          }
          
      }

      // add residual to result (if longer than meeting duration).
      end = TimeRange.END_OF_DAY;
      if (meetingDuration <= end - start) {
              result.add(TimeRange.fromStartEnd(start, end, true));
      }

      return result;
  }

}

