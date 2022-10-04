package com.example.movieapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionCompanies {
    private long id;
    private String name;
    private String logo_path;
    private long movie_id;
}
