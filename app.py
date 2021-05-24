from prediction import predict_desease, read_imagefile
import uvicorn
from fastapi import FastAPI, File, UploadFile


# 2. Create the app object
app = FastAPI()  


@app.get('/')
def index():
    return {'message': 'Hello, World'}

@app.post('/api/predict')
def predict_image(file: bytes = File(...)):
    #read the file
    image = read_imagefile(file)
    #predicton
    prediction = predict_desease(image)
    
    #print(prediction)
    print("test")


if __name__ == '__main__':
    uvicorn.run(app, host='127.0.0.1', port=8000)