### What are the advantages of Spark over MapReduce?

### How does Spark DataFrame speed up computation over Spark RDDs?
RDD uses Java serialization to read and write data which is a lengthy proccess and put restriciton on spark performace.
Spark DataFrame uses the concept of schema in which you can describe the data and this in result improves the performance. Distribution of data is done by distributing row objects.

### Do Spark over MapReduce seem to take good advantage of more cores?
