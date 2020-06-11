package com.example.restclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restclient.entity.Good;
import com.example.restclient.entity.Sale;
import com.example.restclient.entity.Tag;
import com.example.restclient.entity.Warehouse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    APIInterface apiInterface;

    private Good globalGood = new Good();

    private int saleNumber = -1;
    private int wr1Number = -1;
    private int wr2Number = -1;

    String token;

    EditText nameEditText;
    EditText priorityEditText;
    EditText WR1CountEditText;
    EditText WR2CountEditText;
    EditText saleCountEditText;
    EditText saleDateEditText;

    private static final String DATE_RESPONSE_FORMAT = "yyyy.MM.dd";

    View view;

    @Override
    protected void onResume() {
        super.onResume();
        initNumber();
    }

    void initNumber() {
        saleNumber = -1;
        wr1Number = -1;
        wr2Number = -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        long id = intent.getLongExtra("id", -1);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (id != -1) {
            getAndShowGood(id);
        }

        nameEditText = (EditText) findViewById(R.id.name);
        priorityEditText = (EditText) findViewById(R.id.priopity);
        WR1CountEditText = (EditText) findViewById(R.id.WR1_count);
        WR2CountEditText = (EditText) findViewById(R.id.WR2_count);
        saleCountEditText = (EditText) findViewById(R.id.sale_count);
        saleDateEditText = (EditText) findViewById(R.id.sale_date);


        Button addChangeGoodButton = (Button) findViewById(R.id.add_change_good);
        addChangeGoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    if (nameEditText.getText().toString().trim().equals("") &&
                            priorityEditText.getText().toString().trim().equals("")) {
                        return;
                    }

                    if (!nameEditText.getText().toString().trim().equals(""))  {
                        globalGood.setName(nameEditText.getText().toString());
                    }

                    if (!priorityEditText.getText().toString().trim().equals(""))  {
                        globalGood.setPriority(Float.parseFloat(priorityEditText.
                                getText().toString()));
                    }
                } catch (NumberFormatException e) {
                    priorityEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Please enter number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                showGood(globalGood);
            }
        });

        Button addSaleButton = (Button) findViewById(R.id.add_sale);
        addSaleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Sale> sales = globalGood.getSales();

                DateFormat utcFormat = new SimpleDateFormat(DATE_RESPONSE_FORMAT, Locale.ENGLISH);

                try {
                    String currentDate;
                    if (saleDateEditText.getText().toString().trim().equals("")) {
                        currentDate = utcFormat.format(new Date());
//                        System.out.println(newDate);
//                        currentDate = utcFormat.parse ( newDate );
                    } else {
                        Date date = utcFormat.parse(saleDateEditText.getText().toString());
                        currentDate = utcFormat.format(date);
                    }

                    sales.add(new Sale(Integer.parseInt(saleCountEditText.getText().toString()),
                            currentDate));
                } catch (ParseException e) {
                    saleDateEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Please provide correct date " +
                            "yyyy.MM.dd/HH:mm", Toast.LENGTH_SHORT).show();
                    return;
                } catch (NumberFormatException e) {
                    saleCountEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Please enter number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                globalGood.setSales(sales);
                showGood(globalGood);
            }
        });

        Button addWR1Button = (Button) findViewById(R.id.add_WR1);
        addWR1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Warehouse> warehouses = globalGood.getWarehouse1Goods();

                try {
                    warehouses.add(new Warehouse(Integer.parseInt(WR1CountEditText.getText()
                            .toString())));
                } catch (NumberFormatException e) {
                    WR1CountEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Please enter number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                globalGood.setWarehouse1Goods(warehouses);
                showGood(globalGood);
            }
        });

        Button addWR2Button = (Button) findViewById(R.id.add_WR2);
        addWR2Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Warehouse> warehouses = globalGood.getWarehouse2Goods();

                try {
                    warehouses.add(new Warehouse(Integer.parseInt(WR2CountEditText.getText()
                            .toString())));
                } catch (NumberFormatException e) {
                    WR2CountEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Please enter number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                globalGood.setWarehouse2Goods(warehouses);
                showGood(globalGood);
            }
        });

        Button changeSaleButton = (Button) findViewById(R.id.change_sale);
        changeSaleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Sale> sales = globalGood.getSales();

                if (saleNumber == -1 || sales.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please choose any " +
                            "sale from table", Toast.LENGTH_SHORT).show();
                    return;
                }

                DateFormat utcFormat = new SimpleDateFormat(DATE_RESPONSE_FORMAT, Locale.ENGLISH);

                String currentDate;
                int count;
                try {
                    Date date = utcFormat.parse(saleDateEditText.getText().toString());
                    currentDate = utcFormat.format(date);
                    count = Integer.parseInt(saleCountEditText.getText().toString());
                } catch (ParseException e) {
                    saleDateEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Please provide correct date" +
                            " yyyy.MM.dd/HH:mm", Toast.LENGTH_SHORT).show();
                    return;
                } catch (NumberFormatException e) {
                    saleCountEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Please enter number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Sale sale = sales.get(saleNumber);
                sale.setGoodCount(count);
                sale.setCreateDate(currentDate);

                sales.set(saleNumber, sale);

                globalGood.setSales(sales);
                showGood(globalGood);
            }
        });

        Button changeWR1Button = (Button) findViewById(R.id.change_WR1);
        changeWR1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Warehouse> warehouses = globalGood.getWarehouse1Goods();

                if (wr1Number == -1 || warehouses.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please choose any " +
                            "Wr1 from table", Toast.LENGTH_SHORT).show();
                    return;
                }

                ListIterator<Warehouse> iterator = warehouses.listIterator();

                int count;
                try {
                    count = Integer.parseInt(WR1CountEditText.getText().toString());
                } catch (NumberFormatException e) {
                    WR1CountEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Please enter number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Warehouse warehouse = warehouses.get(wr1Number);
                warehouse.setGoodCount(count);

                warehouses.set(wr1Number, warehouse);

                globalGood.setWarehouse1Goods(warehouses);
                showGood(globalGood);
            }
        });

        Button changeWR2Button = (Button) findViewById(R.id.change_WR2);
        changeWR2Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Warehouse> warehouses = globalGood.getWarehouse2Goods();

                if (wr2Number == -1 || warehouses.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please choose any" +
                            " Wr2 from table", Toast.LENGTH_SHORT).show();
                    return;
                }

                int count;
                try {
                    count = Integer.parseInt(WR2CountEditText.getText().toString());
                } catch (NumberFormatException e) {
                    WR2CountEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Please enter number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Warehouse warehouse = warehouses.get(wr2Number);
                warehouse.setGoodCount(count);

                warehouses.set(wr2Number, warehouse);

                globalGood.setWarehouse2Goods(warehouses);
                showGood(globalGood);
            }
        });

        Button deleteSaleButton = (Button) findViewById(R.id.delete_sale);
        deleteSaleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Sale> sales = globalGood.getSales();

                if (saleNumber == -1 || sales.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please choose any" +
                            " sale from table", Toast.LENGTH_SHORT).show();
                    return;
                }

                sales.remove(saleNumber);

                globalGood.setSales(sales);
                showGood(globalGood);
                initNumber();
            }
        });

        Button deleteWR1Button = (Button) findViewById(R.id.delete_WR1);
        deleteWR1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Warehouse> warehouses = globalGood.getWarehouse1Goods();

                if (wr1Number == -1 || warehouses.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please choose any" +
                            " WR1 count from table", Toast.LENGTH_SHORT).show();
                    return;
                }

                warehouses.remove(wr1Number);
                globalGood.setWarehouse1Goods(warehouses);
                showGood(globalGood);
                initNumber();
            }
        });

        Button deleteWR2Button = (Button) findViewById(R.id.delete_WR2);
        deleteWR2Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Warehouse> warehouses = globalGood.getWarehouse2Goods();

                if (wr2Number == -1 || warehouses.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please choose any" +
                            " WR2 count from table", Toast.LENGTH_SHORT).show();
                    return;
                }

                warehouses.remove(wr2Number);

                globalGood.setWarehouse2Goods(warehouses);
                showGood(globalGood);
                initNumber();
            }
        });

        Button saveGood = (Button) findViewById(R.id.saveGood);
        saveGood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (globalGood.getName().equals("") || globalGood.getPriority() == -1) {
                    Toast.makeText(getApplicationContext(), "Please enter name and priority",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                addGood(globalGood);
                Intent intent = new Intent(FormActivity.this, RecyclerActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        Button deleteGood = (Button) findViewById(R.id.deleteGood);
        deleteGood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (globalGood.getId() == -1) {
                    Toast.makeText(getApplicationContext(), "This good was not saved",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                deleteGood(globalGood.getId());
                Intent intent = new Intent(FormActivity.this, RecyclerActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
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

    private void addGood(Good good) {
        Call<Good> call = apiInterface.addGood(good, token);
        call.enqueue(new Callback<Good>() {
            @Override
            public void onResponse(Call<Good> call, Response<Good> response) {

                if (response.isSuccessful()) {
                    Good good = response.body();
                    //globalGood = good;

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

    public void getAndShowGood(long id) {
        Call<Good> call = apiInterface.getGood(id, token);
        call.enqueue(new Callback<Good>() {
            @Override
            public void onResponse(Call<Good> call, Response<Good> response) {

                if (response.isSuccessful()) {
                    Good good = response.body();
                    globalGood = good;

                    showGood(good);

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

    public void showGood(Good good) {
        int countSale = good.getSales().size();
        int countWR1 = good.getWarehouse1Goods().size();
        int countWR2 = good.getWarehouse2Goods().size();

        int[] tab = {countSale, countWR1, countWR2};
        int max = Arrays.stream(tab).max().getAsInt();

        if (good.getName() != null && !good.getName().isEmpty()) {

            TableLayout tableLayout = (TableLayout) findViewById(R.id.view_table);

            tableLayout.removeAllViews();
            tableLayout.setClickable(true);
            LayoutInflater inflater = LayoutInflater.from(FormActivity.this);
            TableRow tr = (TableRow) inflater.inflate(R.layout.activity_table_row, null);

            TextView tv = (TextView) tr.findViewById(R.id.name_table_tow);
            tv.setText(good.getName());

            // tr.addView(tv);

            tv = (TextView) tr.findViewById(R.id.priopity_table_tow);
            tv.setText(Float.toString(good.getPriority()));

            //tr.addView(tv);

            if (max == 0) {
                tableLayout.addView(tr);
                return;
            }

            int count = 0;
            while (count < max) {

                if (countSale > 0) {
                    tv = (TextView) tr.findViewById(R.id.count_sale_table_tow);
                    tr.setTag(new Tag(count, Tag.Type.SALE));
                    tv.setText(Integer.toString(good.getSales().get(count).getGoodCount()));
                    //   tr.addView(tv);

                    tv = (TextView) tr.findViewById(R.id.date_sale_table_tow);
                    tr.setTag(new Tag(count, Tag.Type.SALE));
                    tv.setText(good.getSales().get(count).getCreateDate().toString());
                    // tr.addView(tv);
                }

                if (countWR1 > 0) {
                    tv = (TextView) tr.findViewById(R.id.count_WR1_table_tow);
                    tr.setTag(new Tag(count, Tag.Type.WR1));
                    tv.setText(Integer.toString(good.getWarehouse1Goods().
                            get(count).getGoodCount()));
                    // tr.addView(tv);
                }

                if (countWR2 > 0) {
                    tv = (TextView) tr.findViewById(R.id.count_WR2_table_tow);
                    tr.setTag(new Tag(count, Tag.Type.WR2));
                    tv.setText(Integer.toString(good.getWarehouse2Goods().
                            get(count).getGoodCount()));
                    // tr.addView(tv);
                }

                tr.setOnClickListener(FormActivity.this);
                tableLayout.addView(tr, count);
                // tableLayout.removeView(tr);

                tr = (TableRow) inflater.inflate(R.layout.activity_table_row, null);

                countSale--;
                countWR1--;
                countWR2--;
                count++;
            }
        }

    }

    public void returnBackgroundColor() {
        if (view != null) {
            view.setBackgroundResource(R.color.background);
        }
    }

    @Override
    public void onClick(View v) {
        Tag tag = (Tag) v.getTag();

        if (tag == null) {
            return;
        }

        returnBackgroundColor();
        v.setBackgroundResource(R.color.viewClick);

        view = v;

        int count = tag.getCount();
        Tag.Type type = tag.getType();

        System.out.println("count " + count);

        if (globalGood.getSales().size() >= count + 1) {
            Sale sale = globalGood.getSales().get(count);
            saleNumber = count;

            saleCountEditText.setText(Integer.toString(sale.getGoodCount()));
            saleDateEditText.setText(sale.getCreateDate().toString());
        }

        if (globalGood.getWarehouse1Goods().size() >= count + 1) {
            Warehouse warehouse = globalGood.getWarehouse1Goods().get(count);
            wr1Number = count;

            WR1CountEditText.setText(Integer.toString(warehouse.getGoodCount()));
        }

        if (globalGood.getWarehouse2Goods().size() >= count + 1) {
            Warehouse warehouse = globalGood.getWarehouse2Goods().get(count);
            wr2Number = count;

            WR2CountEditText.setText(Integer.toString(warehouse.getGoodCount()));
        }
    }
}
