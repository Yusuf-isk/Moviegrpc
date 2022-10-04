package com.example.movieapi.constants;

import lombok.Getter;

@Getter
public class MovieSqlConstants {
    private static final String GET_MOVIES = "SELECT * FROM \"movie\".\"movie\"";
    private static final String GET_MOVIE_BY_ID = "SELECT * FROM \"movie\".\"movie\" WHERE \"id\" = :id;";
    private static final String GET_GENRES_BY_ID = "SELECT * FROM \"movie\".\"genres\" WHERE \"movie_id\" = :movie_id;";
    private static final String GET_PRO_COMPANIES = "SELECT * FROM \"movie\".\"productioncompanies\" WHERE \"movie_id\" = :movie_id;";
    private static final String GET_PRO_COUNTRIES = "SELECT * FROM \"movie\".\"productioncountries\" WHERE \"movie_id\" = :movie_id;";
    private static final String UPDATE_FAVORITE_MOVIE="UPDATE \"movie\".\"movie\" SET \"favorite\" = :favorite WHERE \"id\" = :id;";
    public static String getGetMovies(){
        return GET_MOVIES;
    }

    public static String getGetMovieById() {
        return GET_MOVIE_BY_ID;
    }

    public static String getGetGenresById() {
        return GET_GENRES_BY_ID;
    }

    public static String getGetProCompanies() {
        return GET_PRO_COMPANIES;
    }

    public static String getGetProCountries() {
        return GET_PRO_COUNTRIES;
    }

    public static String getUpdateFavoriteMovie() {
        return UPDATE_FAVORITE_MOVIE;
    }
}
