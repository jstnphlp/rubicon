package com.gabriel.draw.view;

import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class DrawingView extends JPanel {
    private final AppService appService;
    private Shape currentShape;

    public DrawingView(AppService appService) {
        this.appService = appService;
        appService.setView(this);
    }

    public void setCurrentShape(Shape shape) {
        this.currentShape = shape;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Drawing drawing = (Drawing) appService.getModel();

        for (Shape shape : drawing.getShapes()) {
            shape.getRendererService().render(g, shape, false);
        }

        if (currentShape != null) {
            currentShape.getRendererService().render(g, currentShape, false);
        }

        Shape selectedShape = drawing.getSelectedShape();
        if (selectedShape != null) {
            renderSelection(g, selectedShape);
        }
    }

    private void renderSelection(Graphics g, Shape shape) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        Point location = shape.getLocation();
        Point end = shape.getEnd();
        int x = Math.min(location.x, end.x);
        int y = Math.min(location.y, end.y);
        int width = Math.abs(end.x - location.x);
        int height = Math.abs(end.y - location.y);

        g2d.drawRect(x - 2, y - 2, width + 4, height + 4);
    }
}
