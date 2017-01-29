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
 * ImagePanel.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.gui;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Panel for previewing an image.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class ImagePanel
  extends JPanel {

  /** the image to paint. */
  protected BufferedImage m_Image;

  /**
   * Initializes the panel.
   */
  public ImagePanel() {
    super();
    m_Image = null;
  }

  /**
   * Sets the image to draw.
   *
   * @param value	the image, can be null
   */
  public void setImage(BufferedImage value) {
    m_Image = value;
    setPreferredSize(new Dimension(m_Image.getWidth(), m_Image.getHeight()));
    if (getParent() != null)
      getParent().revalidate();
    repaint();
  }

  /**
   * Returns the current image.
   *
   * @return		the image, null if none set
   */
  public BufferedImage getImage() {
    return m_Image;
  }

  /**
   * Paints the component.
   *
   * @param g		the context
   */
  @Override
  protected void paintComponent(Graphics g) {
    if (m_Image == null) {
      super.paintComponent(g);
      return;
    }

    g.drawImage(m_Image, 0, 0, m_Image.getWidth(), m_Image.getHeight(), null);
  }
}
