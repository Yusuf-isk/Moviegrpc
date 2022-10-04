package com.example.movieapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetail {
    Movie movie;
    Map<String,List<Genres>> genres;
    Map<String,List<ProductionCountries>> productionCountries;
    Map<String,List<ProductionCompanies>> productionCompanies;

}
