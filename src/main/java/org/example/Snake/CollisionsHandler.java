package org.example.Snake;

import java.util.ArrayList;

public class CollisionsHandler {

    public static boolean detectSnakeCollision(ArrayList<Point> objects){
        for(Point point: objects)
        {
            for(Point point1: objects)
            {
                if(point1 != point){
                    if(point.isColision(point1))
                        return true;
                }

            }
        }
        return false;
    }

    public static boolean detectObjectsColision(ArrayList<Point> snakTail, ArrayList<Point> objects){
        for(Point point: snakTail)
        {
            for (Point object : objects) {
                if (point.isColision(object))
                    return true;
            }
        }
        return false;
    }
}
