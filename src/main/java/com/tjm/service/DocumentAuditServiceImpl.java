package com.tjm.service;

import com.tjm.mapper.DocumentAuditMapper;
import com.tjm.pojo.Document_audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentAuditServiceImpl implements DocumentAuditService{

    @Autowired
    DocumentAuditMapper documentAuditMapper;

    @Override
    public int insertDocumentAudit(Document_audit document_audit) {
        return documentAuditMapper.insertDocumentAudit(document_audit);
    }

    @Override
    public Document_audit findByTaskId(int uid) {
        return documentAuditMapper.findByTaskId(uid);
    }

    @Override
    public int updateAudit(Document_audit document_audit) {
        return documentAuditMapper.updateAudit(document_audit);
    }

    @Override
    public int delDocument(int id) {
        return documentAuditMapper.delDocument(id);
    }

    @Override
    public List<Document_audit> findDocuments(int uid) {
        return documentAuditMapper.findDocuments(uid);
    }

    @Override
    public List<Document_audit> findAllSuccess() {
        return documentAuditMapper.findAllSuccess();
    }
}
