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
 * ColorHelper.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.gui;

import java.awt.Color;

/**
 * Helper class for colors.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class ColorHelper {

  /**
   * Turns the integer into a hex string, left-pads with zero.
   *
   * @param i		the integer to convert
   * @return		the generated string
   */
  protected static String toHex(int i) {
    String	result;

    result = Integer.toHexString(i);
    if (result.length() % 2 == 1)
      result = "0" + result;

    return result;
  }

  /**
   * Returns the color as hex string ("#RRGGBB" or "#AARRGGBB").
   * Alpha is only output if different from 255.
   *
   * @param color	the color to convert
   * @return		the generated string
   */
  public static String toHex(Color color) {
    String	result;

    if (color.getAlpha() < 255)
      result = "#"
	+ toHex(color.getAlpha())
	+ toHex(color.getRed())
	+ toHex(color.getGreen())
	+ toHex(color.getBlue());
    else
      result = "#"
	+ toHex(color.getRed())
	+ toHex(color.getGreen())
	+ toHex(color.getBlue());

    return result;
  }

  /**
   * Returns a color generated from the string.
   * Formats:
   * <ul>
   *   <li>hex notation: #(AA)RRGGBB with AA/RR/GG/BB being hexadecimal strings</li>
   *   </li>predefined names (case-insensitive) : black, blue, cyan, darkgray,
   *   darkgrey, gray, grey, green, lightgray, lightgrey, magenta, orange,
   *   pink, red, white, yellow</li>
   * </ul>
   *
   * @param str		the string to convert to a color
   * @param defColor	the default color if parsing fails
   * @return		the generated color
   */
  public static Color valueOf(String str, Color defColor) {
    Color result;

    result = defColor;

    if (str.startsWith("#"))
      str = str.replaceAll("#", "");
    if (str.length() == 6) {
      result = new Color(
	Integer.parseInt(str.substring(0, 2), 16),
	Integer.parseInt(str.substring(2, 4), 16),
	Integer.parseInt(str.substring(4, 6), 16));
    }
    else if (str.length() == 8) {
      result = new Color(
	Integer.parseInt(str.substring(2, 4), 16),
	Integer.parseInt(str.substring(4, 6), 16),
	Integer.parseInt(str.substring(6, 8), 16),
	Integer.parseInt(str.substring(0, 2), 16));
    }
    else {
      str = str.toLowerCase();
      if (str.equals("black"))
	result = Color.BLACK;
      else if (str.equals("blue"))
	result = Color.BLUE;
      else if (str.equals("cyan"))
	result = Color.CYAN;
      else if (str.equals("darkgray"))
	result = Color.DARK_GRAY;
      else if (str.equals("darkgrey"))
	result = Color.DARK_GRAY;
      else if (str.equals("gray"))
	result = Color.GRAY;
      else if (str.equals("grey"))
	result = Color.GRAY;
      else if (str.equals("green"))
	result = Color.GREEN;
      else if (str.equals("lightgray"))
	result = Color.LIGHT_GRAY;
      else if (str.equals("lightgrey"))
	result = Color.LIGHT_GRAY;
      else if (str.equals("magenta"))
	result = Color.MAGENTA;
      else if (str.equals("orange"))
	result = Color.ORANGE;
      else if (str.equals("pink"))
	result = Color.PINK;
      else if (str.equals("red"))
	result = Color.RED;
      else if (str.equals("white"))
	result = Color.WHITE;
      else if (str.equals("yellow"))
	result = Color.YELLOW;
    }

    return result;
  }

  /**
   * Returns the inverted color.
   *
   * @param color	the color to invert
   * @return		the inverted color
   */
  public static Color invert(Color color) {
    return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
  }
}
