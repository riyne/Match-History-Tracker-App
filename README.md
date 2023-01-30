# Personal Project Description (P0):

**What will the application do?**

*NOTE:* Not all the features listed in the phase 1 write up made it to the final product. (ie. kills/deaths/assists 
stats, gold earned, averages of stats, etc...)

A stats tracking application for the game "League of Legends". (*This is a 5v5 multiplayer game where each player 
chooses one of five roles and the goal is to destroy the enemies base.*) Users would, in theory, enter in their
individual stats into the application after they play a game. There will many fields available to the player in 
which they can enter data which include:
- Win/loss
- Kills/deaths/assists
- Character played
- Position played

There will also be additional fields which are optional to input which, as of P0, include:
- Gold earned
- Game duration
- Opponent characters
- Ally characters

With the recorded data, user will have access to feature that breakdown averages, highs, and lows for specific
characters, roles, etc...

**Who will use it?**

League of Legends players.

**Why is this project of interest to you?**

This project is interesting to me because there are numerous implementable features. This allows me to expand or
compress the flexibility of this application.

## User Stories:

- As a user, I want to create a player profile/name for myself.
- As a user, I want to add a game to my overall record.
- As a user, I want to record which character I played in each match of my history.
- As a user, I want to record whether I won or loss each match in my history.
- As a user, I want to be able to save my profile.
- As a user, I want to be able to load my saved profile data.

# Instructions for Grader

NOTE: There is saved data already stored in the project, so you can press the "Load" button to see an example list of
players, and you can see each player's match by pressing "Edit Player" after selecting a player.

- You can generate the first required event related to adding profiles to the player list by entering a name and level
and then pressing the "Add Player" button.
- You can generate the second required event related to adding games to a profile by selecting the player you would 
like to edit and then pressing the "Edit Player" button. Next, you enter the character you played in the new window and
press the button that says "Win" to toggle the result of the game. After that you press the "Add Game" button to add
the game to the players record.
- You can locate my visual component by using the "Add Player", "Save", "Load", and "Add Game" buttons and a green check
mark will appear notifying the user that their action has been executed.
- You can save the state of my application by pressing the "Save" button.
- You can reload the state of my application by pressing the "Load" button

Phase 4: Task 2

Tue Nov 29 09:49:21 PST 2022
A new profile has been added:
Ryan - level 10

Tue Nov 29 09:49:28 PST 2022
A new Match has been added to Ryan

Tue Nov 29 09:49:30 PST 2022
A new Match has been added to Ryan

Tue Nov 29 09:49:37 PST 2022
A new profile has been added:
Joe - level 94

Tue Nov 29 09:49:42 PST 2022
A new Match has been added to Joe


Process finished with exit code 0

Phase 4: Task 3

Possible refactors:
- Abstract or interface initializing basic setup of the GUI (includes StartScreen and AddMatchScreen class). This will 
increase the cohesion of my classes presently, but also benefit future implementations. Namely, this refactoring would 
be beneficial for further extension that would require more windows or more elaborate windows.
- My design of the GUI is all coded with exact positions and sizes which is not good for future changes. To refactor,
I could change the GUI to utilize layouts. That is, utilize multiple panels instead of one singular one, with each
panel being responsible for different aspects of the GUI, and use layouts to arrange buttons, labels, and text fields.

Side note: JsonWriter and JsonReader have dependencies, but no fields of other classes or implements/extends. That's why
there are no arrows connected to them in the UML diagram. (Phase 4 instruction said not to add dependencies)