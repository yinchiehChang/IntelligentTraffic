package com.tjm.mapper;

import com.tjm.pojo.ReceivedTask.TestRecord;
import com.tjm.pojo.Record_item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper {

    int insertRecord(TestRecord testRecord);

    int insertRecordItems(Record_item record_point);

    List<TestRecord> findAll(String check_id);

    List<TestRecord> findAllError(String check_id);

    List<TestRecord> findByItemCheck(String check_id,int item_id);

    List<Record_item> findAllItems(String check_id);

    TestRecord findDetail(int record_id);

    int updateRecord(int record_id,String text,int isSuitable );

    int sumQualified(String check_id);

    int sumNotQualified(String check_id);
}
