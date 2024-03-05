package org.example.demo2024.api.sys;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.recycler.Recycler;
import org.example.demo2024.anno.CommonLog;
import org.example.demo2024.cfg.ResultBody;
import org.example.demo2024.convert.UserConverter;
import org.example.demo2024.dto.LoginReq;
import org.example.demo2024.entity.Role;
import org.example.demo2024.entity.SysOrg;
import org.example.demo2024.entity.SysUser;
import org.example.demo2024.entity.SysUserRole;
import org.example.demo2024.entity.table.RoleTableDef;
import org.example.demo2024.entity.table.SysUserRoleTableDef;
import org.example.demo2024.entity.table.SysUserTableDef;
import org.example.demo2024.facade.SysLoginFacade;
import org.example.demo2024.mapper.RoleMapper;
import org.example.demo2024.mapper.SysOrgMapper;
import org.example.demo2024.mapper.SysUserMapper;
import org.example.demo2024.mapper.SysUserRoleMapper;
import org.example.demo2024.query.UserPageQueryReq;
import org.example.demo2024.util.I18nUtil;
import org.example.demo2024.vo.BasePage;
import org.example.demo2024.vo.LoginResponse;
import org.example.demo2024.vo.RestPasswdVO;
import org.example.demo2024.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.bean.BeanUtil;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: demo2024
 * @description: user api
 * @author: 作者名
 * @create: 2024/02/04
 */
@Api(tags = "用户类API")
@Slf4j
@RestController
public class UserController {

    @GetMapping("/i18n")
    public String i18n(HttpServletRequest request) {
        String message1 = I18nUtil.getMessage("A00001", request.getHeader("lang"));
        String message2 = I18nUtil.getMessage("A00002", request.getHeader("lang"));
        return message1 + message2;
    }


    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysOrgMapper sysOrgMapper;

    @Autowired
    private UserConverter userConverter;

    //sys/user/query/page?current=1&size=10
    @GetMapping("/sys/user/query/page")
    public ResultBody<BasePage<UserVO>> findUserVoPage(UserPageQueryReq req) {
        QueryWrapper query = QueryWrapper.create()
                .select(SysUserTableDef.SYS_USER.ALL_COLUMNS)
                .from(SysUserTableDef.SYS_USER)
                .where(SysUserTableDef.SYS_USER.ORG_ID.eq(req.getOrgId())
                        .when(StrUtil.isNotBlank(req.getOrgId())))
                .where(SysUserTableDef.SYS_USER.UID.eq(req.getUsername())
                        .when(StrUtil.isNotBlank(req.getUsername())))
                .where(SysUserTableDef.SYS_USER.NAME.like(req.getNickname())
                        .when(StrUtil.isNotBlank(req.getNickname())))
                .where(SysUserTableDef.SYS_USER.PHONE.eq(req.getPhone())
                        .when(StrUtil.isNotBlank(req.getPhone())))
                .where(SysUserTableDef.SYS_USER.IS_ENABLED.eq(req.getEnable())
                        .when(req.getEnable() != null));
        Page<SysUser> paginate = sysUserMapper.paginate(req.getCurrent(), req.getSize(), query);

        List<SysUser> records = paginate.getRecords();
        List<UserVO> newResList = new ArrayList<>();
        if (CollUtil.isEmpty(records)) {
            return ResultBody.success(new BasePage<UserVO>(req.getCurrent(), req.getSize(), 0, newResList));
        }
        newResList = userConverter.sysUsers2UserVos(records);
        for (UserVO userVO : newResList) {
            if (StrUtil.isNotBlank(userVO.getOrgId())) {
                SysOrg sysOrg = sysOrgMapper.selectOneById(userVO.getOrgId());
                if (sysOrg != null) {
                    userVO.setOrgCode(sysOrg.getCode());
                    userVO.setOrgName(sysOrg.getName());
                }
            }
        }
        return ResultBody.success(new BasePage<UserVO>(req.getCurrent(), req.getSize(), paginate.getTotalRow(), newResList));
    }


    //sys/user/check/username?username=mthyk
    @GetMapping("/sys/user/check/username")
    public ResultBody<Boolean> checkName(@RequestParam String username) {
        QueryWrapper query = QueryWrapper.create()
                .select(SysUserTableDef.SYS_USER.ALL_COLUMNS)
                .from(SysUserTableDef.SYS_USER)
                .where(SysUserTableDef.SYS_USER.UID.eq(username));
        long l = sysUserMapper.selectCountByQuery(query);
        if (l>0){
            return ResultBody.success(Boolean.TRUE);
        }else {
            return ResultBody.success(Boolean.FALSE);
        }
    }


    // sys/user/delete
    @DeleteMapping("/sys/user/delete")
    public ResultBody<Void>  deleteByIds(@RequestBody List<String> ids){
        if (CollUtil.isEmpty(ids)){
            return ResultBody.error("删除失败，缺少参数");
        }

        sysUserMapper.deleteBatchByIds(ids);

        QueryWrapper where = QueryWrapper.create()
                .from(SysUserRoleTableDef.SYS_USER_ROLE)
                .where(SysUserRoleTableDef.SYS_USER_ROLE.USER_ID.in(ids));
        sysUserRoleMapper.deleteByQuery(where);

        return ResultBody.success();
    }




    //sys/user/save

