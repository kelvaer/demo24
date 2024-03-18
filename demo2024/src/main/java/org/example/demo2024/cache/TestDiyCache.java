package org.example.demo2024.cache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import org.example.demo2024.convert.UserConverter;
import org.example.demo2024.dto.UserDTO;
import org.example.demo2024.entity.SysUser;
import org.example.demo2024.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;

/**
 *
 *注意：在jetcache 2.7 版本CreateCache注解已经废弃，请改用CacheManager.getOrCreateCache(QuickConfig)*
 *
 * @program: demo2024
 * @description: 测试jetcache api
 * @author: 作者名
 * @create: 2024/03/18
 */
@Component
public class TestDiyCache {

    @Autowired
    private CacheManager cacheManager;

    private Cache<String, UserDTO> userCache;

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private UserConverter userConverter;
    @PostConstruct
    public void init() {
        QuickConfig qc = QuickConfig.newBuilder("userCache")
                .expire(Duration.ofSeconds(100))
//                .cacheType(CacheType.BOTH) // two level cache
                .cacheType(CacheType.REMOTE)
//                .syncLocal(true)
                // invalidate local cache in all jvm process after update
                .build();
        userCache = cacheManager.getOrCreateCache(qc);
    }



    public  UserDTO test111(Long id){
       return userCache.computeIfAbsent(String.valueOf(id),(aLong)->{
           SysUser sysUser = userMapper.selectOneById(aLong);
           return userConverter.entity2UserDTO(sysUser);
       });
    }



}
