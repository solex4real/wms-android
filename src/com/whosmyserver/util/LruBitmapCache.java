package com.whosmyserver.util;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache {
	
	 public static int getDefaultLruCacheSize() {
	        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
	        final int cacheSize = maxMemory / 8;
	 
	        return cacheSize;
	    }

	public LruBitmapCache(int maxSize) {
		super(maxSize);
		// TODO Auto-generated constructor stub
	}

	public LruBitmapCache() {
		this(getDefaultLruCacheSize());
    }
	
	@Override
	public Bitmap getBitmap(String url) {
		// TODO Auto-generated method stub
		return get(url);
	}

	
	
	@Override
	protected int sizeOf(String key, Bitmap value) {
		// TODO Auto-generated method stub
		return value.getRowBytes() * value.getHeight() / 1024;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		// TODO Auto-generated method stub
		put(url, bitmap);
	}

}
