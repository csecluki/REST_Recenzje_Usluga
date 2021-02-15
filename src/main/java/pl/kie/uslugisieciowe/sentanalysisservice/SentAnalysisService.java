/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.kie.uslugisieciowe.sentanalysisservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author jacek
 */

@Path("generic")
@Singleton
public class SentAnalysisService {

    @Context
    private UriInfo context;
    // tu trzymamy listę słów o pozytywnym wydźwięku
    private List<String> wordsPositive;
    // tu trzymamy listę słów o negatywnym wydźwięku
    private List<String> wordsNegative;
    // kolejcja przechowująca obiekty filmów
    // klucz to id filmu, wartość to konkretny obiekt
    private Map<String, Movie> moviesMap;
    
    private ObjectMapper mapper;
    
    
    /**
     * Creates a new instance of SentAnalysisService
     */
    public SentAnalysisService() {
        // lista naszych pozytywnych słów
        wordsPositive = Arrays.asList("dobry fajny super fantastyczny niezły świetny genialny dobra fajna super fantastyczna niezła świetna genialna dobre fajne fantastyczne niezłe świetne genialne".split(" "));
        // lista naszych negatywnych słów
        wordsNegative = Arrays.asList("słaby kiepski beznadziejny zły paskudny brzydki słaba kiepska beznadziejna zła paskudna brzydka słabe kiepskie beznadziejne złe paskudne brzydkie".split(" "));
        // baza przykładowych filmów
        // id: pulpfiction
        moviesMap = new HashMap<>();
        Movie m1 = new Movie("Pulp Fiction", "Quentin Tarantino");
        moviesMap.put(m1.getId(), m1);
        // id: thegodfather
        Movie m2 = new Movie("The Godfather", "Francis Ford Copolla");
        moviesMap.put(m2.getId(), m2);
        // id: fightclub
        Movie m3 = new Movie("Fight Club", "David Fincher");
        moviesMap.put(m3.getId(), m3);
        // id: it
        Movie m4 = new Movie("It", "Andy Muschietti");
        moviesMap.put(m4.getId(), m4);
    }
    
    @GET
    @Path("movie/{movieId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovie(@PathParam("movieId") String movieId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (moviesMap.containsKey(movieId)) {
        return Response.ok(mapper.writeValueAsString(moviesMap.get(movieId))).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Path("movieReview/{movieId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadReview(
            @PathParam("movieId") String movie,
            String review) {
        if (moviesMap.containsKey(movie)) {
            List<String> wordList;
            int pos = 0;
            int neg = 0;
            wordList = Arrays.asList(review.split(" "));
            for (String word: wordList) {
                if (wordsPositive.contains(word.toLowerCase()) || wordsPositive.contains(word.substring(0, word.length() - 1).toLowerCase())) {
                    pos += 1;
                } else if (wordsNegative.contains(word.toLowerCase()) || wordsNegative.contains(word.substring(0, word.length() - 1).toLowerCase())) {
                    neg += 1;
                }
            }

            if (pos > neg) {
                moviesMap.get(movie).recordPositiveSentimentReview();
                return Response.ok("POSITIVE").build();            
            } else if (neg > pos) {
                moviesMap.get(movie).recordNegativeSentimentReview();
                return Response.ok("NEGATIVE").build(); 
            } else {
                return Response.ok("NEUTRAL").build();
            }
        } else {
            return Response.status(422).build();
        }
    }
}
