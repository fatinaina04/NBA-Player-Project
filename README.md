# NBA-Player-Project
# .jar FILES:
this .jar files need to be added into the classpath of your project.
1. org.json.jar
2. commons-lang3-3.14.0.jar
3. opencsv-5.5.2.jar

# SRC (nba2 file)
1. # Api.java
   - the API key only valid until 19 June 2024
   - not sure if Api Class can run after that since we pay for upgrade.(free tier has too little request per minutes)
   - took some times to process (10-15 mins) when you run the Api Class
2. Player.java
   - 
3. MainAppWindow.java
   - 
4. Roster.java
   - 
   - Fetches data from players.csv file (this file is needed in order to run roster class)
   - Must run Api.java first to successfully run roster.java
   - Creates roster.csv upon completion of roster validation
5. PerformanceRanking.java
   - 
6. InjuryReserve.java
   -  
7. **ContractExtensionQueue.java**
   -  allows the user to either add or remove players to contract extension queue by entering their first and last name
   -  if the entered name is not  in the roster, user is not able to add or remove player in the contract extension queue
   -  added player's name will be displayed in a list and the 'Contract Extension' column in 'roster.csv' will change status to true
   -  removed player's name will be removed from the list displayed and the 'Contract Extension' column in 'roster.csv' will change status to false
8. **NBACity.java**
   - displaying the digital map of cities around USA that is involved in the NBA games including their distances from each other
   - calculating the shortest distance and extract the shortest path using BFS and DFS algorithm
   - presenting the overall best journey that could be offered for the Spurs to travel to each city exactly once
   - another digital map of the journey of the shortest path will be displayed along with the algorithms and overall best button
   
# CSV FILES:
1. players.csv :
- created when you run the Api class.
- contains all player attributes and stats (except for players who have missing values)

2. roster.csv:
- a.k.a. the active roster
- contains all the player attributes that you already added to roster.
- created when the roster is validated in th roster class

