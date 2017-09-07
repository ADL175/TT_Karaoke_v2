package com.teamtreehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        mMenu.put("choose", "Choose a song to sing!");
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
                    case "choose":
                        String artist = promptArtist();
                        Song artistSong = promptSongForArtist(artist);
                        // TODO: 9/6/17 add to song queue
                        System.out.printf("You chose: %s %n", artistSong);
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

    private String promptArtist() throws IOException {
        System.out.println("Available artists: ");
        List<String > artists = new ArrayList<>(mSongBook.getArtist());
        int index = promptForIndex(artists);
        return artists.get(index);
    }

    private Song promptSongForArtist(String artist) throws IOException {
        List<Song> songs = mSongBook.getSongsForArtist(artist);
        List<String> songTitles = new ArrayList<>();
        for (Song song : songs){
            songTitles.add(song.getTitle());
        }
        int index = promptForIndex(songTitles);
        return songs.get(index);
    }

    private int promptForIndex(List<String> options) throws IOException {
        int counter = 1;
        for (String option : options){
            System.out.printf("%d.) %s %n", counter, option);
            counter++;
        }
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        System.out.println("Your Choice:   ");
        return choice -1;
    }

}
