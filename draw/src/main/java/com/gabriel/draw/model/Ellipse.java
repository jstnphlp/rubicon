package com.gabriel.draw.model;

import com.gabriel.draw.service.EllipseRenderer;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public class Ellipse extends Shape {
    public Ellipse(Point start, Point end, Color color) {
        super(start);
        this.setEnd(end);
        this.setColor(color);
        this.setRendererService(new EllipseRenderer());
    }
}