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

import nz.ac.waikato.cms.supernova.Calc;
import nz.ac.waikato.cms.supernova.ColorHelper;

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
    m_Colors     = new HashMap<>();
    m_Colors.put(Calc.OPENNESS,          Color.ORANGE);
    m_Colors.put(Calc.EXTRAVERSION,      Color.YELLOW);
    m_Colors.put(Calc.AGREEABLENESS,     Color.GREEN);
    m_Colors.put(Calc.CONSCIENTIOUSNESS, Color.BLUE);
    m_Colors.put(Calc.NEUROTICISM,       Color.RED);
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

    double angle = Calc.calcAngle(test);
    if (m_Verbose)
      m_Logger.info("angle: " + angle);

    Map<String, Integer> numFlips = Calc.calcNumFlips(test);
    if (m_Verbose)
      m_Logger.info("#Flips: " + numFlips);

    double overallFlipCycles = Calc.calcOverallFlipCycles(test);
    if (m_Verbose)
      m_Logger.info("Overall flip cycles: " + overallFlipCycles);

    return doGenerate(test, angle, numFlips, (int) overallFlipCycles, output);
  }
}
