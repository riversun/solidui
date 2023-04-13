package org.riversun.solidui.jmenuitem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class SolidMenuItemUI extends BasicMenuItemUI {

  private SolidMenuItem.Condition mCondition;

  public SolidMenuItemUI(SolidMenuItem.Condition con) {
    mCondition = con;
  }

  @Override
  protected String getPropertyPrefix() {
    return "MenuItem";
  }

  @Override
  protected void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap) {
    JMenuItem menuItem = (JMenuItem) c;
    paintBackground(g, menuItem, selectionBackground);
    paintText(g, menuItem, menuItem.getBounds(), menuItem.getText());
  }

  @Override
  protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    int textX = menuItem.getInsets().left + 16;
    int textY = menuItem.getInsets().top + menuItem.getFontMetrics(menuItem.getFont())
        .getAscent();
    g.setFont(menuItem.getFont());

    if (menuItem.isEnabled()) {
      if (menuItem.isArmed() || menuItem.isSelected()) {
        g.setColor(selectionForeground);
      } else {
        g.setColor(menuItem.getForeground());
      }
    } else {
      g.setColor(UIManager.getColor("MenuItem.disabledForeground"));
    }

    g.drawString(text, textX, textY);

  }

  @Override
  protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
    Color oldColor = g.getColor();
    if (menuItem.isArmed() || menuItem.isSelected()) {
      // Change the background color to gray when the menu item is hovered over or selected
      if (mCondition.colorOnFocus != null) {
        g.setColor(mCondition.colorOnFocus);
      } else {
        g.setColor(UIManager.getColor("MenuItem.selectionBackground"));
      }
    } else {
      g.setColor(menuItem.getBackground());
    }
    g.fillRect(0, 0, menuItem.getWidth(), menuItem.getHeight());
    g.setColor(oldColor);
  }

}
