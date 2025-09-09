package com.gabriel.drawfx.service;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Shape;

import javax.swing.*;
import java.awt.*;

public interface AppService {
    void undo();

    void redo();

    ShapeMode getShapeMode();

    void setShapeMode(ShapeMode shapeMode);

    DrawMode getDrawMode();

    void setDrawMode(DrawMode drawMode);

    Color getColor();

    void setColor(Color color);

    Color getFill();

    void setFill(Color color);

    void move(Shape shape, Point newLoc);

    void scale(Shape shape, Point newEnd);

    void create(Shape shape);

    void delete(Shape shape);

    Shape getSelectedShape();

    void setSelectedShape(Shape shape);

    void clearSelection();

    java.util.List<Shape> getSelectedShapes();

    void setSelectedShapes(java.util.List<Shape> shapes);

    void addSelectedShape(Shape shape);

    void removeSelectedShape(Shape shape);

    void setStatusMessage(String message);

    String getStatusMessage();

    void close();

    Object getModel();

    JPanel getView();

    void setView(JPanel panel);

    void repaint();
}
