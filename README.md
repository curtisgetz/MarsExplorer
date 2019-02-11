Continued development on my Capstone Project from Udacity's Android Developer Nanodegree program. 
Mars Explorer  (Download from the Play Store - https://play.google.com/store/apps/details?id=com.curtisgetz.marsexplorer) 

 API Key in NetworkUtils.java.  Can use "DEMO_KEY" for limited access.  If you reach limits you can apply for a key at https://api.nasa.gov/index.html#apply-for-an-api-key


Mars Explorer allows you to explore The Red Planet as a whole, or through the eyes of one of three NASA rovers currently on the surface. 
Learn about each of the three rovers and the data they collect on the surface of Mars. 

•Search through years worth of pictures the rovers have taken. 
•Save and share your favorite pictures. 
•Learn about how the rovers work and how they were built. 
•Learn something new about Mars every day with the Daily Fact home screen widget.
•Get the latest weather details from the Rover Environmental Monitoring Station on board the Curiosity Rover.


App uses MVVM design pattern. 
Rover mission details and photos are obtained through NASA's REST API. 
Users can search for photos by Martian Sol by entering a number within the valid range of Sols for that rover, have the app select a random Martian Sol, or select a date via a DatePicker Dialog. 
If there are no photos available on the sol or date selected the app will attempt to find the next sol or date with photos. 

Users have the option on retrieving all photos, or limiting the number of photos retrieved. Photos are limited by default. Retrieving all photos can take more time and use more data.
Each camera has a Recyclerview containing photos for that camera on the selected sol/date. Clicking a photo will enlarge the photo with options for saving or sharing. ViewPager allows easy paging through all photos for that camera. Favorite photos are saved in a Room database (Rover mission details are also saved into Room).

Home screen widget displays a daily Mars fact. User can cycle through more facts inside the app. The facts are stored in a Firebase Realtime Database. The source for each fact is displayed and will open the url via an implicit intent.


