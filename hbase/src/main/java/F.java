import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Title: F
 * @Author wangtao
 * @Date 2023/9/1 16:58
 * @description:
 */
public class F {


    public static void main(String[] args) throws ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, String>() {
                            @Override
                            public String load(String String) {
                                System.out.println("load..............");
                                return "xxxxxxxxxx";
                            }
                        }
                );

        cache.put("a","A");
        cache.put("b","B");
        System.out.println(cache.get("a"));
        System.out.println(cache.get("b"));
        System.out.println(cache.get("c"));
    }

}
