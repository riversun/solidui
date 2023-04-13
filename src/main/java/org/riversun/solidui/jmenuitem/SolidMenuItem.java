package org.riversun.solidui.jmenuitem;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * JMenuItem which can do the following
 *
 * - properly place shortcut keys on menu items
 * - Set padding for menu items
 * - adjust the text position of menu items
 *
 * @author Tom Misawa (riversun.org@gmail.com)
 */
public class SolidMenuItem extends JMenuItem {

  JLabel mTextLabel;
  JLabel mShortcutLabel;
  Condition mCondition;

  /**
   * Constructor for SolidMenuItem. Initializes the menu item with the given condition.
   *
   * @param creationCondition the condition specifying the layout and style properties of the menu item
   */
  public SolidMenuItem(Condition creationCondition) {
    this.mCondition = creationCondition;
    setName(creationCondition.getText());
    setLayout(new GridBagLayout());
    setFocusPainted(false);
    setBorderPainted(false);
    setContentAreaFilled(false);
    setOpaque(true);
    setAccelerator(creationCondition.accelerator);

    GridBagConstraints constraints = new GridBagConstraints();

    mTextLabel = new JLabel(creationCondition.text);
    mTextLabel.setFont(UIManager.getFont("MenuItem.font"));
    mTextLabel.setForeground(UIManager.getColor("MenuItem.foreground"));
    mTextLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    // int top, int left, int bottom, int right
    constraints.insets = new Insets(creationCondition.marginVertical, creationCondition.marginLeft, creationCondition.marginVertical, 0); // Set the top and bottom insets to 4
    add(mTextLabel, constraints);

    mShortcutLabel = new JLabel(creationCondition.shortcutText);
    mShortcutLabel.setFont(UIManager.getFont("MenuItem.font"));
    mShortcutLabel.setForeground(UIManager.getColor("MenuItem.acceleratorForeground"));
    mShortcutLabel.setBorder(new EmptyBorder(0, 5, 0, 5));

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.weightx = 0;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.insets = new Insets(creationCondition.marginVertical, creationCondition.marginVertical, creationCondition.marginVertical,
        creationCondition.marginVertical); // Set the top and bottom insets to 4
    add(mShortcutLabel, constraints);
    this.setUI(new SolidMenuItemUI(this.mCondition));

  }

  /**
   * Paints the component. Overridden to handle custom background and antialiasing.
   *
   * @param g the Graphics object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    if (getModel().isArmed() || isSelected()) {
      g2d.setColor(UIManager.getColor("MenuItem.selectionBackground"));
      g2d.fillRect(0, 0, getWidth(), getHeight());
    } else {
      g2d.setColor(getBackground());
      g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    super.paintComponent(g);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension preferredSize = super.getPreferredSize();
    FontMetrics fm = getFontMetrics(getFont());
    int height = fm.getHeight() + getInsets().top + getInsets().bottom + mCondition.marginVertical * 2;
    int width = fm.stringWidth(mTextLabel.getText()) + fm.stringWidth(mShortcutLabel.getText()) + 80; // Add additional width

    preferredSize.setSize(mCondition.width == 0 ? width : mCondition.width, height);
    return preferredSize;
  }

  /**
   * Represents the configuration options for creating a SolitMenuItem
   * Created by https://riversun.github.io/java-builder/
   *
   * class Condition String text String shortcutText KeyStroke accelerator int marginVertical int marginLeft int menuWidth int width
   *
   * @author riversun.org@gmail.com
   */
  public static class Condition {

    String text;
    String shortcutText;
    KeyStroke accelerator;
    int marginVertical;
    int marginLeft;
    int width;
    Color colorOnFocus;

    /**
     * Returns the display text of the menu item.
     *
     * @return the text of the menu item
     */
    public String getText() {
      return text;
    }

    /**
     * Returns the shortcut text of the menu item.
     *
     * @return the shortcut text of the menu item
     */
    public String getShortcutText() {
      return shortcutText;
    }

