package addressbook.codekon.de.consulateaddressbook;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AddressBookDataBase extends SQLiteOpenHelper {

    private static final String DB = "addressBookDataBase";
    private static final int VERSION = 1;

    public AddressBookDataBase(Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableConsulat = "CREATE TABLE consulat (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "country TEXT COLLATE NOCASE,consulat_name TEXT COLLATE NOCASE,street TEXT COLLATE NOCASE, zipcode TEXT COLLATE NOCASE, city TEXT COLLATE NOCASE, tlf TEXT COLLATE NOCASE, fax TEXT COLLATE NOCASE, eMail TEXT COLLATE NOCASE)";

        String createTableCountries = "CREATE TABLE countries (id INTEGER PRIMARY KEY AUTOINCREMENT," + "country TEXT COLLATE NOCASE)";

        sqLiteDatabase.execSQL(createTableConsulat);
        sqLiteDatabase.execSQL(createTableCountries);

        insertConsulatData(sqLiteDatabase);
        insertCountries(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL("DROP TABLE if EXISTS consulat");
        onCreate(sqLiteDatabase);
    }

    private void insertConsulatData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO consulat VALUES(1,'Almanya','Konsolosluk','Paris Cad. 29','06540','Ankara','0090 312 4555 100','0090 312 4555 100','info@ankara.diplo.de');");
        db.execSQL("INSERT INTO consulat VALUES(2,'Amerika','Konsolosluk','Remzi Oğuz Arık Mh','06540','Ankara','0090 312 455 5555','0090 312 455 5555','');");
    }

    private void insertCountries(SQLiteDatabase db) {
        db.execSQL("INSERT INTO countries VALUES(1,'Almanya');");
        db.execSQL("INSERT INTO countries VALUES(2,'Amerika');");
        db.execSQL("INSERT INTO countries VALUES(3,'Danimarka');");
        db.execSQL("INSERT INTO countries VALUES(4,'Fransa');");
    }

    public ArrayList<String> getCountries() {

        ArrayList<String> countries = new ArrayList<>();
        String selectQuery = "SELECT  * FROM countries";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String country = cursor.getString(1);
                countries.add(country);
            } while (cursor.moveToNext());
        }

        return countries;


    }
}
