package textadventure.game;

import java.util.Scanner;
public class Parser {
    private Scanner kb;
    public Parser() {
        kb= new Scanner(System.in);
    }
    public Command getCommand() {
        System.out.println (">");
        
        String inputLine;
        String wordOne = null;
        String wordTwo = null;
        String line = null;
        
        inputLine= kb.nextLine();
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            wordOne= tokenizer.next();
            if (tokenizer.hasNext()) {
                wordTwo=tokenizer.next();
            }
            if (tokenizer.hasNext()) {
                line = tokenizer.nextLine();
            }
        }
        return new Command(wordOne, wordTwo, line);
    }
}