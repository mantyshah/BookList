package com.mediaplayer.manthanshah.booklist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> book){
        super(context, 0, book);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        Book book = getItem(position);

        if(listItemView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(R.layout.book_list, parent, false);
        }

        TextView titleView =  listItemView.findViewById(R.id.book_title);
        TextView descrpView = listItemView.findViewById(R.id.book_description);
        TextView authorView = listItemView.findViewById(R.id.author_name);

        titleView.setText(book.getmTitle());
        descrpView.setText(checkForEmptyDescription(book.getmDescription()));
        authorView.setText(book.getmAuthor());

        return listItemView;
    }

    // Helper method to check for empty description
    private String checkForEmptyDescription(String str){
        if(str == null || str.isEmpty()){
            return "Description not available.";
        }
        return str;
    }
}
