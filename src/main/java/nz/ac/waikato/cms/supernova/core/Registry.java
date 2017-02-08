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

package nz.ac.waikato.cms.supernova.core;

import nz.ac.waikato.cms.supernova.io.AbstractOutputGenerator;
import nz.ac.waikato.cms.supernova.triangle.AbstractTriangleCenterCalculation;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry for available classes.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Registry {

  /**
   * Returns the available output generators.
   *
   * @return		the generators
   */
  public static Class[] getGenerators() {
    return SupernovaClassLister.getSingleton().getClasses(AbstractOutputGenerator.class);
  }

  /**
   * Returns the available triangle center calculations.
   *
   * @return		the centers
   */
  public static Class[] getCenters() {
    return SupernovaClassLister.getSingleton().getClasses(AbstractTriangleCenterCalculation.class);
  }

  /**
   * Turns a class array into a comma-separated string.
   *
   * @param array	the array of classes
   * @param stripPkg 	whether to strop the package name
   * @return		the generated string
   */
  public static String toString(Class[] array, boolean stripPkg) {
    StringBuilder	result;

    result = new StringBuilder();
    for (Class cls: array) {
      if (result.length() > 0)
	result.append(",");
      if (stripPkg)
	result.append(cls.getSimpleName());
      else
	result.append(cls.getName());
    }

    return result.toString();
  }

  /**
   * Turns a class array into a string array.
   *
   * @param array	the array of classes
   * @param stripPkg 	whether to strop the package name
   * @return		the generated string array
   */
  public static String[] toStringArray(Class[] array, boolean stripPkg) {
    List<String> result;

    result = new ArrayList<>();
    for (Class cls: array) {
      if (stripPkg)
	result.add(cls.getSimpleName());
      else
	result.add(cls.getName());
    }

    return result.toArray(new String[result.size()]);
  }
}
