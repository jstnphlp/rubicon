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
            // Check if clicking on existing shape for selection
            Drawing drawing = (Drawing) appService.getModel();
            Shape clickedShape = selectionService.findShapeAtPoint(start, drawing.getShapes());

            if (clickedShape != null) {
                appService.setSelectedShape(clickedShape);
                appService.repaint();
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
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
