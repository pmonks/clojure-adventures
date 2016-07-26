# letterpress-solver

A solver for the orsm iOS game [LetterPress](https://itunes.apple.com/us/app/letterpress-word-game/id526619424?mt=8).

Notes:
 * while this tool can make it trivial to win LetterPress games, it doesn't take all the thinking out.  In particular it is completely oblivious to the territorial aspects of the game (though if you're aware of territorial tactics it can help a great deal, via the "required letters" option).
 * LetterPress uses a custom dictionary that is different to the one used by this tool.  It's likely this tool will find words that LetterPress doesn't accept, and vice versa.
 * This tool doesn't take all possible suffixes of a word into account (particularly plurals).  You may need to add those on yourself, if the board allows.

## Installation

Checkout the source from [GitHub](https://github.com/pmonks/clojure-adventures/tree/master/letterpress-solver).

## Usage

For now the letterpress solver is source only, so...

```shell
 lein deps
 # Wait while Maven downloads teh internetz
 lein run -- [switches]
```
    Switches                 Default      Desc                                                  
    --------                 -------      ----                                                  
    -a, --all-letters                     All letters on the board (mandatory)
    -r, --required-letters                The letters that must appear in the resulting word(s) (optional)
    -n, --num-results        100          The number of results (optional)
    -d, --dictionary-source  linux.words  The source dictionary to use (may be a file or a URL) (optional)
    -h, --no-help, --help    false        Show help (optional)

### Examples:
```shell
 # Find the first 100 words that can be made from the alphabet
 lein run -- -a abcdefghijklmnopqrstuvwxyz

 # As above, but must contain the letters Q and Z
 lein run -- -a abcdefghijklmnopqrstuvwxyz -r qz
```

## License

Copyright © 2012-2016 Peter Monks (pmonks@gmail.com)

Distributed under the Creative Commons Attribution-ShareAlike 3.0 Unported License.
