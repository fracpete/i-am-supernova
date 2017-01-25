# I am supernova

*I am supernova* is an artistic visualization of *The Big Five Personality Test*, 
devised by [Keith Soo](http://www.cms.waikato.ac.nz/people/ceks).

## Example usage ##
Use either the `run` (Linux/Mac) or `run.bat` (Windows) script to execute
class for generating graphical output.

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
  --generator nz.ac.waikato.cms.supernova.io.PNG \
  --output example.png
```

## Parameters
```
  -h, --help             show this help message and exit
  --openness_score openness_score
                         The score for 'openness'.
  --openness_percentile openness_percentile
                         The percentile for 'openness'.
  --openness_color openness_color
                         The color for 'openness' in hex format (e.g., #ffc800).
  --extraversion_score extraversion_score
                         The score for 'extraversion'.
  --extraversion_percentile extraversion_percentile
                         The percentile for 'extraversion'.
  --extraversion_color extraversion_color
                         The color for 'extraversion' in hex format (e.g., #ffff00).
  --agreeableness_score agreeableness_score
                         The score for 'agreeableness'.
  --agreeableness_percentile agreeableness_percentile
                         The percentile for 'agreeableness'.
  --agreeableness_color agreeableness_color
                         The color for  'agreeableness' in hex format (e.g., #00ff00).
  --conscientiousness_score conscientiousness_score
                         The score for 'conscientiousness'.
  --conscientiousness_percentile conscientiousness_percentile
                         The percentile for 'conscientiousness'.
  --conscientiousness_color conscientiousness_color
                         The color for 'conscientiousness' in hex format (e.g., #0000ff).
  --neuroticism_score neuroticism_score
                         The score for 'neuroticism'.
  --neuroticism_percentile neuroticism_percentile
                         The percentile for 'neuroticism'.
  --neuroticism_color neuroticism_color
                         The color for 'neuroticism' in hex format (e.g., #ff0000).
  --background background
                         The background color.
  --opacity opacity      The opacity (0-1).
  --width width          The width of the output.
  --height height        The height of the output.
  --generator generator  The name of the generator class to use.
  --output output        The output file to generate.
  --verbose              Whether to output logging information.
```