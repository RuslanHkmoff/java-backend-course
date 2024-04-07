package edu.java.sender;

import edu.java.models.request.LinkUpdateRequest;

public interface UpdateSender {
    void sendUpdate(LinkUpdateRequest request);
}
