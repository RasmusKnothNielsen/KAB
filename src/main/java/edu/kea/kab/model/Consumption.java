package edu.kea.kab.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Consumption implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    double musicHours;

    double videoHours;

    double mobileHours;

    int week;

    int year;

    String session;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMusicHours() {
        return musicHours;
    }

    public void setMusicHours(double musicHours) {
        this.musicHours = musicHours;
    }

    public double getVideoHours() {
        return videoHours;
    }

    public void setVideoHours(double videoHours) {
        this.videoHours = videoHours;
    }

    public double getMobileHours() {
        return mobileHours;
    }

    public void setMobileHours(double mobileHours) {
        this.mobileHours = mobileHours;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
