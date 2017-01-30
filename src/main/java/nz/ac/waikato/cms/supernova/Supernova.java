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
 * Copyright (C) 2016-2017 University of Waikato, Hamilton, New Zealand
 */

package nz.ac.waikato.cms.supernova;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.internal.HelpScreenException;
import nz.ac.waikato.cms.supernova.gui.ColorHelper;
import nz.ac.waikato.cms.supernova.io.AbstractOutputGenerator;
import nz.ac.waikato.cms.supernova.io.AbstractOutputGeneratorWithDimensions;
import nz.ac.waikato.cms.supernova.io.PNG;
import nz.ac.waikato.cms.supernova.triangle.AbstractTriangleCenterCalculation;
import nz.ac.waikato.cms.supernova.triangle.Incenter;

import java.awt.Color;
import java.io.File;
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
public class Supernova {

  public static final String BACKGROUND = "background";

  public static final String OPACITY = "opacity";

  public static final String WIDTH = "width";

  public static final String HEIGHT = "height";

  public static final String OUTPUT = "output";

  public static final String SCORE_SUFFIX = "_score";

  public static final String PERCENTILE_SUFFIX = "_percentile";

  public static final String COLOR_SUFFIX = "_color";

  public static final String VERBOSE = "verbose";

  public static final String CENTER = "center";

  public static final String GENERATOR = "generator";

  public static final String MARGIN = "margin";

