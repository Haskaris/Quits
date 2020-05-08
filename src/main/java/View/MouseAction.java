/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controleur.Mediator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Mathis
 */
public class MouseAction extends MouseAdapter {

    BoardGraphic board;
    Mediator mediator;

    MouseAction(BoardGraphic board, Mediator mediator) {
        this.board = board;
        this.mediator = mediator;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int l = ((e.getY() - (this.board.getHeightTile() / 2)) / this.board.getHeightTile());
        int c = ((e.getX() - (this.board.getHeightTile() / 2)) / this.board.getWidthTile());
        this.mediator.mouseClick(l, c);
        this.board.repaint();
    }
}
