package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class ResizeShapeCommand implements Command {
    private final Shape shape;
    private final Point oldEnd;
    private final Point newEnd;
    private final AppService appService;

    public ResizeShapeCommand(AppService appService, Shape shape, Point newEnd) {
        this.appService = appService;
        this.shape = shape;
        this.oldEnd = shape.getEnd();
        this.newEnd = newEnd;
    }

    @Override
    public void execute() {
        appService.scale(shape, newEnd);
        appService.repaint();
    }

    @Override
    public void undo() {
        appService.scale(shape, oldEnd);
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
