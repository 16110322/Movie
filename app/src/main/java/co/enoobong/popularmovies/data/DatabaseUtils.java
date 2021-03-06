package co.enoobong.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DatabaseUtils {

    public static ContentValues getMovieDetails(Context context, MovieKt movie) {
        Glide.with(context).load(movie.getPosterUrl()).downloadOnly(500, 500);
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoritesContract.MoviesEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(FavoritesContract.MoviesEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(FavoritesContract.MoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(FavoritesContract.MoviesEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(FavoritesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(FavoritesContract.MoviesEntry.COLUMN_ID, movie.getMovieId());
        return contentValues;
    }

    public static ContentValues getTrailerDetails(MovieKt movie, ArrayList<TrailerKt> trailerList) {
        ContentValues contentValues = new ContentValues();
        for (TrailerKt trailer : trailerList) {
            contentValues.put(FavoritesContract.TrailerEntry.COLUMN_NAME, trailer.getName());
            contentValues.put(FavoritesContract.TrailerEntry.COLUMN_VIDEO_URL, trailer.getVideoUrl());
        }
        contentValues.put(FavoritesContract.MoviesEntry.COLUMN_ID, movie.getMovieId());
        return contentValues;
    }

    public static ContentValues getReviewDetails(MovieKt movie, ArrayList<ReviewKt> reviewsList) {
        ContentValues contentValues = new ContentValues();
        for (ReviewKt review : reviewsList) {
            contentValues.put(FavoritesContract.ReviewsEntry.COLUMN_AUTHOR, review.getAuthor());
            contentValues.put(FavoritesContract.ReviewsEntry.COLUMN_CONTENT, review.getContent());
        }
        contentValues.put(FavoritesContract.MoviesEntry.COLUMN_ID, movie.getMovieId());
        return contentValues;
    }

    public static ArrayList<MovieKt> getFavoriteMovies(Context context) {
        ArrayList<MovieKt> moviesList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(FavoritesContract.MOVIES_CONTENT_URI,
                null,
                null,
                null,
                null);
        try {
            while (cursor != null && cursor.moveToNext()) {
                MovieKt movie = new MovieKt();
                movie.setMovieId(cursor.getInt(cursor.getColumnIndexOrThrow(FavoritesContract.MoviesEntry.COLUMN_ID)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.MoviesEntry.COLUMN_OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.MoviesEntry.COLUMN_POSTER_PATH)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.MoviesEntry.COLUMN_TITLE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.MoviesEntry.COLUMN_RELEASE_DATE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(FavoritesContract.MoviesEntry.COLUMN_VOTE_AVERAGE)));
                moviesList.add(movie);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return moviesList;
    }
}

