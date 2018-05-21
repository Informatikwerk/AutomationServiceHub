package com.codingisthinking.hub.service;

import com.codingisthinking.hub.domain.Sources;
import com.codingisthinking.hub.repository.SourcesRepository;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DownloadService {

    private final SourcesRepository sourcesRepository;

    public DownloadService(SourcesRepository sourcesRepository) {
        this.sourcesRepository = sourcesRepository;
    }

    public Sources downloadSources(Long id){
        System.out.println("Starts download process.");
        Velocity.init();

        VelocityContext context = new VelocityContext();

        context.put( "name", new String("Velocity") );

        Template template = null;
        System.out.println("Ends download process.");
        return sourcesRepository.findOne(id);
    }
}
