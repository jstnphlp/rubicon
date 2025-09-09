package com.gabriel.draw.controller;

import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.draw.service.SelectionService;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.model.Drawing;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingController implements MouseListener, MouseMotionListener {
    private Point end;
    final private DrawingView drawingView;
    private SelectionService selectionService;

    Shape currentShape;
    AppService appService;
    private Point selectionStart;
    private java.awt.Rectangle selectionRectangle;

    public DrawingController(AppService appService, DrawingView drawingView) {
        this.appService = appService;
        this.drawingView = drawingView;
        this.selectionService = new SelectionService();
        drawingView.addMouseListener(this);
        drawingView.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point start = e.getPoint();

        if (appService.getDrawMode() == DrawMode.Idle) {
            Drawing drawing = (Drawing) appService.getModel();
            Shape clickedShape = selectionService.findShapeAtPoint(start, drawing.getShapes());

            if (clickedShape != null) {
                if (e.isControlDown()) {
                    if (appService.getSelectedShapes().contains(clickedShape)) {
                        appService.removeSelectedShape(clickedShape);
                    } else {
                        appService.addSelectedShape(clickedShape);
                    }
                } else {
                    appService.clearSelection();
                    appService.setSelectedShape(clickedShape);
                    appService.addSelectedShape(clickedShape);
                }
                appService.setStatusMessage("Selected " + appService.getSelectedShapes().size() + " shape(s)");
                appService.repaint();
            } else {
                if (e.isControlDown()) {
                    appService.clearSelection();
                    selectionStart = start;
                    selectionRectangle = new java.awt.Rectangle(start);
                    appService.setDrawMode(DrawMode.Selecting);
                    appService.setStatusMessage("Selecting shapes...");
                } else {
                    appService.clearSelection();
                    switch (appService.getShapeMode()) {
                        case Line:
                            currentShape = new Line(start, start, appService.getColor());
                            break;
                        case Rectangle:
                            currentShape = new Rectangle(start, start, appService.getColor());
                            break;
                        case Ellipse:
                            currentShape = new Ellipse(start, start, appService.getColor());
                            break;
                    }
                    drawingView.setCurrentShape(currentShape);
                    appService.setDrawMode(DrawMode.MousePressed);
                    appService.setStatusMessage("Drawing " + appService.getShapeMode().name());
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed) {
            end = e.getPoint();
            appService.create(currentShape);
            drawingView.setCurrentShape(null);
            appService.setDrawMode(DrawMode.Idle);
            appService.setStatusMessage("Shape created");
        } else if (appService.getDrawMode() == DrawMode.Selecting) {
            Drawing drawing = (Drawing) appService.getModel();
            java.util.List<Shape> shapesInSelection = selectionService.findShapesInRectangle(selectionRectangle,
                    drawing.getShapes());
            appService.setSelectedShapes(shapesInSelection);
            appService.setDrawMode(DrawMode.Idle);
            selectionRectangle = null;
            appService.setStatusMessage("Selected " + shapesInSelection.size() + " shape(s)");
            appService.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed) {
            end = e.getPoint();
            appService.scale(currentShape, end);
            appService.repaint();
        } else if (appService.getDrawMode() == DrawMode.Selecting) {
            Point current = e.getPoint();
            int x = Math.min(selectionStart.x, current.x);
            int y = Math.min(selectionStart.y, current.y);
            int width = Math.abs(current.x - selectionStart.x);
            int height = Math.abs(current.y - selectionStart.y);
            selectionRectangle = new java.awt.Rectangle(x, y, width, height);
            appService.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public java.awt.Rectangle getSelectionRectangle() {
        return selectionRectangle;
    }
}
