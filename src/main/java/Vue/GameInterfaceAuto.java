/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

/**
 *
 * @author Mathis
 */
public class GameInterfaceAuto extends javax.swing.JPanel {
    
    VuePlateau vuePlateau;
    
    
    /**
     * Creates new form GameInterfaceAuto
     */
    public GameInterfaceAuto(VuePlateau p) {
        initComponents();
        this.vuePlateau = p;
        this.jPanelPlateau.add(this.vuePlateau);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPlateau = new javax.swing.JLayeredPane();

        javax.swing.GroupLayout jPanelPlateauLayout = new javax.swing.GroupLayout(jPanelPlateau);
        jPanelPlateau.setLayout(jPanelPlateauLayout);
        jPanelPlateauLayout.setHorizontalGroup(
            jPanelPlateauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        jPanelPlateauLayout.setVerticalGroup(
            jPanelPlateauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addComponent(jPanelPlateau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPlateau, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jPanelPlateau;
    // End of variables declaration//GEN-END:variables
}