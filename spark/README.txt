
Following are the steps taken to get the wikipedia popular pages using spark
1. Create a Spark dataframe by reading input csv files with predifined schema.
2. Filter out the dataframe where language is equal to english, page name does start with 'Special:' and it is not 'Main_Page'.
3. Just select columns 'timestamp', 'page_name' and 'page_views' to group the dataframe on 'timestamp' columsn, aggreating on max of 'view_pages' column to get new dataframe.
4. Join the dataframes max dataframe and original dataframe by doing right outter join for resolving max viewed page tie.
5. Sort the result dataframe on timestamp, page_name and page_views.
6. Fomat the output with helper function 'tab_separated' and save in text file using rdd.
