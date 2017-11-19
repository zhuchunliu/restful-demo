package com.personal.healthcloud.jpa.repository;

import com.personal.healthcloud.jpa.entity.PhysicalIdentify;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PhysicalIdentifyRepository extends JpaRepository<PhysicalIdentify, String> {

    @Query(value="select count(hq) from PhysicalIdentify hq where openid=?1 and result is not null  and result != '' and delFlag = '0'")
    Integer getTotalQuestion(String openid);

    @Query(value="select hq from PhysicalIdentify hq where openid=?1 and result is not null and result != '' and delFlag = '0'")
    List<PhysicalIdentify> findQuestionList(String openid, Pageable pageable);

    @Query(nativeQuery = true,value="select * from app_tb_physical_identify  " +
            " where openid=?1 and del_flag = '0' and result is not null  and result != '' order by create_date desc limit 1")
    PhysicalIdentify findRecent(String openid);
}
