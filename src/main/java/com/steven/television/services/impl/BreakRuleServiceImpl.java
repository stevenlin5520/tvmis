package com.steven.television.services.impl;

import com.steven.television.constant.CommonConstrant;
import com.steven.television.dao.TBreakRuleMapper;
import com.steven.television.dao.TSystemMapper;
import com.steven.television.entity.Page;
import com.steven.television.entity.TBreakRule;
import com.steven.television.entity.TSupplier;
import com.steven.television.entity.TSystem;
import com.steven.television.services.BreakRuleService;
import com.steven.television.services.SupplierService;
import com.steven.television.services.SystemService;
import com.steven.television.util.StringUtil;
import com.steven.television.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @desc
 * @author steven
 * @date 2020/11/9 22:35
 */
@Service
public class BreakRuleServiceImpl implements BreakRuleService {

    @Resource
    private TBreakRuleMapper breakRuleMapper;
    @Resource
    private TSystemMapper systemMapper;
    @Resource
    private SupplierService supplierService;

    @Override
    public List<TBreakRule> list() {
        return breakRuleMapper.list(new HashMap(2));
    }

    @Override
    public Page<TBreakRule> list(Page<TBreakRule> pager){
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("startRow",pager.getStartRow());
        map.put("limit",pager.getLimit());
        List<TBreakRule> list = breakRuleMapper.selectByPage(map);
        List<TSupplier> suppliers = supplierService.list().stream().filter(s -> s.getAuditStatus() == 1).collect(Collectors.toList());
        list.forEach(rule -> suppliers.forEach(supplier -> {
            if(supplier.getSupplierId().equals(rule.getOrgId())){
                rule.setOrgId(supplier.getSupplierName());
            }
        }));
        pager.returnData(list,breakRuleMapper.selectByPageCount(map));
        return pager;
    }

    @Override
    public TBreakRule selectById(String id) {
        return breakRuleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(String id) {
        TBreakRule tBreakRule = breakRuleMapper.selectByPrimaryKey(id);
        if (tBreakRule == null) {
            return 0;
        }
        tBreakRule.setUpdateTime(new Date());
        tBreakRule.setStatus(1);
        return breakRuleMapper.updateByPrimaryKeySelective(tBreakRule);
    }

    @Override
    public int insert(TBreakRule tSystem) {
        return breakRuleMapper.insert(tSystem);
    }

    @Override
    public int update(TBreakRule tSystem) {
        return breakRuleMapper.updateByPrimaryKeySelective(tSystem);
    }

    /**
     * 自动添加节目审核违规超过一定次数的节目到黑名单中
     */
    @Override
    public void autoTelevisionWarn() {
        String value = systemMapper.selectValueByKey(CommonConstrant.FILE_VIDEO_PATH);
        if(StringUtil.isBlank(value)){
            return;
        }
        List<Map> ruleList = breakRuleMapper.selectTelevisionOverCount(Integer.parseInt(value));
        if(ruleList == null || ruleList.size() == 0){
            return;
        }
        for (Map map : ruleList) {
            breakRuleMapper.updateRuleBatch(((String) map.get("ruleIds")));
        }
    }

    /**
     * 将达到告警次数的供应商添加到告警中（不包含已经告警的）
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void autoSupplierWarn() {
        String value = systemMapper.selectValueByKey(CommonConstrant.SUPPLIER_WARN_COUNT);
        if(StringUtil.isBlank(value)){
            return;
        }
        //查询已经加入告警的供应商
        HashMap<String, String> map1 = new HashMap<String, String>(2);
        map1.put("condition", " and status=1 and rule_type=2 ");
        List<TBreakRule> selectWarnList = breakRuleMapper.selectByCondition(map1);
        Set<String> unIncludeSet = new HashSet<String>(16);
        for (TBreakRule breakRule : selectWarnList) {
            unIncludeSet.add(breakRule.getOrgId());
        }

        List<Map> ruleList = breakRuleMapper.selectSupplierBreakRule(Integer.parseInt(value));
        if(ruleList == null || ruleList.size() == 0){
            return;
        }
        Date createTime = new Date();
        for (Map map : ruleList) {
            if(unIncludeSet.contains(map.get("orgId"))){
                continue;
            }
            //breakRuleMapper.updateRuleBatch((String)map.get("ruleIds"));
            TBreakRule breakRule = new TBreakRule();
            breakRule.setOrgId((String)map.get("orgId"));
            breakRule.setStatus(1);
            breakRule.setRuleType(2);
            breakRule.setCreateTime(createTime);
            breakRule.setRuleId(UUIDUtil.getUUIDSTR());
            breakRule.setRuleDesc("达到告警次数："+value);
            breakRule.setRuleName("告警");
            breakRuleMapper.insert(breakRule);
        }
    }


    /**
     * 将达到黑名单规定次数的供应商添加到黑名单中（不包含已经加入黑名单的，但是加入黑名单之后，违规记录将删除）
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void autoSupplierBacklist() {
        String value = systemMapper.selectValueByKey(CommonConstrant.SUPPLIER_BACKLIST_COUNT);
        if(StringUtil.isBlank(value)){
            return;
        }
        List<Map> ruleList = breakRuleMapper.selectSupplierBreakRule(Integer.parseInt(value));
        if(ruleList == null || ruleList.size() == 0){
            return;
        }
        Date now = new Date();
        for (Map map : ruleList) {
            //删除违规记录
            breakRuleMapper.updateRuleBatch((String)map.get("ruleIds"));

            //删除告警记录(status=-1)
            HashMap<String, String> map1 = new HashMap<String, String>(2);
            map1.put("condition", " and status=1 and rule_type=2 and org_id='"+((String)map.get("orgId"))+"' ");
            List<TBreakRule> rules = breakRuleMapper.selectByCondition(map1);
            TBreakRule breakRule1 = rules.get(0);
            breakRule1.setStatus(-1);
            breakRule1.setUpdateTime(now);
            breakRuleMapper.updateByPrimaryKeySelective(breakRule1);

            //加入一条黑名单记录（type=3，status=1）
            TBreakRule breakRule = new TBreakRule();
            breakRule.setOrgId((String)map.get("orgId"));
            breakRule.setStatus(1);
            breakRule.setRuleType(3);
            breakRule.setCreateTime(now);
            breakRule.setRuleId(UUIDUtil.getUUIDSTR());
            breakRule.setRuleDesc("达到黑名单次数："+value);
            breakRule.setRuleName("黑名单");
            breakRuleMapper.insert(breakRule);

            //修改供应商审核状态
            TSupplier supplier = supplierService.selectById((String) map.get("orgId"));
            supplier.setUpdateTime(now);
            supplier.setAuditStatus(2);
            supplierService.update(supplier);
        }


    }
}
