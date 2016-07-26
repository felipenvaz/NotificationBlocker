package com.enterprises.fnv.notificationblocker.managefilters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.enterprises.fnv.notificationblocker.MDL.EFilterAt;
import com.enterprises.fnv.notificationblocker.R;

/**
 * Created by FELIPE on 25/12/2015.
 */
public class AddFilterItemDialogActivity extends Activity implements View.OnClickListener {

    public static final String TEXT = "text";
    public static final String AT = "at";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_item);

        ((Button)findViewById(R.id.filter_item_add)).setOnClickListener(this);
        ((Spinner)findViewById(R.id.filter_item_spinner)).setAdapter(new ArrayAdapter<EFilterAt>(this, R.layout.filter_item_spinner_item, EFilterAt.values()));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.filter_item_add){
            EditText text = (EditText) findViewById(R.id.filter_item_filter);
            if("".equals(text.getText().toString().trim())) {
                text.setBackground(getDrawable(R.drawable.red_border));
            }else {
                Intent i = new Intent();
                i.putExtra(TEXT, text.getText().toString());

                Spinner spinner = (Spinner) findViewById(R.id.filter_item_spinner);
                i.putExtra(AT, ((EFilterAt) spinner.getAdapter().getItem(spinner.getSelectedItemPosition())).getItemId());

                setResult(Activity.RESULT_OK, i);
                finish();
            }
        }
    }
}
