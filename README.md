# GamerChat
A platform for browsing the top free and paid android games and engage in real-time game-specific discussion with players around the world.

**Firebase, AngularJS, AngularFire, Java**

###Approach

**Data Collection
The Java crawler accesses Google Play Store game section and extracts all the available information about the top games like display image, title, rating, rank, description, developer information and price. This information is then stored in Firebase using its Java API. Since the Java program would terminate before the data is completely stored in the firebase instance, we keep the connection alive by setting the running thread to sleep mode.

**Back-end
A firebase data store instance is used to store the information about the games as well as the chat messages. To reduce impact to the end user and to minimize the page-loading time, data is denormalized and stored in separate sections called messages, details and gamesList. This way, only the required data is downloaded to the client machine.

**Front-end
The web application was developed using AngularJS, AngularFire and Bootstrap. AngularFire along with AngularJS provides three-way binding of data and with this, realtime synchronization of chat messages became possible. The application was then hosted to Firebase application server using Firebase CLI.\\

The user interface was developed using Twitter Bootstrap and the design is responsive in nature and adapts to the browser window size. This ensures that regardless of the monitor size, users can have a good user experience with the application.
