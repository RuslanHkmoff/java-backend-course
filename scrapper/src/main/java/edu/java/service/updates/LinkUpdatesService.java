package edu.java.service.updates;

import java.util.List;

public interface LinkUpdatesService {
    List<LinkUpdateDto> getUpdatedLink(int size);
}
