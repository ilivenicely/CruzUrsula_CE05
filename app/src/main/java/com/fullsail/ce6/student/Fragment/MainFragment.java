package com.fullsail.ce6.student.Fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fullsail.ce6.student.core.CustomDetailDialog;
import com.fullsail.ce6.student.Adapter.CustomListAdapter;
import com.fullsail.ce6.student.Model.Book;
import com.fullsail.ce6.student.R;

import java.util.ArrayList;


public class MainFragment extends Fragment {


    private ArrayList<Book> bookArrayList;
    private ListView listView;
    private CustomListAdapter customListAdapter;

    public static MainFragment newInstance() {
        MainFragment frag = new MainFragment();
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Cursor mCursor =  getActivity().getContentResolver().query(
                Uri.parse("content://com.fullsail.ce6.provider/books"),   // The content URI of the words table
                null,                        // The columns to return for each row
                null,                    // Selection criteria
                null,                     // Selection criteria
                null);                        // The sort order for the returned rows


        bookArrayList = new ArrayList<>();
        if (mCursor!= null && mCursor.moveToFirst())
        {
            Log.d("mCursor",mCursor.getCount()+"");
            //mCursor.moveToFirst();
            do {
                Book book = new Book();
                book.setId(mCursor.getInt(mCursor.getColumnIndex("_id")));
                book.setTitle(mCursor.getString(mCursor.getColumnIndex("title")));
                book.setThumbnail(mCursor.getString(mCursor.getColumnIndex("thumbnail")));
                book.setDescription(mCursor.getString(mCursor.getColumnIndex("Description")));
                book.setBook_id(mCursor.getString(mCursor.getColumnIndex("book_id")));
                Log.d("id",""+mCursor.getInt(mCursor.getColumnIndex("_id")));
                Log.d("title",""+mCursor.getString(mCursor.getColumnIndex("title")));
                Log.d("thumbnail",""+mCursor.getString(mCursor.getColumnIndex("thumbnail")));
                Log.d("Description",""+mCursor.getString(mCursor.getColumnIndex("Description")));
                Log.d("book_id",""+mCursor.getString(mCursor.getColumnIndex("book_id")));
                Log.d("Tag","========================");
                bookArrayList.add(book);
            } while (mCursor.moveToNext());
        } else {
            Log.d("Cursor","No data");
        }
        customListAdapter = new CustomListAdapter(getActivity(),bookArrayList);
        listView.setAdapter(customListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomDetailDialog customDetailDialog = new CustomDetailDialog(getActivity());
                customDetailDialog.showDalog(bookArrayList.get(position).getTitle(),bookArrayList.get(position).getDescription());
            }
        });

    }
}