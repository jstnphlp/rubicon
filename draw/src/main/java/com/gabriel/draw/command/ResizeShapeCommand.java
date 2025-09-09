package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResizeShapeCommand implements Command {
    private final List<Shape> shapes;
    private final List<Point> oldEnds;
    private final List<Point> newEnds;
    private final AppService appService;

    public ResizeShapeCommand(AppService appService, Shape shape, Point newEnd) {
        this.appService = appService;
        this.shapes = new ArrayList<>();
        this.shapes.add(shape);
        this.oldEnds = new ArrayList<>();
        this.oldEnds.add(shape.getEnd());
        this.newEnds = new ArrayList<>();
        this.newEnds.add(newEnd);
    }

    public ResizeShapeCommand(AppService appService, List<Shape> shapes, double scaleFactor) {
        this.appService = appService;
        this.shapes = new ArrayList<>(shapes);
        this.oldEnds = new ArrayList<>();
        this.newEnds = new ArrayList<>();

        for (Shape shape : shapes) {
            this.oldEnds.add(shape.getEnd());
            Point location = shape.getLocation();
            Point oldEnd = shape.getEnd();
            int newWidth = (int) (Math.abs(oldEnd.x - location.x) * scaleFactor);
            int newHeight = (int) (Math.abs(oldEnd.y - location.y) * scaleFactor);
            Point newEnd = new Point(location.x + newWidth, location.y + newHeight);
            this.newEnds.add(newEnd);
        }
    }

    @Override
    public void execute() {
        for (int i = 0; i < shapes.size(); i++) {
            appService.scale(shapes.get(i), newEnds.get(i));
        }
        appService.setStatusMessage("Resized " + shapes.size() + " shape(s)");
        appService.repaint();
    }

    @Override
    public void undo() {
        for (int i = 0; i < shapes.size(); i++) {
            appService.scale(shapes.get(i), oldEnds.get(i));
        }
        appService.setStatusMessage("Resize undone");
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
