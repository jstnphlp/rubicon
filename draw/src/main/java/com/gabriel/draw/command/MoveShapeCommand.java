package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class MoveShapeCommand implements Command {
    private final Shape shape;
    private final Point oldLoc;
    private final Point newLoc;
    private final AppService appService;

    public MoveShapeCommand(AppService appService, Shape shape, Point newLoc) {
        this.appService = appService;
        this.shape = shape;
        this.oldLoc = shape.getLocation();
        this.newLoc = newLoc;
    }

    @Override
    public void execute() {
        appService.move(shape, newLoc);
        appService.repaint();
    }

    @Override
    public void undo() {
        appService.move(shape, oldLoc);
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
