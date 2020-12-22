package com.steven.television.services.impl;

import com.steven.television.dao.TFormMapper;
import com.steven.television.dao.TImportMapper;
import com.steven.television.dao.TPlayMapper;
import com.steven.television.dao.TTelevisionMapper;
import com.steven.television.entity.*;
import com.steven.television.services.ImportService;
import com.steven.television.util.DateUtil;
import com.steven.television.util.Result;
import com.steven.television.util.StringUtil;
import com.steven.television.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 13:33
 */
@Service
public class ImportServiceImpl implements ImportService {

    @Resource
    private TImportMapper tImportMapper;
    @Resource
    private TFormMapper formMapper;
    @Resource
    private TTelevisionMapper televisionMapper;
    @Resource
    private TPlayMapper playMapper;

    @Override
    public List<TImport> list() {
//        return tImportMapper.list();
        return null;
    }

    @Override
    public Page<ImportVo> list(Page<ImportVo> pager, String orgId, int type) {
        Map<String, Object> map = new HashMap<String, Object>(8);
        map.put("startRow",pager.getStartRow());
        map.put("limit",pager.getLimit());
        map.put("orgId",orgId);
        map.put("type",type);
        pager.returnData(tImportMapper.selectByPage(map),tImportMapper.selectByPageCount(map));
        return pager;
    }

    /**
     * 电视节目/广告审核
     * @param pager
     * @param search
     * @return
     */
    @Override
    public Page<ImportVo> list(Page<ImportVo> pager, String search) {
        Map<String, Object> map = new HashMap<String, Object>(8);
        map.put("startRow",pager.getStartRow());
        map.put("limit",pager.getLimit());
        map.put("search",search);
        pager.returnData(tImportMapper.selectByPage(map),tImportMapper.selectByPageCount(map));
        return pager;
    }

