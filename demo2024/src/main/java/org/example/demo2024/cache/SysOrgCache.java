package org.example.demo2024.cache;

import org.example.demo2024.entity.SysOrg;
import org.example.demo2024.mapper.SysOrgMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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


    @Cacheable(cacheNames = "org", key = "#id")
    public SysOrg getById(String id){
        return orgMapper.selectOneById(id);
    }


    @CacheEvict(cacheNames = "org", key = "#id")
    public void delOrg(String id){
        SysOrg sysOrg = orgMapper.selectOneById(id);
        if (sysOrg!=null){
            orgMapper.delete(sysOrg);
        }
    }

    @CachePut(cacheNames = "org", key = "#org.id")
    public SysOrg addOrg(SysOrg org){
        orgMapper.insert(org);
        return org;
    }


    @CachePut(cacheNames = "org", key = "#up.id")
    public SysOrg updateOrg(SysOrg up){
        SysOrg sysOrg = orgMapper.selectOneById(up.getId());
        if (sysOrg!=null){
            BeanUtils.copyProperties(up,sysOrg);
            orgMapper.insert(sysOrg);
        }
        return up;
    }

}
