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
 * PDF.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.io;

import com.googlecode.jfilechooserbookmarks.core.Utils;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import nz.ac.waikato.cms.core.FileUtils;

import java.awt.Graphics2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Generates PDF output.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class PDF
  extends AbstractOutputGeneratorWithGraphics2D<ByteArrayOutputStream> {

  /**
   * Returns the default extension to use.
   *
   * @return		the extension (excluding dot)
   */
  public String getExtension() {
    return "pdf";
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
  public ByteArrayOutputStream generatePlot(Map<String,List<Double>> test, double angle, Map<String,Integer> numFlips, int overallFlipCycles, StringBuilder errors) {
    ByteArrayOutputStream	result;
    Document			document;
    PdfWriter 			writer;
    PdfContentByte 		canvas;
    PdfTemplate 		template;
    Graphics2D			g;

    result   = new ByteArrayOutputStream();
    g        = null;
    document = null;
    try {
      document = new Document(new Rectangle(m_Width, m_Height));
      writer   = PdfWriter.getInstance(document, result);
      document.open();
      canvas   = writer.getDirectContent();
      template = canvas.createTemplate(m_Width, m_Height);
      g        = new PdfGraphics2D(template, m_Width, m_Height);
      draw(g, test, angle, numFlips, overallFlipCycles, errors);
      canvas.addTemplate(template, 0, 0);
    }
    catch (Exception e) {
      errors.append(Utils.throwableToString(e));
    }
    finally {
      try {
	if (g != null)
	  g.dispose();
      }
      catch (Exception e) {
	// ignored
      }
      try {
	if (document != null)
	  document.close();
      }
      catch (Exception e) {
	// ignored
      }
    }

    return result;
  }

  /**
   * Generates the output.
   *
   * @param plot		the plot to save
   * @param output		the file to save the result in
   * @return			null if successfully generated, otherwise error message
   */
  public String savePlot(ByteArrayOutputStream plot, File output) {
    FileOutputStream	stream;
    byte[]		data;

    stream = null;
    try {
      data   = plot.toByteArray();
      stream = new FileOutputStream(output);
      stream.write(data, 0, data.length);
    }
    catch (Exception e) {
      return "Failed to write output to '" + output + ": " + e;
    }
    finally {
      FileUtils.closeQuietly(stream);
    }

    return null;
  }
}
