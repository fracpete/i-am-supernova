# I am supernova

*I am supernova* is an artistic visualization of *The Big Five Personality Test*, 
devised by [Keith Soo](http://www.cms.waikato.ac.nz/people/ceks).

## Output formats
Currently available output formats:

* PNG


## Example usage (single output) ##
Use either the `run` (Linux/Mac) or `run.bat` (Windows) script to execute
the class for generating a single graphical output.

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
the class for generating a graphical output based on a CSV file. The generated
output files get stored in the specified output directory.

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
