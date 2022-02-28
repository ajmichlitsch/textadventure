package textadventure.game;

public class Command {
    private String commandWord;
    private String secondWord;
    private String line;
    public Command(String commandWord, String secondWord, String line) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.line= line;
    }
    public String getCommandWord() {
        return commandWord;
    }
    public String getSecondWord() {
        return secondWord;
    }
        public String getLine() {
        return line;
    }
    public boolean hasSecondWord() {
        return ( secondWord != null) ;
    }
    public boolean hasLine() {
        return ( line != null) ;
    }
}