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

import com.github.fracpete.jclipboardhelper.ClipboardHelper;
import com.googlecode.jfilechooserbookmarks.core.Utils;
import com.googlecode.jfilechooserbookmarks.gui.BasePanel;
import com.googlecode.jfilechooserbookmarks.gui.BaseScrollPane;
import nz.ac.waikato.cms.core.BrowserHelper;
import nz.ac.waikato.cms.gui.core.BaseFrame;
import nz.ac.waikato.cms.gui.core.DirectoryChooserPanel;
import nz.ac.waikato.cms.gui.core.ExtensionFileFilter;
import nz.ac.waikato.cms.gui.core.FileChooserPanel;
import nz.ac.waikato.cms.gui.core.GUIHelper;
import nz.ac.waikato.cms.supernova.core.Registry;
import nz.ac.waikato.cms.supernova.io.AbstractOutputGenerator;
import nz.ac.waikato.cms.supernova.io.AbstractOutputGeneratorWithDimensions;
import nz.ac.waikato.cms.supernova.io.PNG;
import nz.ac.waikato.cms.supernova.triangle.AbstractTriangleCenterCalculation;
import nz.ac.waikato.cms.supernova.triangle.Incenter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interface for I Am Supernova.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Supernova
  extends BasePanel {

  /** for logging. */
  protected Logger m_Logger;

  /** tabbed pane. */
  protected JTabbedPane m_TabbedPane;

  /** the panel for generating a single graph. */
  protected BasePanel m_PanelSingle;

  /** the preview of the generated image. */
  protected ImagePanel m_PanelSinglePreview;

  /** the table for the statistics. */
  protected StatisticsTable m_SingleStatistics;

  /** the background. */
  protected ColorButton m_SingleBackground;

  /** the opacity. */
  protected JSpinner m_SingleOpacity;

  /** the margin. */
  protected JSpinner m_SingleMargin;

  /** the width. */
  protected JSpinner m_SingleWidth;

  /** the height. */
  protected JSpinner m_SingleHeight;

  /** the generator. */
  protected JComboBox m_SingleGenerator;

  /** the center. */
  protected JComboBox m_SingleCenter;

  /** the output. */
  protected FileChooserPanel m_SingleOutput;

  /** generates the preview. */
  protected JButton m_SinglePreview;

  /** copies the preview. */
  protected JButton m_SingleCopy;

  /** generates the output. */
  protected JButton m_SingleGenerate;

  /** the panel for batch processing. */
  protected BasePanel m_PanelBatch;

  /** the log for batch processing. */
  protected JTextArea m_BatchLog;

  /** the table for the statistics. */
  protected ColorTable m_BatchColors;

  /** the background. */
  protected ColorButton m_BatchBackground;

  /** the opacity. */
  protected JSpinner m_BatchOpacity;

  /** the margin. */
  protected JSpinner m_BatchMargin;

  /** the width. */
  protected JSpinner m_BatchWidth;

  /** the height. */
  protected JSpinner m_BatchHeight;

  /** the generator. */
  protected JComboBox m_BatchGenerator;

  /** the center. */
  protected JComboBox m_BatchCenter;

  /** the CSV file. */
  protected FileChooserPanel m_BatchCSV;

  /** the output. */
  protected DirectoryChooserPanel m_BatchOutput;

  /** generates the output. */
  protected JButton m_BatchGenerate;

  /** the collected labels to be adjusted. */
  protected List<JLabel> m_ParamLabels;

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_ParamLabels = new ArrayList<>();
    m_Logger      = Logger.getLogger(getClass().getName());
  }

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    super.initGUI();

    setLayout(new BorderLayout());

    m_ParamLabels.clear();
    m_PanelSingle = createSinglePanel();

    m_ParamLabels.clear();
    m_PanelBatch = createBatchPanel();

    m_TabbedPane = new JTabbedPane();
    m_TabbedPane.addTab("Graph", m_PanelSingle);
    m_TabbedPane.addTab("Batch", m_PanelBatch);
    add(m_TabbedPane);
  }

  /**
   * Creates a panel with a parameter.
   *
   * @param labelText	the label text
   * @param comp	the component
   * @return		the panel
   */
  protected JPanel createParameter(String labelText, JComponent comp) {
    JPanel	param;
    JLabel	label;

    label = new JLabel(labelText);
    label.setLabelFor(comp);
    param = new JPanel(new FlowLayout(FlowLayout.LEFT));
    param.add(label);
    param.add(comp);
    m_ParamLabels.add(label);

    return param;
  }

  /**
   * Adjusts the collected labels.
   */
  protected void adjustLabels() {
    int		max;

    max = 0;
    for (JLabel label: m_ParamLabels)
      max = (int) Math.max(label.getPreferredSize().getWidth(), max);

    for (JLabel label: m_ParamLabels)
      label.setPreferredSize(new Dimension(max, (int) label.getPreferredSize().getHeight()));
  }

  /**
   * Creates the panel for generating a single graph.
   *
   * @return		the panel
   */
  protected BasePanel createSinglePanel() {
    BasePanel 	result;
    JPanel	params;
    JPanel 	left1;
    JPanel 	left2;
    JPanel	panel;
    JPanel	panel2;

    result = new BasePanel(new BorderLayout());
    panel = new JPanel(new BorderLayout());
    result.add(panel, BorderLayout.CENTER);
    m_PanelSinglePreview = new ImagePanel();
    panel.add(new BaseScrollPane(m_PanelSinglePreview), BorderLayout.CENTER);
    panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panel.add(panel2, BorderLayout.SOUTH);
    m_SingleCopy = new JButton(GUIHelper.getIcon("copy.gif"));
    m_SingleCopy.setEnabled(false);
    m_SingleCopy.addActionListener((ActionEvent e) -> copySinglePreview());
    panel2.add(m_SingleCopy);

    left1 = new JPanel(new BorderLayout());
    result.add(left1, BorderLayout.WEST);

    // params with height 2
    params = new JPanel(new GridLayout(0, 1));
    left1.add(params, BorderLayout.NORTH);

    m_SingleStatistics = new StatisticsTable();
    left1.add(new BaseScrollPane(m_SingleStatistics), BorderLayout.NORTH);

    // params with height 1
    panel = new JPanel(new BorderLayout());
    left1.add(panel, BorderLayout.CENTER);
    left2 = new JPanel(new BorderLayout());
    panel.add(left2, BorderLayout.NORTH);
    params = new JPanel(new GridLayout(0, 1));
    left2.add(params, BorderLayout.NORTH);

    // background
    m_SingleBackground = new ColorButton(Color.BLACK);
    params.add(createParameter("Background", m_SingleBackground));

    // opacity
    m_SingleOpacity = new JSpinner();
    m_SingleOpacity.setValue(10);
    ((SpinnerNumberModel) m_SingleOpacity.getModel()).setMinimum(0);
    ((SpinnerNumberModel) m_SingleOpacity.getModel()).setMaximum(100);
    ((SpinnerNumberModel) m_SingleOpacity.getModel()).setStepSize(10);
    ((JSpinner.DefaultEditor) m_SingleOpacity.getEditor()).getTextField().setColumns(5);
    params.add(createParameter("Opacity %", m_SingleOpacity));

    // margin
    m_SingleMargin = new JSpinner();
    m_SingleMargin.setValue(20);
    ((SpinnerNumberModel) m_SingleMargin.getModel()).setMinimum(0);
    ((SpinnerNumberModel) m_SingleMargin.getModel()).setMaximum(100);
    ((SpinnerNumberModel) m_SingleMargin.getModel()).setStepSize(10);
    ((JSpinner.DefaultEditor) m_SingleMargin.getEditor()).getTextField().setColumns(5);
    params.add(createParameter("Margin %", m_SingleMargin));

    // width
    m_SingleWidth = new JSpinner();
    m_SingleWidth.setValue(400);
    ((SpinnerNumberModel) m_SingleWidth.getModel()).setMinimum(1);
    ((SpinnerNumberModel) m_SingleWidth.getModel()).setStepSize(100);
    ((JSpinner.DefaultEditor) m_SingleWidth.getEditor()).getTextField().setColumns(5);
    params.add(createParameter("Width", m_SingleWidth));

    // height
    m_SingleHeight = new JSpinner();
    m_SingleHeight.setValue(400);
    ((SpinnerNumberModel) m_SingleHeight.getModel()).setMinimum(1);
    ((SpinnerNumberModel) m_SingleHeight.getModel()).setStepSize(100);
    ((JSpinner.DefaultEditor) m_SingleHeight.getEditor()).getTextField().setColumns(5);
    params.add(createParameter("Height", m_SingleHeight));

    // generator
    m_SingleGenerator = new JComboBox<>(Registry.toStringArray(Registry.getGenerators(), true));
    m_SingleGenerator.addActionListener((ActionEvent e) -> {
      updateSingleOutput();
    });
    params.add(createParameter("Generator", m_SingleGenerator));

    // center
    m_SingleCenter = new JComboBox<>(Registry.toStringArray(Registry.getCenters(), true));
    params.add(createParameter("Center", m_SingleCenter));

    // output
    m_SingleOutput = new FileChooserPanel();
    m_SingleOutput.setUseSaveDialog(true);
    m_SingleOutput.setPreferredSize(new Dimension(170, (int) m_SingleOutput.getPreferredSize().getHeight()));
    params.add(createParameter("Output", m_SingleOutput));

    // preview/generate
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    m_SinglePreview = new JButton("Preview");
    m_SinglePreview.addActionListener((ActionEvent e) -> generateSinglePreview());
    m_SingleGenerate = new JButton("Generate");
    m_SingleGenerate.addActionListener((ActionEvent e) -> generateSingleOutput());
    panel.add(m_SinglePreview);
    panel.add(m_SingleGenerate);
    params.add(panel);

    adjustLabels();

    return result;
  }

  /**
   * Updates the filechooser for the single output.
   */
  protected void updateSingleOutput() {
    ExtensionFileFilter		filter;
    String			cls;
    AbstractOutputGenerator 	generator;

    if (m_SingleGenerator.getSelectedIndex() == -1)
      return;
    try {
      cls       = AbstractOutputGenerator.class.getPackage().getName() + "." + m_SingleGenerator.getSelectedItem();
      generator = (AbstractOutputGenerator) Class.forName(cls).newInstance();
      filter    = new ExtensionFileFilter(generator.getExtension().toUpperCase() + " files", generator.getExtension());
      m_SingleOutput.removeChoosableFileFilters();
      m_SingleOutput.addChoosableFileFilter(filter);
      m_SingleOutput.setFileFilter(filter);
    }
    catch (Exception ex) {
      m_SingleOutput.removeChoosableFileFilters();
    }
  }

  /**
   * Copies the current preview to the clipboard.
   */
  protected void copySinglePreview() {
    if (m_PanelSinglePreview.getImage() != null)
      ClipboardHelper.copyToClipboard(m_PanelSinglePreview.getImage());
  }

  /**
   * Creates the panel for batch generation.
   *
   * @return		the panel
   */
  protected BasePanel createBatchPanel() {
    BasePanel 	result;
    JPanel	params;
    JPanel 	left1;
    JPanel 	left2;
    JPanel	panel;

    result = new BasePanel(new BorderLayout());
    m_BatchLog = new JTextArea(20, 40);
    m_BatchLog.setFont(Font.decode("Monospaced-PLAIN-12"));
    result.add(new BaseScrollPane(m_BatchLog), BorderLayout.CENTER);

    left1 = new JPanel(new BorderLayout());
    result.add(left1, BorderLayout.WEST);

    // params with height 2
    params = new JPanel(new GridLayout(0, 1));
    left1.add(params, BorderLayout.NORTH);

    m_BatchColors = new ColorTable();
    left1.add(new BaseScrollPane(m_BatchColors), BorderLayout.NORTH);

    // params with height 1
    panel = new JPanel(new BorderLayout());
    left1.add(panel, BorderLayout.CENTER);
    left2 = new JPanel(new BorderLayout());
    panel.add(left2, BorderLayout.NORTH);
    params = new JPanel(new GridLayout(0, 1));
    left2.add(params, BorderLayout.NORTH);

    // background
    m_BatchBackground = new ColorButton(Color.BLACK);
    params.add(createParameter("Background", m_BatchBackground));

    // opacity
    m_BatchOpacity = new JSpinner();
    m_BatchOpacity.setValue(10);
    ((SpinnerNumberModel) m_BatchOpacity.getModel()).setMinimum(0);
    ((SpinnerNumberModel) m_BatchOpacity.getModel()).setMaximum(100);
    ((SpinnerNumberModel) m_BatchOpacity.getModel()).setStepSize(10);
    ((JSpinner.DefaultEditor) m_BatchOpacity.getEditor()).getTextField().setColumns(5);
    params.add(createParameter("Opacity %", m_BatchOpacity));

    // margin
    m_BatchMargin = new JSpinner();
    m_BatchMargin.setValue(20);
    ((SpinnerNumberModel) m_BatchMargin.getModel()).setMinimum(0);
    ((SpinnerNumberModel) m_BatchMargin.getModel()).setMaximum(100);
    ((SpinnerNumberModel) m_BatchMargin.getModel()).setStepSize(10);
    ((JSpinner.DefaultEditor) m_BatchMargin.getEditor()).getTextField().setColumns(5);
    params.add(createParameter("Margin %", m_BatchMargin));

    // width
    m_BatchWidth = new JSpinner();
    m_BatchWidth.setValue(400);
    ((SpinnerNumberModel) m_BatchWidth.getModel()).setMinimum(1);
    ((SpinnerNumberModel) m_BatchWidth.getModel()).setStepSize(100);
    ((JSpinner.DefaultEditor) m_BatchWidth.getEditor()).getTextField().setColumns(5);
    params.add(createParameter("Width", m_BatchWidth));

    // height
    m_BatchHeight = new JSpinner();
    m_BatchHeight.setValue(400);
    ((SpinnerNumberModel) m_BatchHeight.getModel()).setMinimum(1);
    ((SpinnerNumberModel) m_BatchHeight.getModel()).setStepSize(100);
    ((JSpinner.DefaultEditor) m_BatchHeight.getEditor()).getTextField().setColumns(5);
    params.add(createParameter("Height", m_BatchHeight));

    // generator
    m_BatchGenerator = new JComboBox<>(Registry.toStringArray(Registry.getGenerators(), true));
    params.add(createParameter("Generator", m_BatchGenerator));

    // center
    m_BatchCenter = new JComboBox<>(Registry.toStringArray(Registry.getCenters(), true));
    params.add(createParameter("Center", m_BatchCenter));

    // csv
    m_BatchCSV = new FileChooserPanel();
    m_BatchCSV.addChoosableFileFilter(new ExtensionFileFilter("CSV files", "csv"));
    m_BatchCSV.setPreferredSize(new Dimension(170, (int) m_BatchCSV.getPreferredSize().getHeight()));
    params.add(createParameter("CSV", m_BatchCSV));

    // output
    m_BatchOutput = new DirectoryChooserPanel();
    m_BatchOutput.setPreferredSize(new Dimension(170, (int) m_BatchOutput.getPreferredSize().getHeight()));
    params.add(createParameter("Output", m_BatchOutput));

    // generate
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    m_BatchGenerate = new JButton("Generate");
    m_BatchGenerate.addActionListener((ActionEvent e) -> {
      SwingWorker worker = new SwingWorker() {
	@Override
	protected Object doInBackground() throws Exception {
	  generateBatchOutput();
	  return null;
	}
      };
      worker.execute();
    });
    panel.add(m_BatchGenerate);
    params.add(panel);

    adjustLabels();

    return result;
  }

  /**
   * Finishes the initialization.
   */
  @Override
  protected void finishInit() {
    super.finishInit();
    updateSingleOutput();
  }

  /**
   * Creates the menu bar.
   *
   * @return		the menu bar
   */
  public JMenuBar createMenuBar() {
    JMenuBar	result;
    JMenu	menu;
    JMenuItem	menuitem;

    result = new JMenuBar();

    menu = new JMenu("Program");
    result.add(menu);

    menuitem = new JMenuItem("Close", GUIHelper.getIcon("stop.gif"));
    menuitem.addActionListener((ActionEvent e) -> closeParent());
    menu.add(menuitem);

    menu = new JMenu("Help");
    result.add(menu);

    menuitem = new JMenuItem("Homepage", GUIHelper.getIcon("homepage.png"));
    menuitem.addActionListener((ActionEvent e) -> BrowserHelper.openURL("https://github.com/fracpete/i-am-supernova"));
    menu.add(menuitem);

    return result;
  }

  /**
   * Updates the generator with the values from the "single" tab.
   *
   * @param generator	the generator to update
   */
  protected void configureSingle(AbstractOutputGenerator generator) {
    String				cls;
    AbstractOutputGeneratorWithDimensions pixel;

    generator.setColors(m_SingleStatistics.getColors());
    generator.setBackground(m_SingleBackground.getColor());
    generator.setOpacity(((Number) m_SingleOpacity.getValue()).doubleValue() / 100.0);
    generator.setMargin(((Number) m_SingleMargin.getValue()).doubleValue() / 100.0);
    if (generator instanceof AbstractOutputGeneratorWithDimensions) {
      pixel = (AbstractOutputGeneratorWithDimensions) generator;
      pixel.setWidth(((Number) m_SingleWidth.getValue()).intValue());
      pixel.setHeight(((Number) m_SingleHeight.getValue()).intValue());
    }
    try {
      cls = AbstractTriangleCenterCalculation.class.getPackage().getName() + "." + m_SingleCenter.getSelectedItem();
      generator.setCenter((AbstractTriangleCenterCalculation) Class.forName(cls).newInstance());
    }
    catch (Exception e) {
      generator.setCenter(new Incenter());
    }
  }

  /**
   * Updates the generator with the values from the "batch" tab.
   *
   * @param generator	the generator to update
   */
  protected void configureBatch(AbstractOutputGenerator generator) {
    String				cls;
    AbstractOutputGeneratorWithDimensions pixel;

    generator.setColors(m_BatchColors.getColors());
    generator.setBackground(m_BatchBackground.getColor());
    generator.setOpacity(((Number) m_BatchOpacity.getValue()).doubleValue() / 100.0);
    generator.setMargin(((Number) m_BatchMargin.getValue()).doubleValue() / 100.0);
    if (generator instanceof AbstractOutputGeneratorWithDimensions) {
      pixel = (AbstractOutputGeneratorWithDimensions) generator;
      pixel.setWidth(((Number) m_BatchWidth.getValue()).intValue());
      pixel.setHeight(((Number) m_BatchHeight.getValue()).intValue());
    }
    try {
      cls = AbstractTriangleCenterCalculation.class.getPackage().getName() + "." + m_BatchCenter.getSelectedItem();
      generator.setCenter((AbstractTriangleCenterCalculation) Class.forName(cls).newInstance());
    }
    catch (Exception e) {
      generator.setCenter(new Incenter());
    }
  }

  /**
   * Updates the preview of the "single" tab.
   */
  protected void generateSinglePreview() {
    PNG			generator;
    BufferedImage	img;
    StringBuilder	errors;

    errors    = new StringBuilder();
    generator = new PNG();
    configureSingle(generator);
    img = generator.generatePlot(m_SingleStatistics.getStatistics(), errors);
    if (errors.length() == 0)
      m_PanelSinglePreview.setImage(img);
    m_SingleCopy.setEnabled(m_PanelSinglePreview.getImage() != null);
  }

  /**
   * Generates the output of the "single" tab.
   */
  protected void generateSingleOutput() {
    String			cls;
    AbstractOutputGenerator	generator;
    String			msg;

    SwingUtilities.invokeLater(() -> m_SingleGenerate.setEnabled(false));

    try {
      cls = AbstractOutputGenerator.class.getPackage().getName() + "." + m_SingleGenerator.getSelectedItem();
      generator = (AbstractOutputGenerator) Class.forName(cls).newInstance();
    }
    catch (Exception e) {
      m_Logger.log(Level.SEVERE, "Failed to instantiate output generator - falling back on PNG", e);
      generator = new PNG();
    }
    configureSingle(generator);
    m_Logger.info("Generating: " + m_SingleOutput.getCurrent());
    m_Logger.info("Using: " + m_SingleStatistics.getStatistics());
    msg = generator.generate(m_SingleStatistics.getStatistics(), m_SingleOutput.getCurrent());
    if (msg != null) {
      m_Logger.severe("Failed to generate output:\n" + msg);
      GUIHelper.showErrorMessage(this, "Failed to generate output:\n" + msg);
    }

    SwingUtilities.invokeLater(() -> m_SingleGenerate.setEnabled(true));
  }

  /**
   * Logs an exception during batch processing.
   *
   * @param msg		the message
   * @param t 		the exception
   */
  protected void batchLog(String msg, Throwable t) {
    m_Logger.log(Level.SEVERE, msg, t);
    m_BatchLog.append(msg + "\n" + Utils.throwableToString(t) + "\n");
  }

  /**
   * Logs a message/error during batch processing.
   *
   * @param msg		the message
   * @param error	true if error
   */
  protected void batchLog(String msg, boolean error) {
    if (error) {
      m_BatchLog.append(msg + "\n");
      m_Logger.severe(msg);
    }
    else {
      m_BatchLog.append(msg + "\n");
      m_Logger.info(msg);
    }
  }

  /**
   * Generates the output of the "batch" tab.
   */
  protected void generateBatchOutput() {
    String			cls;
    AbstractOutputGenerator	generator;
    int 			colID;
    int 			colMeasure;
    int 			colScore;
    int 			colPercentile;
    Reader 			reader;
    CSVParser 			csvparser;
    String 			oldID;
    Map<String,List<Double>> 	test;
    String 			id;
    File 			outfile;
    String 			msg;
    String 			measure;
    double 			score;
    double 			percentile;
    String			error;

    m_BatchLog.setText("");
    m_BatchGenerate.setEnabled(false);

    try {
      cls = AbstractOutputGenerator.class.getPackage().getName() + "." + m_SingleGenerator.getSelectedItem();
      generator = (AbstractOutputGenerator) Class.forName(cls).newInstance();
    }
    catch (Exception e) {
      batchLog("Failed to instantiate output generator - falling back on PNG", e);
      generator = new PNG();
    }

    try {
      colID         = 0;
      colMeasure    = 1;
      colScore      = 2;
      colPercentile = 3;
      reader        = new FileReader(m_BatchCSV.getCurrent());
      csvparser     = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
      oldID         = "";
      test          = new HashMap<>();
      for (CSVRecord rec : csvparser) {
	if (rec.size() < 4)
	  continue;
	id = rec.get(colID);
	if (!id.equals(oldID)) {
	  if (!test.isEmpty()) {
	    outfile = new File(m_BatchOutput.getCurrent() + File.separator + oldID + "." + generator.getExtension());
	    batchLog("Generating: " + outfile, false);
	    batchLog("Using: " + test, false);
	    msg     = generator.generate(test, outfile);
	    if (msg != null) {
	      error = "Failed to generate output for ID: " + oldID;
	      batchLog(error, true);
	    }
	  }
	  test.clear();
	  oldID = id;
	}
	measure    = rec.get(colMeasure);
	score      = Double.parseDouble(rec.get(colScore));
	percentile = Double.parseDouble(rec.get(colPercentile));
	test.put(measure, new ArrayList<>(Arrays.asList(new Double[]{score, percentile})));
      }
      if (!test.isEmpty()) {
	outfile = new File(m_BatchOutput.getCurrent() + File.separator + oldID + "." + generator.getExtension());
	batchLog("Generating: " + outfile, false);
	batchLog("Using: " + test, false);
	msg     = generator.generate(test, outfile);
	if (msg != null) {
	  error = "Failed to generate output for ID: " + oldID;
	  batchLog(error, true);
	}
      }
    }
    catch (Exception e) {
      batchLog("Failed to generate output!", e);
    }

    m_BatchGenerate.setEnabled(true);
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
    frame.setJMenuBar(panel.createMenuBar());
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(panel);
    frame.setSize(1024, 768);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
