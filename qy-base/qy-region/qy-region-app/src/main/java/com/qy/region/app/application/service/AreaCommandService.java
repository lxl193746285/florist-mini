package com.qy.region.app.application.service;

import com.qy.region.app.application.command.CreateAreaCommand;
import com.qy.region.app.application.command.UpdateAreaCommand;

public interface AreaCommandService {

    void createArea(CreateAreaCommand command);

    void updateArea(UpdateAreaCommand command);

    void deleteArea(Long id);
}
