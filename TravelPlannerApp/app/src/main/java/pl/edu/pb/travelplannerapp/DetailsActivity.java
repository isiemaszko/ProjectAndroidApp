package pl.edu.pb.travelplannerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ID_TRAVEL="pb.edu.pl.EXTRA_ID_TRAVEL";

    private PlannerViewModel palnViewModel;
    private Planner plann;
    private int id;
    public static final int NEW_PLANNER_ACTIVITY_REQUEST_CODE=1;

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
                List<Planner> pom=new ArrayList<Planner>();
                for (Planner el:planners
                     ) {
                    Log.d("MainActivity","elem "+el.getIdTravel()+ " "+el.getName());
                    if(el.getIdTravel()==id){

                        pom.add(el);
                    }
                }
                adapter.setPlanners(pom);
                Log.d("MainActivity","pl"+pom);
            }
        });


        FloatingActionButton addBookButton = findViewById(R.id.add_details);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailsActivity.this, EditPlannerActivity.class);

                startActivityForResult(intent,NEW_PLANNER_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==NEW_PLANNER_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
            Planner planner=new Planner(id,data.getStringExtra(EditPlannerActivity.EXTRA_EDIT_NAME_PLANNER),
                    data.getStringExtra(EditPlannerActivity.EXTRA_EDIT_DATE),
                    data.getStringExtra(EditPlannerActivity.EXTRA_EDIT_TIME));
            palnViewModel.insert(planner);

            Snackbar.make(findViewById(R.id.coordinator_layout_details),getString(R.string.planner_added),
                    Snackbar.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.planner_empty_not_saved,
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private class DetailsHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView deleteImageView;
        public DetailsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.planner_list_item,parent,false));

            name=itemView.findViewById(R.id.day_name);
            deleteImageView=itemView.findViewById(R.id.delete_planner);

            deleteImageView.setImageResource(R.drawable.ic_delete_black_24dp);
        }


        public void bind(Planner plan){
            Log.d("MainActivity", "nam"+plan.getName());

            name.setText(plan.getName());
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    palnViewModel.delete(plan);
                }
            });
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
