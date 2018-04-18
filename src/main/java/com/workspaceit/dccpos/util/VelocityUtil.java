package com.workspaceit.dccpos.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

/**
 * Created by mi on 12/29/16.
 */
@Component
public class VelocityUtil {
    private VelocityEngine velocityEngine;

    @Autowired
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public String getHtmlByTemplateAndContext(String templateName, VelocityContext context){

        VelocityEngine ve = this.velocityEngine;

        Template template = ve.getTemplate(templateName);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
}