    @PostMapping("/sys/user/save")
    public ResultBody<UserVO>  saveNewSysUser(@RequestBody UserVO userVO){

        String userName = "未知";
        if (StpUtil.isLogin()) {
            userName = (String) StpUtil.getExtra("uid");
        }
        userVO.setCreatedBy(userName);
        userVO.setModifiedBy(userName);
        if (userVO.getIsManage()==null){
            userVO.setIsManage(0);
        }
        SysUser sysUser = userConverter.userVo2SysUser(userVO);
        sysUserMapper.insert(sysUser);
        List<Long> roleIds = userVO.getRoleIds();
        if (CollUtil.isNotEmpty(roleIds)){
            List<SysUserRole> sysUserRoleList = new ArrayList<>();
            for (Long roleId : roleIds) {
                SysUserRole item = SysUserRole.builder()
                        .userId(sysUser.getId())
                        .roleId(roleId)
                        .build();
                sysUserRoleList.add(item);
            }
            sysUserRoleMapper.insertBatch(sysUserRoleList);
        }
        userVO.setId(sysUser.getId());

        return ResultBody.success(userVO);
    }



    //sys/user/query/role/ids?id=12

    @GetMapping("/sys/user/query/role/ids")
    public ResultBody<List<Long>>  findUserRoleIds(@RequestParam String id){
        Object userId = StpUtil.getExtra("user_id");
        String userIdStr = userId.toString();
        QueryWrapper qw = QueryWrapper.create()
                .select(SysUserRoleTableDef.SYS_USER_ROLE.ALL_COLUMNS)
                .from(SysUserRoleTableDef.SYS_USER_ROLE)
                .where(SysUserRoleTableDef.SYS_USER_ROLE.USER_ID.eq(userIdStr));

        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectListByQuery(qw);
        if (CollUtil.isNotEmpty(sysUserRoles)){
            List<Long> collectRoleId = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            List<Role> roles = roleMapper.selectListByQuery(QueryWrapper.create()
                    .select(RoleTableDef.ROLE.ALL_COLUMNS)
                    .from(RoleTableDef.ROLE)
                    .where(RoleTableDef.ROLE.ENABLED.eq(1))
                    .and(RoleTableDef.ROLE.ID.in(collectRoleId))
            );
            if (CollUtil.isNotEmpty(roles)){
                List<Long> roleIdList = roles.stream().map(Role::getId).collect(Collectors.toList());
                return ResultBody.success(roleIdList);
            }
        }
        return ResultBody.success(new ArrayList<>());
    }







    @CommonLog(value = "系统注销")
    @ApiOperation(value = "系统注销", notes = "退出登录接口")
    @PostMapping("/auth/logout")
    public ResultBody<String> logout() {
        StpUtil.logout();
        return ResultBody.success("成功");
    }




    //sys/user/update/1762443338271531009
    @PutMapping("/sys/user/update/{id}")
    public ResultBody<UserVO>  updateSysUser(@PathVariable String id, @RequestBody UserVO userVO){
        String userName = "未知";
        if (StpUtil.isLogin()) {
            userName = StpUtil.getExtra("uid").toString();
        }
        userVO.setModifiedBy(userName);
        String userId = StpUtil.getExtra("user_id").toString();
        SysUser sysUser = sysUserMapper.selectOneById(id);
        if (sysUser==null){
            return ResultBody.error("用户不存在");
        }
        BeanUtil.copyProperties(userVO,sysUser);
        sysUser.setUid(userVO.getUsername());
        sysUser.setName(userVO.getNickname());
        sysUserMapper.update(sysUser);

        sysUserRoleMapper.deleteByQuery(QueryWrapper.create()
                .from(SysUserRoleTableDef.SYS_USER_ROLE).where(SysUserRoleTableDef.SYS_USER_ROLE.USER_ID.eq(userId)));
        List<Long> roleIds = userVO.getRoleIds();
        if (CollUtil.isNotEmpty(roleIds)){
            for (Long roleId : roleIds) {
                sysUserRoleMapper.insert(SysUserRole.builder()
                                .roleId(roleId)
                                .userId(Long.valueOf(userId))
                        .build());
            }
        }
        return ResultBody.success(userVO);
    }


    //sys/user/reset/passwd/yc2024
    @PutMapping("/sys/user/reset/passwd/{username}")
    public ResultBody<Void> resetSysUserPwd(@PathVariable(value = "username") String username,
                                            @Validated @RequestBody RestPasswdVO vo){

        if (!StrUtil.equals(vo.getNewPassword(),vo.getConfirmPassword())){
            return ResultBody.error("两次密码不一致");
        }
        SysUser sysUser = sysUserMapper.selectOneByQuery(QueryWrapper.create().select(SysUserTableDef.SYS_USER.ALL_COLUMNS)
                .from(SysUserTableDef.SYS_USER).where(SysUserTableDef.SYS_USER.UID.eq(username)));
        sysUser.setPwd(vo.getConfirmPassword());
        sysUserMapper.update(sysUser);
        return ResultBody.success();
    }






    @Autowired
    private SysLoginFacade sysLoginFacade;

    @CommonLog(value = "登录系统")
    @ApiOperation(value = "系统登录", notes = "系统登录接口")
    // 测试登录，浏览器访问： http://localhost:8080/user/doLogin?username=zhang&password=123456
    @PostMapping("/auth/login")
    public ResultBody<LoginResponse> doLogin(@RequestBody LoginReq loginReq) {

        LoginResponse loginResponse = sysLoginFacade.execDoLogin(loginReq);
        return ResultBody.success(loginResponse);

    }

    @CommonLog(value = "查询登录状态")
    // 查询登录状态，浏览器访问： http://localhost:8080/user/isLogin
    @GetMapping("isLogin")
    public String isLogin() {


        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}
