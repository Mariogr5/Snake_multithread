package org.example.Snake;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GraphicsGenerator {

    public static ArrayList<Rectangle> generateSquares(ArrayList<Point> points)
    {
        ArrayList<Rectangle> listOfObjects = new ArrayList<>();
        for(Point point: points){
            listOfObjects.add(generateSquare(point.x, point.y, Settings.UNIT_SIZE));
        }
        return  listOfObjects;
    }

    public static Ellipse2D.Double generateApple(Point point){

        return generateCircle(point.x, point.y, Settings.UNIT_SIZE);
    }

    public static Rectangle generateSquare(int centerX, int centerY, int size) {

        return new Rectangle(centerX, centerY, size, size);
    }

    public static Ellipse2D.Double generateCircle(int centerX, int centerY, int size) {

        return new Ellipse2D.Double(centerX, centerY, size, size);
    }

}
