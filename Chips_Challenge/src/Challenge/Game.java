package Challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private ArrayList<User> users;

    private Scanner reader;
    private String userFile = "users.txt";

    public Game() {

    }

    public ArrayList<User> loadUsers() {
        ArrayList<User> temp = new ArrayList<>();

        try {

            reader = new Scanner(new File(userFile));

            while (reader.hasNext()) {

                String line = reader.nextLine();

                User tempUser = new User();
                tempUser.setUserName(line.split(",")[0]);



                users.add(tempUser);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return users;

    }
}
