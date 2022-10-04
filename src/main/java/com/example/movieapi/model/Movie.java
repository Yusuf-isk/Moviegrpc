package com.example.movieapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private long id;
    private String status;
    private double vote_average;
    private String tagline;
    private String original_language;
    private String original_title;
    private String overview;
    private int popularity;
    private String poster_path;
    private String release_date;
    private int revenue;
    private long budget;
    private boolean favorite;

}
