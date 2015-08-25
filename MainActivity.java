package com.example.adri4n.databaseex1;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;

import com.example.adri4n.databaseex1.database.StudentsTable;
import com.example.adri4n.databaseex1.model.Student;
import com.example.adri4n.databaseex1.provider.StudentsContentProvider;


public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;
    private static final int DELETE_ID = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillData();
        int a = 10;
        registerForContextMenu(getListView());

    }

    private void fillData() {

        String[] from = new String[] {StudentsTable.COLUMN_NAME };

        int[] to = new int[] {R.id.label };

        getLoaderManager().initLoader(0, null, this);

        adapter = new SimpleCursorAdapter(this, R.layout.student_row, null,from, to, 0);

        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.insert:
                addStudent();
                return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addStudent() {

        Intent i = new Intent(this, AddStudentActivity.class);
        startActivity(i);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Construct the new query in the form of a Cursor Loader. Use the id
        // parameter to construct and return different loaders.
        String[] projection = {StudentsTable.COLUMN_ID, StudentsTable.COLUMN_NAME};

        String where = null;
        String[] whereArgs = null;
        String sortOrder = null;

        //Query URI
        Uri queryUri = StudentsContentProvider.CONTENT_URI;

        // Create the new Cursor loader.
        return new CursorLoader(this, queryUri, projection, where, whereArgs, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Uri uri = Uri.parse(StudentsContentProvider.CONTENT_URI + "/" + info.id);
                getContentResolver().delete(uri, null, null);
                fillData();
                return true;
        }


        return super.onContextItemSelected(item);
    }
}
