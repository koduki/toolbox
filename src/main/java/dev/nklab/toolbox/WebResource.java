/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.nklab.toolbox;

import dev.nklab.toolbox.fw.Template;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author koduki
 */
@Path("/web")
public class WebResource {
    @Inject
    Template template;
    @Path("/index.html")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String goIndex() throws IOException, TemplateException, URISyntaxException {
        return template.view("/index", Map.of(
                "msg1", "Hello",
                "msg2", "World!"
        ));
    }
}
