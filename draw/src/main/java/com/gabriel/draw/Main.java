package com.gabriel.draw;

import com.gabriel.draw.component.DrawingMenuBar;
import com.gabriel.draw.service.DeawingCommandAppService;
import com.gabriel.draw.service.DrawingAppService;
import com.gabriel.draw.view.DrawingFrame;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        AppService drawingAppService = new DrawingAppService();
        AppService appService = new DeawingCommandAppService(drawingAppService);

        DrawingFrame drawingFrame = new DrawingFrame(appService);
        DrawingMenuBar menuBar = new DrawingMenuBar(appService);

        menuBar.setVisible(true);
        drawingFrame.setJMenuBar(menuBar);

        drawingFrame.setVisible(true);
        drawingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawingFrame.setSize(500, 500);
    }
}