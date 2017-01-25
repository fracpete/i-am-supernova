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
 * Calc.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Library for calculating the parameters based on the test results.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Calc {

  public static final String EXTRAVERSION = "extraversion";

  public static final String CONSCIENTIOUSNESS = "conscientiousness";

  public static final String NEUROTICISM = "neuroticism";

  public static final String AGREEABLENESS = "agreeableness";

  public static final String OPENNESS = "openness";

  public static final String[] MEASURES = new String[]{
    OPENNESS,
    EXTRAVERSION,
    AGREEABLENESS,
    CONSCIENTIOUSNESS,
    NEUROTICISM
  };

  /**
   * Calculates the angle.
   *
   * @param test	the test results (measure - [score, percentile])
   * @return		the angle
   */
  public static double calcAngle(Map<String, List<Double>> test) {
    double result = 0;
    for (String key: test.keySet())
      result += test.get(key).get(1) / 5.0;
    return result;
  }

  /**
   * Calculates the number of flips for each test result.
   *
   * @param test	the test results (measure - [score, percentile])
   * @return		the number of flips
   */
  public static Map<String, Integer> calcNumFlips(Map<String, List<Double>> test) {
    Map<String,Integer> result = new HashMap<>();
    for (String key: test.keySet()) {
      int flips;
      double percentile = test.get(key).get(1);
      if (percentile <= 19.0)
	flips = 1;
      else if (percentile <= 39)
	flips = 2;
      else if (percentile <= 59)
	flips = 3;
      else if (percentile <= 79)
	flips = 4;
      else
	flips = 5;
      result.put(key, flips);
    }
    return result;
  }

  /**
   * Calculates the overall flip cycles.
   *
   * @param test	the test results (measure - [score, percentile])
   * @return		the cycles
   */
  public static double calcOverallFlipCycles(Map<String, List<Double>> test) {
    double result = 0.0;
    for (String key: test.keySet())
      result += test.get(key).get(0);
    result = Math.round(result);
    return result;
  }
}
