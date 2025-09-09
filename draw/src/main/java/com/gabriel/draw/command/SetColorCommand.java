package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SetColorCommand implements Command {
    private final List<Shape> shapes;
    private final List<Color> oldColors;
    private final Color newColor;
    private final AppService appService;

    public SetColorCommand(AppService appService, Shape shape, Color newColor) {
        this.appService = appService;
        this.shapes = new ArrayList<>();
        this.shapes.add(shape);
        this.oldColors = new ArrayList<>();
        this.oldColors.add(shape.getColor());
        this.newColor = newColor;
    }

    public SetColorCommand(AppService appService, List<Shape> shapes, Color newColor) {
        this.appService = appService;
        this.shapes = new ArrayList<>(shapes);
        this.oldColors = new ArrayList<>();
        for (Shape shape : shapes) {
            this.oldColors.add(shape.getColor());
        }
        this.newColor = newColor;
    }

    @Override
    public void execute() {
        for (Shape shape : shapes) {
            shape.setColor(newColor);
        }
        appService.setStatusMessage("Color changed for " + shapes.size() + " shape(s)");
        appService.repaint();
    }

    @Override
    public void undo() {
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).setColor(oldColors.get(i));
        }
        appService.setStatusMessage("Color change undone");
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
