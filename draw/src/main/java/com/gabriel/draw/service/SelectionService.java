package com.gabriel.draw.service;

import com.gabriel.drawfx.model.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SelectionService {

    public Shape findShapeAtPoint(Point point, List<Shape> shapes) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (getShapeBounds(shape).contains(point)) {
                return shape;
            }
        }
        return null;
    }

    public List<Shape> findShapesInRectangle(Rectangle selectionRect, List<Shape> shapes) {
        List<Shape> selectedShapes = new ArrayList<>();
        for (Shape shape : shapes) {
            Rectangle shapeBounds = getShapeBounds(shape);
            if (selectionRect.intersects(shapeBounds)) {
                selectedShapes.add(shape);
            }
        }
        return selectedShapes;
    }

    public Rectangle getShapeBounds(Shape shape) {
        Point location = shape.getLocation();
        Point end = shape.getEnd();

        int x = Math.min(location.x, end.x);
        int y = Math.min(location.y, end.y);
        int width = Math.abs(end.x - location.x);
        int height = Math.abs(end.y - location.y);

        return new Rectangle(x, y, width, height);
    }
}