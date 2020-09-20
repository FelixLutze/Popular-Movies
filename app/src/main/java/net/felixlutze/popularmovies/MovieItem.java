package net.felixlutze.popularmovies;

public class MovieItem {
    private String imageResource;
    private String name;
    private String released;
    private String rating;

    public MovieItem(String imageResource, String name, String rating, String released) {
        this.imageResource = imageResource;
        this.name = name;
        this.released = released;
        this.rating = rating;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getReleased() {
        return released;
    }

    public String getRating() {
        return rating;
    }
}