    @Override
    public TImport selectById(String id) {
        return tImportMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(String id) {
        TImport tImport = selectById(id);
        if(tImport == null){
            return 0;
        }
        tImport.setUpdateTime(new Date());
        tImport.setStatus(-1);
        return tImportMapper.updateByPrimaryKeySelective(tImport);
    }

    @Override
    public int insert(TImport tImport) {
        return tImportMapper.insert(tImport);
    }

    @Override
    public int update(TImport tImport) {
        return tImportMapper.updateByPrimaryKeySelective(tImport);
    }


    /**
     * 审核节目，并编排节目信息
     * @param tImport
     * @param auditStatus
     * @param content
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Result buildProject(TImport tImport, int auditStatus, String content) {
        //拒绝申请
        Date now = new Date();
        try {
            //状态3为审核拒绝
            if (auditStatus == 3) {
                tImport.setUpdateTime(now);
                tImport.setImportAudit(auditStatus);
                tImport.setAuditContent(content);
                tImportMapper.updateByPrimaryKeySelective(tImport);
                return Result.success("审核成功");
            }

            TTelevision television = televisionMapper.selectByPrimaryKey(tImport.getTelevisionId());
            if(television == null || television.getStatus() == -1){
                return Result.failed("节目找不到");
            }
            /**
             * <p>审核通过，则编排节目</p>
             * <p>节目表中的数据结构：日期（年月日）、开始时间、结束时间、时长、节目ID、节目名、节目描述、节目截图、节目视频</p>
             */
            String date = DateUtil.getDateStr(tImport.getStartTime());
            HashMap<String, Object> hashMap = new HashMap<>(4);
            hashMap.put("search", " and form_content='"+tImport.getChannelId()+"' and form_date='"+date+"'");
            List<TForm> tForms = formMapper.selectByCondition(hashMap);
            String formId;
            if (tForms == null) {
                TForm insertForm = new TForm();
                formId = UUIDUtil.getUUIDSTR();
                insertForm.setFormId(formId);
                insertForm.setFormDate(DateUtil.getDate(date,"yyyy-MM-dd"));
                insertForm.setCreateTime(now);
                insertForm.setStatus(1);
                insertForm.setWatchNum(0);
                insertForm.setFormContent(television.getSupplierId());
                formMapper.insert(insertForm);
            }else{
                TForm tForm = tForms.get(0);
                //存在
                Map<String,Object> map = new HashMap(2);
                map.put("formId", tForm.getFormId());
                List<TPlay> tPlays = playMapper.selectByDateOrForm(map);
                /**
                 * 判断前一个节目的结束时间到后一个节目开始时间之间的时间是否大于要插入节目表的时长，大于的话，则插入，否则报错
                 */
                boolean canInsert = false;
                for(int i=0 ; i < tPlays.size(); i++){
                    //已存在节目表之间
                    if(tPlays.get(i).getPlayEnd().compareTo(tImport.getStartTime()) <= 0 && tPlays.size()>1 && tPlays.get(i+1).getPlayStart().compareTo(tImport.getEntTime()) >= 0){
                        canInsert = true;
                        break;
                    }else if(i == 0 && tPlays.get(i).getPlayStart().compareTo(tImport.getEntTime()) >= 0){
                        //已存在节目表之前
                        canInsert = true;
                        break;
                    }else if(i == 0 && tPlays.get(i).getPlayEnd().compareTo(tImport.getStartTime()) <= 0){
                        //已存在节目表之后
                        canInsert = true;
                        break;
                    }
                }
                if(!canInsert){
                    return Result.failed("排期冲突");
                }
                formId = tForm.getFormId();
            }
            TPlay play = new TPlay();
            play.setPlayId(UUIDUtil.getUUIDSTR());
            play.setPlayForm(formId);
            play.setPlayDate(DateUtil.getDate(date,"yyyy-MM-dd"));
            play.setPlayStart(tImport.getStartTime());
            play.setPlayEnd(tImport.getEntTime());
            play.setPlayLength(tImport.getImportLength());
            play.setPlayTvName(television.getTvName());
            play.setPlayTvId(tImport.getTelevisionId());
            play.setPlayTvDesc(television.getTvDesc());
            play.setPlayScreen(television.getTvScreen());
            play.setPlayVideo(television.getTvLocation());
            playMapper.insert(play);

            //修改审核信息
            tImport.setUpdateTime(now);
            tImport.setImportAudit(auditStatus);
            tImport.setAuditContent("审核通过");
            tImportMapper.updateByPrimaryKeySelective(tImport);
            return Result.success("审核成功");
        }catch(Exception e){
            e.printStackTrace();
            return Result.failed("服务异常");
        }
    }


    /**
     * 审核电视节目信息
     * @param tTelevison
     * @param auditStatus
     * @param content
     * @return
     */
    @Override
    public Result saveAuditTelevision(TTelevision tTelevison, int auditStatus, String content) {
        //状态3为审核拒绝
        tTelevison.setUpdateTime(new Date());
        tTelevison.setAuditState(auditStatus+"");
        tTelevison.setAuditRemark(auditStatus == 2 ? content : "审核通过");
        televisionMapper.updateByPrimaryKeySelective(tTelevison);
        return Result.success("审核成功");
    }


    @Override
    public Result formList(String date,int type,String channelId,int length) throws ParseException {
        List<Map> result = new ArrayList<>(10);

        Map<String,Object> map = new HashMap<>(4);
        if(StringUtil.isNotBlank(date)){
            map.put("date",DateUtil.getDateStr(DateUtil.StrTimeToDate(date)));
        }
        map.put("channelId",channelId);
        map.put("type",type);
        Date date1 = DateUtil.getDate(date,"yyyy-MM-dd");
        Date date2 = new Date(date1.getTime() + (24 * 60 * 60 - 1) * 1000);
        List<TImport> imports = tImportMapper.selectImportForm(map);
        if(imports == null || imports.size() == 0){
            HashMap<String, Object> itemMap = new HashMap<>(4);
            itemMap.put("startTime",date1);
            itemMap.put("endTime",date2);
            itemMap.put("dateDis","00:00:00--23:59:59");
            itemMap.put("type",0);
            itemMap.put("used",0);
            result.add(itemMap);
            return Result.success(result);
        }

        for (int i = 0; i < imports.size(); i++) {
            TImport item = imports.get(i);
            HashMap<String, Object> itemMap = new HashMap<>(4);
            if(i == 0){
                if(item.getStartTime().compareTo(date1)>0) {
                    itemMap.put("startTime", date1);
                    itemMap.put("endTime", item.getStartTime());
                    itemMap.put("dateDis", "00:00:00--" + DateUtil.getTime(item.getStartTime()));
                    itemMap.put("type", 0);
                    itemMap.put("used", 0);
                    result.add(itemMap);

                    itemMap = new HashMap<>(4);
                    itemMap.put("startTime", item.getStartTime());
                    itemMap.put("endTime", item.getEntTime());
                    itemMap.put("dateDis", DateUtil.getTime(item.getStartTime()) + "--" + DateUtil.getTime(item.getEntTime()));
                    itemMap.put("type", item.getType());
                    itemMap.put("used", 1);
                    result.add(itemMap);
                }else if(item.getStartTime().compareTo(date1)==0 && item.getEntTime().compareTo(date2)==0){
                    //刚好铺满一天
                    itemMap = new HashMap<>(4);
                    itemMap.put("startTime",date1);
                    itemMap.put("endTime",date2);
                    itemMap.put("dateDis","00:00:00--23:59:59");
                    itemMap.put("type",item.getType());
                    itemMap.put("used",1);
                    result.add(itemMap);
                }else if(item.getStartTime().compareTo(date1)==0){
                    //开始和0点相同
                    itemMap.put("startTime", date1);
                    itemMap.put("endTime", item.getEntTime());
                    itemMap.put("dateDis", "00:00:00--" + DateUtil.getTime(item.getEntTime()));
                    itemMap.put("type", item.getType());
                    itemMap.put("used", 1);
                    result.add(itemMap);

                    itemMap = new HashMap<>(4);
                    itemMap.put("startTime",item.getEntTime());
                    itemMap.put("endTime",date2);
                    itemMap.put("dateDis",DateUtil.getTime(item.getEntTime())+"--23:59:59");
                    itemMap.put("type",0);
                    itemMap.put("used",0);
                    result.add(itemMap);
                    break;
                }else if(item.getEntTime().compareTo(date2)==0){
                    //结束和24点相同
                    itemMap.put("startTime", date1);
                    itemMap.put("endTime", item.getStartTime());
                    itemMap.put("dateDis", "00:00:00--" + DateUtil.getTime(item.getStartTime()));
                    itemMap.put("type", 0);
                    itemMap.put("used", 0);
                    result.add(itemMap);

                    itemMap = new HashMap<>(4);
                    itemMap.put("startTime",item.getStartTime());
                    itemMap.put("endTime",date2);
                    itemMap.put("dateDis",DateUtil.getTime(item.getStartTime())+"--23:59:59");
                    itemMap.put("type",item.getType());
                    itemMap.put("used",1);
                    result.add(itemMap);
                    break;
                }

                if(imports.get(i+1) != null && item.getEntTime().compareTo(imports.get(i+1).getStartTime())!=0){
                    itemMap = new HashMap<>(4);
                    itemMap.put("startTime",item.getEntTime());
                    itemMap.put("endTime",imports.get(i+1).getStartTime());
                    itemMap.put("dateDis",DateUtil.getTime(item.getEntTime())+"--"+DateUtil.getTime(imports.get(i+1).getStartTime()));
                    itemMap.put("type",0);
                    itemMap.put("used",0);
                    result.add(itemMap);
                }
                if(imports.size()==1 && item.getEntTime().compareTo(date2)<0 && item.getStartTime().compareTo(date1)>0){
                    itemMap = new HashMap<>(4);
                    itemMap.put("startTime",item.getEntTime());
                    itemMap.put("endTime",date2);
                    itemMap.put("dateDis",DateUtil.getTime(item.getEntTime())+"--23:59:59");
                    itemMap.put("type",0);
                    itemMap.put("used",0);
                    result.add(itemMap);
                    break;
                }
            }else if (i == imports.size()-1 && item.getEntTime().compareTo(date2)<0){
                //最后一条
                itemMap.put("startTime",item.getStartTime());
                itemMap.put("endTime",item.getEntTime());
                itemMap.put("dateDis",DateUtil.getTime(item.getStartTime()) + "--" + DateUtil.getTime(item.getEntTime()));
                itemMap.put("type",item.getType());
                itemMap.put("used",1);
                result.add(itemMap);

                itemMap = new HashMap<>(4);
                itemMap.put("startTime",item.getEntTime());
                itemMap.put("endTime",date2);
                itemMap.put("dateDis",DateUtil.getTime(item.getEntTime())+"--23:59:59");
                itemMap.put("type",0);
                itemMap.put("used",0);
                result.add(itemMap);
                break;
            }else if(item.getEntTime().compareTo(imports.get(i+1).getStartTime()) != 0){
                //中间的
                itemMap.put("startTime",item.getStartTime());
                itemMap.put("endTime",item.getEntTime());
                itemMap.put("dateDis",DateUtil.getTime(item.getStartTime()) + "--" + DateUtil.getTime(item.getEntTime()));
                itemMap.put("type",item.getType());
                itemMap.put("used",1);
                result.add(itemMap);

                itemMap = new HashMap<>(4);
                itemMap.put("startTime",item.getEntTime());
                itemMap.put("endTime",imports.get(i+1).getStartTime());
                itemMap.put("dateDis",DateUtil.getTime(item.getEntTime())+"--"+DateUtil.getTime(imports.get(i+1).getStartTime()));
                itemMap.put("type",0);
                itemMap.put("used",0);
                result.add(itemMap);
            }
        }

        /**
         * 白色未拍期0；灰色已排期1；绿色可申请2；红色已占用3
         */
        //申请视频播放结束时间
        Date postStartTime = DateUtil.StrTimeToDate(date);
        Date calcEndTime = new Date(postStartTime.getTime()+length*1000);
        result.forEach(item -> {
            Date startTime = (Date) item.get("startTime");
            Date endTime = (Date) item.get("endTime");
            int used = (int) item.get("used");
            if(startTime.compareTo(postStartTime)<=0 && endTime.compareTo(calcEndTime)>=0){
                if(used == 0){
                    item.put("used",2);
                }else if(used == 1){
                    item.put("used",3);
                }
            }
            item.put("startTime",DateUtil.getDateTimeStr(startTime));
            item.put("endTime",DateUtil.getDateTimeStr(endTime));
        });

        return Result.success(result);
    }
}
