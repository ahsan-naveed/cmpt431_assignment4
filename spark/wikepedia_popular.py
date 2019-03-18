import sys
from pyspark.sql import SparkSession, functions, types
import re, ntpath

spark = SparkSession.builder.appName('wikipedia popular').getOrCreate()

assert sys.version_info >= (3, 4) # make sure we have Python 3.4+
assert spark.version >= '2.1' # make sure we have Spark 2.1+

schema = types.StructType([
    types.StructField('timestamp', types.StringType(), False), 
	types.StructField('lang', types.StringType(), False), 
	types.StructField('page_name', types.StringType(), False),
	types.StructField('page_views', types.LongType(), False),
	types.StructField('page_data', types.LongType(), False),
])


# Output formatter
def tab_separated(kv):
    return "%s\t(%s, '%s')" % (kv[0], kv[2], kv[1])

def main():
    in_directory = sys.argv[1]
    #out_directory = sys.argv[2]
    
    #reading data from csv file 
    wiki_df = spark.read.csv(in_directory, sep=" ", schema=schema)
    
    #filtering dataframe for language=english(en), page_name not equal to MainPage or Special:
    wiki_df = wiki_df.filter(wiki_df['lang'] == 'en')
    wiki_df = wiki_df.filter(wiki_df['page_name'] != 'Main_Page' )
    wiki_df = wiki_df.filter(wiki_df['page_name'].startswith('Special:') == False)
 
 
    wiki_df = wiki_df.select(wiki_df['timestamp'], wiki_df['page_name'], wiki_df['page_views'])
    
    #wiki_df.show(); return
    
    #Getting max count of pages viewed in every hour
    max_count_df = wiki_df.groupBy(wiki_df['timestamp']).agg(functions.max(wiki_df['page_views']).alias('page_views'))

    #Joining the dataframes to resolve most viewed pages tie
    cond = [(wiki_df['page_views'] == max_count_df['page_views']) & (wiki_df['timestamp'] == max_count_df['timestamp'])]
    result_df = wiki_df.join(max_count_df, cond, 'right_outer').select(max_count_df['timestamp'], wiki_df['page_name'], max_count_df['page_views'])
    #result_df = result_df.cache()
    
    #sort
    result_df = result_df.sort(result_df['timestamp'],result_df['page_name'], wiki_df['page_views'])

    #result_df.show();return
    result_df.rdd.map(tab_separated).coalesce(1).saveAsTextFile('output')
    
    #result_df.coalesce(1).write.csv(out_directory + '-wikipedia', mode='overwrite')

if __name__=='__main__':
    main()