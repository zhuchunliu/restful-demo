package com.personal.healthcloud.http.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.personal.healthcloud.dto.JsonListResponseEntity;
import com.personal.healthcloud.dto.JsonResponseEntity;
import com.personal.healthcloud.dto.PhysicalIdentifyAPIEnity;
import com.personal.healthcloud.jpa.entity.PhysicalIdentify;
import com.personal.healthcloud.jpa.repository.PhysicalIdentifyRepository;
import com.personal.healthcloud.service.PhysicalIdentifyService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/physicalIdentify")
public class PhysicalIdentifyController {
    @Autowired
    private PhysicalIdentifyService physicalIdentifyService;

    @Autowired
    private PhysicalIdentifyRepository physicalIdentifyRepo;

    private static final ObjectMapper mapper = new ObjectMapper();

    @PostMapping
    public JsonResponseEntity<List<PhysicalIdentifyAPIEnity>> doPhysiqueIdentify(
            @RequestBody String request) throws IOException {

        JsonResponseEntity<List<PhysicalIdentifyAPIEnity>> response = new JsonResponseEntity<List<PhysicalIdentifyAPIEnity>>();

        JsonNode jsonNode = mapper.readTree(request);
        if(!jsonNode.has("openid") || !jsonNode.has("content") ||
                StringUtils.isEmpty(jsonNode.get("openid").asText()) || StringUtils.isEmpty(jsonNode.get("content").asText())){
            response.setCode(1001);
            response.setMsg("缺少必传字段openid或content");
            return response;
        }
        String openid =  jsonNode.get("openid").textValue();
        String content = jsonNode.get("content").textValue();

        List<PhysicalIdentifyAPIEnity> list = new ArrayList<PhysicalIdentifyAPIEnity>();
        String info = physicalIdentifyService.physiqueIdentify(openid ,content);
        if(null != info && !"".equals(info)){
            String[] arr = info.split(",");
            for (String physique : arr) {
                list.add(new PhysicalIdentifyAPIEnity(physique));
            }
        }
        response.setData(list);
        return response;
    }

    @GetMapping(value = "/list")
    public JsonListResponseEntity getPhysiqueIdentify(
            @RequestParam(required = true) String openid,
            @RequestParam(defaultValue = "1") Integer flag) {
        int pageSize = 10;
        Pageable pageable = new PageRequest(flag-1,pageSize, Sort.Direction.DESC,"createDate");
        List<PhysicalIdentify> list = physicalIdentifyRepo.findQuestionList(openid,pageable);
        Boolean hasMore = false;
        if(list.size() == pageSize && physicalIdentifyRepo.getTotalQuestion(openid) > pageSize * flag){
            hasMore = true;
            flag++;
        }

        List<Map> result = Lists.newArrayList();
        for(PhysicalIdentify question : list){
            ImmutableMap map = null;
            if(DateTime.now().toString("yyyy-MM-dd").equals(new DateTime(question.getCreateDate()).toString("yyyy-MM-dd"))){
                map = ImmutableMap.of("id",question.getId(),"time","今天",
                        "type",StringUtils.isEmpty(question.getResult())?"":question.getResult().split(",")[0]);
            }else{
                map = ImmutableMap.of("id",question.getId(),"time",new DateTime(question.getCreateDate()).toString("yyyy-MM-dd"),
                        "type",StringUtils.isEmpty(question.getResult())?"":question.getResult().split(",")[0]);
            }
            result.add(map);
        }

        JsonListResponseEntity response = new JsonListResponseEntity();
        response.setContent(result,hasMore,null,flag.toString());
        return response;
    }

    /**
     * 查看详情
     * @return
     */
    @GetMapping("/detail")
    public JsonResponseEntity<List<PhysicalIdentifyAPIEnity>> detail(
            @RequestParam String id) {

        JsonResponseEntity<List<PhysicalIdentifyAPIEnity>> response = new JsonResponseEntity<List<PhysicalIdentifyAPIEnity>>();
        PhysicalIdentify question = physicalIdentifyRepo.findOne(id);
        if(null == question){
            return null;
        }
        List<PhysicalIdentifyAPIEnity> list = new ArrayList<PhysicalIdentifyAPIEnity>();

        if(!StringUtils.isEmpty(question.getResult())){
            for (String physique : question.getResult().split(",")) {
                list.add(new PhysicalIdentifyAPIEnity(physique));
            }
        }
        response.setData(list);
        return response;
    }


    /**
     * 查看详情
     * @return
     */
    @GetMapping("/recent")
    public JsonResponseEntity<List<PhysicalIdentifyAPIEnity>> recent(
            @RequestParam String openid) {

        JsonResponseEntity<List<PhysicalIdentifyAPIEnity>> response = new JsonResponseEntity<List<PhysicalIdentifyAPIEnity>>();
        PhysicalIdentify healthQuestion = physicalIdentifyService.getRecentPhysicalIdentify(openid);
        List<PhysicalIdentifyAPIEnity> list = new ArrayList<PhysicalIdentifyAPIEnity>();
        if(null != healthQuestion && !StringUtils.isEmpty(healthQuestion.getResult())){
            for (String physique : healthQuestion.getResult().split(",")) {
                list.add(new PhysicalIdentifyAPIEnity(physique));
            }
        }
        response.setData(list);
        return response;
    }
}
