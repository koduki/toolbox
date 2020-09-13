/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.nklab.toolbox;

import java.math.BigDecimal;

/**
 *
 * @author koduki
 */
public class Item {
    public String name;
    public BigDecimal price;

    public Item(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
    
}