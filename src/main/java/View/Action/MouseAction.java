/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Action;

import Controleur.Mediator;
import View.BoardGraphic;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Mathis
 */
public class MouseAction extends MouseAdapter {

    BoardGraphic board;
    Mediator mediator;

    public MouseAction(BoardGraphic board, Mediator mediator) {
        this.board = board;
        this.mediator = mediator;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        float l = ((e.getY() - (this.board.getHeightTile() / 2.0f)) / this.board.getHeightTile());
        float c = ((e.getX() - (this.board.getHeightTile() / 2.0f)) / this.board.getWidthTile());
        if (l < 0) l = -1f;
        if (c < 0) c = -1f;
        
        
        this.mediator.mouseClick((int) c, (int) l);
        this.board.repaint();
    }
}
