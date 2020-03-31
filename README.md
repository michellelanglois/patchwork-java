# Patchwork

## What is Patchwork?
Patchwork makes quilt planning easy for new quilters. A new quilt is an opportunity to play with shape, colour, and size — to create a piece of functional art. That's the fun part. The *not-so-fun* part is working out the quilt math to turn inspiration into reality. How many 6" blocks do I need to make a baby quilt? How much fabric do I need? How many pieces and what shapes do I need to cut to make all the patches for the blocks? 

Patchwork is a simple app that helps new quilters plan their next project. Based on the size of quilt a user wants to make, and the size of blocks they want to use, Patchwork will lay out a quilt grid. Users can then add blocks to their quilt — and rearrange them as much as they like until they get the perfect layout. When the designing is done, Patchwork will calculate how many of each type of patch need to be made and how much fabric is needed for the quilt top, backing, and binding.

## Why Patchwork?
As a new quilter who wanted to throw pre-made patterns to the wind and design my own quilt tops, I often struggled to visualize what my quilt would look like without using pages and pages of graph paper. I also struggled to get the math right the first time. And, inevitably, by the time I was ready to buy fabric, I'd have misplaced the scrap paper I'd used to scribble down my calculations and have to start all over again. Patchwork can help make both designing and calculating easier, so there's more time for actual **making**!

## User stories
### For Phase I of Patchwork
- As a user, I want to be able to create a new quilt and choose a basic grid size and block size for the quilt.
- As a user, I want to be able to add and remove blocks from my quilt.
- As a user, I want to be able to see a list of the blocks in my quilt.
- As a user, I want to be able to calculate how many of each type of patch I need to create my quilt.
- As a user, I want to be able to calculate the total fabric requirements for my quilt, including backing and binding.
### For Phase 2 of Patchwork
- As a user, I want to be able to save my quilt to file at any time while I design it.
- As a user, when I select the quit option from the application menu, I want to be prompted to save my quilt to file.
- As a user, I want to be able to optionally load my quilt from file when the program starts.
- As a developer, I want to be able to easily add pre-programmed blocks to Patchwork so they are available to users.

## Credits for icons
- Patchwork icon credit: icon made by <a href="https://www.flaticon.com/authors/becris" title="Becris">Becris</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
- Trashcan icon credit: icon made by <a href="https://www.flaticon.com/authors/kiranshastry" title="Kiranshastry">Kiranshastry</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

## Future features
In future iterations of Patchwork I hope to:
- Make more blocks available, and add the ability for users to create their own blocks.
- Add the ability to apply fabrics to specific blocks and/or patches.
- Add blocks with different number of patches (4-patch, 16-patch).
- Add the option of resizing the quilt by changing the block size and/or adding or removing rows or columns of blocks.
- Add an option to calculate how many fat quarters or yards of standard-width fabric are needed to cover the quilt's fabric requirements.

## Instructions for TAs
If you'd like, you can create a new quilt by using the spinners located in the top left of the application window, and clicking the Create button. (If you'd prefer, you can just load the existing quilt using the instructions in the fifth bullet point below.) After doing this:
- You can generate the first required event by clicking on any quilt block in the scroll pane and dragging it onto a block space in the quilt grid. This adds a block to the quilt. (You can drag and drop another block on top of an existing block if you want to replace it.)
- You can generate the second required event by clicking on the trash can icon in the top right corner of any block you've added to the quilt grid. This will remove the block from the quilt.
- You can locate my visual component by creating a new quilt or loading a saved quilt. The rendered quilt image is my visual component. You can change the color of the quilt using the color pickers. Other visual components are: I added an icon to the title bar and I used a trashcan icon to signal that you can delete a block.
- You can save the state of my application by clicking on the Save button in the top right of the application window. Also, if a quilt is created or loaded, and you try to exit the application, you will be prompted to save your quilt before exiting.
- You can reload the state of my application by clicking on the Load button in the top right of the application window.

Additionally, you can interact with the program in the following ways:
- You can choose colors for the quilt by using the color pickers.
- You can calculate how much fabric and how many patches are needed for the quilt by clicking on the Calculate button in the bottom left of the application window.

