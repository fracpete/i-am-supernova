# I am supernova

*I am supernova* is an artistic visualization of *The Big Five Personality Test*, 
devised by [Keith Soo](http://www.cms.waikato.ac.nz/people/ceks).

## Output formats
Currently available output formats:

* PNG


## Example usage (single output) ##
Use either the `run` (Linux/Mac) or `run.bat` (Windows) script to execute
the class `nz.ac.waikato.cms.supernova.Supernova` for generating a single plot.

```bash
run \
  --openness_score 4.3 \
  --openness_percentile 59 \
  --extraversion_score 2.2 \
  --extraversion_percentile 18 \
  --agreeableness_score 4.2 \
  --agreeableness_percentile 63 \
  --conscientiousness_score 3.5 \
  --conscientiousness_percentile 52 \
  --neuroticism_score 2.4 \
  --neuroticism_percentile 25 \
  --opacity 0.1 \
  --generator PNG \
  --output example.png
```


## Example usage (multiple outputs) ##
Use either the `csv` (Linux/Mac) or `csv.bat` (Windows) script to execute
the class `nz.ac.waikato.cms.supernova.SupernovaCSV` for generating plots 
based on parameters stored in a CSV file. 
The generated output files get stored in the specified output directory, 
using the `ID` as the file name.

```bash
csv \
  --csv data.csv \
  --opacity 0.1 \
  --generator PNG \
  --output outdir
```

Example data file:

```csv
ID,measure,score,percentile
me,extraversion,2.2,18
me,conscientiousness,3.5,52
me,neuroticism,2.4,25
me,agreeableness,4.2,63
me,openness,4.3,59
you,extraversion,3.2,32
you,conscientiousness,2.5,32
you,neuroticism,2.7,31
you,agreeableness,4.9,89
you,openness,2.1,23
```

## Example usage (minimal code)
The following code shows how to generate output with the default settings
of the PNG generator:

```java
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nz.ac.waikato.cms.supernova.Calc;
import nz.ac.waikato.cms.supernova.io.PNG;
...
// set up test results
Map<String,List<Double>> test = new HashMap<>();
test.put(Calc.OPENNESS,          new ArrayList<>(Arrays.asList(new Double[]{4.3, 59.0})));
test.put(Calc.EXTRAVERSION,      new ArrayList<>(Arrays.asList(new Double[]{2.2, 18.0})));
test.put(Calc.CONSCIENTIOUSNESS, new ArrayList<>(Arrays.asList(new Double[]{3.5, 52.0})));
test.put(Calc.NEUROTICISM,       new ArrayList<>(Arrays.asList(new Double[]{2.4, 25.0})));
test.put(Calc.AGREEABLENESS,     new ArrayList<>(Arrays.asList(new Double[]{4.2, 63.0})));

// set up generator
PNG generator = new PNG(); 

// generate output
String msg = generator.generate(test, new File("out.png"));
if (msg != null)
  System.err.println(msg);
```

## Example usage (full code)
The following code shows how to set all options for the PNG generator and
generate output:

```java
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nz.ac.waikato.cms.supernova.Calc;
import nz.ac.waikato.cms.supernova.io.PNG;
...
// set up test results
Map<String,List<Double>> test = new HashMap<>();
test.put(Calc.OPENNESS,          new ArrayList<>(Arrays.asList(new Double[]{4.3, 59.0})));
test.put(Calc.EXTRAVERSION,      new ArrayList<>(Arrays.asList(new Double[]{2.2, 18.0})));
test.put(Calc.CONSCIENTIOUSNESS, new ArrayList<>(Arrays.asList(new Double[]{3.5, 52.0})));
test.put(Calc.NEUROTICISM,       new ArrayList<>(Arrays.asList(new Double[]{2.4, 25.0})));
test.put(Calc.AGREEABLENESS,     new ArrayList<>(Arrays.asList(new Double[]{4.2, 63.0})));

// set up colors
Map<String,Color> colors = new HashMap<>();
colors.put(Calc.OPENNESS,          ColorHelper.valueOf("#ffc800", Color.ORANGE));
colors.put(Calc.EXTRAVERSION,      ColorHelper.valueOf("#ffff00", Color.YELLOW));
colors.put(Calc.AGREEABLENESS,     ColorHelper.valueOf("#00ff00", Color.GREEN));
colors.put(Calc.CONSCIENTIOUSNESS, ColorHelper.valueOf("#0000ff", Color.BLUE));
colors.put(Calc.NEUROTICISM,       ColorHelper.valueOf("#ff0000", Color.RED));

// set up generator
PNG generator = new PNG(); 
generator.setVerbose(true);
generator.setColors(colors);
generator.setBackground(ColorHelper.valueOf("#000000", Color.BLACK));
generator.setOpacity(0.1);
generator.setMargin(0.2);
generator.setWidth(2000);
generator.setHeight(2000);

// generate output
String msg = generator.generate(test, new File("out.png"));
if (msg != null)
  System.err.println(msg);
```
