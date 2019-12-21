# OOP Evolution

This is a project for a university OOP course.
The requirements are detailed (in Polish) [here](https://github.com/apohllo/obiektowe-lab/tree/0a1482914ae2f3d11bc532eb617103aa517db299/lab8).

## Instructions

To run this program you need the `com.googlecode.json-simple` library which you can get from maven.

The entry point `agh.cs.evolution.Main` accepts a single optional argument, the path to a JSON configuration file for the simulation.

The JSON file is structured as follows:
```json
{
    "mapSize": [100, 30],
    "jungleSize": [10, 10],
    "startEnergy": 50,
    "plantEnergy": 25,
    "moveEnergy": 1,
    "startAnimals": 25,
    "grassTurns": 50
}
```

- `mapSize`: an array of integers `[width, height]` that describes the shape of the map.
- `jungleSize`: as above, but describes the shape of the "Jungle" area, which has separate grass generation.
- `startEnergy` / `plantEnergy` / `moveEnergy`: Integers that describe how much energy animals start with, how much they get when they eat, and how much they lose each turn they move, respectively.
- `startAnimals`: An integer that describes the number of animals initially placed on the map.
- `grassTurns`: An optional integer value that describes how many turns the simulation will run before placing any animals on the map, this can be used to allow a bit of grass to accumulate on the map so that new animals don't starve at the start of the simulation.

## Example output

```
Using seed: 0
+----------------------------------------------------------------------------------------------------+
|,,o,o  ,,  o o         o     , ,               , o ,    ,  , ,   , , ,,   , , ,,   ,   ,, , , ,  ,, |
|,,,       o     o    o     ,,   ,     ,    ,  ,            ,             , ,         ,  ,,,    , , ,|
|,,,   ,                        , ,     o                 , ,      ,   ,,  ,,        ,,,   ,    ,  , |
|,,,, , ,  o            ,   ,                                      ,    ,,  ,,   , ,       ,      ,  |
|,,, ,,  ,          o     o          ,,    ,                 ,                ,       ,,,  , ,,,  ,  |
|,,oo,,,,     o                     o,                       ,            ,,,   , ,    ,,,   ,  ,  , |
|,,, o ,,         o    o                           o      ,  ,             ,   ,        ,,,,,,  ,,,  |
|,, ,         ,            ,, o       ,    ,   ,       ,           ,, ,   ,         ,, ,   ,     ,  ,|
|,,  , ,o, o    o                     ,      o          ,,  , ,,           ,     ,,,  , ,,,  , ,, , ,|
|,    o ,,                  , ,                 o         ,   ,,  ,     ,  , ,,, ,    ,  , , ,  ,,   |
|        o                         , ,                   ,   ,   ,,  ,    ,,      ,      ,,   ,    , |
|o  , o    ,                            , ,        ,     ,            ,          ,,   , , ,,     , ,,|
|                                        ,                  , ,     ,         ,    ,   ,,  ,,      ,,|
|, o                                     ,       ,             ,,,  , ,  ,,,   ,     ,   ,,,,,    ,  |
|,,                     o                 ,                  o  ,  ,       ,,, ,           ,    ,,,  |
|                 ,                   ,            ,                ,  , , , , , ,,,,,   ,,,   , ,  ,|
|            ,                 ,   o     , ,    , ,    ,  ,   ,,,   ,           , ,, ,      ,  ,,   ,|
|    o                                  ,        ,   ,    , ,          ,   ,  ,,,  ,   , ,,   ,  ,  ,|
|       ,                           ,          , ,    ,  ,,               , , ,,,,  , ,, ,,     ,, , |
|    o                 o         ,    o      , ,,       ,,      ,         ,,   ,,, ,       ,   ,     |
|                     ,o               ,   ,   ,  , ,, ,            ,    , ,    , ,     ,,       ,  o|
|                                       ,  , ,,  ,,,,        ,,              , ,,   ,                |
|              o       o                   , ,,, , , ,,,  ,,          ,    , ,,  ,   , ,    ,        |
|        o                                    ,         ,,     ,  ,  ,,,,    ,     , , ,   o ,       |
|   ,                              ,          ,  , , ,,  ,  ,,            ,   ,   ,    ,          ,  |
|  ,                                          ,   ,,,,,,, ,                 ,     ,,  , ,    ,     , |
|        o         o      ,               ,    ,     ,,,        ,,,    ,,  ,  ,,    ,,,         , ,  |
| o       2o   o   o                o      ,    , ,     ,      ,  ,   ,,  ,  , ,,, ,,     ,    ,,    |
|        o                     ,            ,,    , ,, , , ,  ,,         ,,  ,,     ,,  ,,       ,   |
|           o                  ,    ,             , , ,,  ,     ,,,, ,    , ,,, ,  , , ,  ,, ,,,     |
+----------------------------------------------------------------------------------------------------+
=== STATISTICS ===
Animal count:     56
Average energy:  220.02
Grass count:     548
```
