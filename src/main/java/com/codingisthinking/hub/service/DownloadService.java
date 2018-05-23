package com.codingisthinking.hub.service;

import com.codingisthinking.hub.domain.LibraryRegistry;
import com.codingisthinking.hub.domain.Sources;
import com.codingisthinking.hub.repository.SourcesRepository;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.BitSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class DownloadService {

    private final SourcesRepository sourcesRepository;

    public DownloadService(SourcesRepository sourcesRepository) {
        this.sourcesRepository = sourcesRepository;
    }

    public byte[] getZip(List<Sources> sourcesList) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(bos);
        for(Sources sourceFile:sourcesList){
            ZipEntry e = new ZipEntry(sourceFile.getFileName());
            out.putNextEntry(e);
            byte[] data = sourceFile.getSourceCode().getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();
        }
        out.close();
        bos.close();
        return bos.toByteArray();
    }
}
