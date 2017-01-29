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
 * ColorRenderer.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

/**
 * Table cell renderer for {@link Color} values.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class ColorRenderer
  extends DefaultTableCellRenderer {

  /**
   * Sets the text to display.
   *
   * @param value	the value to render
   */
  @Override
  protected void setValue(Object value) {
    setText(ColorHelper.toHex((Color) value));
  }

  /**
   * Returns the rendering component.
   *
   * @param table	the table
   * @param value	the value
   * @param isSelected	whether selected
   * @param hasFocus	whether focused
   * @param row		the row
   * @param column	the column
   * @return		the renderer
   */
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    Color newColor = (Color) value;
    setBackground(isSelected ? newColor.darker() : newColor);
    setForeground(ColorHelper.invert(newColor));
    setText(ColorHelper.toHex((Color) value));
    return this;
  }
}
