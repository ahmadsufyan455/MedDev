# MedDev

Documentation
1. Create ML model &  API  (for information and health facilities)
2. Convert ML model to tflite
3. Deploy API to google cloud using Google app engine
4. Create android project using android studio
5. Import some library that we need
6. Download and copy tflite model to asset folder in android studio
7. Using tflite library to get data prediction
8. Using retrofit to get data from API

**Android**

Versions Used <br> <br>
Android Studio : 4.2.1 <br>
Kotlin : 1.5.10 <br>
Target SDK : 30 <br>
Min SDK : 21 

How to run in your own pc : 
1. Clone this branch using this command : 

<code>
        git clone -b front-end https://github.com/ahmadsufyan455/MedDev.git
</code>
  
2. Open in Android Studio 
  
3. Sync project with gradle files (under file tab in Android Studio)

===============================================================================================

**Machine Learning**
- System Requirement
- Working on : Google Colab
- Tensorflow : 2.5.0
- Dataset : https://www.kaggle.com/jr2ngb/cataractdataset

the steps to create an ML model
1. Check version tensorflow
2. Import all required library
3. Download Dataset
4. Split Dataset
5. Create image augmentation
6. Create Model or using transfer learning
7. Accuracy, loss, and score of model
8. Visualize the plot
9. Convert to TFlite

===============================================================================================

**Google Cloud**

**Before you begin**
1. In the Google Cloud Console, on the project selector page, select or create a Google Cloud project.
2. Make sure that billing is enabled for your Cloud project. Learn how to confirm that billing is enabled for your project.
3. Enable the Cloud Build API.
4. Enable the API
5. Install and initialize the Cloud SDK.

**Deploy and run Hello World on App Engine**
To deploy your app to the App Engine standard environment:
Deploy the Hello World app by running the following command from the your app local directory:
1. gcloud app deploy <name_your_file.yaml>
2. choose the region where the server is made, try to be close to your location

**Now your Application is ready*

**API nearby location**
1. First you have to enable Place API in the GPC API library
2. After that klik in credential and make new key for you API you can find Credential in same menu of API Library
