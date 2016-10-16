package addressbook.codekon.de.consulateaddressbook;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EnterAddressActivity extends Activity {

    private AddressBookDataBase addressBookDataBase;

    private ArrayAdapter spinnerAdapter;
    private ArrayList<String> countries;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_address);

        addressBookDataBase = new AddressBookDataBase(this);

        final Spinner country_spinner = (Spinner) findViewById(R.id.country_spinner);

        final EditText consulat_name_edit = (EditText) findViewById(R.id.consulat_name_edit);
        final EditText street_edit = (EditText) findViewById(R.id.street_edit);
        final EditText zipcode_edit = (EditText) findViewById(R.id.zipcode_edit);//TODO check if valid
        final EditText city_edit = (EditText) findViewById(R.id.city_edit);
        final EditText tlf_edit = (EditText) findViewById(R.id.tlf_edit);//TODO check if valid
        final EditText fax_edit = (EditText) findViewById(R.id.fax_edit);//TODO check if valid
        final EditText eMail_edit = (EditText) findViewById(R.id.eMail_edit);//TODO check if valid

        final Button save = (Button) findViewById(R.id.save);

        countries = addressBookDataBase.getCountries();
        String [] country = countries.toArray(new String[countries.size()]);

        spinnerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_textview_layout, country);
        country_spinner.setAdapter(spinnerAdapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    //TODO if fields are empty
                    //TODO check if data exists
                    saveData(country_spinner.getSelectedItem().toString(), consulat_name_edit.getText().toString(),
                            street_edit.getText().toString(), zipcode_edit.getText().toString(),
                            city_edit.getText().toString(), tlf_edit.getText().toString(),
                            fax_edit.getText().toString(), eMail_edit.getText().toString());


                    CharSequence saved_info = getString(R.string.saved_info);
                    Toast toast = Toast.makeText(getApplicationContext(), saved_info, Toast.LENGTH_LONG);
                    toast.show();

                } finally {
                    addressBookDataBase.close();
                }
            }
        });
    }

    public void saveData(String country, String consulat_name, String street, String zipcode,
                         String city, String tlf, String fax, String eMail ){

        SQLiteDatabase db = addressBookDataBase.getWritableDatabase();
        ContentValues data = new ContentValues();

        data.put("country", country);
        data.put("consulat_name", consulat_name);
        data.put("street", street);
        data.put("zipcode", zipcode);
        data.put("city", city);
        data.put("tlf", tlf);
        data.put("fax", fax);
        data.put("eMail", eMail);

        db.insertOrThrow("consulat",null,data);

    }
}


