package Global;

public class Tools{

    public static final int MACRO = 35;


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