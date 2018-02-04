package com.antonioleiva.mvpexample.retrofit;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018-02-03.
 */

public class RetrofitActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Observable<List<Repo>> repos = service.listRepos("octocat");
        repos.observeOn()

    }
}
