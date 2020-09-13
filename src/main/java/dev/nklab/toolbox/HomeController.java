package dev.nklab.toolbox;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import java.math.BigDecimal;
import javax.ws.rs.QueryParam;

@Path("/")
public class HomeController {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance hello(); 
        public static native TemplateInstance index(Item item);
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public TemplateInstance get(@QueryParam("name") String name) {
        return Templates.index(new Item("hogehoge", BigDecimal.ONE));
    }
}