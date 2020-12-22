package com.steven.television.services.impl;

import com.steven.television.dao.TFormMapper;
import com.steven.television.dao.TPlayMapper;
import com.steven.television.dao.TSupplierMapper;
import com.steven.television.dao.TTelevisionMapper;
import com.steven.television.entity.*;
import com.steven.television.services.FormService;
import com.steven.television.services.SupplierService;
import com.steven.television.util.DateUtil;
import com.steven.television.util.StringUtil;
import com.steven.television.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 13:33
 */
@Service
public class FormServiceImpl implements FormService {

    @Resource
    private TFormMapper formMapper;
    @Resource
    private TPlayMapper playMapper;
    @Resource
    private TTelevisionMapper televisionMapper;

    @Override
    public List<TForm> list() {
        return formMapper.list();
    }

    @Override
    public Page<FormVo> list2(Page<FormVo> pager, String channelId, String startTime) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("startRow",pager.getStartRow());
        map.put("limit",pager.getLimit());
        if(StringUtil.isNotBlank(startTime)){
            map.put("date", startTime);
        }else if(StringUtil.isBlank(channelId)){
            map.put("date", DateUtil.getDateStr(new Date()));
        }
        if(StringUtil.isNotBlank(channelId)){
            map.put("channelId", channelId);
        }
        pager.returnData(formMapper.selectByConditionPager(map),formMapper.selectByConditionPagerCount(map));
        return pager;
    }

    @Override
    public Page<TForm> list(Page<TForm> pager,String channelId,String startTime) {
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("startRow",pager.getStartRow());
        map.put("limit",pager.getLimit());
        String sql = "";
        if(StringUtil.isNotBlank(channelId)){
            sql += " and form_content='"+channelId+"'";
        }
        if(StringUtil.isNotBlank(startTime)){
            sql += " and form_date='"+startTime+"'";
        }
        map.put("search", sql);
        pager.returnData(formMapper.selectByPage(map),formMapper.selectByPageCount(map));
        return pager;
    }

    @Override
    public TForm selectById(String id) {
        return formMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(String id) {
        TForm form = selectById(id);
        if(form == null){
            return 0;
        }
        form.setUpdateTime(new Date());
        form.setStatus(-1);
        return formMapper.updateByPrimaryKeySelective(form);
    }

    @Override
    public int insert(TForm form) {
        return formMapper.insert(form);
    }

    @Override
    public int update(TForm form) {
        return formMapper.updateByPrimaryKeySelective(form);
    }

    @Override
    public List<TPlay> listPlay(Map map) {
        return playMapper.selectByDateOrForm(map);
    }

    /**
     * 自动编排节目
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autoBuildForm(String date,String channelId){
        try {
            TForm tForm = formMapper.selectByDateAndChannelId(date,channelId);
            String formId = null;
            Date formDate = DateUtil.getDate(date, "yyyy-MM-dd");
            Date now = new Date();
            if (tForm == null) {
                TForm insertForm = new TForm();
                formId = UUIDUtil.getUUIDSTR();
                insertForm.setFormId(formId);
                insertForm.setFormDate(formDate);
                insertForm.setCreateTime(now);
                insertForm.setStatus(1);
                insertForm.setWatchNum(0);
                insertForm.setFormContent(channelId);
                formMapper.insert(insertForm);
            }else{
                formId = tForm.getFormId();
            }
            /**
             * 查询编排好的当天的节目
             */
            Map<String, Object> map = new HashMap(2);
            map.put("formId", formId);
            List<TPlay> tPlays = playMapper.selectByDateOrForm(map);
            //系统节目和广告
            List<TTelevision> sysTelevisionList = televisionMapper.selectSysTelevisionByType(1);
            List<TTelevision> sysAdvertistList = televisionMapper.selectSysTelevisionByType(2);
            long minLength = sysTelevisionList != null && sysTelevisionList.size()>0 ? sysTelevisionList.get(0).getTvLength() :
                    sysAdvertistList != null && sysAdvertistList.size()>0 ? sysAdvertistList.get(0).getTvLength() : 0 ;
            for(TTelevision tv : sysTelevisionList){
                minLength = minLength>tv.getTvLength() ? tv.getTvLength() : minLength;
            }
            for(TTelevision tv : sysAdvertistList){
                minLength = minLength>tv.getTvLength() ? tv.getTvLength() : minLength;
            }

            //编排节目
            for (int i = 0; i < tPlays.size(); i++) {
                //时间间隔（单位：s）
                Long diff = 0L;
                //插入记录的开始时间基数
                Date diffBaseDate = null;
                if (i == 0) {
                    diffBaseDate = DateUtil.getDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
                    diff = DateUtil.diffDate(diffBaseDate, tPlays.get(i).getPlayStart());
                } else if (i == tPlays.size() - 1) {
                    diffBaseDate = tPlays.get(i).getPlayEnd();
                    diff = DateUtil.diffDate(tPlays.get(i).getPlayEnd(), DateUtil.getDate(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
                } else {
                    diffBaseDate = tPlays.get(i).getPlayEnd();
                    diff = DateUtil.diffDate(tPlays.get(i).getPlayEnd(), tPlays.get(i + 1).getPlayStart());
                }
                insertTelevision(sysTelevisionList, sysAdvertistList, diff, diffBaseDate, formId, formDate,minLength);
            }
            if(tPlays.size() == 0){
                Date diffBaseDate = DateUtil.getDate(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
                Long diff = 24*60*60L-1;
                insertTelevision(sysTelevisionList, sysAdvertistList, diff, diffBaseDate, formId, formDate,minLength);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void insertTelevision(List<TTelevision> sysTelevisionList,List<TTelevision> sysAdvertistList,Long diff,Date diffBaseDate,String formId,Date formDate,long minLength){
        while (diff > 0){
            int t1=0;
            int a1=0;
            //TODO 没办法跳出，只好设个时间跳出
            int dateTimeLimit = 24*60*60;
            int circleCount = 0;
            //间隔插入
            while(diff >= minLength && circleCount<dateTimeLimit) {
                for (; t1 < sysTelevisionList.size(); t1++) {
                    TTelevision tTelevision = sysTelevisionList.get(t1);
                    if (tTelevision.getTvLength() <= diff) {
                        insertTv(tTelevision, formId, formDate, new Date(diffBaseDate.getTime()), tTelevision.getTvLength());
                        diff -= tTelevision.getTvLength();
                        System.out.println("diff = " + diff);
                        diffBaseDate.setTime(diffBaseDate.getTime() + tTelevision.getTvLength() * 1000);
                        t1++;
                        break;
                    }
                }
                for (; a1 < sysAdvertistList.size(); a1++) {
                    TTelevision tTelevision = sysAdvertistList.get(a1);
                    if (tTelevision.getTvLength() <= diff) {
                        insertTv(tTelevision, formId, formDate, new Date(diffBaseDate.getTime()), tTelevision.getTvLength());
                        diff -= tTelevision.getTvLength();
                        System.out.println("diff = " + diff);
                        diffBaseDate.setTime(diffBaseDate.getTime() + tTelevision.getTvLength() * 1000);
                        a1++;
                        break;
                    }
                }
                if (t1 >= sysTelevisionList.size() && a1 >= sysAdvertistList.size()) {
                    t1 = 0;
                    a1 = 0;
                }
                circleCount += 1;
            }
            //最后就插入最前面一条
            if(diff < minLength){
                insertTv(sysTelevisionList.size()>0 ? sysTelevisionList.get(0) : sysAdvertistList.get(0),formId,formDate,new Date(diffBaseDate.getTime()),diff);
                diff = 0L;
                System.out.println("diff = " + diff);
                diffBaseDate.setTime(diffBaseDate.getTime()+diff*1000);
            }
        }
    }

    public TPlay insertTv(TTelevision tv,String formId,Date formDate,Date baseDate,Long diff){
        if(diff<=0){
            return null;
        }
        TPlay play = new TPlay();
        play.setPlayId(UUIDUtil.getUUIDSTR());
        play.setPlayForm(formId);
        play.setPlayVideo(tv.getTvLocation());
        play.setPlayScreen(tv.getTvScreen());
        play.setPlayTvDesc(tv.getTvDesc());
        play.setPlayTvName(tv.getTvName());
        play.setPlayTvId(tv.getTvId());
        play.setPlayDate(formDate);
        play.setPlayStart(baseDate);
        play.setPlayEnd(new Date(baseDate.getTime()+diff*1000));
        play.setPlayLength(diff);
        playMapper.insert(play);
        return play;
    }
}
