package com.tjm.mapper;

import com.tjm.pojo.ReceivedTask.TestRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper {

    int insertRecord(TestRecord testRecord);

    List<TestRecord> findAll(String check_id);

    TestRecord findDetail(int record_id);

    int updateRecord(int record_id,String text,int isSuitable );

    int sumQualified(String check_id);

    int sumNotQualified(String check_id);
}
