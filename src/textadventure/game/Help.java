package textadventure.game;

public class Help {
	private String commandWord;
    private String answer;
    public Help (String commandWord, String answer) {
        this.commandWord = commandWord;
        this.answer = answer;
    }
    public String getCommandWord () {
        return commandWord;
    }
    public String getAnswer() {
        return answer;
    }
}
