package org.example.Snake;

public class Point {
    Point(int X, int Y){
        x = X;
        y = Y;
    }
    public int x;
    public int y;


    public boolean isColision(Point anotherPoint)
    {
        int colisions = 0;
        if(Math.abs(x - anotherPoint.x) <= Settings.UNIT_SIZE/2)
            colisions++;
        if(Math.abs(y - anotherPoint.y) <= Settings.UNIT_SIZE/2)
            colisions++;

        if (colisions == 2)
            return true;
        return false;
    }
}