    /**
     * Returns the KeyStroke representing the keyboard accelerator for the menu item.
     *
     * @return the KeyStroke for the menu item's accelerator
     */
    public KeyStroke getAccelerator() {
      return accelerator;
    }

    /**
     * Returns the vertical margin for the menu item.
     *
     * @return the vertical margin of the menu item
     */
    public int getMarginVertical() {
      return marginVertical;
    }

    /**
     * Returns the left margin for the menu item.
     *
     * @return the left margin of the menu item
     */
    public int getMarginLeft() {
      return marginLeft;
    }

    /**
     * Returns the width of the menu item.
     *
     * @return the width of the menu item
     */
    public int getWidth() {
      return width;
    }

    /**
     * Returns the color of the menu item.
     *
     * @return the color of the menu item
     */
    public Color getColor() {
      return colorOnFocus;
    }

    /**
     * Builder class for constructing a Condition instance.
     */
    public static class Builder {

      private String text;
      private String shortcutText;
      private KeyStroke accelerator;
      private int marginVertical = 4;
      private int marginLeft = 8;
      private int width;
      private Color color;

      /**
       * Constructs a new Builder instance.
       */
      public Builder() {
      }

      /**
       * Sets the color for the menu item.
       *
       * @param color the color for the menu item
       * @return this Builder instance for method chaining
       */
      public Builder colorOnFocus(Color color) {
        this.color = color;
        return Builder.this;
      }

      /**
       * Sets the display text for the menu item.
       *
       * @param text the display text for the menu item
       * @return this Builder instance for method chaining
       */
      public Builder text(String text) {
        this.text = text;
        return Builder.this;
      }

      /**
       * Sets the shortcut text for the menu item.
       *
       * @param shortcutText the shortcut text for the menu item
       * @return this Builder instance for method chaining
       */
      public Builder shortcutText(String shortcutText) {
        this.shortcutText = shortcutText;
        return Builder.this;
      }

      /**
       * Sets the keyboard accelerator for the menu item.
       *
       * @param accelerator the KeyStroke for the menu item's accelerator
       * @return this Builder instance for method chaining
       */
      public Builder accelerator(KeyStroke accelerator) {
        this.accelerator = accelerator;
        return Builder.this;
      }

      /**
       * Sets the vertical margin for the menu item.
       *
       * @param marginVertical the vertical margin for the menu item
       * @return this Builder instance for method chaining
       */
      public Builder marginVertical(int marginVertical) {
        this.marginVertical = marginVertical;
        return Builder.this;
      }

      /**
       * Sets the left margin for the menu item.
       *
       * @param marginLeft the left margin for the menu item
       * @return this Builder instance for method chaining
       */
      public Builder marginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
        return Builder.this;
      }

      /**
       * Sets the width of the menu item.
       *
       * @param width the width of the menu item
       * @return this Builder instance for method chaining
       */
      public Builder width(int width) {
        this.width = width;
        return Builder.this;
      }

      /**
       * Constructs a new Condition instance using the values provided to the Builder.
       *
       * @return a new Condition instance
       */
      public Condition build() {
        return new Condition(this);
      }
    }

    /**
     * Constructs a new Condition instance using the values provided by the Builder.
     *
     * @param builder the Builder instance providing values for the new Condition
     */
    private Condition(Builder builder) {
      this.text = builder.text;
      this.shortcutText = builder.shortcutText;
      this.accelerator = builder.accelerator;
      this.marginVertical = builder.marginVertical;
      this.marginLeft = builder.marginLeft;
      this.width = builder.width;
      this.colorOnFocus = builder.color;
    }

    /**
     * Returns a string representation of the Condition instance.
     *
     * @return a string representation of the Condition
     */
    @Override
    public String toString() {
      return "Condition [text=" + text + ", shortcutText=" + shortcutText + ", accelerator=" + accelerator + ", marginVertical=" + marginVertical + ", marginLeft=" + marginLeft
          + ", width=" + width + "]";
    }

  }
}