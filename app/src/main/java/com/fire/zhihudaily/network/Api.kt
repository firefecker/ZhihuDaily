package com.fire.zhihudaily.network

import com.fire.zhihudaily.entity.CommentsCount
import com.fire.zhihudaily.entity.InternetNews
import com.fire.zhihudaily.entity.LatestNews
import com.fire.zhihudaily.entity.LisSections
import com.fire.zhihudaily.entity.NewsDetail
import com.fire.zhihudaily.entity.ShortComments
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
interface Api {

    @GET("news/latest")
    fun latestNews(): Observable<Response<LatestNews>>

    @GET("news/before/{time}")
    fun beforeNews(@Path("time") time : String): Observable<Response<LatestNews>>

    @GET("theme/{item}")
    fun otherNews(@Path("item") item : String): Observable<Response<InternetNews>>

    @GET("news/{id}")
    fun newsDetail(@Path("id") id : String): Observable<Response<NewsDetail>>

    @GET("story-extra/{id}")
    fun getCommetsCount(@Path("id") id : String): Observable<Response<CommentsCount>>

    @GET("section/{section}/before/{time}")
    fun getSection(@Path("section") id : String,@Path("time") time : String): Observable<Response<LisSections>>

    @GET("story/{id}/short-comments")
    fun getShortComments(@Path("id") id : String): Observable<Response<ShortComments>>

    @GET("story/{id}/long-comments")
    fun getLongComments(@Path("id") id : String): Observable<Response<ShortComments>>

}