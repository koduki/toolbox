/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.nklab.toolbox;

/**
 *
 * @author koduki
 */
public class Item {

    public String id;
    public String name;
    public String type;
    public String description;

    public Item(String id, String name, String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

}
