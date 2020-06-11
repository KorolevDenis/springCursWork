package com.example.restclient;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restclient.controller.RecyclerAdapter;
import com.example.restclient.entity.Good;
import com.example.restclient.entity.GoodRecord;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private Button newGoodButton;
    private Good globalGood;

    private static final String DATE_RESPONSE_FORMAT="yyyy.MM.dd";

    String token;
    APIInterface apiInterface;

    @Override
    protected void onResume() {
        super.onResume();
        getGoods();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerActivity.this));
        recyclerAdapter = new RecyclerAdapter(cardsToRecords(new ArrayList<>()));
        recyclerView.setAdapter(recyclerAdapter);

        getGoods();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodRecord record) {
                Intent intent = new Intent(RecyclerActivity.this, FormActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("id", record.getId());
                startActivity(intent);
            }
        });

        newGoodButton = (Button) findViewById(R.id.new_good_button);
        newGoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerActivity.this, FormActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("id", "-1");
                startActivity(intent);
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            final GoodRecord record = recyclerAdapter.remove(position);
            getGood(record.getId());
            deleteGood(record.getId());
            Snackbar.make(recyclerView, record.getName(), Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recyclerAdapter.addItem(position, record);
                            addGood(globalGood);
                            recyclerAdapter.notifyItemInserted(position);
                        }
                    }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY,
                    actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.
                            getColor(RecyclerActivity.this, R.color.colorDelete))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void addGood(Good good) {
        Call<Good> call = apiInterface.addGood(good, token);
        call.enqueue(new Callback<Good>() {
            @Override
            public void onResponse(Call<Good> call, Response<Good> response) {

                if (response.isSuccessful()) {

                } else {
                    APIError error = ErrorUtils.parseError(response);
                    Toast.makeText(getApplicationContext(), error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<Good> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getGood(long id) {
        Call<Good> call = apiInterface.getGood(id, token);
        call.enqueue(new Callback<Good>() {
            @Override
            public void onResponse(Call<Good> call, Response<Good> response) {
                if (response.isSuccessful()) {
                    globalGood = (Good) response.body();

                } else {
                    APIError error = ErrorUtils.parseError(response);
                    Toast.makeText(getApplicationContext(), error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<Good> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getGoods() {

        Call<List<Good>> call = apiInterface.getGoods(token);
        call.enqueue(new Callback<List<Good>>() {
            @Override
            public void onResponse(Call<List<Good>> call, Response<List<Good>> response) {
                if (response.isSuccessful()) {

                    List<Good> goods = new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++) {
                        Good good = (Good)response.body().get(i);
                        goods.add(good);
                    }
                    recyclerAdapter.setItems(cardsToRecords(goods));

                } else {
                    APIError error = ErrorUtils.parseError(response);
                    Toast.makeText(getApplicationContext(), "error.getMessage()",
                            Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<List<Good>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void deleteGood(long id) {
        Call<Good> call = apiInterface.deleteGood(id, token);
        call.enqueue(new Callback<Good>() {
            @Override
            public void onResponse(Call<Good> call, Response<Good> response) {
                if (response.isSuccessful()) {

                } else {
                    APIError error = ErrorUtils.parseError(response);
                    Toast.makeText(getApplicationContext(), error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<Good> call, Throwable t) {
                call.cancel();
            }
        });
    }


    private List<GoodRecord> cardsToRecords(List<Good> goods) {
        List<GoodRecord> records = new ArrayList<>();
        for (Good good : goods) {
            records.add(new GoodRecord(good.getId(), good.getName(), good.getPriority()));
        }
        return records;
    }

}