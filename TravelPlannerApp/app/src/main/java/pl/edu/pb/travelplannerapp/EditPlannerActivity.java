package pl.edu.pb.travelplannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditPlannerActivity extends AppCompatActivity {

    public static final String EXTRA_EDIT_NAME_PLANNER="pb.edu.pl.EXTRA_EDIT_NAME";
    public static final String EXTRA_EDIT_DATE="pb.edu.pl.EXTRA_EDIT_DATE";
    public static final String EXTRA_EDIT_TIME="pb.edu.pl.EXTRA_EDIT_TIME";

    private EditText editNameEditText;
    private EditText editDataEditText;
    private EditText editTimeEditText;
    DatePickerDialog datePickerDialog;
    TimePickerDialog mTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_planner);

        editNameEditText=findViewById(R.id.edit_planner_name);
        editDataEditText=findViewById(R.id.edit_data);
        editTimeEditText=findViewById(R.id.edit_time);

        if(getIntent().hasExtra(EXTRA_EDIT_NAME_PLANNER)&&
                getIntent().hasExtra(EXTRA_EDIT_DATE)&&
                getIntent().hasExtra(EXTRA_EDIT_TIME))
        {
            editNameEditText.setText((getIntent().getSerializableExtra(EXTRA_EDIT_NAME_PLANNER).toString()));
            editDataEditText.setText((getIntent().getSerializableExtra(EXTRA_EDIT_DATE).toString()));
            editTimeEditText.setText((getIntent().getSerializableExtra(EXTRA_EDIT_TIME).toString()));
        }

        editDataEditText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(EditPlannerActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                editDataEditText.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(EditPlannerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTimeEditText.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        final Button button=findViewById(R.id.button_save_planner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent=new Intent();
                if(TextUtils.isEmpty((editNameEditText.getText())) ||
                        TextUtils.isEmpty(editDataEditText.getText())
                        || TextUtils.isEmpty(editTimeEditText.getText())){

                    setResult(RESULT_CANCELED, replyIntent);
                }else {
                    String name=editNameEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_NAME_PLANNER, name);

                    String data=editDataEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_DATE, data);

                    String time=editTimeEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_TIME,time);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
