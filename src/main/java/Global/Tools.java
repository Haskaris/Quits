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

    /**
     * Énumération des directions
     */
    public static enum Dir {
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

    /**
     * DEPRECATED
     * @param d
     * @return 
     */
    public static int DirToInt(Dir d){
        switch (d){
            case NO:return 0;
            case NE:return 1;
            case SE:return 2;
            case SO:return 3;
        }
        return 4;
    }
    
    /**
     * DEPRECATED
     * @param n
     * @return 
     */
    public static Dir IntToDir(int n){
        switch (n){
            case 0:return Dir.NO;
            case 1:return Dir.NE;
            case 2:return Dir.SE;
            case 3:return Dir.SO;
        }
        return Dir.RIEN;
    }

    /**
     * DEPRECATED
     * @param d
     * @return 
     */
    public static Point DirToPoint(Dir d){
        int x = 0,y = 0;
        switch (d){
            case NO: y--; x--; break;
            case NE: y--; x++; break;
            case SO: y++; x--; break;
            case SE: y++; x++; break;
        }
        return new Point(x,y);
    }

    /**
     * DEPRECATED
     * @param p
     * @return 
     */
    public static Dir PointToDir(Point p){
        if(p.x == -1 && p.y == -1)
            return Dir.NO;
        if(p.x == 1 && p.y == -1)
            return Dir.NE;
        if(p.x == -1 && p.y == 1)
            return Dir.SO;
        if(p.x == 1 && p.y == 1)
            return Dir.SE;
        return Dir.RIEN;
    }

    /**
     * Retourne la direction inverse de d
     * @param d
     * @return Dir
     */
    public static Dir reverse(Dir d){
        switch (d){
            case N: return Dir.S;
            case NE: return Dir.SO;
            case E: return Dir.O;
            case SE: return Dir.NO;
            case S: return Dir.N;
            case SO: return Dir.NE;
            case O: return Dir.E;
            case NO: return Dir.SE;
        }
        return Dir.RIEN;
    }

    public static void Print(String S){
        System.out.println(S);
    }
    public static void Print(int n){
        System.out.println(Integer.toString(n));
    }
    public static void Print(Exception e){
        System.out.println(e.toString());
    }
    public static void Print(Object o){
        System.out.println(o.toString());
    }
}