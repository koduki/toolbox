package dev.nklab.toolbox;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.QueryParam;

@Path("/")
public class HomeController {

    @CheckedTemplate
    public static class Templates {

        public static native TemplateInstance index();

        public static native TemplateInstance list(List<String> tags);

        public static native TemplateInstance show(Item item);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public TemplateInstance get() {
        return Templates.index();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/list")
    public TemplateInstance list(@QueryParam("tags") String tags) {
        return Templates.list(Arrays.asList("cloud", "security"));
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/show")
    public TemplateInstance show(@QueryParam("id") String id) {
        return Templates.show(new Item("hogehoge", BigDecimal.ONE));
    }
}
