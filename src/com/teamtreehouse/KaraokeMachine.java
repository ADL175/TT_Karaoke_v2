package com.teamtreehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;

public class KaraokeMachine {
    private SongBook mSongBook;
    private BufferedReader mReader;
    private HashMap<String, String> mMenu;

    public KaraokeMachine(SongBook songBook){
        mSongBook = songBook;
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new HashMap<String, String>();
        mMenu.put("Add", "Add a new song to the songbook");
        mMenu.put("Quit", "Give up, exit the program");
    }

    private String promptAction() throws IOException {
        System.out.printf("There are %d songs available. Your options are: %n",
                mSongBook.getSongCount());
        for (Map.Entry<String, String> option : mMenu.entrySet()){
            System.out.printf("%s - %s %n",
                    option.getKey(),
                    option.getValue());
        }
        System.out.println("What do you want to do: ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }

    public void run(){
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice){
                    case "add":
                        Song song = promptNewSong();
                        mSongBook.addSong(song);
                        System.out.printf("%s added %n%n",
                                song);
                        break;
                    case "quit":
                        System.out.println("Thanks for playing!");
                        break;
                    default:
                        System.out.printf("Unknown choice: '%s'. Try again. %n%n%n",
                                choice);
                }
            } catch (IOException ioe){
                System.out.println("Problem w/ your input");
                ioe.printStackTrace();
            }
        } while(!choice.equals("quit"));
    }

    private Song promptNewSong() throws IOException {
        System.out.println("Enter artist's name: ");
        String artist = mReader.readLine();
        System.out.println("Enter the title: ");
        String title = mReader.readLine();
        System.out.println("Enter the video URL: ");
        String videoURL = mReader.readLine();
        return new Song(artist, title, videoURL);
    }

}
