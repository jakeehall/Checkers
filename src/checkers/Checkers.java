/**
 * @author Jacob Hall
 */

package checkers; 

import objectdraw.FrameWindowController;
import objectdraw.Location;
import objectdraw.Text;

public class Checkers extends FrameWindowController {
    Checker[] redChecker = new Checker[12];
    Checker[] whiteChecker = new Checker[12];
    boolean redsTurn = true;
    int clickedChecker = -1;
    Location oldPoint, dragPoint, newPoint;
    Text turnText, score;
    int[] scores = new int[2];
    
    public Checkers()
    {
        Settings settings = new Settings();
        setSize((settings.boardColumns * 40), (settings.boardRows * 40) + 84);
        Board board = new Board(canvas);
        board.createBoard();
        turnText = new Text("It's Reds Turn!", 6, (settings.boardRows * 40) + 2, canvas);
        scores[0] = redChecker.length;
        scores[1] = whiteChecker.length;
        score = new Text("Reds: "+scores[0]+"  Whites: "+scores[1], 6, (settings.boardRows * 40) + 20, canvas);
        for (int i=0; i<redChecker.length; i++) {
            redChecker[i]=new Checker();
            whiteChecker[i]=new Checker();
        }
        board.resetCheckerLocations(redChecker, whiteChecker);
    }
    
    @Override
    public void onMousePress(Location pt) {
        Move move = new Move();
        oldPoint = pt;
        dragPoint = pt;
        if(redsTurn && move.containsPoint(redChecker, pt) >= 0) {
            clickedChecker = move.containsPoint(redChecker, pt);
        } else if (!redsTurn && move.containsPoint(whiteChecker, pt) >=0) {
            clickedChecker = move.containsPoint(whiteChecker, pt);
        } else {
            clickedChecker = -1;
        }
    }
    
    @Override
    public void onMouseDrag(Location pt) {
        if(clickedChecker >=0) {
            if(redsTurn) {
                redChecker[clickedChecker].checker.move(pt.getX() - dragPoint.getX(), pt.getY() - dragPoint.getY());
                if(redChecker[clickedChecker].king) {
                    redChecker[clickedChecker].kingText.move(pt.getX() - dragPoint.getX(), pt.getY() - dragPoint.getY());
                }
                dragPoint = pt;
            } else if (!redsTurn) {
                whiteChecker[clickedChecker].checker.move(pt.getX() - dragPoint.getX(), pt.getY() - dragPoint.getY());
                if(whiteChecker[clickedChecker].king) {
                    whiteChecker[clickedChecker].kingText.move(pt.getX() - dragPoint.getX(), pt.getY() - dragPoint.getY());
                }
                dragPoint = pt;
            }
        }
    }
    
    @Override
    public void onMouseRelease(Location pt) {
        Rules rules = new Rules();
        newPoint = pt;
        if(clickedChecker >=0)
        {                
            if(rules.validMove(redChecker, whiteChecker, redsTurn, clickedChecker, oldPoint, newPoint, scores)) {
                int newx = (int)(newPoint.getX()/40);
                int newy = (int)(newPoint.getY()/40);
                if(redsTurn) {
                    redChecker[clickedChecker].location.column = newx;
                    redChecker[clickedChecker].location.row = newy;
                    redChecker[clickedChecker].checker.moveTo(newx*40, newy*40);
                    if(newy == rules.settings.boardRows-1 && !redChecker[clickedChecker].king) {
                        redChecker[clickedChecker].king=true;
                        redChecker[clickedChecker].kingText = new Text("K", redChecker[clickedChecker].location.column*40+16, redChecker[clickedChecker].location.row*40+12, canvas);
                    }
                    if(redChecker[clickedChecker].king) {
                        redChecker[clickedChecker].kingText.moveTo(redChecker[clickedChecker].location.column*40+16, redChecker[clickedChecker].location.row*40+12);
                    }
                } else if (!redsTurn) {
                    whiteChecker[clickedChecker].location.column = newx;
                    whiteChecker[clickedChecker].location.row = newy;
                    whiteChecker[clickedChecker].checker.moveTo(newx*40, newy*40);
                    if(newy == 0 && !whiteChecker[clickedChecker].king) {
                        whiteChecker[clickedChecker].king=true;
                        whiteChecker[clickedChecker].kingText = new Text("K", whiteChecker[clickedChecker].location.column*40+16, whiteChecker[clickedChecker].location.row*40+12, canvas);
                    }
                    if(whiteChecker[clickedChecker].king) {
                        whiteChecker[clickedChecker].kingText.moveTo(whiteChecker[clickedChecker].location.column*40+16, whiteChecker[clickedChecker].location.row*40+12);
                    }
                }
                //check for double jump
                if(scores[0] != 0 && scores[1] !=0) {
                    score.setText("Reds: "+scores[0]+"  Whites: "+scores[1]);
                } else {
                    if(scores[0] == 0)
                        score.setText("Game Over, White Wins!");
                    else if(scores[1] == 0)
                        score.setText("Game Over, Red Wins!");
                }
                clickedChecker = -1;
                redsTurn = !redsTurn;
            } else {
                int oldx = (int)(oldPoint.getX()/40);
                int oldy = (int)(oldPoint.getY()/40);
                if(redsTurn) {
                    redChecker[clickedChecker].checker.moveTo(oldx*40, oldy*40);
                } else if (!redsTurn) {
                    whiteChecker[clickedChecker].checker.moveTo(oldx*40, oldy*40);
                }
            }
            if(redsTurn)
                turnText.setText("It's Reds Turn!");
            else
                turnText.setText("It's Whites Turn!");
        }
    }
    
    public static void main(String[] args) {
        Checkers checkers = new Checkers();
    }
}