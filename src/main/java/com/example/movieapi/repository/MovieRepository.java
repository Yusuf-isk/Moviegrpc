package com.example.movieapi.repository;

import com.example.movieapi.constants.MovieSqlConstants;
import com.example.movieapi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;
    private static Logger logger = LoggerFactory.getLogger(MovieRepository.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MovieRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Movie> getAll() {
        List<Movie> movies;
        movies = jdbcTemplate.query(MovieSqlConstants.getGetMovies(), (rs, rowNum) -> {
            return new Movie(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9)
                    , rs.getString(10), rs.getInt(11), rs.getLong(12), rs.getBoolean(13));
        });
        return movies;
    }

    public MovieDetail getById(long movie_id) {
        MovieDetail moviedetail = new MovieDetail() ;
        Movie movie;
        Map<String,List<Genres>> genres =  new HashMap<>();
        Map<String,List<ProductionCompanies>>productionCompanies = new HashMap<>();
        Map<String,List<ProductionCountries>> productionCountries = new HashMap<>();
        RowMapper<Movie> rowMapper = (rs, rowNum) -> {

            return new Movie(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9)
                    , rs.getString(10), rs.getInt(11), rs.getLong(12), rs.getBoolean(13));
        };

        RowMapper<Genres> genresRowMapper = (rs, rowNum) -> {
            return new Genres(rs.getLong(1), rs.getString(2), rs.getLong(3));
        };
        RowMapper<ProductionCompanies> productionCompaniesRowMapper = (rs, rowNum) -> {
            return new ProductionCompanies(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4));
        };
        RowMapper<ProductionCountries> productionCountriesRowMapper = (rs, rowNum) -> {
            return new ProductionCountries(rs.getLong(1), rs.getString(2), rs.getLong(3));
        };
        HashMap<String, Object> valuemap = new HashMap<>();
        valuemap.put("id", movie_id);
        HashMap<String, Object> valuemap1 = new HashMap<>();
        valuemap1.put("movie_id",movie_id);
            movie = namedParameterJdbcTemplate.queryForObject(MovieSqlConstants.getGetMovieById(), valuemap, rowMapper);
            moviedetail.setMovie(movie);

            genres.put("genres2",namedParameterJdbcTemplate.query(MovieSqlConstants.getGetGenresById(),valuemap1,genresRowMapper));
            moviedetail.setGenres(genres);

            productionCompanies.put("production_companies", namedParameterJdbcTemplate.query(MovieSqlConstants.getGetProCompanies(),valuemap1,productionCompaniesRowMapper));
            moviedetail.setProductionCompanies(productionCompanies);

            productionCountries.put("production_countries",namedParameterJdbcTemplate.query(MovieSqlConstants.getGetProCountries(),valuemap1,productionCountriesRowMapper));
            moviedetail.setProductionCountries(productionCountries);


        return moviedetail;
    }

    public void updateFavorite(Movie movie) {
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",movie.getId());
        paramMap.put("favorite",movie.isFavorite());
         namedParameterJdbcTemplate.update(MovieSqlConstants.getUpdateFavoriteMovie(),paramMap);

    }
}
