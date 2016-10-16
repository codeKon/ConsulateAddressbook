package addressbook.codekon.de.consulateaddressbook;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowAddressActivity extends Activity {

    private AddressBookDataBase addressBookDataBase;
    private SQLiteDatabase sqLiteDatabase;

    private ArrayAdapter spinnerAdapter;
    private ArrayList<String> countries;

    private TextView result;
    private EditText enter_city_or_zip_code_edit;
    private Spinner country_spinner;

    private String enter = "";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_address);

        addressBookDataBase = new AddressBookDataBase(this);
        sqLiteDatabase = addressBookDataBase.getWritableDatabase();

        final Button find = (Button) findViewById(R.id.find);

        country_spinner = (Spinner) findViewById(R.id.country_spinner);
        enter_city_or_zip_code_edit = (EditText)findViewById(R.id.enter_city_or_zip_code_edit);
        result = (TextView)findViewById(R.id.result);

        countries = addressBookDataBase.getCountries();
        String [] country = countries.toArray(new String[countries.size()]);

        spinnerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_textview_layout, country);
        country_spinner.setAdapter(spinnerAdapter);


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = dataSelect();

                if (cursor != null) {
                    showData(cursor);
                }

            }
        });
    }

    private Cursor dataSelect(){


        String cityOrZipCode = enter_city_or_zip_code_edit.getText().toString();
        String countrySpinner =  country_spinner.getSelectedItem().toString();

        if(ifExists()){

            String selectEingabe = "SELECT * FROM consulat WHERE country = '"+countrySpinner+"' AND (city LIKE '" + cityOrZipCode +"' OR zipCode LIKE '" + cityOrZipCode+"')";
            Cursor cursor = sqLiteDatabase.rawQuery(selectEingabe, null);

            return cursor;

        }

        else{

            result.setText("");
            Context context = getApplicationContext();

            CharSequence message_not_found = getString(R.string.message_not_found);
            Toast toast = Toast.makeText(context, message_not_found, Toast.LENGTH_LONG);
            toast.show();

            return null;

        }
    }

    private void showData(Cursor cursor){

        StringBuilder builder = new StringBuilder("\n");

        while (cursor.moveToNext()){

            String country = cursor.getString(cursor.getColumnIndex("country"));
            String consulatName = cursor.getString(cursor.getColumnIndex("consulat_name"));
            String street = cursor.getString(cursor.getColumnIndex("street"));
            String zipcode = cursor.getString(cursor.getColumnIndex("zipcode"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String tlf = cursor.getString(cursor.getColumnIndex("tlf"));
            String fax = cursor.getString(cursor.getColumnIndex("fax"));
            String eMail = cursor.getString(cursor.getColumnIndex("eMail"));

            builder.append(country+"\n");
            builder.append(consulatName+"\n");
            builder.append(street+"\n");
            builder.append(zipcode+"\n");
            builder.append(city+"\n");
            builder.append(tlf+"\n");
            builder.append(fax+"\n");
            builder.append(eMail+"\n");

        }

        result.setText(builder);
    }


    private boolean ifExists(){

        enter = enter_city_or_zip_code_edit.getText().toString().trim();
        String countrySpinner =  country_spinner.getSelectedItem().toString();

        String selectCityOrZipcode ="SELECT * FROM consulat WHERE country = '"+countrySpinner+"' AND city = '" + enter+ "' OR zipcode = '" + enter+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectCityOrZipcode, null);

        return cursor.moveToNext();

    }

}