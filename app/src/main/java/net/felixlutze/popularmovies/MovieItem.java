package net.felixlutze.popularmovies;

public class MovieItem {
    private String imageResource;
    private String name;
    private String rating;

    public MovieItem(String imageResource, String name, String rating) {
        this.imageResource = imageResource;
        this.name = name;
        this.rating = rating;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }
}
