/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.kie.uslugisieciowe.sentanalysisservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author jacek
 */
public class Movie {

    private String title;
    private String director;
    // tu zapisujemy ile recenzji pozytywnych i negatywnych wykryto dla tego filmy
    private int positiveSentimentCoutner;
    private int negativeSentimentCounter;
    // identyfikator filmu. usuwamy spacje i wszystko do malych liter
    // dla Fight Club id to fightclub
    private String id;

    public Movie() {
        // ustalamy id 
        this.id = String.join("", title.split(" ")).toLowerCase();
    }

    public Movie(String title, String director) {
        // ustalamy id
        this.id = String.join("", title.split(" ")).toLowerCase();
        this.title = title;
        this.director = director;
        this.positiveSentimentCoutner = 0;
        this.negativeSentimentCounter = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getPositiveSentimentCoutner() {
        return positiveSentimentCoutner;
    }

    public void setPositiveSentimentCoutner(int positiveSentimentCoutner) {
        this.positiveSentimentCoutner = positiveSentimentCoutner;
    }

    public int getNegativeSentimentCounter() {
        return negativeSentimentCounter;
    }

    public void setNegativeSentimentCounter(int negativeSentimentCounter) {
        this.negativeSentimentCounter = negativeSentimentCounter;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }
    
    public void recordPositiveSentimentReview()
    {
        this.positiveSentimentCoutner++;
    }
    
    public void recordNegativeSentimentReview()
    {
        this.negativeSentimentCounter++;
    }
        
}
