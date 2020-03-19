# Patchwork

## What is Patchwork?
Patchwork makes quilt planning easy for new quilters. A new quilt is an opportunity to play with shape, colour, and size — to create a piece of functional art. That's the fun part. The *not-so-fun* part is working out the quilt math to turn inspiration into reality. How many 6" blocks do I need to make a baby quilt? How much fabric do I need? How many pieces and what shapes do I need to cut to make all the patches for the blocks? 

Patchwork is a simple app that helps new quilters plan their next project. Based on the size of quilt a user wants to make, and the size of blocks they want to use, Patchwork will lay out a quilt grid. Users can then add blocks to their quilt — and rearrange them as much as they like until they get the perfect layout. When the designing is done, Patchwork will calculate how many of each type of patch need to be made and how much fabric is needed for the quilt top, backing, and binding.

## Why Patchwork?
As a new quilter who wanted to throw pre-made patterns to the wind and design my own quilt tops, I often struggled to visualize what my quilt would look like without using pages and pages of graph paper. I also struggled to get the math right the first time. And, inevitably, by the time I was ready to buy fabric, I'd have misplaced the scrap paper I'd used to scribble down my calculations and have to start all over again. Patchwork can help make both designing and calculating easier, so there's more time for actual **making**!

## User stories
### For Phase I of Patchwork:
- As a user, I want to be able to create a new quilt and choose a basic grid size and block size for the quilt.
- As a user, I want to be able to add and remove blocks from my quilt.
- As a user, I want to be able to see a list of the blocks in my quilt.
- As a user, I want to be able to calculate how many of each type of patch I need to create my quilt.
- As a user, I want to be able to calculate the total fabric requirements for my quilt, including backing and binding.
### For Phase 2 of Patchwork:
- As a user, I want to be able to save my quilt to file at any time while I design it.
- As a user, when I select the quit option from the application menu, I want to be prompted to save my quilt to file.
- As a user, I want to be able to optionally load my quilt from file when the program starts.
- As a developer, I want to be able to easily add pre-programmed blocks to Patchwork so they are available to users.

## Instructions for TAs:
If you'd like, you can create a new quilt by using the spinners located in the top left of the application window, and clicking on Create button. After doing this:
- You can generate the first required event by clicking on any quilt block in the scroll pane and dragging it onto a block space in the quilt grid. This adds a block to the quilt. (You can drag and drop another block on top of an existing block if you want to replace it.)
- You can generate the second required event by clicking on the trash can icon in the top right corner of any block you've added to the quilt grid. This will remove the block from the quilt.
- You can locate my visual component by creating a new quilt or loading a saved quilt. The rendered quilt image is my visual component.
- You can save the state of my application by clicking on the Save button in the top right of the application window. Also, if a quilt is created or loaded, and you try to exit the application, you will be prompted to save your quilt before exiting.
- You can reload the state of my application by clicking on the Load button in the top right of the application window.

Additionally, you can interact with the program in the following ways:
- You can choose colors for the quilt by using the color pickers.
- You can calculate how much fabric and how many patches are needed for the quilt by clicking on the Calculate button in the bottom left of the application window.

## Credits for icons:
- Patchwork icon credit: icon made by <a href="https://www.flaticon.com/authors/becris" title="Becris">Becris</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>

## Future features
In future iterations of Patchwork I hope to:
- Make more blocks available, and add the ability for users to create their own blocks.
- Add the ability to apply fabrics to specific blocks and/or patches.
- Add blocks with different number of patches (4-patch, 16-patch).
- Add the option of resizing the quilt by changing the block size and/or adding or removing rows or columns of blocks.
- Add an option to calculate how many fat quarters or yards of standard-width fabric are needed to cover the quilt's fabric requirements.
