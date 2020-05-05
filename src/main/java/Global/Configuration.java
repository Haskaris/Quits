package Global;

import java.lang.reflect.Field;
import java.lang.ClassLoader;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;


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

    /** Liste des paramètres configurables et sauvegardable
     *  A faire correspondre avec la class Properties pour la sauvegarde
     */
    private static String LogLevel = "WARNING";
    private static Boolean Maximized = false;
    private static Boolean Animations = true;
    private static int Taille = 5;
    private static int Joueurs = 2;
    private static String Tuile1 = "TuilesIHM/tuile_couleur_1.png";
    private static String Tuile2 = "TuilesIHM/tuile_couleur_2.png";
    private static String Tuile3 = "TuilesIHM/tuile_couleur_3.png";
    private static String Tuile4 = "TuilesIHM/tuile_couleur_4.png";
    private static String Tuile5 = "TuilesIHM/tuile_couleur_5.png";
    private static String Tuile6 = "TuilesIHM/tuile_couleur_6.png";
    private static String Tuile7 = "TuilesIHM/tuile_couleur_7.png";
    private static String Tuile8 = "TuilesIHM/tuile_couleur_8.png";

    public static Boolean Ecris(String S, Object valeur) {
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

    public static Object Lis(String S)  {
        Field[] fields = instance().getClass().getDeclaredFields();
        for(Field field : fields){
            if(S.equals(field.getName()))
                try{return field.get(instance);}
                catch(Exception e){Tools.Print(e);}
        }
        throw new NoSuchElementException("Element de configuration non trouvé");
    }


    public static Logger logger (){
        Logger l = Logger.getLogger("SokobanLogger");
        l.setLevel((Level.parse(LogLevel)));
        return l;

    }

    public static InputStream charge(String path){
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        return stream;
    }

}