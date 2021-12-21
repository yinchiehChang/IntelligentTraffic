package com.tjm.service;

import com.tjm.pojo.ReceivedTask.TestRecord;
import com.tjm.pojo.Record_item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecordService {

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
