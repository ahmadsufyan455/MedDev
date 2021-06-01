import googlemaps
import time
import json

def nearby_search(location):
    API_KEY = 'AIzaSyCAWBx8-LrhpAUoD7w4GslaGw5cApUqv7o'

    #define the client
    gmaps = googlemaps.Client(API_KEY)
    #do nearby search
    places_result = gmaps.places_nearby(location, radius = 40000, open_now  = False, type = 'hospital')

    time.sleep(3)

    place_result  = gmaps.places_nearby(page_token = places_result['next_page_token'])


    stored_results = []
    data = {}
    data['Nearby_places'] = []

    # loop through each of the places in the results, and get the place details.      
    for place in places_result['results']:

        # define the place id, needed to get place details. Formatted as a string.
        my_place_id = place['place_id']

        # define the fields you would liked return. Formatted as a list.
        my_fields = ['name', 'formatted_address', 'formatted_phone_number', 'icon']

        # make a request for the details.
        places_details  = gmaps.place(place_id= my_place_id , fields= my_fields)
       
        # store the results in a list object.
        stored_results.append(places_details['result'])

    aList = stored_results
    data['Nearby_places'].append(aList)

    return data

