from loc import nearby_search
from flask import Flask
import json
import requests

# 2. Create the app object
app = Flask(__name__)

@app.route('/')
def home():
    return 'Server is Online"'
    
@app.route('/article',methods = ['get'])
def art():
    # Opening JSON file
    f = open('article.json')
    # returns JSON object as 
    # a dictionary
    data = json.load(f)

    return data

@app.route('/nearby',methods = ['get'])
def nearby():
    res = requests.get('https://ipinfo.io/')
    data = res.json()
    loc=data['loc']

    r = nearby_search(loc)

    return r

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=8080, debug=True)

