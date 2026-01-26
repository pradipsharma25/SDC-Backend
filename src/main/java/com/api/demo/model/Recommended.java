package com.api.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Recommended {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int count;

    // Image stored as BLOB
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] image;

    // Reference to existing Music entities
    @ManyToMany
    @JoinTable(
        name = "recommended_music",
        joinColumns = @JoinColumn(name = "recommended_id"),
        inverseJoinColumns = @JoinColumn(name = "music_id")
    )
    private List<Music> musicList;

    // ---------- getters & setters ----------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public List<Music> getMusicList() { return musicList; }
    public void setMusicList(List<Music> musicList) { this.musicList = musicList; }
}
