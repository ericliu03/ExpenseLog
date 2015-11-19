package edu.brandeis.yangliu.expenselog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity {

    final static int ADD_EXPENSE_ACTIVITY = 1;
    final static String NEW_EXPENSE_DATA_CODE = "new expense data";
    SimpleCursorAdapter adapter;
    DBTool dbTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbTool = new DBTool(this);
        String[] from = new String[]{dbTool.DATE, dbTool.DESCRIPTION, dbTool.NOTE};
        int[] to = new int[]{R.id.entry_date_view, R.id.entry_description_view, R.id.entry_note_view};
        this.adapter = new SimpleCursorAdapter(this, R.layout.expense_entry, this.dbTool.getAllData(), from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        ListView listview = (ListView) findViewById(R.id.main_list_view);
        listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                removeItemFromList(position);
                return true;
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_expense:
                Intent intent = new Intent(this, AddExpenseActivity.class);
                startActivityForResult(intent, ADD_EXPENSE_ACTIVITY);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (ADD_EXPENSE_ACTIVITY): {
                if (resultCode == Activity.RESULT_OK) {
                    this.dbTool.insertData((ExpenseLogEntryData) data.getSerializableExtra(NEW_EXPENSE_DATA_CODE));
                    this.adapter.changeCursor(dbTool.getAllData());
                }
                break;
            }
        }
    }

    protected void removeItemFromList(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(
                MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // main code on after clicking yes
                dbTool.delete(adapter.getItemId(position));
                adapter.changeCursor(dbTool.getAllData());
            }
        });
        alert.setNegativeButton("CANCEL", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

    }

}
