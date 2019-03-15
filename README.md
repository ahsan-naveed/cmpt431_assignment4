## Summary

MapReduce class WikipediaPopular that finds the number of times the mostvisited page was visited each hour in the dataset.

## Getting Started

1. `cd cmpt431_assignment4`
2. `hadoop com.sun.tools.javac.Main WikipediaPopular.java`
3. `jar cf wp.jar WikipediaPopular*.class`
4. `hadoop jar wp.jar WikipediaPopular input/pagecounts-with-time-2/ ./output`

## Single Core vs Multi Core

- SINGLE_CORE_JOB_FINISH_TIME = 25027.458 milliseconds
- MULTI_CORE_JOB_FINISH_TIME = 29678.331 milliseconds
