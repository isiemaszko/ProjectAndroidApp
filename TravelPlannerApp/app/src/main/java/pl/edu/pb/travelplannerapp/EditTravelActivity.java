package pl.edu.pb.travelplannerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class EditTravelActivity extends AppCompatActivity {
    public static final String EXTRA_EDIT_NAME="pb.edu.pl.EXTRA_EDIT_NAME";
    public static final String EXTRA_EDIT_PLACE="pb.edu.pl.EXTRA_EDIT_PLACE";
    public static final String EXTRA_EDIT_START="pb.edu.pl.EXTRA_EDIT_START";

    private EditText editNameEditText;
    private EditText editPlaceEditText;
    private EditText editDataStart;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_edit_travel);

        editNameEditText=findViewById(R.id.edit_name);
        editPlaceEditText=findViewById(R.id.edit_place);

        if(getIntent().hasExtra(EXTRA_EDIT_NAME)&&
        getIntent().hasExtra(EXTRA_EDIT_PLACE)){
            editNameEditText.setText((getIntent().getSerializableExtra(EXTRA_EDIT_NAME).toString()));
            editPlaceEditText.setText((getIntent().getSerializableExtra(EXTRA_EDIT_PLACE).toString()));
        }

        editDataStart=findViewById(R.id.edit_data_start);
        editDataStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(EditTravelActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                editDataStart.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        final Button button=findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent=new Intent();
                if(TextUtils.isEmpty((editNameEditText.getText())) ||
                        TextUtils.isEmpty(editPlaceEditText.getText())){

                    setResult(RESULT_CANCELED, replyIntent);
                }else {
                    String name=editNameEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_NAME, name);

                    String place=editPlaceEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_PLACE, place);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}