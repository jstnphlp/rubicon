package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.util.ArrayList;
import java.util.List;

public class DeleteShapeCommand implements Command {
    private final List<Shape> shapes;
    private final AppService appService;

    public DeleteShapeCommand(AppService appService, Shape shape) {
        this.appService = appService;
        this.shapes = new ArrayList<>();
        this.shapes.add(shape);
    }

    public DeleteShapeCommand(AppService appService, List<Shape> shapes) {
        this.appService = appService;
        this.shapes = new ArrayList<>(shapes);
    }

    @Override
    public void execute() {
        for (Shape shape : shapes) {
            appService.delete(shape);
        }
        appService.setStatusMessage("Deleted " + shapes.size() + " shape(s)");
        appService.repaint();
    }

    @Override
    public void undo() {
        for (Shape shape : shapes) {
            appService.create(shape);
        }
        appService.setStatusMessage("Delete undone");
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
