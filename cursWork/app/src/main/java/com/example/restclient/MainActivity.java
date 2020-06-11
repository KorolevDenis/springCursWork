package com.example.restclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restclient.entity.JWTResponce;
import com.example.restclient.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    APIInterface apiInterface;

    EditText username;
    EditText password;

    Button loginButton;
    Button registerButton;
    Button clearButton;
    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                User user = new User(username.getText().toString(), password.getText().toString());
                Call<JWTResponce> call = apiInterface.login(user);

                call.enqueue(new Callback<JWTResponce>() {

                    @Override
                    public void onResponse(Call<JWTResponce> call, Response<JWTResponce> response) {

                        String token = "";
                        if (response.isSuccessful()) {
                            JWTResponce jwtResponce = (JWTResponce) response.body();
                            token  = "Bearer " + jwtResponce.getJwttoken();
                            Toast.makeText(getApplicationContext(), "login ",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            APIError error = ErrorUtils.parseError(response);
                            Toast.makeText(getApplicationContext(), error.getMessage() ,
                                    Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }

                        if (token.isEmpty()) {
                            return;
                        }

                        Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure (Call <JWTResponce> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                User user = new User(username.getText().toString(), password.getText().toString());
                Call<User> call = apiInterface.register(user);

                call.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.isSuccessful()) {
                            User user = response.body();
                            Toast.makeText(getApplicationContext(), "You successfully registered",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            APIError error = ErrorUtils.parseError(response);
                            Toast.makeText(getApplicationContext(), error.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                    }

                    @Override
                    public void onFailure (Call <User> call, Throwable t) {

                        call.cancel();
                    }
                });
            }
        });

        clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                username.setText("");
                password.setText("");
            }
        });

        exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}
