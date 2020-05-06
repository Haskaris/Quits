package Global;

import java.awt.*;

public class Tools {

    /**
     * Énumération des niveaux d'ia
     */
    public static enum AILevel {
        Player,
        Easy,
        Medium,
        Hard,
    }

    //Après audit : On pourrait le mettre en vecteur xy
    //Ex: Nord = 0;-1
    //    Sud = 0;+1
    //    SudOuest = -1;+1
    /**
     * Énumération des directions
     */
    public static enum Direction {
        N,
        NE,
        E,
        SE,
        S,
        SO,
        O,
        NO,
        RIEN;
    }

    public static Point getNextPoint(Point p, Direction d) {
        Point tmp = new Point();
        switch (d) {
            case NO:
                tmp.x = p.x - 1;
                tmp.y = p.y + 1;
                break;
            case NE:
                tmp.x = p.x + 1;
                tmp.y = p.y + 1;
                break;
            case SE:
                tmp.x = p.x + 1;
                tmp.y = p.y - 1;
                break;
            case SO:
                tmp.x = p.x - 1;
                tmp.y = p.y - 1;
                break;
        }
        return tmp;
    }

    /**
     * @param d
     * @return
     */
    public static Point DirToPoint(Direction d) {
        int x = 0, y = 0;
        switch (d) {
            case NO:
                y--;
                x--;
                break;
            case NE:
                y--;
                x++;
                break;
            case SO:
                y++;
                x--;
                break;
            case SE:
                y++;
                x++;
                break;
        }
        return new Point(x, y);
    }

    /**
     * Retourne la direction inverse de d
     *
     * @param d
     * @return Direction
     */
    public static Direction reverse(Direction d) {
        switch (d) {
            case N:
                return Direction.S;
            case NE:
                return Direction.SO;
            case E:
                return Direction.O;
            case SE:
                return Direction.NO;
            case S:
                return Direction.N;
            case SO:
                return Direction.NE;
            case O:
                return Direction.E;
            case NO:
                return Direction.SE;
        }
        return Direction.RIEN;
    }

    public static void Print(String S) {
        System.out.println(S);
    }

    public static void Print(int n) {
        System.out.println(Integer.toString(n));
    }

    public static void Print(Exception e) {
        System.out.println(e.toString());
    }

    public static void Print(Object o) {
        System.out.println(o.toString());
    }
}
