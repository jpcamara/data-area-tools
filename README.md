*What is it*

Tools for tokenizing + parsing + navigating COBOL 82 and IBM z/OS Assembler Data Areas.
Currently only has capabilities for COBOL Data Areas, and not the full set of functionality.

Idea is - Data Area definitions are setup like trees

* 01 TOP-LEVEL
** 05 LOWER-LEVEL
*** 10 SOME-DATA      PIC X(2).

So why not be able to traverse the data like a tree, and use the DA structure as the definition of what that tree looks like?
Then since it's a tree - hey, let's use XPath on it!

*Errr... uh... what?*

Yea I know, right? So awesome you're speechless?

...or more likely you're thinking - "who cares about this junk?"
You probably don't, but I do.
And many others could as well. If nothing else it gives a strange exploration of what it looks like to apply Jaxen to flat data.

*I'm leaving now*

Sure sure. You'll be back.