package org.example.Snake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Obstacle {

    public Obstacle(String direction, int Lenght, Point StartPosition){
        lenght = Lenght;
        moveDirection = direction;
        //obstacleParts.add(StartPosition);
        head = new Point(StartPosition.x, StartPosition.y);
        generate();
    }
    public Point location;

    public int lenght;

    public int maxDirectionMovies = 10;

    public int numberOfMovie = 0;
    public String moveDirection;

    public Point head;

    public Point previousHead;
    public ArrayList<Point> obstacleParts = new ArrayList<Point>();

    public void generate(){
        obstacleParts.clear();
        int initialX = head.x;
        int initialY = head.y;
        for(int i =0; i < lenght; i++)
        {
            obstacleParts.add(new Point(initialX, initialY));
            initialX += Settings.UNIT_SIZE;
        }
    }


    public void move(){

        if(numberOfMovie == maxDirectionMovies) {
            head = new Point(obstacleParts.get(lenght-1).x, obstacleParts.get(lenght-1).y);
            if(moveDirection == "U")
                moveDirection = "S";
            else if(moveDirection == "S")
                moveDirection = "U";
            else if(moveDirection == "R")
                moveDirection = "L";
            else
                moveDirection = "R";
            numberOfMovie = 0;
            Collections.reverse(obstacleParts);
            return;
        }
        previousHead = head;


        if(Objects.equals(moveDirection, "R"))
            head = new Point(previousHead.x + Settings.UNIT_SIZE, previousHead.y);
        else if(Objects.equals(moveDirection, "L"))
            head = new Point(previousHead.x - Settings.UNIT_SIZE, previousHead.y);
        else if(Objects.equals(moveDirection, "U"))
            head = new Point(previousHead.x, previousHead.y - Settings.UNIT_SIZE);
        else if(Objects.equals(moveDirection, "S"))
            head = new Point(previousHead.x, previousHead.y + Settings.UNIT_SIZE);






        if(head.x >= Settings.WINDOW_WIDTH)
            head = new Point(0, head.y);
        if(head.y >= Settings.WINDOW_HEIGHT)
            head = new Point(head.x, 0);
        if(head.x <= 0)
            head = new Point(Settings.WINDOW_WIDTH, head.y);
        if(head.y <= 0)
            head = new Point(head.x, Settings.WINDOW_HEIGHT);


        int i = lenght - 1;

        for(Point point: obstacleParts)
        {
            if(i == 0)
            {
                obstacleParts.set(i,head);
            }
            else {
                obstacleParts.set(i,obstacleParts.get(i - 1));
                i--;
            }
        }

        numberOfMovie++;
    }

}
