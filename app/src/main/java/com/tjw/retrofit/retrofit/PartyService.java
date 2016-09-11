package com.tjw.retrofit.retrofit;

import com.tjw.retrofit.StudyMaterial;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * ^-^
 * Created by tang-jw on 9/8.
 */
public interface PartyService {
	
	//lianyi/MtsNews/getNews.do
	@GET("newslist")
	Call<StudyMaterial> getStudyMaterials();
	
}
