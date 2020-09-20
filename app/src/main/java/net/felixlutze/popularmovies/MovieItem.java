package net.felixlutze.popularmovies;

public class MovieItem {
    private String imageResource;
    private String name;
    private String released;
    private String rating;
    private String backdrop;
    private String overview;

    public MovieItem(String imageResource, String name, String rating, String released, String backdrop, String overview) {
        this.imageResource = imageResource;
        this.name = name;
        this.released = released;
        this.rating = rating;
        this.backdrop = backdrop;
        this.overview = overview;
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

    public String getBackdrop() {
        return backdrop;
    }

    public String getOverview() {
        return overview;
    }
}
