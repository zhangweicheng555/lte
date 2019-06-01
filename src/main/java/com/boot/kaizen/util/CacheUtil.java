package com.boot.kaizen.util;

import org.springframework.cache.ehcache.EhCacheCacheManager;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:37:09
 */
public class CacheUtil {
	
	public static Cache getCacheManager(String cacheKey) {
		//获取EhCacheCacheManager类
		EhCacheCacheManager cacheCacheManager=SpringUtil.getApplicationContext().getBean(EhCacheCacheManager.class);
		//获取CacheManager类
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		//获取Cache
		Cache cache = cacheManager.getCache(cacheKey);
	   // cache.put(new Element("Hello", "123"));
		//System.out.println(cache.get("Hello").getObjectValue());
		return cache;
	}

}
