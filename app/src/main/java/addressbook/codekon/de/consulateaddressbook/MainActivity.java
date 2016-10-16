package addressbook.codekon.de.consulateaddressbook;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String menu[] = {this.getResources().getString(R.string.enter_adress), this.getResources().getString(R.string.show_list)};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (position == 0) {

            startActivity(new Intent(MainActivity.this, EnterAddressActivity.class));
        } else if (position == 1) {

            startActivity(new Intent(MainActivity.this, ShowAddressActivity.class));
        }

    }

}