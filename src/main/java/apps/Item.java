/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;

/**
 *
 * @author koduki
 */
public class Item {

    public String id;
    public String name;
    public String type;
    public String tags;
    public String description;
    public String url;

    public String details;

    public Item(String id, String name, String type, String tags, String description, String url, String details) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.tags = tags;
        this.description = description;

        this.url = url;
        this.details = details;
    }

}
