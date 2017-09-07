package com.teamtreehouse.model;


import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import javax.lang.model.type.ArrayType;
import java.util.*;

public class SongBook {
    private List<Song> mSongs;

    public SongBook(){
        mSongs = new ArrayList<>();
    }

    public void addSong(Song song) {
        mSongs.add(song);
    }

    public int getSongCount(){
        return mSongs.size();
    }


    // FIXME: 9/6/17 this should be cached
    private Map<String, List<Song>> byArtist() {
        Map<String, List<Song>> byArtist = new HashMap<>();
        for (Song song : mSongs){
            List<Song> artistSongs = byArtist.get(song.getArtist());
            if (artistSongs == null){
                artistSongs = new ArrayList<>();
                byArtist.put(song.getArtist(), artistSongs);
            }
            artistSongs.add(song);
        }
        return byArtist;
    }
    
    public Set<String> getArtist() {
        return byArtist().keySet();
    }

    public List<Song> getSongsForArtist(String artistName) {
        return byArtist().get(artistName);
    }
}
