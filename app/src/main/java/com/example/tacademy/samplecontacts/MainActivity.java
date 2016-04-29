package com.example.tacademy.samplecontacts;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    EditText inputView;
    ListView listView;
    SimpleCursorAdapter mAdapter;

    String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
    String selection = "(("+ContactsContract.Contacts.DISPLAY_NAME + " NOT NULL) AND (" +
            ContactsContract.Contacts.DISPLAY_NAME + " != ''))";
    String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputView=(EditText)findViewById(R.id.editText);
        listView=(ListView)findViewById(R.id.listView);
        String[] from={ContactsContract.Contacts.DISPLAY_NAME};
        int[] to={android.R.id.text1};
        mAdapter=new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateContacts(null);
    }
    private void updateContacts(String keyword){
        Uri uri=ContactsContract.Contacts.CONTENT_URI;
        if (!TextUtils.isEmpty(keyword)) {
            uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
        }
        Cursor c=getContentResolver().query(uri,
                projection,
                selection,
                null,
                sortOrder);
        mAdapter.changeCursor(c);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.changeCursor(null);
    }
}
