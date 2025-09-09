package com.gabriel.draw.component;

import com.gabriel.draw.command.ResizeShapeCommand;
import com.gabriel.draw.command.MoveShapeCommand;
import com.gabriel.draw.command.DeleteShapeCommand;
import com.gabriel.draw.command.SetColorCommand;
import com.gabriel.draw.command.SetShapeCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawingMenuBar extends JMenuBar implements ActionListener {
    private AppService appService;

    // Properties Menu
    private JMenuItem changeColorMenu = new JMenuItem("Change Color");
    private JMenuItem resizeMenu = new JMenuItem("Resize");
    private JMenuItem moveMenu = new JMenuItem("Move");
    private JMenuItem deleteMenu = new JMenuItem("Delete");

    // File Menu
    private JMenuItem newMenu = new JMenuItem("New");
    private JMenuItem saveMenu = new JMenuItem("Save");

    // Edit Menu
    private JMenuItem undoMenu = new JMenuItem("Undo");
    private JMenuItem redoMenu = new JMenuItem("Redo");

    // Shape Menu
    private JMenuItem lineMenu = new JMenuItem("Line");
    private JMenuItem rectMenu = new JMenuItem("Rectangle");
    private JMenuItem ellMenu = new JMenuItem("Ellipse");

    public DrawingMenuBar(AppService appService) {
        super();
        this.appService = appService;

        JMenu fileMenu = new JMenu("File");
        add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        add(editMenu);

        undoMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoMenu.setToolTipText("Undo the last action (Ctrl+Z)");
        undoMenu.addActionListener(e -> appService.undo());
        editMenu.add(undoMenu);

        redoMenu.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        redoMenu.setToolTipText("Redo the last undone action (Ctrl+Shift+Z)");
        redoMenu.addActionListener(e -> appService.redo());
        editMenu.add(redoMenu);

        editMenu.addSeparator();

        JMenu propertiesMenu = new JMenu("Properties");
        add(propertiesMenu);

        JMenu drawMenu = new JMenu("Shapes");
        editMenu.add(drawMenu);

        newMenu.setToolTipText("Create a new drawing");
        saveMenu.setToolTipText("Save the current drawing");
        fileMenu.add(newMenu);
        fileMenu.addSeparator();
        fileMenu.add(saveMenu);

        changeColorMenu.setToolTipText("Change the color of selected shapes or set default drawing color");
        changeColorMenu.addActionListener(e -> {
            java.util.List<Shape> selectedShapes = appService.getSelectedShapes();
            if (selectedShapes.isEmpty()) {
                Color chosen = JColorChooser.showDialog(null, "Choose Drawing Color", appService.getColor());
                if (chosen != null)
                    appService.setColor(chosen);
            } else {
                Color chosen = JColorChooser.showDialog(null, "Choose Shape Color", selectedShapes.get(0).getColor());
                if (chosen != null)
                    CommandService.ExecuteCommand(new SetColorCommand(appService, selectedShapes, chosen));
            }
        });
        propertiesMenu.add(changeColorMenu);
        propertiesMenu.addSeparator();

        resizeMenu.setToolTipText("Resize selected shapes by a scale factor");
        propertiesMenu.add(resizeMenu);
        resizeMenu.addActionListener(e -> {
            java.util.List<Shape> selectedShapes = appService.getSelectedShapes();
            if (selectedShapes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No shapes selected");
                return;
            }

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Scale Factor:"));
            JTextField scaleField = new JTextField("1.0");
            panel.add(scaleField);
            panel.add(new JLabel("(1.0 = original size)"));
            panel.add(new JLabel(""));

            int result = JOptionPane.showConfirmDialog(null, panel, "Resize " + selectedShapes.size() + " shape(s)",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION)
                return;
            try {
                double scaleFactor = Double.parseDouble(scaleField.getText().trim());
                CommandService.ExecuteCommand(new ResizeShapeCommand(appService, selectedShapes, scaleFactor));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid scale factor");
            }
        });
        moveMenu.setToolTipText("Move selected shapes by a delta amount");
        propertiesMenu.add(moveMenu);
        propertiesMenu.addSeparator();
        moveMenu.addActionListener(e -> {
            java.util.List<Shape> selectedShapes = appService.getSelectedShapes();
            if (selectedShapes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No shapes selected");
                return;
            }

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Delta X:"));
            JTextField xField = new JTextField("0");
            panel.add(xField);
            panel.add(new JLabel("Delta Y:"));
            JTextField yField = new JTextField("0");
            panel.add(yField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Move " + selectedShapes.size() + " shape(s)",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION)
                return;
            try {
                int deltaX = Integer.parseInt(xField.getText().trim());
                int deltaY = Integer.parseInt(yField.getText().trim());
                CommandService
                        .ExecuteCommand(new MoveShapeCommand(appService, selectedShapes, new Point(deltaX, deltaY)));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid position");
            }
        });
        deleteMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        deleteMenu.setToolTipText("Delete selected shapes (Delete key)");
        propertiesMenu.add(deleteMenu);
        deleteMenu.addActionListener(e -> {
            java.util.List<Shape> selectedShapes = appService.getSelectedShapes();
            if (selectedShapes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No shapes selected");
                return;
            }
            CommandService.ExecuteCommand(new DeleteShapeCommand(appService, selectedShapes));
            appService.clearSelection();
        });

        lineMenu.setToolTipText("Set drawing mode to Line");
        drawMenu.add(lineMenu);
        lineMenu.addActionListener(e -> CommandService.ExecuteCommand(new SetShapeCommand(appService, ShapeMode.Line)));

        rectMenu.setToolTipText("Set drawing mode to Rectangle");
        drawMenu.add(rectMenu);
        rectMenu.addActionListener(
                e -> CommandService.ExecuteCommand(new SetShapeCommand(appService, ShapeMode.Rectangle)));

        ellMenu.setToolTipText("Set drawing mode to Ellipse");
        drawMenu.add(ellMenu);
        ellMenu.addActionListener(
                e -> CommandService.ExecuteCommand(new SetShapeCommand(appService, ShapeMode.Ellipse)));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
