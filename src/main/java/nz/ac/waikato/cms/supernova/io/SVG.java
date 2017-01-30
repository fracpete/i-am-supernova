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
 * SVG.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.io;

import nz.ac.waikato.cms.core.FileUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

/**
 * Generates SVG output.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class SVG
  extends AbstractOutputGeneratorWithGraphics2D<String> {

  /**
   * Returns the default extension to use.
   *
   * @return		the extension (excluding dot)
   */
  public String getExtension() {
    return "svg";
  }

  /**
   * Generates the intermediate data structure.
   *
   * @param test		the test results (measure - [score, percentile])
   * @param angle		the angle to use
   * @param numFlips		the number of flips
   * @param overallFlipCycles	the overall flip cycles
   * @param errors		for storing error messages
   * @return			null if successfully generated, otherwise error message
   */
  public String generatePlot(Map<String,List<Double>> test, double angle, Map<String,Integer> numFlips, int overallFlipCycles, StringBuilder errors) {
    String		result;
    SVGGraphics2D 	g;

    g = new SVGGraphics2D(m_Width, m_Height);
    draw(g, test, angle, numFlips, overallFlipCycles, errors);
    result = g.getSVGDocument();
    g.dispose();

    return result;
  }

  /**
   * Generates the output.
   *
   * @param plot		the plot to save
   * @param output		the file to save the result in
   * @return			null if successfully generated, otherwise error message
   */
  public String savePlot(String plot, File output) {
    BufferedWriter	bwriter;
    FileWriter		fwriter;

    bwriter = null;
    fwriter = null;
    try {
      fwriter = new FileWriter(output);
      bwriter = new BufferedWriter(fwriter);
      bwriter.write(plot);
      bwriter.flush();
    }
    catch (Exception e) {
      return "Failed to write output to '" + output + ": " + e;
    }
    finally {
      FileUtils.closeQuietly(bwriter);
      FileUtils.closeQuietly(fwriter);
    }

    return null;
  }
}
