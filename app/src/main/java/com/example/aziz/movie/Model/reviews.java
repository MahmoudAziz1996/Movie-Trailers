package com.example.aziz.movie.Model;

import java.util.List;

public class reviews
{
    private List<Review> results = null;

    public reviews(List<Review> results) {
        this.results = results;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public static class Review {
        private String id;
        private String author;
        private String content;
        private String url;

        public Review() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            String UR="https://www.youtube.com/watch?v=";
            return UR.concat(url);
        }

        public void setUrl(String url) {
            this.url = url;
        }


    }

}
