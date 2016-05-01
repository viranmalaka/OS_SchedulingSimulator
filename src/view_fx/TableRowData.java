/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_fx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author malaka
 */
public class TableRowData {

    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty red;
    private SimpleDoubleProperty green;
    private SimpleDoubleProperty blue;
    
    private SimpleDoubleProperty opacity;

    public TableRowData(String id, String name, double red, double green, double blue,double opacity) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.red = new SimpleDoubleProperty(red);
        this.green = new SimpleDoubleProperty(green);
        this.blue = new SimpleDoubleProperty(blue);
        this.opacity = new SimpleDoubleProperty(opacity);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    /**
     * @return the red
     */
    public double getRed() {
        return red.get();
    }

    /**
     * @param red the red to set
     */
    public void setRed(double red) {
        this.red = new SimpleDoubleProperty(red);
    }

    /**
     * @return the green
     */
    public double getGreen() {
        return green.get();
    }

    /**
     * @param green the green to set
     */
    public void setGreen(double green) {
        this.green = new SimpleDoubleProperty(green);
    }

    /**
     * @return the blue
     */
    public double getBlue() {
        return blue.get();
    }

    /**
     * @param blue the blue to set
     */
    public void setBlue(double blue) {
        this.blue = new SimpleDoubleProperty(blue);
    }

    public SimpleDoubleProperty getOpacity() {
        return opacity;
    }

    public void setOpacity(SimpleDoubleProperty opacity) {
        this.opacity = opacity;
    }

    
}
