package com.battlesim.benajminw5409.battleshipsimulator2016;

/**
 * Created by Ben on 5/31/2016.
 */
public class User {

    String first_name, last_name, avatar_name, email, avatar_image;
    int id, level, coins, battles_won, battles_lost, battles_tied, experience_points;
    Boolean available, online, gaming;

    public User(){

    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar_name() {
        return avatar_name;
    }

    public void setAvatar_name(String avatar_name) {
        this.avatar_name = avatar_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar_image() {
        return avatar_image;
    }

    public void setAvatar_image(String avatar_image) {
        this.avatar_image = avatar_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getBattles_won() {
        return battles_won;
    }

    public void setBattles_won(int battles_won) {
        this.battles_won = battles_won;
    }

    public int getBattles_lost() {
        return battles_lost;
    }

    public void setBattles_lost(int battles_lost) {
        this.battles_lost = battles_lost;
    }

    public int getBattles_tied() {
        return battles_tied;
    }

    public void setBattles_tied(int battles_tied) {
        this.battles_tied = battles_tied;
    }

    public int getExperience_points() {
        return experience_points;
    }

    public void setExperience_points(int experience_points) {
        this.experience_points = experience_points;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getGaming() {
        return gaming;
    }

    public void setGaming(Boolean gaming) {
        this.gaming = gaming;
    }

    @Override
    public String toString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", avatar_name='" + avatar_name + '\'' +
                ", email='" + email + '\'' +
                ", avatar_image='" + avatar_image + '\'' +
                ", id=" + id +
                ", level=" + level +
                ", coins=" + coins +
                ", battles_won=" + battles_won +
                ", battles_lost=" + battles_lost +
                ", battles_tied=" + battles_tied +
                ", experience_points=" + experience_points +
                ", available=" + available +
                ", online=" + online +
                ", gaming=" + gaming +
                '}';
    }
}
