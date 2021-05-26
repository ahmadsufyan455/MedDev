from prediction import predict, prepocess, read_image
import uvicorn
from fastapi import FastAPI, File, UploadFile
# 2. Create the app object
app = FastAPI()  


@app.get("/")
def predict_image():

    #read file from dir
    image = read_image()
    #prepocessing image
    image = prepocess(image)
    #make prediction
    prediction = predict(image)
    
    print(prediction)

    return prediction


if __name__ == '__main__':
    uvicorn.run(app, host='127.0.0.1', port=8000)