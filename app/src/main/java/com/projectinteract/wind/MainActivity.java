package com.projectinteract.wind;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsRecyclerAdapter adapter;
    private List<Article> articleList;

    private LinearProgressIndicator progressIndicator;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MaterialButton btnTop, btnBusiness, btnSports, btnTech, btnHealth;
    private SearchView searchView;

    private String currentCategory = "top";
    private boolean isSearching = false;
    private String lastSearchQuery = "";

    private static final String API_KEY =
            "pub_2f0c266622044f88919e109cab971437";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchView = findViewById(R.id.search_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progress_bar);

        btnTop = findViewById(R.id.btn_top);
        btnBusiness = findViewById(R.id.btn_business);
        btnSports = findViewById(R.id.btn_sports);
        btnTech = findViewById(R.id.btn_tech);
        btnHealth = findViewById(R.id.btn_health);

        articleList = new ArrayList<>();
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    isSearching = true;
                    lastSearchQuery = query;
                    searchNews(query);
                }
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty() && isSearching) {
                    isSearching = false;
                    loadCategory(currentCategory);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            isSearching = false;
            loadCategory(currentCategory);
            return false;
        });


        btnTop.setOnClickListener(v -> switchCategory("top"));
        btnBusiness.setOnClickListener(v -> switchCategory("business"));
        btnSports.setOnClickListener(v -> switchCategory("sports"));
        btnTech.setOnClickListener(v -> switchCategory("technology"));
        btnHealth.setOnClickListener(v -> switchCategory("health"));


        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (isSearching) {
                searchNews(lastSearchQuery);
            } else {
                loadCategory(currentCategory);
            }
        });


        loadCategory(currentCategory);
    }

    private void switchCategory(String category) {
        isSearching = false;
        currentCategory = category;
        loadCategory(category);
    }



    private void loadCategory(String category) {

        Log.i("CATEGORY", "Loading: " + category);
        progressIndicator.setVisibility(View.VISIBLE);

        NewsApiService api = RetrofitClient.getInstance()
                .create(NewsApiService.class);

        Call<JsonObject> call = api.getNewsByCategory(
                API_KEY,
                category,
                "en,hi"
        );

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call,
                                   Response<JsonObject> response) {

                progressIndicator.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (!response.isSuccessful() || response.body() == null) return;

                List<Article> list = new ArrayList<>();
                JsonArray results = response.body().getAsJsonArray("results");

                for (int i = 0; i < results.size(); i++) {
                    JsonObject obj = results.get(i).getAsJsonObject();
                    Article article = new Article();

                    if (obj.has("title") && !obj.get("title").isJsonNull())
                        article.setTitle(obj.get("title").getAsString());

                    if (obj.has("image_url") && !obj.get("image_url").isJsonNull())
                        article.setImage_url(obj.get("image_url").getAsString());

                    if (obj.has("source_id") && !obj.get("source_id").isJsonNull())
                        article.setSource_id(obj.get("source_id").getAsString());


                    if (obj.has("link") && !obj.get("link").isJsonNull())
                        article.setLink(obj.get("link").getAsString());

                    list.add(article);
                }

                adapter.updateData(list);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressIndicator.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Log.e("CATEGORY", "Failed", t);
            }
        });
    }



    private void searchNews(String query) {

        Log.i("SEARCH", "Query: " + query);
        progressIndicator.setVisibility(View.VISIBLE);

        NewsApiService api = RetrofitClient.getInstance()
                .create(NewsApiService.class);

        Call<JsonObject> call = api.searchNews(
                API_KEY,
                query,
                "en,hi"
        );

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call,
                                   Response<JsonObject> response) {

                progressIndicator.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (!response.isSuccessful() || response.body() == null) return;

                List<Article> list = new ArrayList<>();
                JsonArray results = response.body().getAsJsonArray("results");

                if (results.size() == 0) {
                    Log.i("SEARCH", "No results found");
                }

                for (int i = 0; i < results.size(); i++) {
                    JsonObject obj = results.get(i).getAsJsonObject();
                    Article article = new Article();

                    if (obj.has("title") && !obj.get("title").isJsonNull())
                        article.setTitle(obj.get("title").getAsString());

                    if (obj.has("image_url") && !obj.get("image_url").isJsonNull())
                        article.setImage_url(obj.get("image_url").getAsString());

                    if (obj.has("source_id") && !obj.get("source_id").isJsonNull())
                        article.setSource_id(obj.get("source_id").getAsString());

                    if (obj.has("link") && !obj.get("link").isJsonNull())
                        article.setLink(obj.get("link").getAsString());

                    list.add(article);
                }

                adapter.updateData(list);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressIndicator.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Log.e("SEARCH", "Failed", t);
            }
        });
    }
}
