package com.teamtreehouse;

import com.teamtreehouse.model.SongBook;
import com.teamtreehouse.model.Song;
import com.teamtreehouse.KaraokeMachine;

public class Karaoke {
    public static void main(String[] args){
        SongBook songBook = new SongBook();
        KaraokeMachine machine = new KaraokeMachine(songBook);
        machine.run();
    }
}
