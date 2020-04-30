package Global;

import java.awt.*;

public class Tools{
    public final static int JAUNE = 0;
    public final static int ROUGE = 1;
    public final static int BLANC = 2;
    public final static int NOIR = 3;

    public static enum Dir {
        NO,
        NE,
        SE,
        SO,
        RIEN;
    }

    public static int DirToInt(Dir d){
        switch (d){
            case NO:return 0;
            case NE:return 1;
            case SE:return 2;
            case SO:return 3;
        }
        return 4;
    }
    public static Dir IntToDir(int n){
        switch (n){
            case 0:return Dir.NO;
            case 1:return Dir.NE;
            case 2:return Dir.SE;
            case 3:return Dir.SO;
        }
        return Dir.RIEN;
    }

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

    public static Dir InverseDir(Dir d){
        switch (d){
            case NO: return Dir.SE;
            case NE: return Dir.SO;
            case SO: return Dir.NE;
            case SE: return Dir.NO;
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