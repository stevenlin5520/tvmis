package com.steven.television.services.impl;

import com.steven.television.dao.TFormMapper;
import com.steven.television.dao.TImportMapper;
import com.steven.television.dao.TPlayMapper;
import com.steven.television.dao.TTelevisionMapper;
import com.steven.television.entity.*;
import com.steven.television.services.ImportService;
import com.steven.television.util.DateUtil;
import com.steven.television.util.Result;
import com.steven.television.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
            String dateStr = DateUtil.getDateStr(tImport.getStartTime());
            Date date = DateUtil.getDate(dateStr, "yyyy-MM-dd");
            TForm tForm = formMapper.selectByDate(dateStr);
            String formId;
            if (tForm == null) {
                TForm insertForm = new TForm();
                formId = UUIDUtil.getUUIDSTR();
                insertForm.setFormId(formId);
                insertForm.setFormDate(date);
                insertForm.setCreateTime(now);
                insertForm.setStatus(1);
                insertForm.setWatchNum(0);
                formMapper.insert(insertForm);
            }else{
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
                    if(tPlays.get(i).getPlayEnd().compareTo(tImport.getStartTime()) <= 0 && tPlays.get(i+1).getPlayStart().compareTo(tImport.getEntTime()) >= 0){
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
            play.setPlayDate(date);
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
}
