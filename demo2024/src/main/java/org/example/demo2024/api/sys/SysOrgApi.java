package org.example.demo2024.api.sys;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.cfg.ResultBody;
import org.example.demo2024.entity.SysOrg;
import org.example.demo2024.entity.table.SysOrgTableDef;
import org.example.demo2024.mapper.SysOrgMapper;
import org.example.demo2024.query.OrgListQueryReq;
import org.example.demo2024.query.OrgTreeQueryReq;
import org.example.demo2024.vo.OrganizationTree;
import org.example.demo2024.vo.OrganizationVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @Resource
    private SysOrgMapper sysOrgMapper;

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




}
