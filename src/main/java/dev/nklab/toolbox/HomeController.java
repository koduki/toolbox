package dev.nklab.toolbox;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ws.rs.QueryParam;

@Path("/")
public class HomeController {

    @PostConstruct
    public void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            var credentials = GoogleCredentials.getApplicationDefault();

            System.out.println(credentials.getAccessToken());
            var options = new FirebaseOptions.Builder()
                    .setProjectId("toolbox-289508")
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        }
    }

    @CheckedTemplate
    public static class Templates {

        public static native TemplateInstance index(List<Tag> tags, TagHelper tagHelper);

        public static native TemplateInstance list(List<Tag> tags, List<Tag> selectedTags, List<Item> items, TagHelper tagHelper);

        public static native TemplateInstance show(Item item);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public TemplateInstance get(@QueryParam("tags") String tagsParam) throws InterruptedException, ExecutionException {
        var db = FirestoreClient.getFirestore();
        var tags = getTags();

        return Templates.index(tags, new TagHelper(tagsParam));
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/list")
    public TemplateInstance list(@QueryParam("tags") String tagsParam) throws IOException, InterruptedException, ExecutionException {
        var db = FirestoreClient.getFirestore();
        var tags = getTags();
        var tagHelper = new TagHelper(tagsParam);
        var query = findByContainKey(db.collection("Item"), "tag2", tagHelper.getCurrentTagIds());
        var items = query.get().getDocuments().stream()
                .map(item -> new Item(item.getId(), item.getString("Name"), "SaaS", "no detail"))
                .collect(Collectors.toList());

        var selectedTags = getTags(tagHelper.getCurrentTagIds());

        return Templates.list(tags, selectedTags, items, new TagHelper(tagsParam));
    }

    ApiFuture<QuerySnapshot> findByContainKey(CollectionReference col, String property, Set<String> keys) {
        Query query = null;
        for (var key : keys) {
            var name = property + "." + key;
            query = (query == null) ? col.whereEqualTo(name, true) : query.whereEqualTo(name, true);
        }

        return query.get();
    }

    List<Tag> getTags(Set<String> tagIds) throws InterruptedException, ExecutionException {
        var db = FirestoreClient.getFirestore();
        var query = db.collection("Tag").get();
        var tags = query.get().getDocuments().stream()
                .map(r -> new Tag(r.getId(), r.getString("name")))
                .filter(x -> tagIds.contains(x.id))
                .collect(Collectors.toList());
        return tags;
    }

    List<Tag> getTags() throws InterruptedException, ExecutionException {
        var db = FirestoreClient.getFirestore();

        var query = db.collection("Tag").get();
        var tags = query.get().getDocuments().stream().map(r -> new Tag(r.getId(), r.getString("name"))).collect(Collectors.toList());
        return tags;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/show")
    public TemplateInstance show(@QueryParam("id") String id) {
        return Templates.show(new Item("hogehoge", "", "", ""));
    }
}
