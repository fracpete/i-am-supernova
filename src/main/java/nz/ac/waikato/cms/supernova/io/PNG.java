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
   * Generates the output.
   *
   * @param test		the test results
   * @param angle		the angle to use
   * @param numFlips		the number of flips
   * @param overallFlipCycles	the overall flip cycles
   * @param output		the file to save the result in
   * @return			null if successfully generated, otherwise error message
   */
  @Override
  protected String doGenerate(Map<String,List<Double>> test, double angle, Map<String,Integer> numFlips, int overallFlipCycles, File output) {
    return null;
  }
}
