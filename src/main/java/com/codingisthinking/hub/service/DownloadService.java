package com.codingisthinking.hub.service;

import com.codingisthinking.hub.domain.Sources;
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
            byte[] data = sourceFile.getSourceCode().getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();
        }
        out.close();
        bos.close();
        return bos.toByteArray();
    }
}
