package Global;

import java.awt.*;

public class Tools {

    public static enum GameMode {
        TwoPlayersFiveBalls,
        TwoPlayersThreeBalls,
        FourPlayersFiveBalls,
    }

    /**
     * Énumération des niveaux d'ia
     */
    public static enum AILevel {
        Player,
        Easy,
        Medium,
        Hard,
    }

    /**
     * Énumération des états du joueur Un joueur en MarbleSelection attend qu'on
     * clique sur une bille Un joueur en ActionSelection attend qu'on choisisse
     * une action liée à la bille sélectoinnée
     */
    public enum PlayerStatus {
        MarbleSelection,
        ActionSelection,
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
        SW,
        W,
        NW,
        NODIR;
    }

    public static Point getNextPoint(Point p, Direction d) {
        Point tmp = new Point();
        switch (d) {
            case NW:
                tmp.x = p.x - 1;
                tmp.y = p.y - 1;
                break;
            case NE:
                tmp.x = p.x + 1;
                tmp.y = p.y - 1;
                break;
            case SE:
                tmp.x = p.x + 1;
                tmp.y = p.y + 1;
                break;
            case SW:
                tmp.x = p.x - 1;
                tmp.y = p.y + 1;
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
            case NW:
                y--;
                x--;
                break;
            case NE:
                y--;
                x++;
                break;
            case SW:
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
                return Direction.SW;
            case E:
                return Direction.W;
            case SE:
                return Direction.NW;
            case S:
                return Direction.N;
            case SW:
                return Direction.NE;
            case W:
                return Direction.E;
            case NW:
                return Direction.SE;
        }
        return Direction.NODIR;
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

    public static Direction PointToDir(Point p) {
        if (p.x == 1 && p.y == 0) {
            return Direction.E;
        } else if (p.x == -1 && p.y == 0) {
            return Direction.W;
        } else if (p.x == -1 && p.y == -1) {
            return Direction.NW;
        } else if (p.x == 0 && p.y == -1) {
            return Direction.N;
        } else if (p.x == 1 && p.y == -1) {
            return Direction.NE;
        } else if (p.x == -1 && p.y == 1) {
            return Direction.SW;
        } else if (p.x == 0 && p.y == 1) {
            return Direction.S;
        } else if (p.x == 1 && p.y == 1) {
            return Direction.SE;
        } else {
            return Direction.NODIR;
        }
    }

    public static Point PointToPointDiff(Point start, Point end) {
        return new Point(end.x - start.x, end.y - start.y);
    }

    public static int findAppropriateCoordinatesForTileShifts(int value) {
        if (value > 4) {
            return 0;
        }
        if (value < 0) {
            return 4;
        }
        return value;

    }
}
