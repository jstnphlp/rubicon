package com.gabriel.drawfx.model;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Drawing {

    private Color color;
    private Color fill;
    private ShapeMode shapeMode = ShapeMode.Rectangle;
    private DrawMode drawMode = DrawMode.Idle;
    private Shape selectedShape;
    List<Shape> shapes;

    public Drawing() {
        shapes = new ArrayList<>();
        this.color = Color.BLACK; // Set default color to black
    }
}
