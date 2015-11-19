package edu.brandeis.yangliu.expenselog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    final static int ADD_EXPENSE_ACTIVITY = 1;
    final static String NEW_EXPENSE_DATA_CODE = "new expense data";
    ExpenseArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listview = (ListView) findViewById(R.id.list_main);

        final ArrayList<ExpenseLogEntryData> list = new ArrayList<>();
        addTestItems(list);

        this.adapter = new ExpenseArrayAdapter(this, R.layout.expense_entry, list);
        listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                removeItemFromList(position, adapter);
                return true;
            }

        });
    }

    private void addTestItems(ArrayList<ExpenseLogEntryData> list) {
        String[] testValues = new String[]{"Android", "iPhone", "WindowsMobile", "BlackBarry", "Symbian"};
        for (int i = 0; i < testValues.length; ++i) {
            ExpenseLogEntryData temp = new ExpenseLogEntryData(testValues[i], "test: " + String.valueOf(i));
            list.add(temp);
        }
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
                    this.adapter.add((ExpenseLogEntryData) data.getSerializableExtra(NEW_EXPENSE_DATA_CODE));
                    this.adapter.notifyDataSetChanged();
                    this.adapter.notifyDataSetInvalidated();
                }
                break;
            }
        }
    }

    protected void removeItemFromList(int position, final ExpenseArrayAdapter adapter) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // main code on after clicking yes
                adapter.remove(adapter.getItem(deletePosition));
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

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

    private class ExpenseArrayAdapter extends ArrayAdapter<ExpenseLogEntryData> {

        Context context;
        int layoutResourceId;

        public ExpenseArrayAdapter(Context context, int textViewResourceId,
                                   ArrayList<ExpenseLogEntryData> expenses) {
            super(context, textViewResourceId, expenses);
            this.context = context;
            layoutResourceId = textViewResourceId;
        }

        public View getView(int index, View view, ViewGroup parent) {
            View row = view;
            ExpenseHolder holder;

            if (row == null) {
                LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ExpenseHolder();
                holder.date = (TextView) row.findViewById(R.id.entry_date_view);
                holder.description = (TextView) row.findViewById(R.id.entry_description_view);
                holder.note = (TextView) row.findViewById(R.id.entry_note_view);

                row.setTag(holder);
            } else {
                holder = (ExpenseHolder) row.getTag();
            }

            ExpenseLogEntryData expense = this.getItem(index);
            holder.date.setText(expense.getDate().toString());
            holder.description.setText(expense.getDescription());
            holder.note.setText(expense.getNote());

            return row;
        }

        class ExpenseHolder {
            TextView date;
            TextView description;
            TextView note;
        }

    }
}
