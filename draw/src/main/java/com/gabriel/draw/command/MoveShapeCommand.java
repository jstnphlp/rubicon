package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveShapeCommand implements Command {
    private final List<Shape> shapes;
    private final List<Point> oldLocations;
    private final List<Point> newLocations;
    private final AppService appService;

    public MoveShapeCommand(AppService appService, Shape shape, Point newLoc) {
        this.appService = appService;
        this.shapes = new ArrayList<>();
        this.shapes.add(shape);
        this.oldLocations = new ArrayList<>();
        this.oldLocations.add(shape.getLocation());
        this.newLocations = new ArrayList<>();
        this.newLocations.add(newLoc);
    }

    public MoveShapeCommand(AppService appService, List<Shape> shapes, Point delta) {
        this.appService = appService;
        this.shapes = new ArrayList<>(shapes);
        this.oldLocations = new ArrayList<>();
        this.newLocations = new ArrayList<>();

        for (Shape shape : shapes) {
            this.oldLocations.add(shape.getLocation());
            Point newLoc = new Point(shape.getLocation().x + delta.x, shape.getLocation().y + delta.y);
            this.newLocations.add(newLoc);
        }
    }

    @Override
    public void execute() {
        for (int i = 0; i < shapes.size(); i++) {
            appService.move(shapes.get(i), newLocations.get(i));
        }
        appService.setStatusMessage("Moved " + shapes.size() + " shape(s)");
        appService.repaint();
    }

    @Override
    public void undo() {
        for (int i = 0; i < shapes.size(); i++) {
            appService.move(shapes.get(i), oldLocations.get(i));
        }
        appService.setStatusMessage("Move undone");
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
