# MN-Espresso

### Summary: ###
MovieNight is a sample Android application that uses the clean architecture approach and is written in Kotlin.

P.S: [I've written a blog post about this project.](https://goo.gl/KoVEh5)

![Screenshots](https://github.com/automaker10/MN_Espresso/blob/master/screenshots/screens-es-1.jpg)
![Screenshots](https://github.com/automaker10/MN_Espresso/blob/master/screenshots/screens-es-2.jpg)

### Espresso Testing: ### 
File TestScript is found: `.../presentation/java/androidtest/...` \
-TC01_Popuplar\
-TC02_Search

### Scenario Google Sheet: ### 
Reference: [I write Scenario in Google Sheet.](http://bit.ly/34t1JDg)

### Report Espresso Testing: ### 
Change package project navigation is "Project".\
File Report is found: `.../presentation/build/report/...` \
name: `Test Result - Test_in_... .html` \

### How to run the app: ###
This app uses [The MovieDB public API](https://developers.themoviedb.org/3/getting-started/introduction).
Register (its free) and grab your API key, 
then paste it inside: `.../res/values/api_key.xml/...`