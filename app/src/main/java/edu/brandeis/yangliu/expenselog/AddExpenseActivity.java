package edu.brandeis.yangliu.expenselog;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
    }

    public void save(View view) {
        Intent resultIntent = new Intent();
        EditText newDes = (EditText) findViewById(R.id.new_description_text);
        EditText newNote = (EditText) findViewById(R.id.new_note_text);
        ExpenseLogEntryData newEntry = new ExpenseLogEntryData(
                newDes.getText().toString(), newNote.getText().toString()
        );
        resultIntent.putExtra(MainActivity.NEW_EXPENSE_DATA_CODE, newEntry);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
