package org.example.demo2024.cache;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import org.example.demo2024.entity.SysOrg;
import org.example.demo2024.mapper.SysOrgMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: demo2024
 * @description: org cache
 * @author: 作者名
 * @create: 2024/03/11
 */
@Component
public class SysOrgCache {

    @Autowired
    private SysOrgMapper orgMapper;

    //@Cached 注解用于向缓存中放数据 ，适合GET/find查询操作

    //@CacheRefresh 注解用于在指定时间后就重新向数据库查询数据并放到缓存中

    //@CacheUpdate 注解用于在更新数据库数据时，同步更新到缓存中，适合update更新操作

    //@CacheInvalidate 注解用于在删除数据库数据时，同步将缓存中的对应数据删除,适合delete删除操作



    //框架默认cacheType使用 REMOTE远程缓存

    // LOCAL 本地缓存
//    @Cached(cacheType = CacheType.LOCAL,expire = 15000,name = "org",key = "#id")

    // REMOTE远程缓存 如redis
//    @Cached(cacheType = CacheType.REMOTE,expire = 200,name = "org:",key = "#id")

    // BOTH 两级缓存
    // localExpire 本地缓存有效期
    // expire 远程缓存有效期
    @Cached(cacheType = CacheType.BOTH,expire = 300,localExpire = 180,name = "org:",key = "#id")
    public SysOrg getById(String id){
        return orgMapper.selectOneById(id);
    }


    @CacheInvalidate(name = "org:",key = "#id")
    public void delOrg(String id){
        SysOrg sysOrg = orgMapper.selectOneById(id);
        if (sysOrg!=null){
            orgMapper.delete(sysOrg);
        }
    }

    public SysOrg addOrg(SysOrg org){
        orgMapper.insert(org);
        return org;
    }



    @CacheUpdate(name = "org:", key = "#up.id",value = "#up")
    public SysOrg updateOrg(SysOrg up){
        SysOrg sysOrg = orgMapper.selectOneById(up.getId());
        if (sysOrg!=null){
            BeanUtils.copyProperties(up,sysOrg);
            orgMapper.insert(sysOrg);
        }
        return up;
    }

}