## Phase 4: Task 2
- I implemented a type hierarchy. I have an abstract class, Patch, and three subclasses: HalfSquareTriangle, HalfSquare, and Square. The three subclasses override two methods from Patch: calculateFabric() and getType(). (Note: calculateFabric() is called from Patch's calculateFabric(String fabric) method, and provides a different calculation of how much fabric is needed for each patch type.)
- I also made Quilt a robust method. The quilt constructor now throws an IllegalQuiltSizeException if passed parameters that are < 0 for numBlocksAcross, numBlocksDown, or blockSize. Additionally, addBlock(String blockMap, int slot) and removeBlock(int slot) both throw a SlotOutOfBoundsException if given a slot that is < 0 or >= total number of blocks in the quilt. The exceptions are handled in my GUI. I added tests for these exceptions in the QuiltTest class.

## Phase 4: Task 3
### Minor problems
- I identified some methods that were no longer needed (e.g. toString()-type methods used for the console version of the app) and removed these.
- I removed a field in Patch -- type -- that was not necessary and changed the automatically generated Gson type field to be more descriptive in my GsonConfigured class.

### Bigger problems
#### Coupling and code redundancy between Quilt, Block, and Patch
#####Problem:
There was code redundancy and coupling between Quilt, Block, and Patch in that I was maintaining a finishedSize field in Block and Patch that was entirely dependent on the Quilt's blockSize, and was only using this field in the calculateFabric() method (which was only ever called from Quilt). 

#####Solution: 
I removed this field. I realized I could pass in the finished block size from Quilt as a parameter to the calculateFabric() methods in Block and Patch instead. That way, if I want to change the quilt size after creating a quilt, I only need to change the size in one place -- Quilt -- instead of propagating that change through the Quilt's blocks and patches, too.

#### Lack of cohesion in Block
#####Problem:
There was lack of cohesion in Block in that Block contained a helper method (getPatchesFromPattern()) to get the pattern of patches for the block type being instantiated. This also introduced some coupling between Block, BlockMap, and Reader in that Block needed to know what kind of information BlockMap stored to access the file name where Block's pattern was stored, and then call on Reader to read the pattern. If I wanted, for example, to have BlockMap store the actual File as a value instead of the file name, I would have to make changes in Block also. 

#####Solution: 
Since the purpose of the BlockMap class was to maintain all information about available blocks and their patterns, I decided to move the patch pattern reading logic into a method in BlockMap so that Block can just directly ask the BlockMap for its appropriate patches in its constructor. This improved cohesion in Block and reduced coupling. (I also added a listAvailableBlocks() method to BlockMap for use in the QuiltApp to reduce some minor coupling that existed there where QuiltApp was directly accessing the block map's keys.)

#### Cohesion and coupling issues in ui package
#####Problem: 
There were significant coupling and cohesion issues in my ui package. For example:
- QuiltApp (my main GUI class) was not very cohesive: it initialized the layout, set up basic procedures to close the app, directly initialized and populated some panels (but not others that were separate classes), contained logic to render blocks, and more.
- Some panels that I designed as classes had other panels as fields, introducing strong coupling. For example, the BlockSpace class had a QuiltGrid field so that it could access the current Quilt that was a field in the QuiltGrid class, which QuiltGrid was getting from QuiltApp. Messy!

#####Solution:
I refactored my GUI as follows:
- All subpanels for the main layout are now classes and the main QuiltApp just composes them into the overall layout(each subpanel class is a field in QuiltApp). 
- QuiltApp handles events that affect the Quilt (e.g. loading, saving, adding/removing blocks), more than one subpanel, or the overall app (e.g. closing).
- If an event in a subpanel needs to change the Quilt or affects another subpanel, the event handler calls a method in QuiltApp, which then calls methods on Quilt or informs other subpanels that they need to make changes. This means that the only other panel my subpanels can access is the QuiltApp, which now also has the only Quilt field.

There are no doubt still things I could do better. Implementing the Observer pattern might simplify some code in the GUI, for example. However, even this initial refactoring made it easier to see how the pieces of the GUI fit together. Plus, the refactoring made it simpler to add the functionality of saving and reloading the quilt colors chosen by the user.

