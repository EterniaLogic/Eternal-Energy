# Eternal Energy (EtE)
Minecraftforge Mod, dealing with power systems and electrical engineering (Minecraft version 1.8.9+)

This mod is being designed to be practical in a typical minecraft world, while also practicing simple to complex electrical circuits in an easy way. It is understandable that some people may not even want to come close to these ideas and learn them, so a sharing system will be implemented.

Ideas for this mod come directly out of electrical engineering and the corresponding units (Voltage, Amperage, Resistance and Wattage) However, the central idea for this mod is to help people understand what specific modules do with a simple interface.

## What does this bring to the table? What can I expect? ##
* Initial to End-game power solutions which range from 0.01 rf/t to 1.5B RF/t (Measured in Wh to gWh)
* High-speed programmable devices that can handle hundreds of I/O
* Initial to End-game item and liquids management
* Nanorobotics and robotics systems to modify the environment. Nanorobots can attack/heal/shield, Robots can Quarry, move blocks. Nanorobots are Item-only, while Robots are block-only.
* Circuit and Radio Communications block based development. Players can share schematics so others do not have to struggle with the basics (Internet required)

## What is the point in this mod being so complex? ##
Players can have the final say in what they want to do in minecraft. Want an item that shields you using nanorobots? Ok. Want a satellite that can pass messages, liquids, RF Signals or items across the world? Yay!

### Blocks ###
* **Ender Reactor**: Generates power from The End with low-frequency mass oscillations, unrelated to the Schematic system.
* **Schematic Creator**: Primary design block to build custom blocks and items that have schematics using player-created content. Materials required for said item/block is calculated on the complexity of the schematic.
* **Custom Schematic Block**: Block that is designed from the Schematic Creator for specific player content
* **CubeSat Rocket**: Launches Cube Satellites into space, which will end up in Geosynchronous orbit

### Items ###
* **CubeSat**: Cube Satellite that can transfer Laser Power, messages or Matter (Items/liquids)
* **Custom Schematic Item**: Held Item that is designed from the Schematic Creator for specific player content

### Drop-in schematic module list ###
* **Conversion**:  Buck/Boost/SEPIC converters, Regulators
* **RF**: Filters, Antennas, Amplifiers, Mixers, Microstrips, Oscillator
* **Audio**: Speaker, Mic (P2P Voip, voice commands)
* **Computers**: CPU, Memory, I/O (1.2 / 3.3 Volt operation)
* **World**: Nanorobotics, Robotics Interface, Item/Fluid
* **Player/Items**: Crafting, Smelting, Sorting, Ender Materializer
* **Visualization**: 3D Graphics in world generation, Particles, Videos

## Dependencies
* CoFH (RF/t conversion, when it reaches 1.8.9)
* Project Red (Blue power conversion)
* Industrial Craft 2 (eu/t conversion)
