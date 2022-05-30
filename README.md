# Redstone Bits

**Redstone Bits** is a Minecraft mod which adds a few new Redstone utilities!<br>
Using these, in conjunction with vanilla blocks like Observers and Pistons, one can automate many things... or simply make neat contraptions. 

Download it on [CurseForge here](https://www.curseforge.com/minecraft/mc-mods/redstone-bits).<br>
Or on [Modrinth here](https://modrinth.com/mod/redstone-bits).

Enjoy!

**DISCLAIMER**: This is NOT a Forge mod! It does not work with Forge. It uses the Fabric Mod Loader in addition to the Fabric API. It will most likely never be ported to Forge. Do not ask for a Forge port! You will be ignored!<br>
You are, however, free to port it to Forge yourself if you would like. Be sure to give credit, of course, and a share of any Curse Points would be appreciated...

## Blocks

### Placer
The Placer will take blocks inside its inventory and place them in front of it when it receives a Redstone signal.<br>
Like a Dispenser, it chooses from its nine slots at random.

Any items in the item tag `redstonebits:placer_blacklist` will be blacklisted, unable to be placed. Use this if something should definitely *not* be able to be used to placed automatically. The tag is empty by default.

### Breaker
The Breaker will break blocks placed in front of it when it receives a Redstone signal.<br>
You can put a tool (or any item) into it, and it will use that tool to break the block. This includes enchantments. Without a tool, it will act as if being broken by a player's hand.<br>
It takes time to break the block, the same amount of time the item inside would take to break it if held by a player.<br>
You can cancel breaking a block by giving it another Redstone signal while active. It will also cancel the break if the tool being used is removed or changed, or the block being broken is removed or changed.<br>
Comparators can be used to determine the breaking progress. It will also display the visual cracks in its GUI and on the block being broken.<br>
It cannot be used to break unbreakable blocks like Bedrock or Barriers.

Any blocks in the block tag `redstonebits:breaker_blacklist` will be blacklisted, unable to be broken. Use this if something should definitely *not* be able to be broken automatically. The tag is empty by default.<br>
Any items in the item tag `redstonebits:breaker_tool_blacklist` will be blacklisted, unable to be used as a tool to break blocks. Use this if something should definitely *not* be able to be used to break blocks automatically. The tag is empty by default.<br>

### Rotator
The Rotator will rotate blocks placed in front of it when it receives a Redstone signal.<br>
It can be right clicked to switch from rotating clockwise to counterclockwise.<br>
Using a comparator you can determine the current rotation of the targeted block (if supported).

Any blocks in the block tag `redstonebits:rotator_blacklist` will be blacklisted, unable to be rotated. Use this if something should definitely *not* be able to be rotated after being placed. The tag is empty by default.

### Checker
The Checker will output a signal if the block placed in front of it matches any of the ones in its inventory.<br>
Its inventory slots are numbered 0-15. The inventory slot the block is in determines the output signal strength.

### Counter
The Counter will emit a Redstone signal strength based on how many times it has received a signal.<br>
Power it once, and it will output a signal strength of 1 until you power it again, whereupon it'll go up to 2.<br>
Once it reaches 15, it will loop back around to 0. If right clicked, it will go in reverse.<br>
Like Repeaters, if it is recieving a signal into its side with another Redstone Gate it will become 'locked', making its output unable to be changed.

### Adder
The Adder is like a Counter, only its signal strength will be increased by the Redstone signal strength it receives.<br>
If you power it with strength 3, it will output a strength of 3. Power it again with a strength of 5, it will go up to 8.<br>
It, too, will loop back around to (and past) 0, and can be right clicked to subtract instead.<br>
Like Repeaters, if it is recieving a signal into its side with another Redstone Gate it will become 'locked', making its output unable to be changed.

### Resistor
The Resistor will reduce the strength of a signal given to it.<br>
By default, it halves the signal strength. You can right click it to change it to "third mode", which will divide the signal by 3.<br>
Another right click will change it to "one point five mode", which will divide the signal by 1.5.<br>
One more right click will return it to "halve mode".<br>
Like Repeaters, if it is recieving a signal into its side with another Redstone Gate it will become 'locked', making its output unable to be changed.

### Inverter
The Inverter is pretty straightforward, it will invert any signal strength (or lack thereof) that gets input.<br>
Input strength 15, it outputs 0. Input strength 3, it outputs 12. Input nothing, it outputs 15.<br>
Can be right clicked to go into "inverter no inverting!" mode, where it acts as a simple one-way gate that preserves the strength input.<br>
Like Repeaters, if it is recieving a signal into its side with another Redstone Gate it will become 'locked', making its output unable to be changed.

### Copper Buttons
Copper Buttons are buttons that oxidize over time, and can be waxed to preserve their current oxidation.<br>
Each oxidation level changes how long the Button will stay pressed for. The lengths can be configured individually through the mod's config.

### Medium Weighted Pressure Plates
Medium Weighted Pressure Plates act similarly to Light and Heavy Weighted Pressure Plates, but somewhere in the middle.<br>
They're made of Copper, and thus oxidize over time, and can be waxed to preserve their current oxidation.<br>
Each oxidation level changes the weighting of the Pressure Plate to gradually get heavier. The weightings can be configured individually through the mod's config.

### Analog Redstone Lamp
The Analog Redstone Lamp emits a light level based on the Redstone signal strength it receives.

### Redstone Display
The Redstone Display visually shows the Redstone signal strength it receives as a number.
