package com.tjm.service;

import com.tjm.pojo.ReceivedTask.TestRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecordService {

    int insertRecord(TestRecord testRecord);

    List<TestRecord> findAll(String check_id);

    TestRecord findDetail(int record_id);

    int updateRecord(int record_id,String text,int isSuitable );

    int sumQualified(String check_id);

    int sumNotQualified(String check_id);
}
