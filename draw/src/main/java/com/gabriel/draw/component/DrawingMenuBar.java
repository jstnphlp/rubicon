package com.gabriel.draw.component;

import com.gabriel.draw.command.ResizeShapeCommand;
import com.gabriel.draw.command.MoveShapeCommand;
import com.gabriel.draw.command.DeleteShapeCommand;
import com.gabriel.draw.command.SetColorCommand;
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
        undoMenu.addActionListener(e -> appService.undo());
        editMenu.add(undoMenu);

        redoMenu.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        ;
        redoMenu.addActionListener(e -> appService.redo());
        editMenu.add(redoMenu);

        JMenu propertiesMenu = new JMenu("Properties");
        add(propertiesMenu);

        JMenu drawMenu = new JMenu("Shapes");
        editMenu.add(drawMenu);

        fileMenu.add(newMenu);
        fileMenu.add(saveMenu);

        changeColorMenu.addActionListener(e -> {
            Shape s = appService.getSelectedShape();
            if (s == null) {
                Color chosen = JColorChooser.showDialog(null, "Choose Drawing Color", appService.getColor());
                if (chosen != null)
                    appService.setColor(chosen);
            } else {
                Color chosen = JColorChooser.showDialog(null, "Choose Shape Color", s.getColor());
                if (chosen != null)
                    CommandService.ExecuteCommand(new SetColorCommand(appService, s, chosen));
            }
        });
        propertiesMenu.add(changeColorMenu);

        propertiesMenu.add(resizeMenu);
        resizeMenu.addActionListener(e -> {
            Shape s = appService.getSelectedShape();
            if (s == null) {
                JOptionPane.showMessageDialog(null, "No shape selected");
                return;
            }
            Point loc = s.getLocation();
            int curW = Math.abs(s.getEnd().x - loc.x);
            int curH = Math.abs(s.getEnd().y - loc.y);
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Width:"));
            JTextField widthField = new JTextField(String.valueOf(curW));
            panel.add(widthField);
            panel.add(new JLabel("Height:"));
            JTextField heightField = new JTextField(String.valueOf(curH));
            panel.add(heightField);
            int result = JOptionPane.showConfirmDialog(null, panel, "Resize", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION)
                return;
            try {
                int w = Integer.parseInt(widthField.getText().trim());
                int h = Integer.parseInt(heightField.getText().trim());
                Point newEnd = new Point(loc.x + w, loc.y + h);
                CommandService.ExecuteCommand(new ResizeShapeCommand(appService, s, newEnd));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid size");
            }
        });
        propertiesMenu.add(moveMenu);
        moveMenu.addActionListener(e -> {
            Shape s = appService.getSelectedShape();
            if (s == null) {
                JOptionPane.showMessageDialog(null, "No shape selected");
                return;
            }
            Point p = s.getLocation();
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("X:"));
            JTextField xField = new JTextField(String.valueOf(p.x));
            panel.add(xField);
            panel.add(new JLabel("Y:"));
            JTextField yField = new JTextField(String.valueOf(p.y));
            panel.add(yField);
            int result = JOptionPane.showConfirmDialog(null, panel, "Move", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION)
                return;
            try {
                int x = Integer.parseInt(xField.getText().trim());
                int y = Integer.parseInt(yField.getText().trim());
                CommandService.ExecuteCommand(new MoveShapeCommand(appService, s, new Point(x, y)));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid position");
            }
        });
        deleteMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
        propertiesMenu.add(deleteMenu);
        deleteMenu.addActionListener(e -> {
            Shape s = appService.getSelectedShape();
            if (s == null) {
                JOptionPane.showMessageDialog(null, "No shape selected");
                return;
            }
            CommandService.ExecuteCommand(new DeleteShapeCommand(appService, s));
            appService.clearSelection();
        });

        drawMenu.add(lineMenu);
        lineMenu.addActionListener(e -> appService.setShapeMode(ShapeMode.Line));

        drawMenu.add(rectMenu);
        rectMenu.addActionListener(e -> appService.setShapeMode(ShapeMode.Rectangle));

        drawMenu.add(ellMenu);
        ellMenu.addActionListener(e -> appService.setShapeMode(ShapeMode.Ellipse));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
