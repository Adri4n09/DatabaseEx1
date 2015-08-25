package com.example.adri4n.databaseex1;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.adri4n.databaseex1.database.StudentsTable;
import com.example.adri4n.databaseex1.model.Student;
import com.example.adri4n.databaseex1.provider.StudentsContentProvider;


public class AddStudentActivity extends ActionBarActivity {


    private EditText etName;
    private EditText etEmail;
    private EditText etAddress;
    private EditText etGrade;

    private Uri studentUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);


        etName = (EditText) findViewById(R.id.et_Name);
        etEmail = (EditText) findViewById(R.id.et_Email);
        etAddress = (EditText) findViewById(R.id.et_Address);
        etGrade = (EditText) findViewById(R.id.et_Grade);

        studentUri = null;
    }

    public void onClickAdd(View view) {
        setResult(RESULT_OK);
        addStudent();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void addStudent() {

        String name = etName.getText().toString();
        String email = etName.getText().toString();
        String address = etAddress.getText().toString();
        String grade = etGrade.getText().toString();

        if (name.length() == 0 && email.length() == 0 && address.length() == 0 && grade.length() == 0) {
            return;
        }

        ContentValues values = new ContentValues();

        values.put(StudentsTable.COLUMN_NAME, name);
        values.put(StudentsTable.COLUMN_EMAIL, email);
        values.put(StudentsTable.COLUMN_ADDRESS, address);
        values.put(StudentsTable.COLUMN_GRADE, grade);

        if (studentUri == null) {
            studentUri = getContentResolver().insert(StudentsContentProvider.CONTENT_URI, values);
        } else {
            getContentResolver().update(studentUri, values, null, null);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
