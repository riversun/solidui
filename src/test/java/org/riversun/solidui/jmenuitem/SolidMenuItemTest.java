package org.riversun.solidui.jmenuitem;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SolidMenuItemTest {

  @Test
  public void testSolidMenuItem() {
    // Create a condition using the builder
    SolidMenuItem.Condition condition = new SolidMenuItem.Condition.Builder()
        .text("Test Item")
        .shortcutText("Ctrl+T")
        .accelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK))
        .marginVertical(4)
        .marginLeft(8)
        .width(150)
        .colorOnFocus(Color.RED)
        .build();

    // Create a SolidMenuItem using the condition
    SolidMenuItem menuItem = new SolidMenuItem(condition);

    // Verify the properties of the SolidMenuItem
    assertEquals("Test Item", menuItem.mCondition.getText());
    assertEquals("Ctrl+T", menuItem.mCondition.getShortcutText());
    assertEquals(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK), menuItem.mCondition.getAccelerator());
    assertEquals(4, menuItem.mCondition.getMarginVertical());
    assertEquals(8, menuItem.mCondition.getMarginLeft());
    assertEquals(150, menuItem.mCondition.getWidth());
    assertEquals(Color.RED, menuItem.mCondition.getColor());

    // Verify that the JLabel components are created and have the correct text
    assertNotNull(menuItem.mTextLabel);
    assertNotNull(menuItem.mShortcutLabel);
    assertEquals("Test Item", menuItem.mTextLabel.getText());
    assertEquals("Ctrl+T", menuItem.mShortcutLabel.getText());
  }
}
