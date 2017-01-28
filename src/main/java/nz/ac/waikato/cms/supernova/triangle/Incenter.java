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
 * Incenter.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.triangle;

/**
 * Calculates the incenter of a triangle.
 * https://en.wikipedia.org/wiki/Incenter
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Incenter
  extends AbstractTriangleCenterCalculation {

  /**
   * Calculates the center.
   *
   * @param w		the width of the triangle
   * @param h		the height of the triangle
   * @return		the x/y coordinates of the center
   */
  @Override
  public double[] calculate(double w, double h) {
    double	a;
    double	b;
    double	c;
    double	xa;
    double	ya;
    double	xb;
    double	yb;
    double	xc;
    double	yc;

    /*
      https://en.wikipedia.org/wiki/Incenter#Cartesian_coordinates

      y:percentile/h  ^
		      |    b
		      | -------
		      | \     |
		      |  \    |
		      |   \   | a
		      | c  \  |
		      |     \ |
		      |      \|
		      +-----------> x:score/w

     */

    a = h;
    b = w;
    c = Math.sqrt(a*a + b*b);

    xa = 0;
    ya = h;
    xb = w;
    yb = 0;
    xc = w;
    yc = h;

    return new double[]{
      (a*xa + b*xb + c*xc) / (a + b + c),
      (a*ya + b*yb + c*yc) / (a + b + c),
    };
  }
}
