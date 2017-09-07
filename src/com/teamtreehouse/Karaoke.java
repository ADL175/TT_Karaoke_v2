package com.teamtreehouse;

import com.teamtreehouse.model.SongBook;

public class Karaoke {
    public static void main(String[] args){
        SongBook songBook = new SongBook();
        KaraokeMachine machine = new KaraokeMachine(songBook);
        machine.run();
    }
}
