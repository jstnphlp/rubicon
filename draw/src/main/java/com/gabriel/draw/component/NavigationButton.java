package com.gabriel.draw.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavigationButton extends JButton {
    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;

    public NavigationButton(String text, String tooltip) {
        super(text);
        setToolTipText(tooltip);

        normalColor = getBackground();
        hoverColor = normalColor.brighter();
        pressedColor = normalColor.darker();

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor);
            }
        });
    }

    public NavigationButton(String text, String tooltip, Icon icon) {
        this(text, tooltip);
        setIcon(icon);
    }

    public void setNormalColor(Color color) {
        this.normalColor = color;
        setBackground(color);
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    public void setPressedColor(Color color) {
        this.pressedColor = color;
    }
}
