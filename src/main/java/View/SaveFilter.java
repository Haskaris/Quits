/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Mathis
 */
public class SaveFilter extends FileFilter {
    
    @Override
    public boolean accept(File f) {
        String nameFile = null;
        String extension = null;
        int i = -1;
        if (f.isDirectory()) {
            return true;
        }
        nameFile = f.getName();
        i = nameFile.lastIndexOf('.');
        if(i > 0 &&  i < nameFile.length() - 1) {
            extension = nameFile.substring(i+1).toLowerCase();
        }
      return extension != null && extension.equals("save");
    }

    @Override
    public String getDescription() {
        return "Fichiers de sauvegardes (*.save)";
    }
    
}
