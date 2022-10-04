package com.example.movieapi.service;


import com.example.movieapi.model.MovieDetail;
import com.example.movieapi.proto.*;
import com.example.movieapi.repository.MovieRepository;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;


@GrpcService
public class GrpcServiceMovie extends MovieServiceGrpc.MovieServiceImplBase {
    @Autowired
    MovieRepository movieRepository;
    @Override
    public void getAllMovies(MovieRequest request, StreamObserver<MovieResponse> responseObserver) {
        List<Movie> movieDtoList = movieRepository.getAll()
                .stream()
                .map(movie -> Movie.newBuilder()
                        .setId(movie.getId())
                        .setStatus(movie.getStatus())
                        .setOriginalLanguage(movie.getOriginal_language())
                        .setTagline(movie.getTagline())
                        .setVoteAverage(movie.getVote_average())
                        .setOriginalTitle(movie.getOriginal_title())
                        .setPopularity(movie.getPopularity())
                        .setOverview(movie.getOverview())
                        .setPosterPath(movie.getPoster_path())
                        .setReleaseDate(movie.getRelease_date())
                        .setRevenue(movie.getRevenue())
                        .setBudget(movie.getBudget())
                        .setFavorite(movie.isFavorite())

                        .build())
                .collect(Collectors.toList());
        responseObserver.onNext(MovieResponse.newBuilder().addAllMovie(movieDtoList).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getMovieById(MovieDetailRequest request, StreamObserver<MovieDetailResponse> responseObserver) {
        MovieDetail movieDetail = movieRepository.getById(request.getId());
        System.out.println(movieDetail);
        List<Genres> genresList= movieDetail.getGenres().get("genres2").stream().map(movie -> Genres.newBuilder()
                .setId(movie.getId())
                .setName(movie.getName())
                .setMovieId(movie.getMovie_id())
                .build()).collect(Collectors.toList());
        List<ProductionCompanies> productionCompaniesList = movieDetail.getProductionCompanies().get("production_companies").stream().map(productionCompanies ->
                ProductionCompanies.newBuilder()
                        .setId(productionCompanies.getId())
                        .setName(productionCompanies.getName())
                        .setLogoPath(productionCompanies.getLogo_path())
                        .setMovieId(productionCompanies.getMovie_id())
                        .build()).collect(Collectors.toList());
        List<ProductionCountries> productionCountriesList = movieDetail.getProductionCountries().get("production_countries").stream().map(productionCountries ->
                ProductionCountries.newBuilder()
                        .setId(productionCountries.getId())
                        .setName(productionCountries.getName())
                        .setMovieId(productionCountries.getMovie_id()).build()).collect(Collectors.toList());



        responseObserver.onNext(MovieDetailResponse.newBuilder().setId(movieDetail.getMovie().getId())
                .setStatus(movieDetail.getMovie().getStatus())
                .setOriginalLanguage(movieDetail.getMovie().getOriginal_language())
                .setTagline(movieDetail.getMovie().getTagline())
                .setVoteAverage(movieDetail.getMovie().getVote_average())
                .setOriginalTitle(movieDetail.getMovie().getOriginal_title())
                .setPopularity(movieDetail.getMovie().getPopularity())
                .setOverview(movieDetail.getMovie().getOverview())
                .setPosterPath(movieDetail.getMovie().getPoster_path())
                .setReleaseDate(movieDetail.getMovie().getRelease_date())
                .setRevenue(movieDetail.getMovie().getRevenue())
                .setBudget(movieDetail.getMovie().getBudget())
                .setFavorite(movieDetail.getMovie().isFavorite())
                .addAllGenres(genresList)
                .addAllProductionCompanies(productionCompaniesList)
                .addAllProductionCountries(productionCountriesList)
                .build());
        responseObserver.onCompleted();

    }

        @Override
        public void updateMovie(UpdateMovieRequest request, StreamObserver<Movie> responseObserver) {
            com.example.movieapi.model.Movie movie = new com.example.movieapi.model.Movie(request.getId(),request.getStatus(),request.getVoteAverage(),request.getTagline(),request.getOriginalLanguage(),
                    request.getOriginalTitle(),request.getOverview(),request.getPopularity(),request.getPosterPath(),request.getReleaseDate(),request.getRevenue(),request.getBudget(),request.getFavorite());
            Movie movieResponse;
            movieRepository.updateFavorite(movie);


            movieResponse = Movie.newBuilder().setId(movie.getId()).setStatus(movie.getStatus()).setVoteAverage(movie.getVote_average())
                    .setTagline(movie.getTagline()).setOriginalLanguage(movie.getOriginal_language()).setOriginalTitle(movie.getOriginal_title()).setOverview(movie.getOverview())
                    .setPopularity(movie.getPopularity()).setPosterPath(movie.getPoster_path()).setReleaseDate(movie.getRelease_date()).setRevenue(movie.getRevenue()).setBudget(movie.getBudget())
                    .setFavorite(movie.isFavorite()).build();

            responseObserver.onNext(movieResponse);
            responseObserver.onCompleted();
        }
}
