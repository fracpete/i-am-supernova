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
 * SupernovaCSV.java
 * Copyright (C) 2017 University of Waikato, Hamilton, New Zealand
 */

package nz.ac.waikato.cms.supernova;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.internal.HelpScreenException;
import nz.ac.waikato.cms.supernova.io.AbstractOutputGenerator;
import nz.ac.waikato.cms.supernova.io.AbstractPixelBasedOutputGenerator;
import nz.ac.waikato.cms.supernova.io.PNG;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates output for "I am supernova".
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 */
public class SupernovaCSV {

  public static final String BACKGROUND = "background";

  public static final String OPACITY = "opacity";

  public static final String WIDTH = "width";

  public static final String HEIGHT = "height";

  public static final String OUTPUT = "output";

  public static final String CSV = "csv";

  public static final String ID = "id";

  public static final String MEASURE = "measure";

  public static final String SCORE = "score";

  public static final String PERCENTILE = "percentile";

  public static final String COLOR_SUFFIX = "_color";

  public static final String VERBOSE = "verbose";

  public static final String GENERATOR = "generator";

  public static final String MARGIN = "margin";

  public static final String MEASURE_LIST =
    Calc.OPENNESS + ","
      + Calc.EXTRAVERSION + ","
      + Calc.AGREEABLENESS + ","
      + Calc.CONSCIENTIOUSNESS + ","
      + Calc.NEUROTICISM;

