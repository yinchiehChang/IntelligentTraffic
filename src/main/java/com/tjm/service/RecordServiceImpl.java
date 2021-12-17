package com.tjm.service;

import com.tjm.mapper.RecordMapper;
import com.tjm.pojo.ReceivedTask.TestRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    RecordMapper recordMapper;

    @Override
    public int insertRecord(TestRecord testRecord) {
        return recordMapper.insertRecord(testRecord);
    }

    @Override
    public List<TestRecord> findAll(String check_id) {
        return recordMapper.findAll(check_id);
    }

    @Override
    public TestRecord findDetail(int record_id) {
        return recordMapper.findDetail(record_id);
    }

    @Override
    public int updateRecord(int record_id,String text, int isSuitable) {
        return recordMapper.updateRecord(record_id,text,isSuitable);
    }

    @Override
    public int sumQualified(String check_id) {
        return recordMapper.sumQualified(check_id);
    }

    @Override
    public int sumNotQualified(String check_id) {
        return recordMapper.sumNotQualified(check_id);
    }
}
