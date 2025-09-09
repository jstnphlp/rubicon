package com.gabriel.draw.component;

import com.gabriel.draw.command.SetShapeCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class DrawingToolBar extends JToolBar {
    private AppService appService;

    public DrawingToolBar(AppService appService) {
        this.appService = appService;
        setFloatable(false);
        setRollover(true);

        NavigationButton lineButton = new NavigationButton("Line", "Draw lines");
        lineButton
                .addActionListener(e -> CommandService.ExecuteCommand(new SetShapeCommand(appService, ShapeMode.Line)));
        add(lineButton);

        NavigationButton rectButton = new NavigationButton("Rectangle", "Draw rectangles");
        rectButton.addActionListener(
                e -> CommandService.ExecuteCommand(new SetShapeCommand(appService, ShapeMode.Rectangle)));
        add(rectButton);

        NavigationButton ellipseButton = new NavigationButton("Ellipse", "Draw ellipses");
        ellipseButton.addActionListener(
                e -> CommandService.ExecuteCommand(new SetShapeCommand(appService, ShapeMode.Ellipse)));
        add(ellipseButton);

        addSeparator();

        NavigationButton undoButton = new NavigationButton("Undo", "Undo last action (Ctrl+Z)");
        undoButton.addActionListener(e -> appService.undo());
        add(undoButton);

        NavigationButton redoButton = new NavigationButton("Redo", "Redo last undone action (Ctrl+Shift+Z)");
        redoButton.addActionListener(e -> appService.redo());
        add(redoButton);

        addSeparator();

        JLabel selectionLabel = new JLabel("Selection: None");
        selectionLabel.setToolTipText("Shows number of selected shapes");
        add(selectionLabel);

        Timer timer = new Timer(100, e -> {
            int count = appService.getSelectedShapes().size();
            if (count == 0) {
                selectionLabel.setText("Selection: None");
            } else if (count == 1) {
                selectionLabel.setText("Selection: 1 shape");
            } else {
                selectionLabel.setText("Selection: " + count + " shapes");
            }
        });
        timer.start();
    }
}
