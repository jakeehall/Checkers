package checkers;

import objectdraw.Location;

public class Rules {
    Settings settings = new Settings();
    int jumpedChecker = -1;
    
    boolean outOfBounds(int newx, int newy){
        return newx > (settings.boardColumns-1) || newy > (settings.boardRows-1);
    }
    
    boolean checkerMoved(int oldx, int oldy, int newx, int newy) {
        return !(newx == oldx && newy == oldy);
    }
    
    boolean stacked(Checker[] redChecker, Checker[] whiteChecker, int newx, int newy) {
        for(int i=0; i<redChecker.length; i++) {
            if((redChecker[i].location.column == newx && redChecker[i].location.row == newy) || (whiteChecker[i].location.column == newx && whiteChecker[i].location.row == newy))
                return true;
        }
        return false;
    }
    
    boolean jumpedChecker(Checker[] redChecker, Checker[] whiteChecker, int oldx, int oldy, int deltaX, int deltaY, boolean redsTurn) {
        if(redsTurn) {
            if(deltaX == 2) {
                //right +
                if(deltaY == 2) {
                    //down +
                    for(int i=0; i<whiteChecker.length; i++)
                        if((whiteChecker[i].location.column == oldx+1) && (whiteChecker[i].location.row == oldy+1)) {
                            jumpedChecker = i;
                            return true;
                        }
                } else if(deltaY == -2) {
                    //up -
                    for(int i=0; i<whiteChecker.length; i++)
                        if((whiteChecker[i].location.column == oldx+1) && (whiteChecker[i].location.row == oldy-1)) {
                            jumpedChecker = i;
                            return true;
                        }
                }
            } else if (deltaX == -2) {
                //left -
                if(deltaY == 2) {
                    //down +
                    for(int i=0; i<whiteChecker.length; i++)
                        if((whiteChecker[i].location.column == oldx-1) && (whiteChecker[i].location.row == oldy+1)) {
                            jumpedChecker = i;
                            return true;
                        }
                } else if(deltaY == -2) {
                    //up -
                    for(int i=0; i<whiteChecker.length; i++)
                        if((whiteChecker[i].location.column == oldx-1) && (whiteChecker[i].location.row == oldy-1)) {
                            jumpedChecker = i;
                            return true;
                        }
                }
            }
        } else if (!redsTurn) {
            if(deltaX == 2) {
                //right +
                if(deltaY == 2) {
                    //down +
                    for(int i=0; i<redChecker.length; i++)
                        if((redChecker[i].location.column == oldx+1) && (redChecker[i].location.row == oldy+1)) {
                            jumpedChecker = i;
                            return true;
                        }
                } else if(deltaY == -2) {
                    //up -
                    for(int i=0; i<redChecker.length; i++)
                        if((redChecker[i].location.column == oldx+1) && (redChecker[i].location.row == oldy-1)) {
                            jumpedChecker = i;
                            return true;
                        }
                }
            } else if (deltaX == -2) {
                //left -
                if(deltaY == 2) {
                    //down +
                    for(int i=0; i<redChecker.length; i++)
                        if((redChecker[i].location.column == oldx-1) && (redChecker[i].location.row == oldy+1)) {
                            jumpedChecker = i;
                            return true;
                        }
                } else if(deltaY == -2) {
                    //up -
                    for(int i=0; i<redChecker.length; i++)
                        if((redChecker[i].location.column == oldx-1) && (redChecker[i].location.row == oldy-1)) {
                            jumpedChecker = i;
                            return true;
                        }
                }
            }
        }
        return false;
    }
    
    void jump(Checker[] redChecker, Checker[] whiteChecker, boolean redsTurn, int[] scores) {
        if(redsTurn) {
            whiteChecker[jumpedChecker].location.column = -3;
            whiteChecker[jumpedChecker].location.row = -3;
            whiteChecker[jumpedChecker].checker.moveTo(-3*40, -3*40);
            scores[1]--;
        } else if (!redsTurn) {
            redChecker[jumpedChecker].location.column = -3;
            redChecker[jumpedChecker].location.row = -3;
            redChecker[jumpedChecker].checker.moveTo(-3*40, -3*40);
            scores[0]--;
        }
    }
    
    boolean validMove(Checker[] redChecker, Checker[] whiteChecker, boolean redsTurn, int clickedChecker, Location oldPoint, Location newPoint, int[] scores) {
        int oldx = (int)(oldPoint.getX()/40);
        int oldy = (int)(oldPoint.getY()/40);
        int newx = (int)(newPoint.getX()/40);
        int newy = (int)(newPoint.getY()/40);
        if(checkerMoved(oldx, oldy, newx, newy)) {
            if(!outOfBounds(newx, newy)) {
                if(!stacked(redChecker, whiteChecker, newx, newy)){
                    if(redsTurn){
                        if(!redChecker[clickedChecker].king){
                            //red
                            if((newy - redChecker[clickedChecker].location.row == 1) && (Math.abs(newx - redChecker[clickedChecker].location.column)==1)) {
                                return true;
                            }
                            if(((newy - redChecker[clickedChecker].location.row == 2) && (Math.abs(newx - redChecker[clickedChecker].location.column)==2)) && (jumpedChecker(redChecker, whiteChecker, oldx, oldy, newx - oldx, newy - oldy, redsTurn))) {
                                jump(redChecker, whiteChecker, redsTurn, scores);
                                return true;
                            }
                        } else if (redChecker[clickedChecker].king){
                            //red king
                            if((Math.abs(newy - redChecker[clickedChecker].location.row) == 1) && (Math.abs(newx - redChecker[clickedChecker].location.column)==1)) {
                                return true;
                            }
                            if(((Math.abs(newy - redChecker[clickedChecker].location.row) == 2) && (Math.abs(newx - redChecker[clickedChecker].location.column)==2)) && (jumpedChecker(redChecker, whiteChecker, oldx, oldy, newx - oldx, newy - oldy, redsTurn))) {
                                jump(redChecker, whiteChecker, redsTurn, scores);
                                return true;
                            }
                        }
                    } else if (!redsTurn) {
                        if(!whiteChecker[clickedChecker].king){
                            //white
                            if((newy - whiteChecker[clickedChecker].location.row == -1) && (Math.abs(newx - whiteChecker[clickedChecker].location.column)==1)) {
                                return true;
                            }
                            if(((newy - whiteChecker[clickedChecker].location.row == -2) && (Math.abs(newx - whiteChecker[clickedChecker].location.column)==2)) && (jumpedChecker(redChecker, whiteChecker, oldx, oldy, newx - oldx, newy - oldy, redsTurn))) {
                                jump(redChecker, whiteChecker, redsTurn, scores);
                                return true;
                            }
                        } else if (whiteChecker[clickedChecker].king){
                            //white king
                            if((Math.abs(newy - whiteChecker[clickedChecker].location.row) == 1) && (Math.abs(newx - whiteChecker[clickedChecker].location.column)==1)) {
                                return true;
                            }
                            if(((Math.abs(newy - whiteChecker[clickedChecker].location.row) == 2) && (Math.abs(newx - whiteChecker[clickedChecker].location.column)==2)) && (jumpedChecker(redChecker, whiteChecker, oldx, oldy, newx - oldx, newy - oldy, redsTurn))) {
                                jump(redChecker, whiteChecker, redsTurn, scores);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}