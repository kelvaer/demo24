package org.example.demo2024.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.demo2024.entity.SysPermission;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {


}