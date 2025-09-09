package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.ShapeMode;

public class SetShapeCommand implements Command {
    private final AppService appService;
    private final ShapeMode oldShapeMode;
    private final ShapeMode newShapeMode;

    public SetShapeCommand(AppService appService, ShapeMode newShapeMode) {
        this.appService = appService;
        this.oldShapeMode = appService.getShapeMode();
        this.newShapeMode = newShapeMode;
    }

    @Override
    public void execute() {
        appService.setShapeMode(newShapeMode);
        appService.setStatusMessage("Drawing " + newShapeMode.name());
        appService.repaint();
    }

    @Override
    public void undo() {
        appService.setShapeMode(oldShapeMode);
        appService.setStatusMessage("Drawing " + oldShapeMode.name());
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
