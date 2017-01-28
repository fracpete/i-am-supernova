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
 * Supernova.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.gui;

import com.googlecode.jfilechooserbookmarks.gui.BasePanel;
import nz.ac.waikato.cms.gui.core.BaseDirectoryChooser;
import nz.ac.waikato.cms.gui.core.BaseFileChooser;
import nz.ac.waikato.cms.gui.core.BaseFrame;
import nz.ac.waikato.cms.gui.core.ExtensionFileFilter;

import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

/**
 * Interface for I Am Supernova.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Supernova
  extends BasePanel {

  /** tabbed pane. */
  protected JTabbedPane m_TabbedPane;

  /** the panel for generating a single graph. */
  protected BasePanel m_PanelSingle;

  /** the panel for batch processing. */
  protected BasePanel m_PanelBatch;

  /** the filechooser for csv files. */
  protected BaseFileChooser m_FileChooserCSV;

  /** the filechooser for saving plots. */
  protected BaseFileChooser m_FileChooserPlots;

  /** the directory chooser for the output directory. */
  protected BaseDirectoryChooser m_DirChooserOutput;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    ExtensionFileFilter		filter;
    
    super.initialize();
    
    m_FileChooserCSV = new BaseFileChooser();
    filter = new ExtensionFileFilter("CSV files", "csv");
    m_FileChooserCSV.addChoosableFileFilter(filter);
    m_FileChooserCSV.setFileFilter(filter);
    m_FileChooserCSV.setAcceptAllFileFilterUsed(false);
    m_FileChooserCSV.setFileSelectionMode(BaseFileChooser.FILES_ONLY);
    
    m_FileChooserPlots = new BaseFileChooser();
    filter = new ExtensionFileFilter("PNG file", "png");
    m_FileChooserPlots.addChoosableFileFilter(filter);
    m_FileChooserPlots.setFileFilter(filter);
    m_FileChooserPlots.setAcceptAllFileFilterUsed(false);
    m_FileChooserPlots.setFileSelectionMode(BaseFileChooser.FILES_ONLY);

    m_DirChooserOutput = new BaseDirectoryChooser();
  }

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    super.initGUI();

    setLayout(new BorderLayout());

    // single
    m_PanelSingle = new BasePanel(new BorderLayout());

    // batch
    m_PanelBatch = new BasePanel(new BorderLayout());

    m_TabbedPane = new JTabbedPane();
    m_TabbedPane.addTab("Graph", m_PanelSingle);
    m_TabbedPane.addTab("Batch", m_PanelBatch);
    add(m_TabbedPane);
  }

  /**
   * Starts the GUI.
   *
   * @param args	ignored
   */
  public static void main(String[] args) {
    BaseFrame frame = new BaseFrame("I Am Supernova");
    frame.setDefaultCloseOperation(BaseFrame.EXIT_ON_CLOSE);
    Supernova panel = new Supernova();
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(panel);
    frame.setSize(1024, 768);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
