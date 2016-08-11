package checkers;

import objectdraw.Location;

public class Move {
    
    int containsPoint(Checker[] checker, Location pt)
    {
        for (int i=0; i<checker.length; i++) {
            if(checker[i].checker.contains(pt))
                return i;
        }
        return -1;
    }
}
