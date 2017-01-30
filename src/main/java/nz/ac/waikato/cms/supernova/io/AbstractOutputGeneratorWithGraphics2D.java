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
 * AbstractOutputGeneratorWithGraphics2D.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.io;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;

/**
 * Ancestor for output generators that use Graphics2D.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 * @param <T> the type of the intermediate data structure being generated
 */
public abstract class AbstractOutputGeneratorWithGraphics2D<T>
  extends AbstractOutputGeneratorWithDimensions<T> {

  /**
   * Adjusts the y value.
   *
   * @param y		the y to adjust
   * @return		the adjust value
   */
  protected int adjustY(int y) {
    return m_Height - y + 1;
  }

  /**
   * Rotates the triangle point around the center of the triangle.
   *
   * @param p		the point of the triangle to rotate
   * @param tc 		the triangle center
   * @param angle	the angle to rotate counterclockwise (in degree)
   * @return		the rotated point (y unadjusted!)
   */
  protected double[] rotate(double[] p, double[] tc, double angle) {
    double[] 	pNew;
    double[] 	pRot;
    double	rad;
    double[]	pFin;

    pNew    = new double[2];
    // move origin from triangle into (0,0)
    pNew[0] = p[0] - tc[0];
    pNew[1] = p[1] - tc[1];
    // rotate point
    // https://en.wikipedia.org/wiki/Rotation_(mathematics)#Two_dimensions
    pRot    = new double[2];
    rad     = Math.toRadians(angle);
    pRot[0] = pNew[0]*Math.cos(rad) - pNew[1]*Math.sin(rad);
    pRot[1] = pNew[0]*Math.sin(rad) + pNew[1]*Math.cos(rad);
    // move origin back into triangle center
    pFin    = new double[2];
    pFin[0] = pRot[0] + tc[0];
    pFin[1] = pRot[1] + tc[1];

    return pFin;
  }

  /**
   * Performs the drawing.
   *
   * @param g			the graphics context
   * @param angle		the angle to use
   * @param numFlips		the number of flips
   * @param overallFlipCycles	the overall flip cycles
   * @param errors		for storing error messages
   */
  protected void draw(Graphics2D g, Map<String,List<Double>> test, double angle, Map<String,Integer> numFlips, int overallFlipCycles, StringBuilder errors) {
    int			cx;
    int			cy;
    int			dx;
    int			dy;
    int			cycle;
    int			flip;
    int			w;
    int			h;
    int			m;
    String 		measure;
    double[]		tc;
    double[]		a;
    double[]		b;
    double[]		c;
    double		currentAngle;

    // background
    g.setColor(m_Background);
    g.fillRect(0, 0, m_Width, m_Height);

    // center of image
    cx = m_Width / 2;
    cy = m_Height / 2;

    // draw triangles
    for (m = 0; m < MEASURES.length; m++) {
      measure = MEASURES[m];
      g.setColor(adjustOpacity(m_Colors.get(measure)));
      currentAngle = angle * m;
      w = (int) Math.round(m_Width * (1.0 - 2*m_Margin) * (test.get(measure).get(0) / (5.0 * 2)));  // score (1 score unit = 10 percentile units)
      h = (int) Math.round(m_Height * (1.0 - 2*m_Margin) * (test.get(measure).get(1) / 100.0));  // percentile
      if (m_Verbose)
	m_Logger.info(measure + " - triangle dimensions: w=" + w + ", h=" + h);
      tc = m_Center.calculate(w, h);
      if (m_Verbose)
	m_Logger.info(measure + " - triangle center: " + tc[0] + "/" + tc[1]);
      for (cycle = 0; cycle < overallFlipCycles; cycle++) {
	for (flip = 0; flip < numFlips.get(measure); flip++) {
	  dx = (int) (cx - tc[0]);
	  dy = (int) (cy - tc[1]);
	  a  = rotate(new double[]{0, h}, tc, currentAngle);
	  b  = rotate(new double[]{w, 0}, tc, currentAngle);
	  c  = rotate(new double[]{w, h}, tc, currentAngle);
	  g.fillPolygon(
	    new int[]{(int) a[0] + dx, (int) b[0] + dx, (int) c[0] + dx},
	    new int[]{adjustY((int) a[1] + dy), adjustY((int) b[1] + dy), adjustY((int) c[1] + dy)},
	    3);
	  currentAngle += angle;
	  if (m_OnlyFirstIteration)
	    break;
	}
	if (m_OnlyFirstIteration)
	  break;
      }
    }
  }
}
