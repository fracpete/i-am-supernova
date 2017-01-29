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
 * Registry.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova;

import nz.ac.waikato.cms.supernova.io.PNG;
import nz.ac.waikato.cms.supernova.triangle.Incenter;

/**
 * Registry for available classes.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Registry {

  /** the available generators. */
  public static Class[] GENERATORS = new Class[]{
    PNG.class,
  };

  /** the available triangle center calculations. */
  public static Class[] CENTERS = new Class[]{
    Incenter.class,
  };

  /**
   * Turns a class list into a comma-separated string.
   *
   * @param list	the list of classes
   * @param stripPkg 	whether to strop the package name
   * @return		the generated string
   */
  public static String listToString(Class[] list, boolean stripPkg) {
    StringBuilder	result;

    result = new StringBuilder();
    for (Class cls: list) {
      if (result.length() > 0)
	result.append(",");
      if (stripPkg)
	result.append(cls.getSimpleName());
      else
	result.append(cls.getName());
    }

    return result.toString();
  }
}
