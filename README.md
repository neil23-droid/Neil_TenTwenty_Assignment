# Neil_TenTwenty_Assignment

The Movies App mimics the flow which users take when booking a ticket for a movie on a mobile app. It is a hard coded app.

The app comprises of 9 packages and 4 screens

Packages:
1)Adapters - adapters for displaying data in spinners and lists
2)BookedTicketHistory - UI displays the list of tickets booked by the user and fetches data from the database
3)Database - constains files wrt database creation
4)DataModel - contains models used in the spinner adapters
5)MovieDetails - UI tha displays movies details after user selects a movie from the up coming movies list
6)MovieListing - UI that displays a list of upcoming movies
7)Others - files containing reused constants, extensions of classes and prefernces
8)Retrofit - files for making API calls to remote data sources
9)BookTicket - UI that enales the user to confirm ticket details

Screens & flow
1)MoviesListing Screen
-When the user opens the app, the MoviesListing screen will be displayed that shows the list of upcoming movies. 
-The upcoming movies is fetched from an API call and is  then stored and fetched thereafter in a local database
-User can click on a menu from the top left of the screen where the user will able to see list of confirmed tickets

2)Movie Details Screen
-On selecting a movie from the up coming movies list will direct the user to this page.
-displays details of the selected movie

3)Book Ticket Screen
-on clicking the book ticket button from movie details screen will direct the user to this screen
-users can select location, cinema and seat number drom a drop down
-on clicking confirm ticket button will save the ticket information in a local database and direct the user to the movies listing screen

4)Booked Ticket History
-Users need to select this option from the movies listing screen
-displays the list of confirmed tickets for the user

Architecture:
-Uses basics of MVVM
-Each Screen is made up of an Activity, a View Model and a Repository
