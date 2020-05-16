package Global;

import java.lang.reflect.Field;
import java.lang.ClassLoader;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;

//À revoir → Faire comme le sokoban
public final class Configuration {
    static volatile Configuration instance = null;
    public static Configuration instance(){
        if(instance == null)
            instance = new Configuration();
        return instance;
    }
    
    private Configuration(){
        super();
    }

    /** 
     * Liste des paramètres configurables et sauvegardable
     * A faire correspondre avec la class Properties pour la sauvegarde
     */
    private static String LogLevel = "WARNING";
    private static Boolean Maximized = false;
    private static Boolean Animations = true;
    private static int Taille = 5;
    private static int Joueurs = 2;
    private static String Tile1 = "Tiles/tile_1.png";
    private static String Tile2 = "Tiles/tile_2.png";
    private static String Tile3 = "Tiles/tile_3.png";
    private static String Tile4 = "Tiles/tile_4.png";
    private static String SelectedTile = "Tiles/selected_tile.png";
    private static String ArrowLeft = "Arrows/arrow_left.png";
    private static String ArrowUp = "Arrows/arrow_up.png";
    private static String ArrowDown = "Arrows/arrow_down.png";
    private static String ArrowRight = "Arrows/arrow_right.png";
    private static String DefaultMarble = "Marbles/default_marble.png";
    private static String Background = "Tiles/background.png";
    private static String SelectedActions = "Tiles/selected_actions.png";
    
    
    

    public static Boolean write(String S, Object valeur) {
        Field[] fields = instance().getClass().getDeclaredFields();
        for(Field field : fields){
            if(S.equals(field.getName())){
                try{field.set(instance,valeur);}
                catch(Exception e){
                    try{field.set(instance,Boolean.parseBoolean(valeur.toString()));}
                    catch(Exception e2){
                        try{field.set(instance,Integer.parseInt(valeur.toString()));}
                        catch(Exception e3){
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        throw new NoSuchElementException("Element de configuration non trouvé");
    }

    public static Object read(String S)  {
        Field[] fields = instance().getClass().getDeclaredFields();
        for(Field field : fields){
            if(S.equals(field.getName()))
                try{return field.get(instance);}
                catch(Exception e){Tools.Print(e);}
        }
        throw new NoSuchElementException("Element de configuration non trouvé");
    }

    public static Logger logger (){
        Logger l = Logger.getLogger("QuitsLogger");
        l.setLevel((Level.parse(LogLevel)));
        return l;
    }

    public static InputStream charge(String path){
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        return stream;
    }

}