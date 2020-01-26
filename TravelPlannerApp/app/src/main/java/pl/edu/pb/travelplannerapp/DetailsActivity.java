package pl.edu.pb.travelplannerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
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

    private PlanTravelViewModel palnViewModel;
    private PlanTravel plann;
    private int id;
    private ImageView imageinCamera;
    public static final int NEW_PLANNER_ACTIVITY_REQUEST_CODE=1;
    Bitmap bitmap;
    String stringBitmap;

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

        palnViewModel= ViewModelProviders.of(this).get(PlanTravelViewModel.class);
//        ArrayList<Plan> plans=new ArrayList<Plan>();

        palnViewModel.findAll().observe(this, new Observer<List<PlanTravel>>() {
            @Override
            public void onChanged(List<PlanTravel> planners) {
                List<PlanTravel> pom=new ArrayList<PlanTravel>();
                for (PlanTravel el:planners
                     ) {
                    if(el.getIdTravel()==id){
                        pom.add(el);
                    }
                }
                adapter.setPlanners(pom);
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
             stringBitmap= data.getStringExtra(EditPlannerActivity.EXTRA_PHOTO);
            Log.d("MainActivity","bitmap z intent "+ stringBitmap);
            bitmap=StringToBitMap(stringBitmap);
            Log.d("MainActivity","bitmap po convercie "+bitmap);
            PlanTravel planner=new PlanTravel(id,data.getStringExtra(EditPlannerActivity.EXTRA_EDIT_NAME_PLANNER),
                    data.getStringExtra(EditPlannerActivity.EXTRA_EDIT_DATE),
                    data.getStringExtra(EditPlannerActivity.EXTRA_EDIT_TIME),stringBitmap
                   );


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
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
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

            imageinCamera=itemView.findViewById(R.id.picture);
            imageinCamera.setImageResource(R.drawable.ic_image_black_24dp);


        }


        public void bind(PlanTravel plan){

            name.setText(plan.getName());
            String st = plan.getBitmap();
            if(st!=null){
                Log.d("MainActivity","bitmap details  "+st);
                imageinCamera.setImageBitmap(StringToBitMap(st));
            }
            else {
                imageinCamera.setImageResource(R.drawable.ic_image_black_24dp);
            }
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    palnViewModel.delete(plan);
                }
            });


        }



    }

    private class DetailsAdapter extends RecyclerView.Adapter<DetailsHolder>{

        private List<PlanTravel> plans;
        @NonNull
        @Override
        public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DetailsHolder(getLayoutInflater(),parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailsHolder holder, int position) {
            if(plans!=null){
                PlanTravel plan=plans.get(position);
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

        void setPlanners(List<PlanTravel> plans){
            this.plans=plans;
            notifyDataSetChanged();
        }
    }
}
