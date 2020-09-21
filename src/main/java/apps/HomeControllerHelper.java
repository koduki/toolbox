/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author koduki
 */
public class HomeControllerHelper {

    private Set<String> tagIds;

    public HomeControllerHelper() {
        var set = new HashSet<String>();
        this.tagIds = set;
    }

    public HomeControllerHelper(String tagsParam) {
        var set = new HashSet<String>();
        if (tagsParam != null) {
            for (String tag : tagsParam.split(",")) {
                if (!tag.trim().isBlank()) {
                    set.add(tag);
                }
            }
        }
        this.tagIds = set;
    }

    public Set<String> getCurrentTagIds() {
        return tagIds;
    }

    public String joing(String tagId) {
        var ids = new HashSet<String>(tagIds);
        ids.add(tagId);
        return String.join(",", ids);
    }

    public String remove(String tagId) {
        var ids = new HashSet<String>(tagIds);
        ids.remove(tagId);
        return String.join(",", ids);
    }
}
