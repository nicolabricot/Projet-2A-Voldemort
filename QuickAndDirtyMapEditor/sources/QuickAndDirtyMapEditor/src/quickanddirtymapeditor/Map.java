/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quickanddirtymapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author bruno
 */
public class Map extends javax.swing.JPanel {

    private ArrayList<Polygon> polygons;
    private Polygon activePolygon;
    private Image image;

    /**
     * Creates new form Map
     */
    public Map() {
        initComponents();
        this.polygons = new ArrayList<Polygon>();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Color old = g.getColor();

        g.setColor(Color.BLACK);

        if (this.image != null) {
            int w = this.image.getWidth(this);
            int h = this.image.getHeight(this);

            if (w > h) {

                g.drawImage(this.image, 0, (this.getHeight() - this.getWidth() * h / w)/2, this.getWidth(), this.getWidth() * h / w, this);
            } else {
                g.drawImage(this.image, (this.getWidth() - this.getHeight() * w / h)/2, 0, this.getHeight() * w / h, this.getHeight(), this);
            }




        }



        Iterator<Polygon> it = this.polygons.iterator();
        while (it.hasNext()) {
            Polygon p = it.next();
            if (p.npoints == 1) {
                g.drawLine(p.xpoints[0] - 1, p.ypoints[0], p.xpoints[0] + 1, p.ypoints[0]);
                g.drawLine(p.xpoints[0], p.ypoints[0] - 1, p.xpoints[0], p.ypoints[0] + 1);
            } else {
                g.drawPolygon(p);
            }
        }

        g.setColor(old);


    }

    public void setImage(Image image) {
        this.image = image;
        this.repaint();
    }

    public void addPoint(int x, int y) {
        this.activePolygon.addPoint(x, y);
        this.repaint();
    }

    public Polygon newPolygon() {
        this.activePolygon = new Polygon();
        this.polygons.add(activePolygon);

        return this.activePolygon;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(520, 560));
        setMinimumSize(new java.awt.Dimension(520, 560));
        setPreferredSize(new java.awt.Dimension(520, 560));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
