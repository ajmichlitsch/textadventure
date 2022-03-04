package textadventure.game;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room sandlot;
    
    private Room treeHouse;
    private Room beastYard;
    
    private Item pfFlyers;
    
    private Player player;
    private CLS cls_var;
    public Game() {
        parser= new Parser();
        player = new Player();
    }

    public static void main (String[] args) {
        Game game = new Game();
        game.setupGame();
        game.play();
    }

    public void printInformation() {      
        System.out.println (currentRoom.getShortDescription());
        System.out.println (currentRoom.getExitString());
        System.out.println(player.getInventoryString());
        System.out.println(currentRoom.getInventoryString());
    }

    public void setupGame() {
        sandlot = new Room("sandlot", " You are in the Sandlot.", "you are in the Sandlot.  To the north is your house, to the south is the tree house, and the east is The Beast’s yard.  There is a large fence you can’t climb over");
        treeHouse = new Room ("tree house", "you are in the tree house", "you are in the tree house.  To the north is the Sandlot, and to the NW is the beast’s yard, but an unprotected fall from this height would surely injure you");
        Room shoeStore = new Room ("shoeStore", "you are in the shoe store", "You are in the shoe store.  There is a pair of brand new PF Flyers (guaranteed to make a kid run faster and jump higher) on the shelf which cost $50.  To the west is smalls house.");
        Room smallsHouse= new Room ("smallsHouse", "you are in Small’s house", "You are in Small’s house.  On a shelf is some money and a rope long enough to help you climb down a tall fence. To the west is the shoe store");
        beastYard = new Room ("beastYard", "you are in the Beast’s backyard.", "You are in the Beast’s backyard.  In front of you sits the ball signed by Babe Ruth.");

        pfFlyers = new Item ("pfFlyers", "shoe descript");
        Item money= new Item ("money", "money descript");
        Item rope = new Item ("rope", "rope descript");
        Item ball = new Item ("ball", "ball descript");

        sandlot.setExit("south", treeHouse);
        sandlot.setExit("north", smallsHouse);
        sandlot.setExit("east", beastYard);
        treeHouse.setExit ("northeast", beastYard);
        treeHouse.setExit("north", sandlot);
        shoeStore.setExit ("east", smallsHouse);
        smallsHouse.setExit ("south", sandlot);
        smallsHouse.setExit("west", shoeStore);
        beastYard.setExit ("west", sandlot); 
        shoeStore.setItem("pfFlyers", pfFlyers);
        smallsHouse.setItem("money", money);
        smallsHouse.setItem("rope", rope);
        beastYard.setItem ("ball", ball);
        
        Help go = new Help ("go", "Use go command to move across the map. Use go and then a direction as stated in the description");
        Help grab = new Help ("grab", "Use grab command to pick up things found in rooms.  Use grab and then an item name as stated in the description.");
        Help drop = new Help ("drop", "Use drop command to remove items from your inventory.  Use drop and then an item name as stated in the description");
        Help inspect = new Help ("inspect", "Use inspect command to gain more details about a certain aspect of the game.");
        Help help = new Help ("help", "Use help to review rules associated with the game.  Use help by itself for a general guidline, add a command word for better detail");
        Help buy = new Help ("buy", "Use buy to purchase an item.");
       
        
        beastYard.setLocked(true); 
        
        currentRoom= sandlot;
        try {
            cls_var.main(); 
        }catch(Exception e) {
            System.out.println(e); 
        }
        
        printInformation(); 
        play();
     
    }

    public void play() {
        while(true) {
            Command command= parser.getCommand();
            try {
                cls_var.main(); 
            }catch(Exception e) {
                System.out.println(e); 
            }
            processCommand(command);
        }
    }

    public void processCommand (Command command) {
        String commandWord = command.getCommandWord().toLowerCase();

        switch(commandWord) {
            case "speak" :
                System.out.println (" you wanted me to speak this word, " + command.getSecondWord());
                break;
            case "go":
                goRoom(command);
                break;
            case "grab":
                grab (command);
                break;
            case "drop":
                drop (command);
                break;
            case "inspect":
                inspect (command);
            case "help" :
            	help (command);
            case "buy" :
            	buy (command);
        }
    }

    public void grab (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println ("grab what?");
            return;
        }
        String item = command.getSecondWord();
        Item toGrab = currentRoom.removeItem (item);
        if (toGrab == null) {
            System.out.println ("you cant grab that");
            return;
        }
        else {
            player.setItem(item, toGrab);
        }
        printInformation();
    }

    public void drop (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println ("drop what?");
            return;
        }
        String item = command.getSecondWord();
        Item toDrop = player.removeItem (item);
        if (toDrop == null) {
            System.out.println ("you cant drop that");
            return;
        }
        else {
            currentRoom.setItem(item, toDrop);
        }
        printInformation();
    }
    public void buy (Command command) {
    	if (!command.hasSecondWord()) {
    		System.out.println ("buy what?");
    	}
    	String toBuy= command.getSecondWord();
    	if (!toBuy.equals("pfFlyers")){
    		System.out.println("you can't buy that!");
    	}
    	if (toBuy.equals("pfFlyers")) {
    	player.setItem("pfFlyers", pfFlyers);
    	player.removeItem ("money");
    	printInformation();
    	}

    }

    public void goRoom (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println ("go where?");
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit (direction);
        if (nextRoom == null) {
            System.out.println ("you cant go there");
            return;
        }
        else {
            if(!nextRoom.getLocked()) {
                currentRoom= nextRoom;
            }
            else {
        	   if (currentRoom.equals(sandlot) && direction.equals("east") && player.getItem("pfFlyers")!=null) {
        		   currentRoom = beastYard; 
        		   
        	   }
        	   else {
        		   System.out.println ("you can't go there");
        	   }
           }
            

        
        printInformation();
        }
    }

    public void inspect (Command command) {
        String printString = "looking at ";
        if (!command.hasSecondWord()) {
            System.out.println ("inspect what?");
            return;
        }
        String lookingAt= null;
        if (!command.hasLine()) {
            lookingAt = command.getSecondWord();
        }
        else if (command.hasLine()) {
            lookingAt = command.getSecondWord()+command.getLine();
        }

        if (lookingAt.equals(currentRoom.getName())) {
            printString += "the room: " + currentRoom.getName() + "\n" + currentRoom.getLongDescription();
        }
        else if (currentRoom.getItem (lookingAt) != null) {
            printString += "the item: " + currentRoom.getItem(lookingAt).getName() + "\n" + currentRoom.getItem(lookingAt).getDescription();
        }
        else if (player.getItem (lookingAt) != null) {
            printString += "the item: " + player.getItem(lookingAt).getName() + "\n" + player.getItem(lookingAt).getDescription();
        }
        else {
            printString += "you can't look at that!";
        }
        System.out.println (printString);
    }
    public void help (Command command) {
    	 if (!command.hasSecondWord()) {
    		 System.out.println ("Command words: go, grab, drop, inspect, buy, and help.  Use inspect to get a more detailed description and potential hints.  The goal is to grab Babe Ruth’s baseball without getting caught by The Beast.");
    	 }
    	 else {
    		 String whatToHelp = command.getSecondWord();
    		 if (whatToHelp==null) {
    			 System.out.println("you can't get help with this"); 
    		 }
    		 else {
  
    		 }
    	 }
    }
}
