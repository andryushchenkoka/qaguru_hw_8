package pojo;

import java.util.ArrayList;

public class Film {
    public String name;
    public Integer season;
    public Integer series;
    public String year;
    public ArrayList<String> genres;
    public String country;
    public ArrayList<Crew> crew;
    public ArrayList<Actor> actors;
    public String premierDate;

    public String getName() {
        return name;
    }

    public Integer getSeason() {
        return season;
    }

    public Integer getSeries() {
        return series;
    }

    public String getYear() {
        return year;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public String getPremierDate() {
        return premierDate;
    }
}
