from os import listdir
import random
import shutil
import os
listFichier = listdir("./avant")
random.shuffle(listFichier)

def split_list(a_list):
    half = len(a_list)//2
    return a_list[:half], a_list[half:]

trainSet,rest = split_list(listFichier)
validationSet,testSet = split_list(rest)


os.mkdir("./data/train/train")
os.mkdir("./data/train/label")
for i in trainSet:
    shutil.copy("./avant/"+i,"./data/train/train/"+i)
    shutil.copy("./apres/"+i,"./data/train/label/"+i)

os.mkdir("./data/validation/train")
os.mkdir("./data/validation/label")
for i in validationSet:
    shutil.copy("./avant/"+i,"./data/validation/train/"+i)
    shutil.copy("./apres/"+i,"./data/validation/label/"+i)


os.mkdir("./data/test/test_folder")

for i in testSet:
    shutil.copy("./avant/"+i,"./data/test/test_folder/"+i)
