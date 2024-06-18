package org.example.Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Snake {

    public Snake()
    {
        lenght = 4;
        head = new Point(Settings.WINDOW_WIDTH/2, Settings.WINDOW_HEIGHT - (Settings.UNIT_SIZE/2));
        generateSnakeTail();
    }

    public int lenght = 4;
    public boolean isCollision = false;

    public Point head;
    public Point previousHead;

    public ArrayList<Point> Tail = new ArrayList<>();

    public String direction = "U";


    public void calc_direction(String KeyCode){

        if(Objects.equals(direction, "R") && Objects.equals(KeyCode, "L")){
            direction = "U";
            return;
        }
        if(Objects.equals(direction, "U") && Objects.equals(KeyCode, "L")){
            direction = "L";
            return;
        }
        if(Objects.equals(direction, "S") && Objects.equals(KeyCode, "L")){
            direction = "R";
            return;
        }
        if(Objects.equals(direction, "L") && Objects.equals(KeyCode, "L")){
            direction = "S";
            return;
        }




        if(Objects.equals(direction, "L") && Objects.equals(KeyCode, "R")){
            direction = "U";
            return;
        }
        if(Objects.equals(direction, "U") && Objects.equals(KeyCode, "R")){
            direction = "R";
            return;
        }
        if(Objects.equals(direction, "S") && Objects.equals(KeyCode, "R")){
            direction = "L";
        }
        if(Objects.equals(direction, "R") && Objects.equals(KeyCode, "R")){
            direction = "S";
        }

    }

    public boolean checkifAppleEarned(Point point)
    {
        return head.isColision(point);
    }
    public void generateSnakeTail(){
        Tail.clear();
        int initialX = head.x;
        int initialY = head.y;
        for(int i =0; i < lenght; i++)
        {
            Tail.add(new Point(initialX, initialY));
            initialX += Settings.UNIT_SIZE;
        }
    }


    public void generateSnakePart()
    {
        Tail.add(new Point(Tail.get(lenght - 1).x + Settings.UNIT_SIZE, Tail.get(lenght - 1).y + Settings.UNIT_SIZE));
        lenght++;
    }


    public void move(){

        previousHead = head;
        if(Objects.equals(direction, "R"))
            head = new Point(previousHead.x + Settings.UNIT_SIZE, previousHead.y);
        else if(Objects.equals(direction, "L"))
            head = new Point(previousHead.x - Settings.UNIT_SIZE, previousHead.y);
        else if(Objects.equals(direction, "U"))
            head = new Point(previousHead.x, previousHead.y - Settings.UNIT_SIZE);
        else if(Objects.equals(direction, "S"))
            head = new Point(previousHead.x, previousHead.y + Settings.UNIT_SIZE);

        if(head.x >= Settings.WINDOW_WIDTH)
            head = new Point(0, head.y);
        if(head.y >= Settings.WINDOW_HEIGHT)
            head = new Point(head.x, 0);
        if(head.x < 0)
            head = new Point(Settings.WINDOW_WIDTH, head.y);
        if(head.y < 0)
            head = new Point(head.x, Settings.WINDOW_HEIGHT);

        int i = lenght - 1;

        for(Point point: Tail)
        {
            if(i == 0)
            {
                Tail.set(i,head);
            }
            else {
                Tail.set(i,Tail.get(i - 1));
                i--;
            }
        }
    }
}
