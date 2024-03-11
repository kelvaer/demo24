package org.example.demo2024.api.sys;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.cache.SysOrgCache;
import org.example.demo2024.cfg.ResultBody;
import org.example.demo2024.convert.OrgConverter;
import org.example.demo2024.entity.SysOrg;
import org.example.demo2024.entity.table.SysOrgTableDef;
import org.example.demo2024.mapper.SysOrgMapper;
import org.example.demo2024.query.OrgListQueryReq;
import org.example.demo2024.query.OrgPageReq;
import org.example.demo2024.query.OrgTreeQueryReq;
import org.example.demo2024.util.SecurityUtil;
import org.example.demo2024.vo.BasePage;
import org.example.demo2024.vo.OrganizationTree;
import org.example.demo2024.vo.OrganizationVO;
import org.example.demo2024.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mybatisflex.core.query.QueryMethods.column;
import static org.example.demo2024.entity.table.SysOrgTableDef.SYS_ORG;

/**
 * @program: demo2024
 * @description: org-api
 * @author: 作者名
 * @create: 2024/02/27
 */
@Slf4j
@Api(tags = "组织-机构-部门类API")
@RestController
public class SysOrgApi {

    @Autowired
    private SysOrgCache orgCache;
    @Resource
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private OrgConverter orgConverter;

