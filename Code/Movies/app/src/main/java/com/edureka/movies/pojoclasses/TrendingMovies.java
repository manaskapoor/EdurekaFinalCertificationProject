package com.edureka.movies.pojoclasses;

import android.os.Parcel;
import android.os.Parcelable;

public class TrendingMovies implements Parcelable {

    private String title;
    private String tagline;
    private String overview;
    private String released;
    private String trailer;
    private String homepage;
    private String certificate;
    private String thumbImage;
    private String genre;
    private String fullPoster;
    private String bannerImage;
    private int year,runtime;
    private float rating;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public String getFullPoster() {
        return fullPoster;
    }

    public void setFullPoster(String fullPoster) {
        this.fullPoster = fullPoster;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public TrendingMovies(Parcel p){
        this.title=p.readString();
        this.tagline=p.readString();
        this.overview=p.readString();
        this.year=p.readInt();
        this.thumbImage=p.readString();
        this.bannerImage=p.readString();
        this.fullPoster=p.readString();
        this.released=p.readString();
        this.runtime=p.readInt();
        this.rating=p.readFloat();
        this.certificate=p.readString();
        this.genre=p.readString();
        this.homepage=p.readString();
        this.trailer=p.readString();

    }
    public TrendingMovies(){

    }
    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }


    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(tagline);
        dest.writeString(overview);
        dest.writeInt(year);
        dest.writeString(thumbImage);
        dest.writeString(bannerImage);
        dest.writeString(fullPoster);
        dest.writeString(released);
        dest.writeInt(runtime);
        dest.writeFloat(rating);
        dest.writeString(certificate);
        dest.writeString(genre);
        dest.writeString(homepage);
        dest.writeString(trailer);
    }


    public static final Parcelable.Creator<TrendingMovies> CREATOR = new Parcelable.Creator<TrendingMovies>() {

        @Override
        public TrendingMovies createFromParcel(Parcel parcel) {
            return new TrendingMovies(parcel);
        }

        @Override
        public TrendingMovies[] newArray(int size) {
            return new TrendingMovies[size];
        }
    };

}
