/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Paterns.Observateur;
import java.awt.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Mathis
 */
public abstract class BoardGraphic extends JComponent implements Observateur {

    Graphics2D drawable;

    protected ImageQuits readImage(InputStream in) throws IOException {
        return new ImageQuits(ImageIO.read(in));
    }

    protected void drawBall(Color c, int x, int y, int w, int h) {
        drawable.setPaint(c);
        double ratio = 0.565;

        x = (int) (x + (w * ((1 - ratio) / 2)));
        y = (int) (y + (h * ((1 - ratio) / 2)));
        w = (int) (w * ratio);
        h = (int) (h * ratio);

        drawable.fillOval(x, y, w, h);
        drawable.drawOval(x, y, w, h);
    }

    protected void drawRect(Color c, int x, int y, int l, int h) {
        drawable.setPaint(c);
        drawable.fillRect(x, y, l, h);
        drawable.drawRect(x, y, l, h);
    }

    protected void tracer(ImageQuits i, int x, int y, int l, int h) {
        drawable.drawImage(i.image(), x, y, l, h, null);
    }

    @Override
    public void paintComponent(Graphics g) {
        drawable = (Graphics2D) g;

        // On efface tout
        drawable.clearRect(0, 0, largeur(), hauteur());
        drawBoard();
    }

    public int hauteur() {
        return getHeight();
    }

    public int largeur() {
        return getWidth();
    }

    @Override
    public void miseAJour() {
        repaint();
    }

    // tracerNiveau est la partie indépendante de Swing du dessin qui se trouve dans le descendant
    abstract void drawBoard();

    abstract int getHeightTile();

    abstract int getWidthTile();

    // Méthodes pour les animations
    // décale un des éléments d'une fraction de case (pour les animations)
    public abstract void shift(int l, int c, double dl, double dc);

    // Changements du pousseur
    public abstract void updateDirection(int dL, int dC);

    public abstract void changeStep();
}
