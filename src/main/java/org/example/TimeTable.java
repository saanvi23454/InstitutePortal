package org.example;

import java.util.*;

public class TimeTable {
    public List<Pair<String>> timings = new ArrayList<>();
    public String venue = "";

    public void setTimings(List<Pair<String>> _timings){
        timings = _timings;
    }

    public void setVenue(String _venue){
        venue = _venue;
    }

    public String toString(){
        String prnt = "Venue : " + venue + "\n";
        int i = 1;
        for (Pair<String> k : timings){
            prnt = prnt + i + ") " + k.val1 + " : " + k.val2 + "\n";
            i++;
        }
        return prnt;
    }
}
