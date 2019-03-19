### What are the advantages of Spark over MapReduce?

1. Spark does in-memory computation and caching while MapReduce continue on disk after the map or reduce operation.
2. Spark has easy to follow API's while MapReduce is difficult to program.
3. Spark has upper hand in data processing where it can process different kind of data (like graphs and machine learning libraries) in batch and real time. But MapReduce is only suitable for batch process.

### How does Spark DataFrame speed up computation over Spark RDDs?

RDD uses Java serialization to read and write data which is a lengthy proccess and put restriciton on spark performace.
Spark DataFrame uses the concept of schema in which you can describe the data and this in result improves the performance. Distribution of data is done by distributing row objects.

### Do Spark over MapReduce seem to take good advantage of more cores?

Yes, in case of MapReduce, local job runner of MapReduce spawns a subprocess for each MapReduce stage, Mapper, Combiner (optional) and Reducer and passes data between them via data-pipe which is not designed to take advantage of multi-core systems. On the other hand, Hadoop cluster running on your local machine in pseduo-distributed mode should be able to take advantage of higher cores but in our case we ran the job in local-standalone mode which is a non-distributed mode, a single Java process. In case of Spark, we can specify the number of cores to `n` the executor can run a maximum of `n` tasks at the same time. Hence, Spark seem to take good advantage of more cores.

### References:

1.  https://stackoverflow.com/questions/31181608/utilize-multi-core-with-localmrjobrunner-for-mrjob
2.  https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SingleCluster.html#Standalone_Operation
3.  https://www.xplenty.com/blog/apache-spark-vs-hadoop-mapreduce/
4.  https://stackoverflow.com/questions/41807879/how-does-spark-exploits-the-multi-core-parallelism-in-each-machine
