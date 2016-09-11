package com.tjw.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tjw.retrofit.retrofit.PartyService;
import com.tjw.retrofit.retrofit.converter.GsonConverterFactory;
import com.tjw.retrofit.retrofit.CacheControlInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
	
	private OkHttpClient mOkHttpClient;
	private TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTextView = (TextView) findViewById(R.id.textView);
		
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
		mOkHttpClient = new OkHttpClient().newBuilder()
				.addInterceptor(loggingInterceptor)
				.addInterceptor(new CacheControlInterceptor())
				.connectTimeout(5000L, TimeUnit.MILLISECONDS)
				.readTimeout(5000L, TimeUnit.MILLISECONDS)
				.cache(new Cache(new File(getCacheDir().getAbsolutePath()), 10 * 1024 * 1024))
				.build();
	}
	
	public void click(View view) {
		retrofit();
	}
	
	private void retrofit() {
		//http://118.145.26.214:8086/lianyi/MtsNews/getNews.do
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://192.168.199.185:8080/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(mOkHttpClient)
				.build();
		
		retrofit.create(PartyService.class)
				.getStudyMaterials()
				.enqueue(new Callback<StudyMaterial>() {
					@Override
					public void onResponse(Call<StudyMaterial> call, Response<StudyMaterial> response) {
						System.out.println(Thread.currentThread().getName());
						mTextView.setText(response.body().getData().get(0).getName());
					}
					
					@Override
					public void onFailure(Call<StudyMaterial> call, Throwable t) {
						System.out.println(t.toString());
						MyToast.show(App.mApp, t.getMessage());
					}
				});
	}
	
}
