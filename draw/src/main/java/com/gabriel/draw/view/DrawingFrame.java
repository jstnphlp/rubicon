package com.gabriel.draw.view;

import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.controller.DrawingWindowController;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;

public class DrawingFrame extends JFrame {

    public DrawingFrame(AppService appService) {
        DrawingWindowController drawingWindowController = new DrawingWindowController(appService);
        this.addWindowListener(drawingWindowController);
        this.addWindowFocusListener(drawingWindowController);
        this.addWindowStateListener(drawingWindowController);

        DrawingView drawingView = new DrawingView(appService);
        new DrawingController(appService, drawingView);
        this.getContentPane().add(drawingView);
    }
}
