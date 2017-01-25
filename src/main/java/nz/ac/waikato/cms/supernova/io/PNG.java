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
 * PNG.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.io;

import nz.ac.waikato.cms.supernova.Calc;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Generates PNG output.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class PNG
  extends AbstractPixelBasedOutputGenerator {

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
   * Generates the output.
   *
   * @param test		the test results (measure - [score, percentile])
   * @param angle		the angle to use
   * @param numFlips		the number of flips
   * @param overallFlipCycles	the overall flip cycles
   * @param output		the file to save the result in
   * @return			null if successfully generated, otherwise error message
   */
  @Override
  protected String doGenerate(Map<String,List<Double>> test, double angle, Map<String,Integer> numFlips, int overallFlipCycles, File output) {
    BufferedImage	img;
    Graphics2D		g;
    int			cx;
    int			cy;
    int			dx;
    int			dy;
    int			cycle;
    int			flip;
    int			w;
    int			h;
    double[]		tc;
    double		currentAngle;

    img = new BufferedImage(m_Width, m_Height, BufferedImage.TYPE_INT_ARGB);
    g   = img.createGraphics();

    // background
    g.setColor(m_Background);
    g.fillRect(0, 0, m_Width, m_Height);

    // center of image
    cx = m_Width / 2;
    cy = m_Height / 2;

    // draw triangles
    for (String measure: Calc.MEASURES) {
      g.setColor(adjustOpacity(m_Colors.get(measure)));
      currentAngle = 0.0;
      w = (int) Math.round(m_Width * (1.0 - 2*m_Margin) * (test.get(measure).get(0) / (5.0 * 2)));  // score (1 score unit = 10 percentile units)
      h = (int) Math.round(m_Height * (1.0 - 2*m_Margin) * (test.get(measure).get(1) / 100.0));  // percentile
      if (m_Verbose)
	m_Logger.info(measure + " - triangle dimensions: w=" + w + ", h=" + h);
      tc = Calc.incenter(w, h);
      if (m_Verbose)
	m_Logger.info(measure + " - triangle center: " + tc[0] + "/" + tc[1]);
      for (cycle = 0; cycle < overallFlipCycles; cycle++) {
	for (flip = 0; flip < numFlips.get(measure); flip++) {
	  dx = (int) (cx - tc[0]);
	  dy = (int) (cy - tc[1]);
	  g.rotate(currentAngle / 360.0 * 2 * Math.PI);
	  g.fillPolygon(new int[]{w, w, 0}, new int[]{adjustY(0), adjustY(h), adjustY(h)}, 3);
	  currentAngle += angle;
	  break;
	}
	break;
      }
    }

    // clean up
    g.dispose();

    try {
      ImageIO.write(img, "png", output);
    }
    catch (Exception e) {
      return "Failed to write output to '" + output + ": " + e;
    }

    return null;
  }
}
