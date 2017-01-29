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
 * AbstractPixelBasedOutputGenerator.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.io;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Ancestor for output generators that are pixel based.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 * @param <T> the type of the intermediate data structure being generated
 */
public abstract class AbstractPixelBasedOutputGenerator<T>
  extends AbstractOutputGenerator<T> {

  /** the width. */
  protected int m_Width;

  /** the height. */
  protected int m_Height;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();
    setWidth(2000);
    setHeight(2000);
  }

  /**
   * Sets the width to use.
   *
   * @param value	the width
   */
  public void setWidth(int value) {
    if (value > 0)
      m_Width = value;
  }

  /**
   * Returns the width.
   *
   * @return		the width
   */
  public int getWidth() {
    return m_Width;
  }

  /**
   * Sets the height to use.
   *
   * @param value	the height
   */
  public void setHeight(int value) {
    if (value > 0)
      m_Height = value;
  }

  /**
   * Returns the height.
   *
   * @return		the height
   */
  public int getHeight() {
    return m_Height;
  }

  /**
   * Generates the output.
   *
   * @param test		the test results
   * @param output		the file to save the result in
   * @return			null if successfully generated, otherwise error message
   */
  public String generate(Map<String,List<Double>> test, File output) {
    if (m_Verbose) {
      m_Logger.info("Width: " + m_Width);
      m_Logger.info("Height: " + m_Height);
    }

    return super.generate(test, output);
  }
}
