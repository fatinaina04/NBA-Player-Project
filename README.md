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
5. PerformanceRanking.java
   - 
6. InjuryReserve.java
   -  
7. ContractExtensionQueue.java
   -  
8. NBACity.java
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