    public List<OrganizationTree> buildTree(List<OrganizationVO> listData) {
        List<OrganizationTree> treeList = BeanUtil.copyToList(listData, OrganizationTree.class);
        List<OrganizationTree> treeDataList = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        for (OrganizationTree treeData : treeList) {
            if (StrUtil.isBlank(treeData.getParentId())) {
                treeDataList.add(treeData);
            }
            for (OrganizationTree tree : treeList) {
                if (treeData.getId().equals(tree.getParentId())) {
                    if (treeData.getChildren() == null) {
                        treeData.setChildren(new ArrayList<>());
                    }
                    treeData.getChildren().add(tree);

                    ids.add(tree.getId());
                }
            }
        }
        if (treeDataList.size() == 0) {
            treeDataList = treeList.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return treeDataList;
    }


    //sys/org/query/tree?name=&isEnable=1

    @GetMapping("/sys/org/query/tree")
    public ResultBody<List<OrganizationTree>> getTree(OrgTreeQueryReq req){
        QueryWrapper query = QueryWrapper.create()
                .select("t.*")
                .select(column("p.code").as(OrganizationVO::getParentCode))
                .select(column("p.name").as(OrganizationVO::getParentName))
                .from(SYS_ORG.as("t"))
                .leftJoin(SYS_ORG).as("p")
                .on("t.parent_id = p.id")
                .where(column("t.is_enable")
                        .eq(req.getIsEnable())
                        .when(req.getIsEnable() != null))
                .where(column("t.parent_id")
                        .eq(req.getParentId())
                        .when(StrUtil.isNotBlank(req.getParentId())))
                .where(column("t.name")
                        .like(req.getName())
                        .when(StrUtil.isNotBlank(req.getName())))
                .where(column("t.code")
                        .eq(req.getCode())
                        .when(StrUtil.isNotBlank(req.getCode())));
        List<OrganizationVO> sysOrgVos = sysOrgMapper.selectListByQueryAs(query, OrganizationVO.class);
        List<OrganizationTree> organizationTrees = buildTree(sysOrgVos);
        return ResultBody.success(organizationTrees);
    }








    //sys/org/query/list
    @GetMapping("/sys/org/query/list")
    public ResultBody<List<OrganizationVO>> findOrgList(OrgListQueryReq req) {
        QueryWrapper query = QueryWrapper.create()
                .select("t.*")
                .select(column("p.code").as(OrganizationVO::getParentCode))
                .select(column("p.name").as(OrganizationVO::getParentName))
                .from(SYS_ORG.as("t"))
                .leftJoin(SYS_ORG).as("p")
                .on("t.parent_id = p.id")
                .where(column("t.is_enable")
                        .eq(req.getIsEnable())
                        .when(req.getIsEnable() != null))
                .where(column("t.parent_id")
                        .eq(req.getParentId())
                        .when(StrUtil.isNotBlank(req.getParentId())))
                .where(column("t.name")
                        .like(req.getName())
                        .when(StrUtil.isNotBlank(req.getName())))
                .where(column("t.code")
                        .eq(req.getCode())
                        .when(StrUtil.isNotBlank(req.getCode())));
        List<OrganizationVO> sysOrgVos = sysOrgMapper.selectListByQueryAs(query, OrganizationVO.class);
        return ResultBody.success(sysOrgVos);
    }



    ///sys/org/query/page?current=1&size=10
    @GetMapping("/sys/org/query/page")
    public ResultBody<BasePage<OrganizationVO>>  findOrgPage(OrgPageReq req){

        QueryWrapper query = QueryWrapper.create()
                .select("t.*")
                .select(column("p.code").as(OrganizationVO::getParentCode))
                .select(column("p.name").as(OrganizationVO::getParentName))
                .from(SYS_ORG.as("t"))
                .leftJoin(SYS_ORG).as("p")
                .on("t.parent_id = p.id")
                .where(column("t.is_enable")
                        .eq(req.getIsEnable())
                        .when(req.getIsEnable() != null))
                .where(column("t.parent_id")
                        .eq(req.getParentId())
                        .when(StrUtil.isNotBlank(req.getParentId())))
                .where(column("t.name")
                        .like(req.getName())
                        .when(StrUtil.isNotBlank(req.getName())))
                .where(column("t.code")
                        .eq(req.getCode())
                        .when(StrUtil.isNotBlank(req.getCode())));
        Page<OrganizationVO> paginate = sysOrgMapper.paginateAs(req.getCurrent(), req.getSize(), query,OrganizationVO.class);
        return ResultBody.success(new BasePage<OrganizationVO>(req.getCurrent(), req.getSize(), paginate.getTotalRow(), paginate.getRecords()));

    }


    //sys/org/save
    @PostMapping("/sys/org/save")
    public ResultBody<OrganizationVO> saveNewOrg(@RequestBody OrganizationVO vo){

        String parentId = vo.getParentId();
        if (StrUtil.isBlank(parentId)){
            return ResultBody.error("不允许添加根节点");
        }
        vo.setCreatedBy(SecurityUtil.getCurrentUserName());
        vo.setCreated(LocalDateTime.now());
        vo.setIsSystem(0);

        SysOrg parentOrg = orgCache.getById(parentId);
        if (parentOrg==null) {
            return ResultBody.error("父机构不存在");
        }
        String snowflakeNextId = IdUtil.getSnowflakeNextIdStr();
        vo.setCode(snowflakeNextId);

        SysOrg ins = orgConverter.vo2entity(vo);
        sysOrgMapper.insert(ins);
        String id = ins.getId();
        // 机构path = 上级网点path+本级ID
        ins.setPath(parentOrg.getPath()+","+id);
        sysOrgMapper.update(ins);

        vo.setId(ins.getId());
        vo.setPath(ins.getPath());
        return ResultBody.success(vo);
    }


    //sys/org/update/29208416195000163
    @PutMapping("/sys/org/update/{id}")
    public ResultBody<OrganizationVO> updateOrg(@PathVariable("id") String id, @RequestBody OrganizationVO vo){

        SysOrg sysOrgDb = orgCache.getById(id);
        vo.setModifiedBy(SecurityUtil.getCurrentUserName());
        vo.setModified(LocalDateTime.now());
        BeanUtil.copyProperties(vo, sysOrgDb, "id", "code", "path", "isSystem");
        sysOrgMapper.update(sysOrgDb);

        return ResultBody.success(vo);

    }


    //sys/org/del

    @DeleteMapping("/sys/org/del")
    public ResultBody<Void> delByIds(@RequestBody List<String> ids){
        if (CollUtil.isEmpty(ids)){
            return ResultBody.error("参数ids为空");
        }
        List<SysOrg> sysOrgs = sysOrgMapper.selectListByIds(ids);
        if (CollUtil.isEmpty(sysOrgs)){
            return ResultBody.error("机构不存在");
        }
        if (sysOrgs.stream().anyMatch(e -> e.getIsSystem() == 1)) {
            return ResultBody.error("系统机构不允许删除");
        }
        sysOrgMapper.deleteBatchByIds(ids);
        return ResultBody.success();
    }


}
