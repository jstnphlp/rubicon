package com.gabriel.draw.service;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.service.MoverService;
import com.gabriel.drawfx.service.ScalerService;

import javax.swing.*;
import java.awt.*;

public class DrawingAppService implements AppService {

    final private Drawing drawing;
    MoverService moverService;
    ScalerService scalerService;
    JPanel drawingView;

    public DrawingAppService() {
        drawing = new Drawing();
        moverService = new MoverService();
        scalerService = new ScalerService();
        drawing.setDrawMode(DrawMode.Idle);
        drawing.setShapeMode(ShapeMode.Ellipse);
    }

    @Override
    public void undo() {
        com.gabriel.drawfx.command.CommandService.undo();
        repaint();
    }

    @Override
    public void redo() {
        com.gabriel.drawfx.command.CommandService.redo();
        repaint();
    }

    @Override
    public ShapeMode getShapeMode() {
        return drawing.getShapeMode();
    }

    @Override
    public void setShapeMode(ShapeMode shapeMode) {
        drawing.setShapeMode(shapeMode);
    }

    @Override
    public DrawMode getDrawMode() {
        return drawing.getDrawMode();
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        this.drawing.setDrawMode(drawMode);
    }

    @Override
    public Color getColor() {
        return drawing.getColor();
    }

    @Override
    public void setColor(Color color) {
        drawing.setColor(color);
    }

    @Override
    public Color getFill() {
        return drawing.getFill();
    }

    @Override
    public void setFill(Color color) {
        drawing.setFill(color);
    }

    @Override
    public void move(Shape shape, Point newLoc) {
        moverService.move(shape, newLoc);
    }

    @Override
    public void scale(Shape shape, Point newEnd) {
        shape.setEnd(newEnd);
    }

    @Override
    public void create(Shape shape) {
        shape.setId(this.drawing.getShapes().size());
        this.drawing.getShapes().add(shape);
    }

    @Override
    public void delete(Shape shape) {
        drawing.getShapes().remove(shape);
        if (drawing.getSelectedShape() == shape) {
            drawing.setSelectedShape(null);
        }
    }

    @Override
    public void close() {
        System.exit(0);
    }

    @Override
    public Object getModel() {
        return drawing;
    }

    @Override
    public JPanel getView() {
        return drawingView;
    }

    @Override
    public void setView(JPanel panel) {
        this.drawingView = panel;
    }

    @Override
    public void repaint() {
        drawingView.repaint();
    }

    @Override
    public Shape getSelectedShape() {
        return drawing.getSelectedShape();
    }

    @Override
    public void setSelectedShape(Shape shape) {
        drawing.setSelectedShape(shape);
    }

    @Override
    public void clearSelection() {
        drawing.setSelectedShape(null);
        drawing.getSelectedShapes().clear();
    }

    @Override
    public java.util.List<Shape> getSelectedShapes() {
        return drawing.getSelectedShapes();
    }

    @Override
    public void setSelectedShapes(java.util.List<Shape> shapes) {
        drawing.setSelectedShapes(shapes);
    }

    @Override
    public void addSelectedShape(Shape shape) {
        if (!drawing.getSelectedShapes().contains(shape)) {
            drawing.getSelectedShapes().add(shape);
        }
    }

    @Override
    public void removeSelectedShape(Shape shape) {
        drawing.getSelectedShapes().remove(shape);
    }

    @Override
    public void setStatusMessage(String message) {
        drawing.setStatusMessage(message);
    }

    @Override
    public String getStatusMessage() {
        return drawing.getStatusMessage();
    }
}
