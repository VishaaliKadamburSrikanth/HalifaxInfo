import csv
import nltk
import re

positive_list = []
negative_list = []
poslist = []

#Positive wordlist
with open("Pos_Words.csv", 'r',encoding="utf8") as positive_words:
    reader = csv.reader(positive_words)
    for row in reader:
        word1 = row[0]
        positive_list.append(word1)

#Negative wordlist
with open("Neg_Words.csv", 'r',encoding="utf8") as negative_words:
    reader = csv.reader(negative_words)
    for row in reader:
        word2 = row[0]
        negative_list.append(word2)

##Cleaning tweets
with open("processedTweets.csv", 'r',encoding="utf8") as file:
    reader = csv.reader(file)
    for tweet in reader:

        # Remove Links
        tweet = re.sub(r"http\S+", "", tweet[0])
        # removing special characters
        tweet = re.sub('[^A-Za-z0-9]+', ' ', tweet[0])

        # Performing parts of speech using nltk
        tokenize = nltk.word_tokenize(tweet[0])
        pos = nltk.pos_tag(tokenize)
        poslist.append(pos)

        #Tagging polarity based on the created dict
        for x in poslist:
            count = 0;
            for word in x:
                word_lower_case = word[0].lower()
                if word_lower_case in negative_list:
                         count = count - 1
                if word_lower_case in positive_list:
                        count = count + 1
            if count > 0:
                result = 'positive'
            elif count < 0:
                result = 'negative'
            else:
                result = 'neutral'

        #Writting the outfile with polarity in file
        with open("Sentimental_Polarity.csv", "a", newline="", encoding='utf-8') as Result_file:
             writer = csv.writer(Result_file, delimiter=",")
             writer.writerow([tweet[0], result])
