/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * ColorButton.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.gui;

import nz.ac.waikato.cms.gui.core.GUIHelper;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * TODO: What class does.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class ColorButton
  extends JButton {

  /** the color of the button. */
  protected Color m_Color;

  /**
   * Initializes the button with {@link Color#WHITE}.
   */
  public ColorButton() {
    this(Color.WHITE);
  }

  /**
   * Initializes the button with the specified color.
   *
   * @param color	the color to use
   */
  public ColorButton(Color color) {
    super();
    setOpaque(true);
    setColor(color);
    addActionListener((ActionEvent e) -> selectColor());
  }

  /**
   * The color to use.
   *
   * @param value	the color
   */
  public void setColor(Color value) {
    m_Color = value;
    update();
  }

  /**
   * Returns the current color.
   *
   * @return		the color
   */
  public Color getColor() {
    return m_Color;
  }

  /**
   * Updates the button colors.
   */
  protected void update() {
    setBackground(m_Color);
    setForeground(ColorHelper.invert(m_Color));
    setText(ColorHelper.toHex(m_Color));
  }

  /**
   * Lets the user select a color from a color dialog.
   */
  protected void selectColor() {
    Color	selected;

    selected = JColorChooser.showDialog(GUIHelper.getParentComponent(this), "Select color", m_Color);
    if (selected != null)
      setColor(selected);
  }
}
