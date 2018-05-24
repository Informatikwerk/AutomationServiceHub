package com.codingisthinking.hub.service;

import com.codingisthinking.hub.domain.Sources;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class DownloadService {

    public byte[] getZip(List<Sources> sourcesList) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(bos);
        for(Sources sourceFile:sourcesList){
            ZipEntry e = new ZipEntry(sourceFile.getFileName());
            out.putNextEntry(e);
            byte[] data = replacePlaceholderWithVelocity(sourceFile.getSourceCode(), "i1231312123").getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();
        }
        out.close();
        bos.close();
        return bos.toByteArray();
    }

    private String replacePlaceholderWithVelocity(String sourceCode, String realmKey) {
        // Initialize the engine.
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(Velocity.RESOURCE_LOADER, "string");
        engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        engine.addProperty("string.resource.loader.repository.static", "false");
        engine.init();

        // Initialize my template repository. You can replace the "Hello $w" with your String.
        StringResourceRepository repo = (StringResourceRepository) engine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
        repo.putStringResource("sourceCode", sourceCode);

        // Set parameters for my template.
        VelocityContext context = new VelocityContext();
        context.put("realmKey_holder",realmKey);

        // Get and merge the template with my parameters.
        Template template = engine.getTemplate("sourceCode");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        // Show the result.
        return writer.toString();
    }
}
