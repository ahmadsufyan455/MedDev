from io import BytesIO
from sys import path
from fastapi import responses
import numpy as np
import tensorflow as tf
from PIL import Image, ImageOps
import cv2
import glob
from tensorflow.keras.applications import imagenet_utils
from tensorflow.keras.applications.imagenet_utils import decode_predictions


model = None

def load_model():
    model = tf.keras.models.load_model('67.hdf5')
    print("Model loaded")
    return model

_model = load_model()

def read_image():
    image = Image.open('C:/Users/asus/Desktop/MedDev2.0/temp/cataract1.png')
    return image

def prepocess(image: Image.Image):
    size = (150, 150)
    image =ImageOps.fit(image, size, Image.ANTIALIAS)
    image = np.asarray(image)
    image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    img_resize = (cv2.resize(image, dsize=(150, 150),
                    interpolation=cv2.INTER_CUBIC))/255.

    image= img_resize[np.newaxis, ...]

    return image


def predict(image: np.ndarray):
    class_names = ["Normal", "cataract", "glaucoma", "eye desease"]
    predictions = _model.predict(image)
    predictions = np.random.randn (1, 4) # instead of the return value of predict ()
    pred_labels = np.argmax (predictions, axis =-1)
    #print (pred_labels) # [2 0 0 2 2 2 4 2 3 3]
    # When replaced with a label name
    pred_label_names = [class_names [x] for x in pred_labels]
    print(predictions)
    return pred_label_names
