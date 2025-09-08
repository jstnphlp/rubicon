package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class DeleteShapeCommand implements Command {
    private final Shape shape;
    private final AppService appService;

    public DeleteShapeCommand(AppService appService, Shape shape) {
        this.appService = appService;
        this.shape = shape;
    }

    @Override
    public void execute() {
        appService.delete(shape);
        appService.repaint();
    }

    @Override
    public void undo() {
        appService.create(shape);
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
