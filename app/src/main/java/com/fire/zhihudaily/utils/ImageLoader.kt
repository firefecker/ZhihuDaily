package com.fire.zhihudaily.utils

import android.content.Context
import com.bumptech.glide.BitmapTypeRequest
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.fire.zhihudaily.R
import com.fire.zhihudaily.base.BaseApplication

/**
 * Created by fire on 2017/8/24.
 */

class ImageLoader private constructor(context: Context) {
    private var mRequestManager: RequestManager = Glide.with(BaseApplication.getInstance())

    companion object {
        private var sImageLoader: ImageLoader? = null
        fun getInstance(context: Context): ImageLoader? {
            if (sImageLoader == null) {
                synchronized(ImageLoader::class.java) {
                    if (sImageLoader == null) {
                        sImageLoader = ImageLoader(context)
                    }
                }
            }
            return sImageLoader
        }

        fun <T> loadImage(model: T): DrawableRequestBuilder<*> {
            if (sImageLoader!!.mRequestManager == null) {
                sImageLoader!!.mRequestManager =  Glide.with(BaseApplication.getInstance())
            }
            return sImageLoader!!.mRequestManager.load(model)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .centerCrop()
                    .dontAnimate()
        }

        fun loadImage(model: String): BitmapTypeRequest<*> {
            if (sImageLoader!!.mRequestManager == null) {
                sImageLoader!!.mRequestManager =  Glide.with(BaseApplication.getInstance())
            }
            return sImageLoader!!.mRequestManager.load(model)
                    .asBitmap()
        }

        fun <T> loadImage(model: T, resourceId: Int): DrawableRequestBuilder<*> {
            if (sImageLoader!!.mRequestManager == null) {
                sImageLoader!!.mRequestManager =  Glide.with(BaseApplication.getInstance())
            }
            return sImageLoader!!.mRequestManager.load(model)
                    .placeholder(resourceId)
                    .error(resourceId)
                    .dontAnimate()
        }


        fun <T> loadImagePic(model: T): DrawableRequestBuilder<*> {
            if (sImageLoader!!.mRequestManager == null) {
                sImageLoader!!.mRequestManager =  Glide.with(BaseApplication.getInstance())
            }
            return sImageLoader!!.mRequestManager.load(model)
                    .dontAnimate()
        }
    }


}
