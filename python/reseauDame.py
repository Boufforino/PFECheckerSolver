from keras.models import Sequential
from keras.layers import Conv2D,Dropout,Dense,Reshape,Flatten
from keras_preprocessing.image import ImageDataGenerator
from keras import optimizers
from PIL import Image
from os import listdir
import numpy
import random
# import matplotlib.pyplot as plt
datagen=ImageDataGenerator()

###############################################################################

PATHt = "./data/train/train/"
PATHl = "./data/train/label/"
listFichier = listdir(PATHt)

listX= []
for i in listFichier :
    img = Image.open(PATHt+i)
    savet = numpy.array(img)
    img.close()
    img = Image.open(PATHl+i)
    savel = numpy.array(img)
    img.close()
    listX.append((savet,savel))

random.shuffle(listX)

def separateTrainLabel(list):
    t = []
    l = []
    for i in list :
        t.append(i[0])
        l.append(i[1])
    return t , l

X_train , x_label = separateTrainLabel(listX)
X_train = numpy.array(X_train)
x_label = numpy.array(x_label)

##############################################################################

PATHt = "./data/validation/train/"
PATHl = "./data/validation/label/"
listFichier = listdir(PATHt)

listV= []
for i in listFichier :
    img = Image.open(PATHt+i)
    savet = numpy.array(img)
    img.close()
    img = Image.open(PATHl+i)
    savel = numpy.array(img)
    img.close()
    listV.append((savet,savel))

random.shuffle(listV)


V_train , v_label = separateTrainLabel(listV)

V_train = numpy.array(V_train)
v_label = numpy.array(v_label)






model = Sequential()

model.add(Conv2D(filters = 5,
                 kernel_size = 5,
                 strides = 1,
                 activation = 'relu',
                 padding='same',
                 input_shape= (8,4,3)))

model.add(Conv2D(filters = 5,
                 kernel_size = 5,
                 strides = 1,
                 activation = 'relu',
                 padding='same'))


model.add(Flatten())

model.add(Dense(120))

model.add(Dense(96))

model.add(Reshape((8,4,3)))


# model.add(Dense(128))
#
# model.add(Dense(96))
#
# model.add(Reshape((8,4,3)))
#


optim = optimizers.Adam(lr=0.1, beta_1=0.9, beta_2=0.999, epsilon=None, decay=0.0, amsgrad=False)

model.compile(optimizer=optim, loss="mse", metrics=["accuracy"])

model.summary()
# #
from keras.callbacks import TensorBoard
tensorboard = TensorBoard(log_dir="logs")

history = model.fit(X_train, x_label, \
                          validation_split = 0.1,epochs = 5, batch_size = 128, \
                          verbose=True, callbacks=[tensorboard])


loss, accuracy = model.evaluate(x=V_train, y=v_label, verbose=0)
print("Données de test - Perte: %2.2f - Précision: %2.2f" %(loss, 100*accuracy))




# def plot_learning_curves(history):
#     acc = history.history["acc"]
#     loss = history.history["loss"]
#     val_acc = history.history["val_acc"]
#     val_loss = history.history["val_loss"]
#     epochs = range(len(acc))
#
#     fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(15,5))
#
#     ax1.plot(epochs, acc, label="Entraînement")
#     ax1.plot(epochs, val_acc, label="Validation")
#     ax1.set_title("Précision - Données entraînement vs. validation.")
#     ax1.set_ylabel("Précision (%)")
#     ax1.set_xlabel("Epoch")
#     ax1.legend()
#
#     ax2.plot(epochs, loss, label="Entraînement")
#     ax2.plot(epochs, val_loss, label="Validation")
#     ax2.set_title("Perte - Données entraînement vs. validation.")
#     ax2.set_ylabel('Perte')
#     ax2.set_xlabel('Epoch')
#     ax2.legend()
#
#     fig.show()
#
# plot_learning_curves(history)
#
#










#
