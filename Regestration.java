package vr.si.fly46;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Regestration extends AppCompatActivity implements Regestratiion {

    Button btnAdd, btnRead, btnClear;
    EditText etName, etlastname, etfightnumber;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regestration);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener((View.OnClickListener) this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener((View.OnClickListener) this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener((View.OnClickListener) this);

        etName = (EditText) findViewById(R.id.etName);
        etlastname = (EditText) findViewById(R.id.etlastname);
        etfightnumber = (EditText) findViewById(R.id.etfightnumber);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String name = etName.getText().toString();
        String lastname = etlastname.getText().toString();
        String fightnumber = etfightnumber.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        switch (v.getId()) {

            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_LASTNAME, lastname);
                contentValues.put(DBHelper.KEY_FIGHTNUMBER, fightnumber);

                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                break;

            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int lastnameIndex = cursor.getColumnIndex(DBHelper.KEY_LASTNAME);
                    int fightnumberIndex = cursor.getColumnIndex(DBHelper.KEY_FIGHTNUMBER);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", lastname = " + cursor.getString(lastnameIndex) +
                                ", fightnumber = " + cursor.getString(fightnumberIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");

                cursor.close();
                break;

            case R.id.btnClear:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                break;
        }
        dbHelper.close();
    }
}
