package pl.edu.pb.travelplannerapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class MainActivity extends AppCompatActivity {

    private TravelViewModel travelViewModel;
    private Travel travell;
    public int size;

    public static final int NEW_TRAVEL_ACTIVITY_REQUEST_CODE=1;
    public static final int EDIT_TRAVEL_ACTIVITY_REQUEST_CODE=2;
    public static final int DETAILS_BOOK_ACTIVITY_REQUEST_CODE=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final TravelAdapter adapter=new TravelAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        travelViewModel= ViewModelProviders.of(this).get(TravelViewModel.class);

        travelViewModel.findAll().observe(this, new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                adapter.setBooks(travels);
                Log.d("MainActivity","trav  "+travels);
            }
        });


        FloatingActionButton addBookButton = findViewById(R.id.add_button);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, EditTravelActivity.class);

                startActivityForResult(intent,NEW_TRAVEL_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==NEW_TRAVEL_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
           Travel travel=new Travel(data.getStringExtra(EditTravelActivity.EXTRA_EDIT_NAME),
                    data.getStringExtra(EditTravelActivity.EXTRA_EDIT_PLACE),
                   data.getStringExtra(EditTravelActivity.EXTRA_EDIT_START),
                   data.getStringExtra(EditTravelActivity.EXTRA_EDIT_END));
            travelViewModel.insert(travel);
            Snackbar.make(findViewById(R.id.coordinator_layout),getString(R.string.travel_added),
                    Snackbar.LENGTH_LONG).show();
        }else if(requestCode==EDIT_TRAVEL_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK) {
            travell.setName(data.getStringExtra(EditTravelActivity.EXTRA_EDIT_NAME));
            travell.setPlace(data.getStringExtra(EditTravelActivity.EXTRA_EDIT_PLACE));
            travell.setCal1(data.getStringExtra(EditTravelActivity.EXTRA_EDIT_START));
            travell.setCal2(data.getStringExtra(EditTravelActivity.EXTRA_EDIT_END));

            travelViewModel.update(travell);
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
            ).show();
        }
    }




    private class TravelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView bookNameTextView;
//        private TextView bookPlaceTextView;
//        private TextView bookDataStartTextView;
//        private TextView bookDataEndTextView;
        private ImageView deleteImageView;
        private ImageView editImageView;
        private Travel travel;

        public TravelHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.travel_list_item,parent,false));
            itemView.setOnClickListener(this);

            bookNameTextView=itemView.findViewById(R.id.travel_name);
          //  bookPlaceTextView=itemView.findViewById(R.id.travel_place);

            deleteImageView=itemView.findViewById(R.id.delete);
            editImageView=itemView.findViewById(R.id.edit);

            deleteImageView.setImageResource(R.drawable.ic_delete_black_24dp);
            editImageView.setImageResource(R.drawable.ic_edit_black_24dp);

        }

        public void bind(Travel travel){
            this.travel=travel;
            bookNameTextView.setText(travel.getName());
//            bookPlaceTextView.setText(travel.getPlace());
//            bookDataStartTextView.setText(travel.getCal1());
//            bookDataEndTextView.setText(travel.getCal2());

            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    travell=travel;
                    Intent intent=new Intent(MainActivity.this, EditTravelActivity.class);
                    intent.putExtra(EditTravelActivity.EXTRA_EDIT_NAME, travell.getName());
                    intent.putExtra(EditTravelActivity.EXTRA_EDIT_PLACE, travell.getPlace());
                    intent.putExtra(EditTravelActivity.EXTRA_EDIT_START,travell.getCal1());
                    intent.putExtra(EditTravelActivity.EXTRA_EDIT_END,travell.getCal2());

                    startActivityForResult(intent,EDIT_TRAVEL_ACTIVITY_REQUEST_CODE);
                }
            });

            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    travelViewModel.delete(travell);
                }
            });
        }

        @Override
        public void onClick(View v) {
            travell=travel;
            Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_ID_TRAVEL,travell.getId());
            Log.d("MainActivity","tr "+travell.getName());
            startActivityForResult(intent,DETAILS_BOOK_ACTIVITY_REQUEST_CODE);
        }


    }

    private class TravelAdapter extends RecyclerView.Adapter<TravelHolder>{

        private List<Travel> travels;


        @Nullable
        @Override
        public TravelHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType){
            return new TravelHolder(getLayoutInflater(),parent);
        }

        @Override
        public void onBindViewHolder(@Nullable TravelHolder holder,int position){
            if(travels!=null){
                Travel travel=travels.get(position);
                holder.bind(travel);
            }else {
                Log.d("MainActivity", "No travels");
            }
        }

        @Override
        public int getItemCount() {
            if(travels!=null){
                return  travels.size();
            }else {
                return 0;
            }
        }

        void setBooks(List<Travel> travels){
            this.travels=travels;
            notifyDataSetChanged();
        }

    }


}
