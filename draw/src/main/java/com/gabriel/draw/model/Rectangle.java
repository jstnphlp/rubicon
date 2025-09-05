package com.gabriel.draw.model;

import com.gabriel.draw.service.RectangleRendererService;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public class Rectangle extends Shape {

    public Rectangle(Point start, Point end, Color color) {
        super(start);
        this.setEnd(end);
        this.setColor(color);
        this.setRendererService(new RectangleRendererService());
    }
}
