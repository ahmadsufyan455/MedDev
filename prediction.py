from io import BytesIO
import numpy as np
import tensorflow as tf
from PIL import Image, ImageOps
import cv2


model = None

def load_model():
    model = tf.keras.models.load_model('my_model.hdf5')
    print("Model loaded")
    return model

def read_imagefile(file) -> Image.Image:
    image = Image.open(BytesIO(file))
    return image

def predict_desease(image: Image.Image):
    global model
    if model is None:
        model = load_model()

    size = (150, 150)
    image =ImageOps.fit(image, size, Image.ANTIALIAS)
    image = np.asarray(image)
    img = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    img_resize = (cv2.resize(img, dsize=(150, 150),
                    interpolation=cv2.INTER_CUBIC))/255.

    img_reshape = img_resize[np.newaxis, ...]

    prediction = model.predict(img_reshape)

    return prediction
