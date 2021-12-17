package com.tjm.mapper;

import com.tjm.pojo.Document_audit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DocumentAuditMapper {
    int insertDocumentAudit(Document_audit document_audit);

    Document_audit findByTaskId(int uid);

    int updateAudit(Document_audit document_audit);

    int delDocument(int id);

    List<Document_audit> findDocuments(int uid);

    List<Document_audit> findAllSuccess();
}
