package com.tjm.service;

import com.tjm.pojo.Document_audit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocumentAuditService {

    int insertDocumentAudit(Document_audit document_audit);

    Document_audit findByTaskId(int uid);

    int updateAudit(Document_audit document_audit);

    //根据任务id删除审核表中对应信息
    int delDocument(int id);

    List<Document_audit> findDocuments(int uid);

    List<Document_audit> findAllSuccess();
}

