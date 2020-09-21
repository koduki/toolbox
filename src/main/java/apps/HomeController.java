package apps;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/")
public class HomeController {

    @Inject
    ToolboxService toolboxService;

    @CheckedTemplate
    public static class Templates {

        public static native TemplateInstance index(HomeControllerHelper helper, List<Tag> tags);

        public static native TemplateInstance list(HomeControllerHelper helper, List<Tag> tags, Set<String> selectedTags, List<Item> items);

        public static native TemplateInstance show(HomeControllerHelper helper, List<Tag> tags, Item item);

        public static native TemplateInstance create(HomeControllerHelper helper, List<Tag> tags);

        public static native TemplateInstance edit(HomeControllerHelper helper, List<Tag> tags, Item item);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public TemplateInstance get(@QueryParam("tags") String tagsParam) throws InterruptedException, ExecutionException {
        return Templates.index(new HomeControllerHelper(tagsParam), toolboxService.getTags());
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/list")
    public TemplateInstance list(@QueryParam("tags") String tagsParam) throws IOException, InterruptedException, ExecutionException {
        var tagHelper = new HomeControllerHelper(tagsParam);
        var items = toolboxService.findItems(tagHelper.getCurrentTagIds());
        var selectedTags = tagHelper.getCurrentTagIds();
        return Templates.list(new HomeControllerHelper(tagsParam), toolboxService.getTags(), selectedTags, items);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/show/{id}")
    public TemplateInstance show(@PathParam("id") String id) throws InterruptedException, ExecutionException {
        var tags = toolboxService.getTags();
        return Templates.show(new HomeControllerHelper(), tags, toolboxService.getItem(id));
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/create")
    public TemplateInstance create() throws InterruptedException, ExecutionException {
        return Templates.create(new HomeControllerHelper(), toolboxService.getTags());
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/edit/{id}")
    public TemplateInstance edit(@PathParam("id") String id) throws InterruptedException, ExecutionException {
        return Templates.edit(new HomeControllerHelper(), toolboxService.getTags(), toolboxService.getItem(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/doCreate")
    public Response doCreate(
            @FormParam("name") String name,
            @FormParam("url") String url,
            @FormParam("type") String type,
            @FormParam("tags") String tags,
            @FormParam("description") String description,
            @FormParam("details") String details
    ) throws InterruptedException, ExecutionException, URISyntaxException {
        var result = toolboxService.createItem(tags, name, url, type, description, details);
        System.out.println("Update time : " + result.get().getUpdateTime());

        return Response
                .seeOther(new URI("/"))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/doUpdate")
    public Response doUpdate(
            @FormParam("id") String id,
            @FormParam("name") String name,
            @FormParam("url") String url,
            @FormParam("type") String type,
            @FormParam("tags") String tags,
            @FormParam("description") String description,
            @FormParam("details") String details
    ) throws InterruptedException, ExecutionException, URISyntaxException {
        var result = toolboxService.updateItem(tags, name, url, type, description, details, id);
        System.out.println("Update time : " + result.get().getUpdateTime());

        return Response
                .seeOther(new URI("/show/" + id))
                .build();
    }

}
