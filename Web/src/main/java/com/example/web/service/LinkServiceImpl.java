package com.example.web.service;

import com.example.web.models.Link;
import com.example.web.models.LinkDbModel;
import com.example.web.models.LinkDto;
import com.example.web.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService {
    private final LinkRepository repository;

    @Autowired
    public LinkServiceImpl(LinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public LinkDbModel createLink(Link link) {
        var linkModel = new LinkDbModel(link);
        repository.save(linkModel);

        return linkModel;
    }

    @Override
    public LinkDbModel getLinkById(int id) throws Exception {
        var link = repository.findById(id);
        if (link.isEmpty()){
            throw new Exception("not found");
        }

        return link.get();
    }

    @Override
    public LinkDbModel updateLink(LinkDto dto) throws Exception {
        repository.updateLinkById(dto.getId(), dto.getStatus());

        return getLinkById(dto.getId());
    }
}
