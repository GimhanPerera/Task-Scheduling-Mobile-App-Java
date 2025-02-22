
package com.example.test3;
//import libraries
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.test3.Model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
//----------------------------------IM/2020/004 - Bimindu Aberathna-----------------------------------------------
// Define the AddTaskFragment class that extends Fragment
public class AddTaskFragment extends Fragment {
    private TextView selectedDateTextView;
    private Calendar selectedDateCalendar;
    Calendar calendar = Calendar.getInstance();
    private int selectedYear = calendar.get(Calendar.YEAR);
    private int selectedMonth = calendar.get(Calendar.MONTH);
    private int selectedDay = calendar.get(Calendar.DAY_OF_MONTH);



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        selectedDateTextView = view.findViewById(R.id.selectedDateTextView);

        //inisiate the elements
        listClass object = listClass.getInstance();
        EditText title = view.findViewById(R.id.addTask_Title);
        EditText description = view.findViewById(R.id.addTask_Description);

        //set elements - NOT WORK
        title.setText("");
        description.setText("");

        // Initialize the selectedDateCalendar
        selectedDateCalendar = Calendar.getInstance();
        Button btn_add = view.findViewById(R.id.btn_add);

        Button selectDateButton = view.findViewById(R.id.selectDateButton);
        ImageButton btn_back = view.findViewById(R.id.Back);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new task
                if(title.getText().toString().equals("")){
                    Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                //Crete new task object
                Task newTask = new Task(object.getNextId(), title.getText().toString(), description.getText().toString(), selectedYear, selectedMonth, selectedDay);
                object.setNewTask(newTask);
                //Toast.makeText(requireContext(),"Ad "+newTask.getId()+" "+newTask.getTitle(), Toast.LENGTH_LONG).show();//For testing
                title.setText("");
                description.setText("");
                ((HomeActivity) getActivity()).openHomePage();//Open home again.
                //getActivity() -  within a Fragment, it returns the parent Activity(HomeActivity in here) associated with that Fragment.
                //We call openHomePage(); Method of Parent Activity
            }}
        });
        view.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    View focusedView = requireActivity().getCurrentFocus();
                    if (focusedView != null) {
                        imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("");
                description.setText("");
                ((HomeActivity) getActivity()).openHomePage();//Open home again.
                //getActivity() -  within a Fragment, it returns the parent Activity(HomeActivity in here) associated with that Fragment.
                //We call openHomePage(); Method of Parent Activity
            }
        });

        selectDateButton.setOnClickListener(new View.OnClickListener() {//popup calender
            @Override
            public void onClick(View v) {
                // Show the DatePickerDialog
                showDatePickerDialog(selectedDateTextView, selectedDateCalendar);
            }
        });




        return view;
    }

    private void showDatePickerDialog(TextView selectedDateTextView, Calendar selectedDateCalendar) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                R.style.DatePickerDialogStyle,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the selected date
                        AddTaskFragment.this.selectedDateCalendar.set(year, month, dayOfMonth);

                        // Extract and store the selected date components
                        selectedYear = AddTaskFragment.this.selectedDateCalendar.get(Calendar.YEAR);
                        selectedMonth = AddTaskFragment.this.selectedDateCalendar.get(Calendar.MONTH); // Note: Month is 0-based (0 = January, 11 = December)
                        selectedDay = AddTaskFragment.this.selectedDateCalendar.get(Calendar.DAY_OF_MONTH);

                        // Format the selected date as needed and display it in the TextView
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        String formattedDate = dateFormat.format(AddTaskFragment.this.selectedDateCalendar.getTime());
                        AddTaskFragment.this.selectedDateTextView.setText(formattedDate);

                        // Now you can use the selectedYear, selectedMonth, and selectedDay as needed.
                    }
                },
                this.selectedDateCalendar.get(Calendar.YEAR),
                this.selectedDateCalendar.get(Calendar.MONTH),
                this.selectedDateCalendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }


}

