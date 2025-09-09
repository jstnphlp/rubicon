package com.gabriel.draw.component;

import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    private JLabel statusLabel;
    private AppService appService;

    public StatusBar(AppService appService) {
        this.appService = appService;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLoweredBevelBorder());
        setPreferredSize(new Dimension(0, 25));

        statusLabel = new JLabel("Ready");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(statusLabel, BorderLayout.WEST);

        Timer timer = new Timer(100, e -> updateStatus());
        timer.start();
    }

    private void updateStatus() {
        String status = appService.getStatusMessage();
        if (!status.equals(statusLabel.getText())) {
            statusLabel.setText(status);
        }
    }
}
