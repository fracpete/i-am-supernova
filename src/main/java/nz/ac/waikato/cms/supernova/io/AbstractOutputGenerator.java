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
 * AbstractOutputGenerator.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.io;

import nz.ac.waikato.cms.supernova.gui.ColorHelper;
import nz.ac.waikato.cms.supernova.triangle.AbstractTriangleCenterCalculation;
import nz.ac.waikato.cms.supernova.triangle.Incenter;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Ancestor for output generates.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractOutputGenerator {

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

  /** for logging. */
  protected Logger m_Logger;

  /** verbose flag. */
  protected boolean m_Verbose;

  /** the colors to use. */
  protected Map<String,Color> m_Colors;

  /** the background. */
  protected Color m_Background;

  /** the opacity (0-1). */
  protected double m_Opacity;

  /** the margin (0-1). */
  protected double m_Margin;

  /** the triangle center algorithm. */
  protected AbstractTriangleCenterCalculation m_Center;

  /**
   * Default constructor.
   *
   * @see #initialize()
   */
  protected AbstractOutputGenerator() {
    initialize();
  }

  /**
   * For initializing the members.
   */
  protected void initialize() {
    m_Logger     = Logger.getLogger(getClass().getName());
    m_Verbose    = false;
    m_Opacity    = 0.1;
    m_Margin     = 0.1;
    m_Background = Color.BLACK;
    m_Center     = new Incenter();
    m_Colors     = new HashMap<>();
    m_Colors.put(OPENNESS,          Color.ORANGE);
    m_Colors.put(EXTRAVERSION,      Color.YELLOW);
    m_Colors.put(AGREEABLENESS,     Color.GREEN);
    m_Colors.put(CONSCIENTIOUSNESS, Color.BLUE);
    m_Colors.put(NEUROTICISM,       Color.RED);
  }

  /**
   * Sets whether to use verbose mode.
   *
   * @param value	true if to use verbose
   */
  public void setVerbose(boolean value) {
    m_Verbose = value;
  }

  /**
   * Returns whether to use verbose mode.
   *
   * @return		true if to use verbose
   */
  public boolean getVerbose() {
    return m_Verbose;
  }

  /**
   * Sets the colors to use.
   *
   * @param value	the colors
   */
  public void setColors(Map<String,Color> value) {
    m_Colors = value;
  }

  /**
   * Returns the colors in use.
   *
   * @return		the colors
   */
  public Map<String,Color> getColors() {
    return m_Colors;
  }

  /**
   * Sets the background.
   *
   * @param value	the background
   */
  public void setBackground(Color value) {
    m_Background = value;
  }

  /**
   * Returns the background.
   *
   * @return		the background
   */
  public Color getBackground() {
    return m_Background;
  }

  /**
   * Sets the opacity.
   *
   * @param value	the opacity (0-1)
   */
  public void setOpacity(double value) {
    if ((value >= 0) && (value <= 1.0))
      m_Opacity = value;
  }

  /**
   * Returns the opacity.
   *
   * @return		the opacity (0-1)
   */
  public double getOpacity() {
    return m_Opacity;
  }

  /**
   * Sets the margin.
   *
   * @param value	the margin (0-1)
   */
  public void setMargin(double value) {
    if ((value >= 0) && (value <= 1.0))
      m_Margin = value;
  }

  /**
   * Returns the margin.
   *
   * @return		the margin (0-1)
   */
  public double getMargin() {
    return m_Margin;
  }

  /**
   * Sets the algorithm for calculating the center of a triangle.
   *
   * @param value	the algorithm
   */
  public void setCenter(AbstractTriangleCenterCalculation value) {
    m_Center = value;
  }

  /**
   * Returns the algorithm for calculating the center of a triangle.
   *
   * @return		the algorithm
   */
  public AbstractTriangleCenterCalculation getCenter() {
    return m_Center;
  }

  /**
   * Adjusts the alpha value of the color using the current opacity.
   *
   * @param color	the color to adjust
   * @return		the adjusted color
   */
  protected Color adjustOpacity(Color color) {
    return new Color(
      color.getRed(),
      color.getGreen(),
      color.getBlue(),
      (int) Math.round(255 * m_Opacity)
    );
  }

  /**
   * Calculates the angle.
   *
   * @param test	the test results (measure - [score, percentile])
   * @return		the angle
   */
  protected double calcAngle(Map<String, List<Double>> test) {
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
  protected Map<String, Integer> calcNumFlips(Map<String, List<Double>> test) {
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
  protected double calcOverallFlipCycles(Map<String, List<Double>> test) {
    double result = 0.0;
    for (String key: test.keySet())
      result += test.get(key).get(0);
    result = Math.round(result);
    return result;
  }

  /**
   * Returns the default extension to use.
   *
   * @return		the extension (excluding dot)
   */
  public abstract String getExtension();

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
  protected abstract String doGenerate(Map<String,List<Double>> test, double angle, Map<String,Integer> numFlips, int overallFlipCycles, File output);

  /**
   * Generates the output.
   *
   * @param test		the test results (measure - [score, percentile])
   * @param output		the file to save the result in
   * @return			null if successfully generated, otherwise error message
   */
  public String generate(Map<String,List<Double>> test, File output) {
    if (m_Verbose) {
      Map<String,String> colorsStr = new HashMap<>();
      for (String key: m_Colors.keySet())
	colorsStr.put(key, ColorHelper.toHex(m_Colors.get(key)));
      m_Logger.info("Colors: " + colorsStr);
      m_Logger.info("Background: " + ColorHelper.toHex(m_Background));
      m_Logger.info("Opacity: " + m_Opacity);
      m_Logger.info("Margin: " + m_Margin);
      m_Logger.info("Test: " + test);
    }

    double angle = calcAngle(test);
    if (m_Verbose)
      m_Logger.info("angle: " + angle);

    Map<String, Integer> numFlips = calcNumFlips(test);
    if (m_Verbose)
      m_Logger.info("#Flips: " + numFlips);

    double overallFlipCycles = calcOverallFlipCycles(test);
    if (m_Verbose)
      m_Logger.info("Overall flip cycles: " + overallFlipCycles);

    return doGenerate(test, angle, numFlips, (int) overallFlipCycles, output);
  }
}
