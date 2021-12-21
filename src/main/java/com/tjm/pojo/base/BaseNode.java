package com.tjm.pojo.base;

import java.util.ArrayList;
import java.util.List;

public class BaseNode {
    private int node_id;
    private int super_id;
    private String node_name;
    private int leaf;
    private String description = "No Description";
    private String info_table;
    private int info_id;
    private List<BaseNode> subNode;

    public BaseNode(int node_id, int super_id, String node_name, int leaf, String description, String info_table, int info_id) {
        this.node_id = node_id;
        this.super_id = super_id;
        this.node_name = node_name;
        this.leaf = leaf;
        this.description = description;
        this.info_table = info_table;
        this.info_id = info_id;
        this.subNode = new ArrayList<>();
    }

    public BaseNode(int node_id, int super_id, String node_name, int leaf, String description, String info_table, int info_id, List<BaseNode> subNode) {
        this.node_id = node_id;
        this.super_id = super_id;
        this.node_name = node_name;
        this.leaf = leaf;
        this.description = description;
        this.info_table = info_table;
        this.info_id = info_id;
        this.subNode = subNode;
    }

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public int getSuper_id() {
        return super_id;
    }

    public void setSuper_id(int super_id) {
        this.super_id = super_id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public int getLeaf() {
        return leaf;
    }

    public void setLeaf(int leaf) {
        this.leaf = leaf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInfo_table() {
        return info_table;
    }

    public void setInfo_table(String info_table) {
        this.info_table = info_table;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public List<BaseNode> getSubNode() {
        return subNode;
    }

    public void setSubNode(List<BaseNode> subNode) {
        this.subNode = subNode;
    }

    public static BaseNode createTree() {
        return new BaseNode(0, 0, "root", 0, "root", "", -1, new ArrayList<BaseNode>());
    }
}
