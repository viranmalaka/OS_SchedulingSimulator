/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author malaka
 */
public class PicLable extends JLabel {
    
    private Colors col;
    private String processId;
    
    public PicLable(String processId, Colors col) {
        this.col = col;
        this.processId = processId;
        this.setIcon(getIcon(col));
        this.setSize(getIcon().getIconWidth(), getIcon().getIconHeight());
        this.setToolTipText("<HTML><font color=\"red\" size = 20 >Process : " + processId + "</font></HTML>");
    }

    /**
     * @return the col
     */
    public Colors getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(Colors col) {
        this.col = col;
    }

    /**
     * @return the processId
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }
    
    public static enum Colors {
        Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8
    }
    
    public static ImageIcon getIcon(Colors col){
        return new ImageIcon(PicLable.class.getResource("/pics/" + col.toString().toLowerCase() + ".jpg"));
    }
}
