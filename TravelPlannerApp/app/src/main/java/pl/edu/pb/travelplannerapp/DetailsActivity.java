package pl.edu.pb.travelplannerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ID_TRAVEL="pb.edu.pl.EXTRA_ID_TRAVEL";

    private PlannerViewModel palnViewModel;
    private Planner plann;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        RecyclerView recyclerView=findViewById(R.id.recyclerviewDetails);
        final DetailsAdapter adapter=new DetailsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(getIntent().hasExtra(EXTRA_ID_TRAVEL)){
            id=(int) getIntent().getSerializableExtra(EXTRA_ID_TRAVEL);
            Log.d("MainActivity","id"+id);
        }

        palnViewModel= ViewModelProviders.of(this).get(PlannerViewModel.class);
//        ArrayList<Plan> plans=new ArrayList<Plan>();

        palnViewModel.findAll().observe(this, new Observer<List<Planner>>() {
            @Override
            public void onChanged(List<Planner> planners) {
                adapter.setPlanners(planners);
                Log.d("MainActivity","pl"+planners);
            }
        });


        FloatingActionButton addBookButton = findViewById(R.id.add_details);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this, EditTravelActivity.class);
//
//                startActivityForResult(intent,NEW_TRAVEL_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private class DetailsHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public DetailsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.planner_list_item,parent,false));

            name=itemView.findViewById(R.id.day_name);
        }


        public void bind(Planner plan){
            Log.d("MainActivity", "nam"+plan.getName());
            name.setText(plan.getName());
        }
    }

    private class DetailsAdapter extends RecyclerView.Adapter<DetailsHolder>{

        private List<Planner> plans;
        @NonNull
        @Override
        public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DetailsHolder(getLayoutInflater(),parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailsHolder holder, int position) {
            if(plans!=null){
                Planner plan=plans.get(position);
                holder.bind(plan);
            }else {
                Log.d("DetailsActivity", "No plans");
            }
        }

        @Override
        public int getItemCount() {
            if(plans!=null){
                return  plans.size();
            }else {
                return 0;
            }
        }

        void setPlanners(List<Planner> plans){
            this.plans=plans;
            notifyDataSetChanged();
        }
    }
}
