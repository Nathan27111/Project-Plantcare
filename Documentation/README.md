# Plantcare project

## The concept

Plantcare is an app which lets you add a plant to the app, after which it keeps track
of when it needs to be watered along with some other data. A plant needs to be placed in a room, 
which a user also adds to the app. 

## Get the project

To use the project, clone the repositories from git to your local machine. After this, the client applications can be used immediately, since they will connect to the laravel serve
r running on heroku at https://whispering-lake-39539.herokuapp.com .

To get the database, either run the sql script in the database directory of this repository or run the laravel migrations locally.

After creating the database, do not forget the update the .env file in the laravel project and the config.json file in the node.js project to
allow the servers to connect to the database.



## Implemented features

Laravel:

- Read, Write, Put, Delete requests to the database
- Multiligual endpoints and string translations
- JWT authentication
- Password encryption on registering a user  
- Eloquent models with relations
- Migrations, seeders and factories
- Data validation

NodeJS:

- Read, Write, put delete requests to the database
- JWT authentication
- Password encryption with bcrypt

PWA:

- Registering
- Logging in
- Displaying a list of plants on the homescreen
- Adding a plant (click the + button on the homescreen)
- Watering a plant
- SASS, Media queries (for the navbar when the screen is smaller)
- Adding a new room or viewing a list of rooms
- A service worker was added, which keeps some pages in it's cache. The register, login and homepages handle being offline/online differently.
Going to any other page is disabled until you go back online.
- Manifest file
- Installation on mobile devices
- Hosting on firebase at https://progressive-web-apps-427b5.web.app/

NMA:

- Multiple different layouts using Fragments and ContstraintLayout
- Navigation using the navigation file, navhostfragment and navigationdrawer
- Every page makes use of ViewModels and Livedata, Recyclerviews are used where needed
- A Room database is used to store the token of the user, so the user can stay logged in
- Connection to the laravel heroku server is made with Retrofit

## Known bugs/not fully/correctly implemented features
- Images are not implemented on any project
- The navigation drawer does not have a hamburger icon past the start menu
- The backbutton will navigate you to the wrong location in certain fragments
- Turning the screen in the NMA will mess up the layout
- Background tasks, implicit intents and notifications are not implemented
- Implementation of an app shell, indexed DB, Push notifications are not implemented
- PWA installation works on Android, but I was not able to test tis on Apple devices. This may not work
- Creating a plant isn't possible if the user did not create a room first
- The layout in the NMA can be messed up on some screens by using the keyboard

## Test user

A testuser is available when running the migrations & seeders of the database and in heroku:

First and lastname: test
email: test@test.be
Very secure password: admin123

This testuser has a few plants and rooms associated with it, so you do not have to create a plant or any rooms right away.