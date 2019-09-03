file = sc.textFile("/home/ubuntu/server/Twitter_Streaming_Data.txt")
word_freq = file.flatMap(lambda line: line.split(" ")).map(lambda word: (word, 1)).reduceByKey(lambda a, b: a + b)
word_freq.saveAsTextFile("/home/ubuntu/server/WordFreqStream.txt")