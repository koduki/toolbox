/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.nklab.toolbox.fw;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Map;
import javax.enterprise.context.Dependent;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 *
 * @author koduki
 */
@Dependent
public class Template {

    public String view(String name, Map<String, String> model) {
        var templateDir = ConfigProvider.getConfig().getOptionalValue("dev.nklab.toolbox.template.dir", String.class).orElse("");
        try {
            var config = new Configuration(Configuration.VERSION_2_3_23);
            config.setDirectoryForTemplateLoading(new File(getClass().getResource(templateDir).toURI()));

            var out = new StringWriter();
            var template = config.getTemplate(name + ".ftl");
            template.process(model, out);
            out.flush();

            return out.toString();
        } catch (TemplateException | IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