  public static void main(String[] args) throws Exception {
    ArgumentParser parser;

    parser = ArgumentParsers.newArgumentParser("I am supernova");
    parser.description(
      "Generates output according to 'I am supernova' by Keith Soo.\n"
	+ "Project homepage:\n"
	+ "https://github.com/fracpete/i-am-supernova");

    // openness
    parser.addArgument("--" + AbstractOutputGenerator.OPENNESS + SCORE_SUFFIX)
      .metavar(AbstractOutputGenerator.OPENNESS + SCORE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The score for '" + AbstractOutputGenerator.OPENNESS + "' (0-5).");
    parser.addArgument("--" + AbstractOutputGenerator.OPENNESS + PERCENTILE_SUFFIX)
      .metavar(AbstractOutputGenerator.OPENNESS + PERCENTILE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The percentile for '" + AbstractOutputGenerator.OPENNESS + "' (0-100).");
    parser.addArgument("--" + AbstractOutputGenerator.OPENNESS + COLOR_SUFFIX)
      .metavar(AbstractOutputGenerator.OPENNESS + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.ORANGE))
      .help("The color for '" + AbstractOutputGenerator.OPENNESS + "' in hex format (e.g., " + ColorHelper.toHex(Color.ORANGE) + ").");

    // extraversion
    parser.addArgument("--" + AbstractOutputGenerator.EXTRAVERSION + SCORE_SUFFIX)
      .metavar(AbstractOutputGenerator.EXTRAVERSION + SCORE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The score for '" + AbstractOutputGenerator.EXTRAVERSION + "' (0-5).");
    parser.addArgument("--" + AbstractOutputGenerator.EXTRAVERSION + PERCENTILE_SUFFIX)
      .metavar(AbstractOutputGenerator.EXTRAVERSION + PERCENTILE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The percentile for '" + AbstractOutputGenerator.EXTRAVERSION + "' (0-100).");
    parser.addArgument("--" + AbstractOutputGenerator.EXTRAVERSION + COLOR_SUFFIX)
      .metavar(AbstractOutputGenerator.EXTRAVERSION + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.YELLOW))
      .help("The color for '" + AbstractOutputGenerator.EXTRAVERSION + "' in hex format (e.g., " + ColorHelper.toHex(Color.YELLOW) + ").");

    // agreeablenes
    parser.addArgument("--" + AbstractOutputGenerator.AGREEABLENESS + SCORE_SUFFIX)
      .metavar(AbstractOutputGenerator.AGREEABLENESS + SCORE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The score for '" + AbstractOutputGenerator.AGREEABLENESS + "' (0-5).");
    parser.addArgument("--" + AbstractOutputGenerator.AGREEABLENESS + PERCENTILE_SUFFIX)
      .metavar(AbstractOutputGenerator.AGREEABLENESS + PERCENTILE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The percentile for '" + AbstractOutputGenerator.AGREEABLENESS + "' (0-100).");
    parser.addArgument("--" + AbstractOutputGenerator.AGREEABLENESS + COLOR_SUFFIX)
      .metavar(AbstractOutputGenerator.AGREEABLENESS + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.GREEN))
      .help("The color for '" + AbstractOutputGenerator.AGREEABLENESS + "' in hex format (e.g., " + ColorHelper.toHex(Color.GREEN) + ").");

    // conscientiousness
    parser.addArgument("--" + AbstractOutputGenerator.CONSCIENTIOUSNESS + SCORE_SUFFIX)
      .metavar(AbstractOutputGenerator.CONSCIENTIOUSNESS + SCORE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The score for '" + AbstractOutputGenerator.CONSCIENTIOUSNESS + "' (0-5).");
    parser.addArgument("--" + AbstractOutputGenerator.CONSCIENTIOUSNESS + PERCENTILE_SUFFIX)
      .metavar(AbstractOutputGenerator.CONSCIENTIOUSNESS + PERCENTILE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The percentile for '" + AbstractOutputGenerator.CONSCIENTIOUSNESS + "' (0-100).");
    parser.addArgument("--" + AbstractOutputGenerator.CONSCIENTIOUSNESS + COLOR_SUFFIX)
      .metavar(AbstractOutputGenerator.CONSCIENTIOUSNESS + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.BLUE))
      .help("The color for '" + AbstractOutputGenerator.CONSCIENTIOUSNESS + "' in hex format (e.g., " + ColorHelper.toHex(Color.BLUE) + ").");

    // neuroticism
    parser.addArgument("--" + AbstractOutputGenerator.NEUROTICISM + SCORE_SUFFIX)
      .metavar(AbstractOutputGenerator.NEUROTICISM + SCORE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The score for '" + AbstractOutputGenerator.NEUROTICISM + "' (0-5).");
    parser.addArgument("--" + AbstractOutputGenerator.NEUROTICISM + PERCENTILE_SUFFIX)
      .metavar(AbstractOutputGenerator.NEUROTICISM + PERCENTILE_SUFFIX)
      .required(true)
      .type(Double.class)
      .help("The percentile for '" + AbstractOutputGenerator.NEUROTICISM + "' (0-100).");
    parser.addArgument("--" + AbstractOutputGenerator.NEUROTICISM + COLOR_SUFFIX)
      .metavar(AbstractOutputGenerator.NEUROTICISM + COLOR_SUFFIX)
      .type(String.class)
      .setDefault(ColorHelper.toHex(Color.RED))
      .help("The color for '" + AbstractOutputGenerator.NEUROTICISM + "' in hex format (e.g., " + ColorHelper.toHex(Color.RED) + ").");

    // other parameters
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
      .setDefault(0.2)
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

    parser.addArgument("--" + CENTER)
      .metavar(CENTER)
      .type(String.class)
      .setDefault(Incenter.class.getName())
      .help(
	"The name of the algorithm for calculating the center of a triangle.\n"
	  + "Available: " + Registry.toString(Registry.CENTERS, true));

    parser.addArgument("--" + GENERATOR)
      .metavar(GENERATOR)
      .type(String.class)
      .setDefault(PNG.class.getName())
      .help(
	"The name of the generator class to use.\n"
	  + "Available: " + Registry.toString(Registry.GENERATORS, true));

    parser.addArgument("--" + OUTPUT)
      .metavar(OUTPUT)
      .type(File.class)
      .help("The output file to generate.");

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

    // test values
    Map<String,List<Double>> test = new HashMap<>();
    test.put(AbstractOutputGenerator.OPENNESS, new ArrayList<>(Arrays.asList(new Double[]{
      namespace.getDouble(AbstractOutputGenerator.OPENNESS + SCORE_SUFFIX),
      namespace.getDouble(AbstractOutputGenerator.OPENNESS + PERCENTILE_SUFFIX)
    })));
    test.put(AbstractOutputGenerator.EXTRAVERSION, new ArrayList<>(Arrays.asList(new Double[]{
      namespace.getDouble(AbstractOutputGenerator.EXTRAVERSION + SCORE_SUFFIX),
      namespace.getDouble(AbstractOutputGenerator.EXTRAVERSION + PERCENTILE_SUFFIX)
    })));
    test.put(AbstractOutputGenerator.CONSCIENTIOUSNESS, new ArrayList<>(Arrays.asList(new Double[]{
      namespace.getDouble(AbstractOutputGenerator.CONSCIENTIOUSNESS + SCORE_SUFFIX),
      namespace.getDouble(AbstractOutputGenerator.CONSCIENTIOUSNESS + PERCENTILE_SUFFIX)
    })));
    test.put(AbstractOutputGenerator.NEUROTICISM, new ArrayList<>(Arrays.asList(new Double[]{
      namespace.getDouble(AbstractOutputGenerator.NEUROTICISM + SCORE_SUFFIX),
      namespace.getDouble(AbstractOutputGenerator.NEUROTICISM + PERCENTILE_SUFFIX)
    })));
    test.put(AbstractOutputGenerator.AGREEABLENESS, new ArrayList<>(Arrays.asList(new Double[]{
      namespace.getDouble(AbstractOutputGenerator.AGREEABLENESS + SCORE_SUFFIX),
      namespace.getDouble(AbstractOutputGenerator.AGREEABLENESS + PERCENTILE_SUFFIX)
    })));

    // colors
    Map<String,Color> colors = new HashMap<>();
    colors.put(AbstractOutputGenerator.OPENNESS,          ColorHelper.valueOf(namespace.getString(AbstractOutputGenerator.OPENNESS          + COLOR_SUFFIX), Color.ORANGE));
    colors.put(AbstractOutputGenerator.EXTRAVERSION,      ColorHelper.valueOf(namespace.getString(AbstractOutputGenerator.EXTRAVERSION      + COLOR_SUFFIX), Color.YELLOW));
    colors.put(AbstractOutputGenerator.AGREEABLENESS,     ColorHelper.valueOf(namespace.getString(AbstractOutputGenerator.AGREEABLENESS     + COLOR_SUFFIX), Color.GREEN));
    colors.put(AbstractOutputGenerator.CONSCIENTIOUSNESS, ColorHelper.valueOf(namespace.getString(AbstractOutputGenerator.CONSCIENTIOUSNESS + COLOR_SUFFIX), Color.BLUE));
    colors.put(AbstractOutputGenerator.NEUROTICISM,       ColorHelper.valueOf(namespace.getString(AbstractOutputGenerator.NEUROTICISM       + COLOR_SUFFIX), Color.RED));

    File outfile = new File(namespace.getString(OUTPUT));

    String centerCls = namespace.getString(CENTER);
    if (!centerCls.contains("."))
      centerCls = AbstractTriangleCenterCalculation.class.getPackage().getName() + "." + centerCls;
    String generatorCls = namespace.getString(GENERATOR);
    if (!generatorCls.contains("."))
      generatorCls = AbstractOutputGenerator.class.getPackage().getName() + "." + generatorCls;
    AbstractOutputGenerator generator = (AbstractOutputGenerator) Class.forName(generatorCls).newInstance();
    generator.setVerbose(namespace.getBoolean(VERBOSE));
    generator.setColors(colors);
    generator.setBackground(ColorHelper.valueOf(namespace.getString(BACKGROUND), Color.BLACK));
    generator.setOpacity(namespace.getDouble(OPACITY));
    generator.setMargin(namespace.getDouble(MARGIN));
    generator.setCenter((AbstractTriangleCenterCalculation) Class.forName(centerCls).newInstance());
    if (generator instanceof AbstractOutputGeneratorWithDimensions) {
      AbstractOutputGeneratorWithDimensions pixel = (AbstractOutputGeneratorWithDimensions) generator;
      pixel.setWidth(namespace.getInt(WIDTH));
      pixel.setHeight(namespace.getInt(HEIGHT));
    }
    String msg = generator.generate(test, outfile);
    System.out.println("\nOutput file '" + outfile + "' generated?\n" + (msg == null ? "yes" : "no, because: " + msg));
  }
}
