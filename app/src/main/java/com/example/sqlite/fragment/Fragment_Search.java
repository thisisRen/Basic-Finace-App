package com.example.sqlite.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sqlite.AddItem;
import com.example.sqlite.R;
import com.example.sqlite.adapter.RecycleViewAdapter;
import com.example.sqlite.dal.SQLiteHelper;
import com.example.sqlite.model.Item;

import java.util.Calendar;
import java.util.List;

public class Fragment_Search extends Fragment implements View.OnClickListener{
    private SearchView searchView;
    private EditText timeFrom, timeTo;
    private TextView tong;
    private Spinner sp;
    private Button btn_search;
    private RecyclerView recyclerView;
    private SQLiteHelper db;
    private RecycleViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.search);
        timeFrom = view.findViewById(R.id.eFrom);
        timeTo = view.findViewById(R.id.eTo);
        tong = view.findViewById(R.id.tv_Tong);
        sp = view.findViewById(R.id.spCategory);
        btn_search = view.findViewById(R.id.btn_search);
        recyclerView = view.findViewById(R.id.search_recycleView);
        String arr[] = getResources().getStringArray(R.array.category);
        String arr1[] = new String[arr.length+1];
        arr1[0] ="All";
        for (int i=0; i<arr.length; i++){
            arr1[i+1]=arr[i];
        }
        sp.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, arr1));

        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Item> list = db.getAll();
        adapter.setList(list);
        tong.setText("Tong so luong: " + list.size());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> list = db.searchByTitle(s);
                tong.setText("Tong so luong: " + list.size());
                adapter.setList(list);
                return true;
            }
        });
        timeFrom.setOnClickListener(this);
        timeTo.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cate = sp.getItemAtPosition(i).toString();
                List<Item> list;
                if(!cate.equalsIgnoreCase("all")){
                    list=db.searchByCategory(cate);
                }
                else {
                    list = db.getAll();
                }
                adapter.setList(list);
                tong.setText("Tong so luong: " + list.size());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == searchView){

        }
        if(view == timeFrom){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m>8){
                        date = d+"/"+(m+1)+"/"+y;
                    }
                    else {
                        date = d+"/0"+(m+1)+"/"+y;
                    }
                    timeFrom.setText(date);
                }
            },year, month, day);
            dialog.show();
        }
        if (view == timeTo){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m>8){
                        date = d+"/"+(m+1)+"/"+y;
                    }
                    else {
                        date = d+"/0"+(m+1)+"/"+y;
                    }
                    timeTo.setText(date);
                }
            },year, month, day);
            dialog.show();
        }
        if (view == btn_search){
            String from = timeFrom.getText().toString();
            String to = timeTo.getText().toString();
            if(!from.isEmpty() && !to.isEmpty()){
                List<Item> list = db.getByDateFromTo(from,to);
                adapter.setList(list);
                tong.setText("Tong so luong: "+list.size());
            }
        }
      }
}