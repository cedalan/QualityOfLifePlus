# QualityOfLifePlus
A minecraft plugin specifically made for my friends and I.

## How to install:
Clone repo to your machine and modify build_and_copy.sh according to the instructions in the script. Then run
```
./build_and_copy.sh
```
to build and copy the file into your plugins folder. If you are on windows I suggest you use WSL or Git bash:)
If for some reason the script is not marked as excecutable after cloning this repo, run:
```
chmod +x build_and_copy.sh
```
and then 
```
./build_and_copy.sh
```

## BlockBreaking (Blb):
As the height of the Minecraft world increases and after the introduction of Deepslate the task of leaving our mineshaft and getting up to the surface can be both difficult and time consuming. In a world devestated by fast-paced algorithms delivering mental stimulation at a never seen pace it is important that the gaming world follows. BlB introduces a level up system to blockbreaking, which gives you one point in haste each time you level up. 

### Commands:

```
/blb list - Lists all commands
/blb leaderboard <tool> - Shows XP leaderboard for specified tool
/blb levels - Shows user levels for all tools
/blb level <tool> - Shows user level for specified tool
/blb xp <tool> <add/remove> <amount> - Adjusts player experience for given tool
```
