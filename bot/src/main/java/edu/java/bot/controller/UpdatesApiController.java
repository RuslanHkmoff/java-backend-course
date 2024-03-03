package edu.java.bot.controller;

import edu.java.models.request.LinkUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;

@Controller
public class UpdatesApiController implements UpdatesApi {
    @Override
    public void updatesPost(@Valid LinkUpdateRequest linkUpdate) {
        // TODO: alertService.sendAlert()
    }
}
