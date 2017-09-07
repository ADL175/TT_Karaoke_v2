package com.teamtreehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;

public class KaraokeMachine {
    private SongBook mSongBook;
    private BufferedReader mReader;
    private Queue<Song> mSongQueue;

    private Map<String, String> mMenu;

    public KaraokeMachine(SongBook songBook){
        mSongBook = songBook;
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mSongQueue = new ArrayDeque<Song>();
        mMenu = new HashMap<String, String>();
        mMenu.put("Add", "Add a new song to the songbook");
        mMenu.put("Play", "Play a song from queue");
        mMenu.put("choose", "Choose a song to sing!");
        mMenu.put("Quit", "Give up, exit the program");
    }

    private String promptAction() throws IOException {
        System.out.printf("There are %d songs available and %d in the queue. Your options are: %n",
                mSongBook.getSongCount(),
                mSongQueue.size()
        );
        for (Map.Entry<String, String> option : mMenu.entrySet()){
            System.out.printf("%s - %s %n",
                    option.getKey(),
                    option.getValue()
            );
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
                        mSongQueue.add(artistSong);
                        System.out.printf("You chose: %s %n",
                                artistSong);
                        break;
                    case "play":
                        playNext();
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
        System.out.printf("Available songs for %s %n",
                artist);
        int index = promptForIndex(songTitles);
        return songs.get(index);
    }

    private int promptForIndex(List<String> options) throws IOException {
        int counter = 1;
        for (String option : options){
            System.out.printf("%d.) %s %n", counter, option);
            counter++;
        }
        System.out.println("Your Choice:   ");
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        return choice -1;
    }

    public void playNext() {
        Song song = mSongQueue.poll();
        if (song == null){
            System.out.println("Sorry, there are no songs in queue. " +
                    "Use choose from menu to add to your queue");
        }
        else {
            System.out.printf("%n%n%n Open %s to hear %s by %ads %n%n%n",
                    song.getVideoUrl(),
                    song.getTitle(),
                    song.getArtist()
            );
        }
    }

}
