package com.example.web.service;

import com.example.web.models.Link;
import com.example.web.models.LinkDbModel;
import com.example.web.models.LinkDto;

public interface LinkService {
    public LinkDbModel createLink(Link link);
    public LinkDbModel getLinkById(int id) throws Exception;
    public LinkDbModel updateLink(LinkDto dto) throws Exception;
}
