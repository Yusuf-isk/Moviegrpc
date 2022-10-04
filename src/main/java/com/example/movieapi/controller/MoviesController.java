package com.example.movieapi.controller;

import com.example.movieapi.model.Movie;
import com.example.movieapi.model.MovieDetail;
import com.example.movieapi.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoviesController {
    private final MovieService movieService;

    public MoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies= movieService.getMovies();
        return ResponseEntity.ok(movies);
    }
    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDetail> moviesDetail(@PathVariable(name = "id") long id) {
        MovieDetail movieDetail = movieService.getMovieById(id);
        return ResponseEntity.ok(movieDetail);

    }

    @PostMapping("/movies")
    public ResponseEntity<String> updateFavorite(@RequestBody Movie movie) {
        return movieService.updateFavorite(movie);

    }
}
