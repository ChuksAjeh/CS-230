# Level File Format

* List the level size, for the Cell[]
  * 0,0 will be top left
* Display the level layout, in ASCII
  * \# will be a wall
  * " " will be an empty spot / entity
* List all entities available in the level and their locations
  * Player start location
    * Player, x, y
  * Enemy locations, types and direction
    * EnemyType, x, y, direction
  * Goal location
    * Goal, x, y
  * Door locations and their values
    * KeyDoor, x, y, colour
    * TokenDoor, x, y, requirement
  * Key and token locations
    * Key, x, y, colour
    * Token, x, y
  * Special terrain locations
    * Fire, x, y
    * Water, x, y
  * Equipment locations
    * FireBoots, x, y
    * Flippers, x, y
  * Teleporter locations, from and to
    * Teleporter, xFrom, yFrom, xTo, yTo

# Example

```
10, 10
# ########
#    #   #
# # ## ###
# ####   #
# ##   # #
# #### # #
# #    # #
# ###### #
#        #
##########
Player,1,0
Goal,8,1
TokenDoor,6,2,3
Token,4,1
Token,3,6
Token,4,4
```

Obviously these would be better designed and actually have enemies etc