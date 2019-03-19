### What are the advantages of Spark over MapReduce?
1. Spark does in-memory computation and caching while MapReduce continue on disk after the map or reduce operation.
2. Spark has easy to follow API's while MapReduce is difficult to program. 
3. Spark has upper hand in data processing where it can process different kind of data (like graphs and machine learning libraries) in batch and real time. But MapReduce is only suitable for batch process.


### How does Spark DataFrame speed up computation over Spark RDDs?
RDD uses Java serialization to read and write data which is a lengthy proccess and put restriciton on spark performace.
Spark DataFrame uses the concept of schema in which you can describe the data and this in result improves the performance. Distribution of data is done by distributing row objects.

### Do Spark over MapReduce seem to take good advantage of more cores?

References:
https://www.xplenty.com/blog/apache-spark-vs-hadoop-mapreduce/