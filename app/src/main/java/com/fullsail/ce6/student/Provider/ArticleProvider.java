package com.fullsail.ce6.student.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fullsail.ce6.student.Articles;
import com.fullsail.ce6.student.Database.DatabaseHelper;


public class ArticleProvider extends ContentProvider {
    DatabaseHelper mDatabaseHelper;
    UriMatcher mMatcher;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(Articles.   URI_AUTHORITY, Articles.DATA_TABLE, 10);
        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int uriType = mMatcher.match(uri);
        if(uriType == 10) {

            return mDatabaseHelper.getReadableDatabase()
                    .query(Articles.DATA_TABLE, projection,
                            selection, selectionArgs, null, null, sortOrder);
        }

        throw new IllegalArgumentException("You must target the DataContract.DATA_TABLE data source.");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int uriType = mMatcher.match(uri);
        if(uriType == 10) {
            return "vnd.android.cursor.dir/vnd." +
                    Articles.URI_AUTHORITY + "." + Articles.DATA_TABLE;
        }
        throw new IllegalArgumentException("This provider does not support single row targeting");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int uriType = mMatcher.match(uri);
        if(uriType == 10) {

            long id = mDatabaseHelper.getWritableDatabase()
                    .insert(Articles.DATA_TABLE, null, values);

            return Uri.parse(Articles.CONTENT_URI_STRING + "/" + id);
        }

        throw new IllegalArgumentException("You must target the DataContract.DATA_TABLE data source.");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = mMatcher.match(uri);
        if(uriType == 10) {

            return mDatabaseHelper.getWritableDatabase()
                    .delete(Articles.DATA_TABLE, selection, selectionArgs);
        }

        throw new IllegalArgumentException("You must target the DataContract.DATA_TABLE data source.");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = mMatcher.match(uri);
        if(uriType == 10) {

            return mDatabaseHelper.getWritableDatabase()
                    .update(Articles.DATA_TABLE, values, selection, selectionArgs);
        }

        throw new IllegalArgumentException("You must target the DataContract.DATA_TABLE data source.");
    }
}
