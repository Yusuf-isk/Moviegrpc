package com.example.movieapi.service;

import com.example.movieapi.model.Genres;
import com.example.movieapi.model.Movie;
import com.example.movieapi.model.MovieDetail;
import com.example.movieapi.model.ProductionCompanies;
import com.example.movieapi.model.ProductionCountries;
import com.example.movieapi.proto.*;
import com.example.movieapi.repository.MovieRepository;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @GrpcClient("Movie")
    public MovieServiceGrpc.MovieServiceBlockingStub movieStub;


    public List<Movie> getMovies() {
        MovieResponse allMovieResponse = movieStub.getAllMovies(MovieRequest.newBuilder().build());
        return allMovieResponse.getMovieList()
                .stream()
                .map(movieDto -> new Movie(movieDto.getId(),movieDto.getStatus(), movieDto.getVoteAverage(), movieDto.getTagline(),movieDto.getOriginalLanguage(),movieDto.getOriginalTitle(),
                        movieDto.getOverview(),movieDto.getPopularity(),movieDto.getPosterPath(),movieDto.getReleaseDate(),movieDto.getRevenue(),movieDto.getBudget(),movieDto.getFavorite()))
                .collect(Collectors.toList());

    }

    public MovieDetail getMovieById(long movie_id) {
        MovieDetailResponse movieDetail = movieStub.getMovieById(MovieDetailRequest.newBuilder().setId(movie_id).build());
        Movie movie = new Movie(movieDetail.getId(),movieDetail.getStatus(),movieDetail.getVoteAverage(),movieDetail.getTagline(),movieDetail.getOriginalLanguage()
                ,movieDetail.getOriginalTitle(),movieDetail.getOverview(),movieDetail.getPopularity(),movieDetail.getPosterPath(),movieDetail.getReleaseDate(),movieDetail.getRevenue(),movieDetail.getBudget(),movieDetail.getFavorite());
        List<Genres> genres = movieDetail.getGenresList().stream()
                .map(genres1 -> new Genres(genres1.getId(),genres1.getName(),genres1.getMovieId())).collect(Collectors.toList());
        List<ProductionCompanies> productionCompanies = movieDetail.getProductionCompaniesList().stream()
                .map( productionCompanies1 -> new ProductionCompanies(productionCompanies1.getId(),productionCompanies1.getName(),productionCompanies1.getLogoPath(), productionCompanies1.getMovieId())).collect(Collectors.toList());
        List<ProductionCountries> productionCountries = movieDetail.getProductionCountriesList().stream()
                .map(productionCountries1 -> new ProductionCountries(productionCountries1.getId(),productionCountries1.getName(),productionCountries1.getMovieId())).collect(Collectors.toList());
            Map<String,List<ProductionCountries>> pCountryMap = new HashMap<>();
            Map<String,List<ProductionCompanies>> pCompanyMap = new HashMap<>();
            Map<String,List<Genres>> genresMap = new HashMap<>();
            pCompanyMap.put("production_companies",productionCompanies);
            pCountryMap.put("production_countries",productionCountries);
            genresMap.put("genres2",genres);
        return new MovieDetail(movie,genresMap,pCountryMap,pCompanyMap);

    }

    public ResponseEntity<String> updateFavorite(Movie movie) {
        movieStub.updateMovie(UpdateMovieRequest.newBuilder().setId(movie.getId()).setStatus(movie.getStatus()).setVoteAverage(movie.getVote_average())
                .setTagline(movie.getTagline()).setOriginalLanguage(movie.getOriginal_language()).setOriginalTitle(movie.getOriginal_title()).setOverview(movie.getOverview())
                .setPopularity(movie.getPopularity()).setPosterPath(movie.getPoster_path()).setReleaseDate(movie.getRelease_date()).setRevenue(movie.getRevenue()).setBudget(movie.getBudget())
                .setFavorite(movie.isFavorite()).build());
        return ResponseEntity.ok().build();

    }
}
