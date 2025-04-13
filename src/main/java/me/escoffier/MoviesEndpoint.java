package me.escoffier;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/movies")
public class MoviesEndpoint {

    @GET
    public List<Movie> getAll() {
        return Movie.listAll();
    }

    @GET
    @Path("/{id}")
    public Movie getOne(Long id) {
        Movie movie = Movie.findById(id);
        if (movie == null) {
            throw new NotFoundException("Movie not found");
        }
        return movie;
    }

    @POST
    @Transactional
    public Movie create(Movie movie) {
        movie.persist();
        return movie;
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public Movie update(Long id, Movie movie) {
        Movie existing = Movie.findById(id);
        if (existing == null) {
            throw new NotFoundException("Movie not found");
        }
        existing.title = movie.title;
        existing.rating = movie.rating;
        return existing;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(Long id) {
        Movie movie = Movie.findById(id);
        if (movie == null) {
            throw new NotFoundException("Movie not found");
        }
        movie.delete();
    }

    @DELETE
    @Transactional
    public void deleteAll() {
        Movie.deleteAll();
    }


}
