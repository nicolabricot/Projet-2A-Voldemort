/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quickanddirtymapeditor;

import java.awt.Component;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author bruno
 */
public class MainWindow extends javax.swing.JFrame {

    private HashMap<String, Polygon> polygons;
    private Map map;

    class ImageFilter extends FileFilter {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";

    /*
     * Get the extension of a file.
     */  
    public String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
        
        
        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }

            String extension = this.getExtension(f);
            if (extension != null) {
                if (extension.equals(ImageFilter.tiff)
                        || extension.equals(ImageFilter.tif)
                        || extension.equals(ImageFilter.gif)
                        || extension.equals(ImageFilter.jpeg)
                        || extension.equals(ImageFilter.jpg)
                        || extension.equals(ImageFilter.png)) {
                    return true;
                } else {
                    return false;
                }
            }

            return false;
        }

        @Override
        public String getDescription() {
            return "Image (tiff, tif, gif, jpeg, jpg, png)";
        }
    }

    class ClicListener implements MouseListener {

        private Component parent;

        ClicListener(Component parent) {
            this.parent = parent;
        }

        public void mouseClicked(MouseEvent me) {
            if (polygons.isEmpty()) {
                jButtonNewPathActionPerformed(null);
            }
            map.addPoint(me.getX(), me.getY());
        }

        public void mousePressed(MouseEvent me) {
        }

        public void mouseReleased(MouseEvent me) {
        }

        public void mouseEntered(MouseEvent me) {
        }

        public void mouseExited(MouseEvent me) {
        }
    }

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();

        this.polygons = new HashMap<String, Polygon>();

        this.map = new Map();
        this.map.addMouseListener(new ClicListener(this));
        this.jPanel4.add(this.map);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButtonGetJSON = new javax.swing.JButton();
        jButtonNewPath = new javax.swing.JButton();
        jButtonImage = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(840, 650));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        jButtonGetJSON.setText("Get json");
        jButtonGetJSON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetJSONActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonGetJSON);

        jButtonNewPath.setText("New path");
        jButtonNewPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewPathActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonNewPath);

        jButtonImage.setText("Background image");
        jButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImageActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonImage);

        jPanel2.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));
        jPanel2.add(jPanel4);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewPathActionPerformed
        this.polygons.put((String) JOptionPane.showInputDialog(this, "Path name", "New path", JOptionPane.QUESTION_MESSAGE), this.map.newPolygon());
    }//GEN-LAST:event_jButtonNewPathActionPerformed

    private void jButtonGetJSONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetJSONActionPerformed


        StringBuilder str = new StringBuilder();
        str.append("{");


        Iterator<Entry<String, Polygon>> it = this.polygons.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Polygon> e = it.next();

            str.append("\n\t\"");
            str.append(e.getKey());
            str.append("\" : \"");

            Polygon p = e.getValue();
            for (int i = 0; i < p.npoints; i++) {
                if (i == 0) {
                    str.append("M ");
                }

                str.append((float) p.xpoints[i]);
                str.append(",");
                str.append((float) p.ypoints[i]);

                if (i < p.npoints - 1) {
                    str.append(" L ");
                } else {
                    str.append(" z ");
                }
            }


            str.append("\"");
            if (it.hasNext()) {
                str.append(",");
            }
        }
        str.append("\n}");

        Toolkit toolKit = Toolkit.getDefaultToolkit();
        Clipboard cb = toolKit.getSystemClipboard();
        cb.setContents(new StringSelection(str.toString()), null);

        JOptionPane.showMessageDialog(this, "Json copied to clipboard.", "JSON", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButtonGetJSONActionPerformed

    private void jButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImageActionPerformed
        JFileChooser fc = new JFileChooser();

        fc.addChoosableFileFilter(new ImageFilter());
        fc.setAcceptAllFileFilterUsed(false);
        
        int returnVal = fc.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Image image = getToolkit().getImage(file.getAbsolutePath());
            if (image != null) {
                this.map.setImage(image);
            }
        }


    }//GEN-LAST:event_jButtonImageActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonGetJSON;
    private javax.swing.JButton jButtonImage;
    private javax.swing.JButton jButtonNewPath;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}