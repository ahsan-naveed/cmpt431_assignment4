## Summary

MapReduce class WikipediaPopular that finds the number of times the mostvisited page was visited each hour in the dataset.

## Getting Started

\*Note: Follow instructions from assignment to compile and run this job on `gateway.sfucloud.ca`. For local machine continue reading.

1. `cd cmpt431_assignment4`
2. `hadoop com.sun.tools.javac.Main WikipediaPopular.java`
3. `jar cf wp.jar WikipediaPopular*.class`
4. `hadoop jar wp.jar WikipediaPopular input/pagecounts-with-time-2/ ./output`

## Design

- _Mapper_
  - _map_: takes care of filtering the en pages and also gets the highest visit count for each en page of each hour.
  - _cleanup_: writes only one entry for each `<key, val>` pair i.e. writes only the timestamp and visit count of most visited page.
- _Reducer_
  - _reduce_: for each `<key, val>` pair prints out `key val` where `key` is the timestamp of most visted en page and `val` is the actual visit count.

## Job execution time (Lower Core vs Higher Core)

\*Note: Job execution times are calculated using `System.nanoTime()` utitiliy provided in Java.

- HIGHER_CORE_JOB_FINISH_TIME = 25027.458 milliseconds
- LOWER_CORE_JOB_FINISH_TIME = 29678.331 milliseconds
- DIFFERENCE_IN_EXEC_TIMES = 4,650.873 milliseconds
