import math
import re
from collections import OrderedDict

list = []
counter = 0
tf_dic = {}
word_existence = {}
idf_dic = {}
dis_dict = {}
document_dict = {}
document_tf_idf = {}
document_cosine = {}
cosine = 0
query_value = 0
top_rank_doc_id = 0


files = ['reut2-0.sgm', 'reut2-1.sgm', 'reut2-2.sgm', 'reut2-3.sgm', 'reut2-4.sgm', 'reut2-5.sgm', 'reut2-6.sgm', 'reut2-7.sgm', 'reut2-8.sgm', 'reut2-9.sgm','reut2-10.sgm','reut2-11.sgm','reut2-12.sgm','reut2-13.sgm', 'reut2-14.sgm', 'reut2-15.sgm','reut2-16.sgm','reut2-17.sgm','reut2-18.sgm','reut2-19.sgm','reut2-20.sgm','reut2-21.sgm']

#Parsing the sgm files
for index, input in enumerate(files):
    sgm_file = open("C:\\input\\reut2-" + str(index) +".sgm" , "r")
    data = sgm_file.read()
    list = (re.findall("<BODY>([\s\S]*?)</BODY>", data))

    #Calculation of term frequency
    for index1,article in enumerate(list):
        word_freq = {}
        words = article.lower().split()
        for word in words:
            if word not in word_freq:
                word_freq[word] = 1
                if word not in word_existence:
                    word_existence[word] = 1
                else:
                    word_existence[word] += 1
            else:
                word_freq[word] += 1
        counter = counter + 1
        tf_dic[counter] = word_freq

# Calculation of inverse document frequency
word_keys = word_existence.keys()

for key in word_keys:
    idf = math.log2(len(tf_dic) / word_existence.get(key))
    idf_dic[key] = idf

    #Calculation for query value and query distance
    if(key =='canada'):

        #Query value
        query_value = idf/ word_existence.get(key)

        #Query distance
        query_distance = math.sqrt(query_value)

#Calculation for document by terms
counter = 0
for  index, doc  in enumerate(tf_dic):

    temp = {}
    sum = 0;
    dis_dict = tf_dic.get(doc)
    #print(dis_dict)
    for index, dis in enumerate(dis_dict):
         idf_value = idf_dic.get(dis)
         tf_value = dis_dict.get(dis)
         value = idf_value * tf_value;
         document_by_term = (idf_value*tf_value)*(idf_value*tf_value)
         sum += document_by_term
         temp[dis] = value
    counter = counter + 1;
    document_dict[counter] = math.sqrt(sum)
    document_tf_idf[counter] = temp

counter = 0;

#Calculation for cosine similarity
for index, key in enumerate(document_dict):
    temp = {}
    doc_row = document_tf_idf.get(key)
    temp = 0

    for index, key1 in enumerate(doc_row):
        if(key1 == "canada"):
           temp = doc_row.get(key1) * query_distance

    cosine = temp / (query_distance * document_dict.get(key))
    counter = counter + 1;
    document_cosine[counter] = cosine

    #sort based on the highest cosine similarity
    Sorted_Cosine_Similarity = OrderedDict(sorted(document_cosine.items(), key=lambda value: value[1], reverse = True))

    #find the document id with highest cosine similarity
    top_rank_doc_id = Sorted_Cosine_Similarity.keys()

print("Cosine simmilarity",Sorted_Cosine_Similarity)
print("Ranked list", top_rank_doc_id)
print("Top Ranked document", max(top_rank_doc_id))
print("Top ranked document content :",list[max(top_rank_doc_id)])

#Creating the output file with highest ranking article
with open("C:\\Output\\Semantic_Analysis.txt", "w") as text_file:
   print(Sorted_Cosine_Similarity, file=text_file)
   print(list[max(top_rank_doc_id)], file=text_file)


