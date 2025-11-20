import ui.MainMenu;
import util.Database;

public class Main{
    public static void main(String[] args){
        Database.initiallize();

        MainMenu menu = new MainMenu();

        menu.mainMenu();
    }
}