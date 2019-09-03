file = sc.textFile("/home/ubuntu/server/Twitter_Search_Data.txt")
word_list = ['not safe', 'safe','accident','long waiting','expensive','friendly','snow storm','good school','good schools','bad school','bad schools','poor school','poor schools','immigrants','immigrant','pollution','bus','buses','parks','park','parking']
count = file.flatMap(lambda line: line.split(" ")).filter(lambda x:x.lower() in word_list).map(lambda value:(value, 1)).reduceByKey(lambda a, b: a + b)
count.saveAsTextFile("/home/ubuntu/server/WordCountSearch.txt")