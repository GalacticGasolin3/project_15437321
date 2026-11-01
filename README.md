UNDER DEVELOPMENT

Compiler for the MCScript programming language for use in developing Minecraft datapacks.

Compiles directly into a Minecraft datapack.

Current features:

Variable declarations with the keywords "let", "var", or "storage", variables are not strongly typed and can be an int, a float, a string, a selector object, or a Minecraft NBT object.

Expression parsing for mathematical expressions

Currently 4 Minecraft commands have been translated to MCScript:
/say -> say(<any>)
/tellraw -> tellraw(<selector>, <nbt>)
/summon -> summon(<string>, )
/kill -> kill(<selector>, <string>)


functions are create with the "function" keyword with the syntax:
function <name>(<identifiers>) {<code>}

functions can have parameters and can return values with the "return" key word.
