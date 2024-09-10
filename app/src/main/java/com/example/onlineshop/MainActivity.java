package com.example.onlineshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlineshop.Models.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private FloatingActionButton btnCart;
    private RequestQueue requestQueue;
    private static final String API_URL = "http://ip-adress:8080/products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(adapter);

        btnCart = findViewById(R.id.fab_cart);
        requestQueue = Volley.newRequestQueue(this);

        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        });

//        // fake data indtil api er klar
//        productList.add(new Product(1, "Produkt 1", "Beskrivelse af Produkt 1", 100, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(2, "Produkt 2", "Beskrivelse af Produkt 2", 200, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(3, "Produkt 3", "Beskrivelse af Produkt 3", 300, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(4, "Produkt 4", "Beskrivelse af Produkt 4", 400, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(5, "Produkt 5", "Beskrivelse af Produkt 5", 500, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(6, "Produkt 6", "Beskrivelse af Produkt 6", 600, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(7, "Produkt 7", "Beskrivelse af Produkt 7", 700, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(8, "Produkt 8", "Beskrivelse af Produkt 8", 3000, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(9, "Produkt 9", "Beskrivelse af Produkt 9", 770, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(10, "Produkt 10", "Beskrivelse af Produkt 10", 880, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(11, "Produkt 11", "Beskrivelse af Produkt 11", 990, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//        productList.add(new Product(12, "Produkt 12", "Beskrivelse af Produkt 12", 110, "https://blog.collinsdictionary.com/wp-content/uploads/sites/39/2023/03/cloth_2057252297.jpg"));
//


        getProducts();
    }

    private void getProducts() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                API_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("MainActivity", "Response: " + response.toString()); // Log the response
                        try {
                            productList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject productObject = response.getJSONObject(i);
                                int id = productObject.getInt("id");
                                String name = productObject.getString("name");
                                String description = productObject.getString("description");
                                int price = productObject.getInt("price");
                                String objectImage = productObject.getString("objectImage");

                                Product product = new Product(id, name, description, price, objectImage);
                                productList.add(product);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e("MainActivity", "JSON parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", "Volley error: " + error.getMessage());
                    }
                }
        );

        // Add request to queue
        if (requestQueue != null) {
            requestQueue.add(jsonArrayRequest);
        } else {
            Log.e("MainActivity", "RequestQueue is null");
        }
    }


}