  public static void main(String[] args) throws Exception {
    ArgumentParser parser;

    parser = ArgumentParsers.newArgumentParser("I am supernova");
    parser.description(
      "Generates output according to 'I am supernova' by Keith Soo.\n"
        + "Loads scores/percentiles from a CSV file to generate multiple outputs at once.\n"
	+ "Expected four columns (name of column is irrelevant):\n"
	+ "- ID: the filename (excluding path and extension)\n"
	+ "- Measure: the measure (" + MEASURE_LIST + ")\n"
	+ "- Score: the score of the measure\n"
	+ "- Percentile: the percentile of the measure\n"
	+ "\n"
	+ "Project homepage:\n"
	+ "https://github.com/fracpete/i-am-supernova");

    // colors
    parser.addArgument("--" + Calc.OPENNESS + COLOR_SUFFIX)
      .metavar(Calc.OPENNESS + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.ORANGE))
      .help("The color for '" + Calc.OPENNESS + "' in hex format (e.g., " + ColorHelper.toHex(Color.ORANGE) + ").");
    parser.addArgument("--" + Calc.EXTRAVERSION + COLOR_SUFFIX)
      .metavar(Calc.EXTRAVERSION + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.YELLOW))
      .help("The color for '" + Calc.EXTRAVERSION + "' in hex format (e.g., " + ColorHelper.toHex(Color.YELLOW) + ").");
    parser.addArgument("--" + Calc.AGREEABLENESS + COLOR_SUFFIX)
      .metavar(Calc.AGREEABLENESS + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.GREEN))
      .help("The color for '" + Calc.AGREEABLENESS + "' in hex format (e.g., " + ColorHelper.toHex(Color.GREEN) + ").");
    parser.addArgument("--" + Calc.CONSCIENTIOUSNESS + COLOR_SUFFIX)
      .metavar(Calc.CONSCIENTIOUSNESS + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.BLUE))
      .help("The color for '" + Calc.CONSCIENTIOUSNESS + "' in hex format (e.g., " + ColorHelper.toHex(Color.BLUE) + ").");
    parser.addArgument("--" + Calc.NEUROTICISM + COLOR_SUFFIX)
      .metavar(Calc.NEUROTICISM + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.RED))
      .help("The color for '" + Calc.NEUROTICISM + "' in hex format (e.g., " + ColorHelper.toHex(Color.RED) + ").");

    // other parameters
    parser.addArgument("--" + CSV)
      .metavar(CSV)
      .type(String.class)
      .required(true)
      .help("The CSV file containing the scores/percentiles (header must be present).");

    parser.addArgument("--" + ID)
      .metavar(ID)
      .type(Integer.class)
      .setDefault(1)
      .help("The 1-based index of the column in the CSV file containing the ID for the output file.");

    parser.addArgument("--" + MEASURE)
      .metavar(MEASURE)
      .type(Integer.class)
      .setDefault(2)
      .help("The 1-based index of the column in the CSV file containing the measure name.\n"
	+ "Allowed values: " + MEASURE_LIST);

    parser.addArgument("--" + SCORE)
      .metavar(SCORE)
      .type(Integer.class)
      .setDefault(3)
      .help("The 1-based index of the column in the CSV file containing the scores.");

    parser.addArgument("--" + PERCENTILE)
      .metavar(PERCENTILE)
      .type(Integer.class)
      .setDefault(4)
      .help("The 1-based index of the column in the CSV file containing the percentiles.");

    parser.addArgument("--" + BACKGROUND)
      .metavar(BACKGROUND)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.BLACK))
      .help("The background color.");

    parser.addArgument("--" + OPACITY)
      .metavar(OPACITY)
      .type(Double.class)
      .setDefault(0.1)
      .help("The opacity (0-1).");

    parser.addArgument("--" + MARGIN)
      .metavar(MARGIN)
      .type(Double.class)
      .setDefault(0.1)
      .help("The margin in the output (0-1).");

    parser.addArgument("--" + WIDTH)
      .metavar(WIDTH)
      .type(Integer.class)
      .setDefault(2000)
      .help("The width of the output.");

    parser.addArgument("--" + HEIGHT)
      .metavar(HEIGHT)
      .type(Integer.class)
      .setDefault(2000)
      .help("The height of the output.");

    parser.addArgument("--" + GENERATOR)
      .metavar(GENERATOR)
      .type(String.class)
      .setDefault(PNG.class.getName())
      .help("The name of the generator class to use.");

    parser.addArgument("--" + OUTPUT)
      .metavar(OUTPUT)
      .type(String.class)
      .help("The directory to store the output in.");

    parser.addArgument("--" + VERBOSE)
      .metavar(VERBOSE)
      .type(Boolean.class)
      .action(Arguments.storeTrue())
      .help("Whether to output logging information.");

    Namespace namespace;
    try {
      namespace = parser.parseArgs(args);
    }
    catch (Exception e) {
      if (!(e instanceof HelpScreenException))
	parser.printHelp();
      return;
    }

    // colors
    Map<String,Color> colors = new HashMap<>();
    colors.put(Calc.OPENNESS,          ColorHelper.valueOf(namespace.getString(Calc.OPENNESS          + COLOR_SUFFIX), Color.ORANGE));
    colors.put(Calc.EXTRAVERSION,      ColorHelper.valueOf(namespace.getString(Calc.EXTRAVERSION      + COLOR_SUFFIX), Color.YELLOW));
    colors.put(Calc.AGREEABLENESS,     ColorHelper.valueOf(namespace.getString(Calc.AGREEABLENESS     + COLOR_SUFFIX), Color.GREEN));
    colors.put(Calc.CONSCIENTIOUSNESS, ColorHelper.valueOf(namespace.getString(Calc.CONSCIENTIOUSNESS + COLOR_SUFFIX), Color.BLUE));
    colors.put(Calc.NEUROTICISM,       ColorHelper.valueOf(namespace.getString(Calc.NEUROTICISM       + COLOR_SUFFIX), Color.RED));

    File outdir = new File(namespace.getString(OUTPUT));

    String generatorCls = namespace.getString(GENERATOR);
    if (!generatorCls.contains("."))
      generatorCls = AbstractOutputGenerator.class.getPackage().getName() + "." + generatorCls;
    AbstractOutputGenerator generator = (AbstractOutputGenerator) Class.forName(generatorCls).newInstance();
    generator.setVerbose(namespace.getBoolean(VERBOSE));
    generator.setColors(colors);
    generator.setBackground(ColorHelper.valueOf(namespace.getString(BACKGROUND), Color.BLACK));
    generator.setOpacity(namespace.getDouble(OPACITY));
    generator.setMargin(namespace.getDouble(MARGIN));
    if (generator instanceof AbstractPixelBasedOutputGenerator) {
      AbstractPixelBasedOutputGenerator pixel = (AbstractPixelBasedOutputGenerator) generator;
      pixel.setWidth(namespace.getInt(WIDTH));
      pixel.setHeight(namespace.getInt(HEIGHT));
    }

    int colID = namespace.getInt(ID) - 1;
    int colMeasure = namespace.getInt(MEASURE) - 1;
    int colScore = namespace.getInt(SCORE) - 1;
    int colPercentile = namespace.getInt(PERCENTILE) - 1;
    Reader reader = new FileReader(namespace.getString(CSV));
    CSVParser csvparser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
    String oldID = "";
    Map<String,List<Double>> test = new HashMap<>();
    for (CSVRecord rec: csvparser) {
      if (rec.size() < 4)
	continue;
      String id = rec.get(colID);
      if (!id.equals(oldID)) {
	if (!test.isEmpty()) {
	  File outfile = new File(outdir + File.separator + oldID + "." + generator.getExtension());
	  String msg = generator.generate(test, outfile);
	  if (msg != null)
	    System.err.println("Failed to generate output for ID: " + oldID);
	}
	test.clear();
	oldID = id;
      }
      String measure = rec.get(colMeasure);
      double score = Double.parseDouble(rec.get(colScore));
      double percentile = Double.parseDouble(rec.get(colPercentile));
      test.put(measure, new ArrayList<>(Arrays.asList(new Double[]{score, percentile})));
    }
    if (!test.isEmpty()) {
      File outfile = new File(outdir + File.separator + oldID + "." + generator.getExtension());
      String msg = generator.generate(test, outfile);
      if (msg != null)
	System.err.println("Failed to generate output for ID: " + oldID);
    }
  }
}
