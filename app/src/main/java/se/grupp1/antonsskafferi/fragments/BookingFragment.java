package se.grupp1.antonsskafferi.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.components.RecyclerViewAdapter;
import se.grupp1.antonsskafferi.data.BookingData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.lib.StringFormatter;

public class BookingFragment extends Fragment {

    //DatePicker
    private TextView mTv;
    private Button mBtn;
    private DatePickerDialog dpd;
    private Calendar c;
    //-------

    //Timepicker
    private TextView tv;
    private Button Btn;
    private Calendar currentTime;
    private int hour, minute;
    private String format;
    //-------

    //Booking
    public interface RecyclerCallback
    {
        void getData(ArrayList<Integer> data);
    }
    private ArrayList<Integer> tableList = new ArrayList<>();
    private ArrayList<Integer> isChecked = new ArrayList<>();

    public interface tablesCallback
    {
        void gotTables();
    }

    private String prevDate;

    //-------


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_booking, container, false);

        //TimePicker
        tv = root.findViewById(R.id.BookingTime);
        Btn = root.findViewById(R.id.BtnTimePicker);

        currentTime = Calendar.getInstance();

        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        tv.setText(StringFormatter.formatTime(hour + ":" + minute));
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
        //End of TimePicker

        //DatePicker
        mTv = root.findViewById(R.id.BookingDate);
        mBtn = root.findViewById(R.id.BtnDatePicker);


        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEJ " + prevDate);
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year =c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        mTv.setText(StringFormatter.formatDate(mYear + "-" + (mMonth + 1) + "-" + mDay));


                        getAvailableTables(mTv.getText().toString(), new tablesCallback() {
                            TextView hiddenCustomerId = root.findViewById(R.id.hiddenCustomerId);
                            String customerId = hiddenCustomerId.getText().toString();
                            TextView hiddenTableId = root.findViewById(R.id.hiddenTableId);
                            String tableId = hiddenTableId.getText().toString();


                            @Override
                            public void gotTables() {
                                if(!customerId.isEmpty() && prevDate.equals(mTv.getText().toString()))
                                    tableList.add(Integer.valueOf(tableId));
                                availableTables(root);
                            }
                        });

                    }
                }, year, month, day);
                dpd.show();


            }
        });
        // End of DatePicker.

        Button SendBtn =  root.findViewById(R.id.SendBtn);
        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText bookingFirstName = root.findViewById(R.id.BookingFirstName);
                EditText bookingLastName = root.findViewById(R.id.BookingLastName);
                EditText bookingPeopleAmount =  root.findViewById(R.id.BookingPeopleAmount);
                EditText bookingPhoneNr =  root.findViewById(R.id.BookingPhoneNr);
                EditText bookingEmail =  root.findViewById(R.id.BookingEmail);
                TextView bookingTime =  root.findViewById(R.id.BookingTime);
                TextView bookingDate =  root.findViewById(R.id.BookingDate);
                TextView hiddenCustomerId = root.findViewById(R.id.hiddenCustomerId);
                TextView hiddenTableId = root.findViewById(R.id.hiddenTableId);

                String firstName = bookingFirstName.getText().toString();
                String lastName = bookingLastName.getText().toString();
                String peopleAmount = bookingPeopleAmount.getText().toString();
                String phoneNr = bookingPhoneNr.getText().toString();
                String email = bookingEmail.getText().toString();
                String time = bookingTime.getText().toString();
                String date = bookingDate.getText().toString();
                String customerId = hiddenCustomerId.getText().toString();
                String tableId = hiddenTableId.getText().toString();

                boolean emptyFields = false;

                if(firstName.isEmpty())
                {
                    emptyFields = true;
                    bookingFirstName.setError("Skriv in förnamn");
                } else bookingFirstName.setError(null);

                if(lastName.isEmpty())
                {
                    emptyFields = true;
                    bookingLastName.setError("Skriv in efternamn");
                } else bookingLastName.setError(null);

                if(peopleAmount.isEmpty())
                {
                    emptyFields = true;
                    bookingPeopleAmount.setError("Skriv in antal bokade platser");
                } else bookingPeopleAmount.setError(null);

                if(phoneNr.isEmpty())
                {
                    emptyFields = true;
                    bookingPhoneNr.setError("Skriv in ett teleNr");
                } else bookingPhoneNr.setError(null);

                if(time.isEmpty() || time.equals(getString(R.string.tid)))
                {
                    emptyFields = true;
                    bookingTime.setError("Skriv in bokad tid");
                } else bookingTime.setError(null);

                if(date.isEmpty() || date.equals(getString(R.string.datum)))
                {
                    emptyFields = true;
                    bookingDate.setError("Skriv in datum");
                } else bookingDate.setError(null);

                if(email.isEmpty())
                {
                    emptyFields = true;
                    bookingEmail.setError("Skriv in Email");
                } else if(!StringFormatter.isValidEmail(email)) {
                    emptyFields = true;
                    bookingEmail.setError("Ej giltig Email");
                } else bookingEmail.setError(null);
                if(isChecked.isEmpty())
                {
                    emptyFields = true;
                    Toast checkToast = Toast.makeText(getContext(), "Måste välja minst ett bord!", Toast.LENGTH_LONG);
                        checkToast.setGravity(Gravity.CENTER, 0, 0);
                        checkToast.show();
                }


                if(!customerId.isEmpty() && !emptyFields)
                {
                    delete(customerId);
                    /*
                    boolean checkedPrevTable = false;
                    for(int i = 0; i < isChecked.size(); i++)
                    {
                        if(Integer.valueOf(tableId) == isChecked.get(i))
                        {
                            isChecked.remove(i);
                            checkedPrevTable = true;
                            i--;
                        }
                    }
                    if(!checkedPrevTable)
                    {
                        //TODO delete customer with id customerId from database
                        delete(customerId);
                    }*/
                }

                for(int i = 0; i < isChecked.size(); i++)
                {
                    BookingData data = new BookingData(
                            firstName,
                            lastName,
                            peopleAmount,
                            phoneNr,
                            time,
                            date,
                            email,
                            isChecked.get(i));

                    if(!emptyFields) {
                        System.out.println("Sent to backend");
                        sendToDatabase(data);
                    }
                }

                if(!emptyFields){
                    bookingFirstName.setText("");
                    bookingLastName.setText("");
                    bookingPeopleAmount.setText("");
                    bookingPhoneNr.setText("");
                    bookingEmail.setText("");
                    bookingTime.setText("");
                    bookingDate.setText("");
                    getAvailableTables(mTv.getText().toString(), new tablesCallback() {
                        @Override
                        public void gotTables() {
                            availableTables(root);
                        }
                    });
                }

            }

            });
        return root;
    }

    private void sendToDatabase(BookingData data) {
        HttpRequest.Response response = new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                System.out.println(status);
                Toast.makeText(getActivity(), status == 200
                        ? "Bokningen genomförd" : "Kunde inte genomföra bokningen, var vänlig försök igen. Felkod: " + status,
                        Toast.LENGTH_SHORT
                ).show();
            }
        };
        HttpRequest httpRequest = new HttpRequest(response);
        httpRequest.setRequestMethod("POST");
        String payload = data.toJSONString();
        System.out.println(payload);
        httpRequest.setPayload(payload);
        httpRequest.execute(DatabaseURL.insertCustomer);
    }


    private void getAvailableTables(final String date, final tablesCallback callback)
    {
        //final String urlString = "http://82.196.113.65:8080/dinnertables/booking?date=" + date;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat todaysDate = new SimpleDateFormat(pattern);
        final String today = todaysDate.format(new Date());


        HttpRequest request = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                tableList.clear();
                try{
                    JSONArray jsonArr = new JSONArray(output);
                    for (int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject table =jsonArr.getJSONObject(i);
                        if(!(table.getBoolean("active") && today.equals(date)))
                        {
                            tableList.add(table.getInt("dinnertableid"));
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                callback.gotTables();
            }
        });
        request.setRequestMethod("GET");
        request.execute(DatabaseURL.getTableAvailableForDate + date);
    }

    private void availableTables(View root)
    {
        isChecked.clear();
        RecyclerView myRecycler = (RecyclerView) root.findViewById(R.id.BookingRecycler);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), tableList, new RecyclerCallback() {
            @Override
            public void getData(ArrayList<Integer> data) {
                isChecked = data;
            }
        });
        myRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        myRecycler.setAdapter(myAdapter);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                checkDate();
                getAvailableTables(mTv.getText().toString(), new tablesCallback() {
                    TextView hiddenCustomerId = view.findViewById(R.id.hiddenCustomerId);
                    String customerId = hiddenCustomerId.getText().toString();
                    TextView hiddenTableId = view.findViewById(R.id.hiddenTableId);
                    String tableId = hiddenTableId.getText().toString();


                    @Override
                    public void gotTables() {
                        if(!customerId.isEmpty() && prevDate.equals(mTv.getText().toString()))
                            tableList.add(Integer.valueOf(tableId));
                        availableTables(view);
                    }
                });
            }
        }, 0);

    }

    private void delete(String customerId)
    {
        HttpRequest.Response response = new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status)
            {
                if(status != 200) {
                    Toast.makeText(getContext(), "Kunde inte ta bort objekt, var vänlig försök igen. Felkod: " + status,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        };
        HttpRequest httpRequest = new HttpRequest(response);
        httpRequest.setRequestMethod("DELETE");

        httpRequest.execute(DatabaseURL.deleteCustomer + customerId);
    }

    public void checkDate()
    {
        prevDate = mTv.getText().toString();
    }


}
