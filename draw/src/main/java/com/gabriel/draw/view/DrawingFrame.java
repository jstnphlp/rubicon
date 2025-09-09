package com.gabriel.draw.view;

import com.gabriel.draw.component.DrawingMenuBar;
import com.gabriel.draw.component.DrawingToolBar;
import com.gabriel.draw.component.StatusBar;
import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.controller.DrawingWindowController;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class DrawingFrame extends JFrame {

    public DrawingFrame(AppService appService) {
        DrawingWindowController drawingWindowController = new DrawingWindowController(appService);
        this.addWindowListener(drawingWindowController);
        this.addWindowFocusListener(drawingWindowController);
        this.addWindowStateListener(drawingWindowController);

        setLayout(new BorderLayout());

        DrawingMenuBar menuBar = new DrawingMenuBar(appService);
        setJMenuBar(menuBar);

        DrawingToolBar toolBar = new DrawingToolBar(appService);
        add(toolBar, BorderLayout.NORTH);

        DrawingView drawingView = new DrawingView(appService);
        drawingView.setPreferredSize(new Dimension(800, 600));
        JScrollPane scrollPane = new JScrollPane(drawingView);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        DrawingController controller = new DrawingController(appService, drawingView);
        drawingView.setController(controller);

        StatusBar statusBar = new StatusBar(appService);

        add(scrollPane, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Drawing Application");
        pack();
        setLocationRelativeTo(null);
    }
}
