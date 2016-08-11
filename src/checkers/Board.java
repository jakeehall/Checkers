package checkers;

import java.awt.Color;
import objectdraw.DrawingCanvas;
import objectdraw.FilledOval;
import objectdraw.FilledRect;

public class Board {
    DrawingCanvas canvas;
    
    Board() {
        System.out.println("Canvas was not passed to board class.");
    }
    
    Board(DrawingCanvas _canvas) {
        canvas = _canvas;
    }
    
    void createBoard()
    {
        Settings settings = new Settings();
        FilledRect checkerSquare;
        for (int x = 1; x <= settings.boardColumns; x++)
        {
            for (int y = 1; y <= settings.boardRows; y++)
            {
                checkerSquare = new FilledRect(40*(x-1), 40*(y-1), 40, 40, canvas);
                if (y%2 == 1)
                {
                    if (x%2 == 1)
                        checkerSquare.setColor(Color.black);
                    else
                        checkerSquare.setColor(Color.red);
                }
                else
                {
                    if (x%2 == 1)
                        checkerSquare.setColor(Color.red);
                    else
                        checkerSquare.setColor(Color.black);
                }
            }
        }
    }
    
    void resetCheckerLocations(Checker[] red, Checker[] white)
    {
        Settings settings = new Settings();
        int redCheckerCount = 0;
        int whiteCheckerCount = 0;
        for (int y = 0; y < 3; y++)
        {
            if (y%2 == 0)
            {
                for (int x = 0; x < settings.boardColumns; x++)
                {
                    if (x%2 == 0)
                    {
                        red[redCheckerCount].location.column = x;
                        red[redCheckerCount].location.row = y;
                        red[redCheckerCount].checker = new FilledOval(x*40, y*40, 40, 40, canvas);
                        red[redCheckerCount].checker.setColor(Color.red);
                        
                        white[whiteCheckerCount].location.column = settings.boardColumns-x-1;
                        white[whiteCheckerCount].location.row = settings.boardRows-y-1;
                        white[whiteCheckerCount].checker = new FilledOval((settings.boardColumns-x-1)*40, (settings.boardRows-y-1)*40, 40, 40, canvas);
                        white[whiteCheckerCount].checker.setColor(Color.white);
                        
                        redCheckerCount++;
                        whiteCheckerCount++;
                    }
                }
            }
            else
            {
                for (int x = 0; x < settings.boardColumns; x++)
                {
                    if (x%2 == 1)
                    {
                        red[redCheckerCount].location.column = x;
                        red[redCheckerCount].location.row = y;
                        red[redCheckerCount].checker = new FilledOval(x*40, y*40, 40, 40, canvas);
                        red[redCheckerCount].checker.setColor(Color.red);
                        
                        white[whiteCheckerCount].location.column = settings.boardColumns-x-1;
                        white[whiteCheckerCount].location.row = settings.boardRows-y-1;
                        white[whiteCheckerCount].checker = new FilledOval((settings.boardColumns-x-1)*40, (settings.boardRows-y-1)*40, 40, 40, canvas);
                        white[whiteCheckerCount].checker.setColor(Color.white);
                        
                        redCheckerCount++;
                        whiteCheckerCount++;
                    }
                }
            }
        }
    }
}