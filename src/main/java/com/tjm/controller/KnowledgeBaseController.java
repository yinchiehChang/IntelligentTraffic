package com.tjm.controller;

import com.tjm.pojo.*;
import com.tjm.pojo.base.*;
import com.tjm.pojo.quality.Indicator;
import com.tjm.pojo.testCase.FunctionPoint;
import com.tjm.service.MenuService;
import com.tjm.utils.ExcelUtils;
import com.tjm.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tjm.service.BaseService;
import com.tjm.service.AttachementService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

@Controller
public class KnowledgeBaseController {
    @Autowired
    private BaseService baseService;
    @Autowired
    AttachementService attachementService;
    @Autowired
    MenuService menuService;

    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

    @RequestMapping("/knowledgeBase")
    public String toKB(Model model, @RequestParam(value = "bid") int bid) {
        Base base = baseService.queryBaseById(bid).get(0);
        int type = base.getBase_type();
        model.addAttribute("bid",bid);
        model.addAttribute("bType", type);
        String baseType = "";
        switch (type) {
            case 0:

            case 2:
                baseType = "treeBase";
                break;

            case 1:
                baseType = "fileBase";
                break;

            case 3:
                baseType = "bugLibList";
                break;
        }

        return "knowledgeBase/" + baseType;
        //return "treeTest";
    }

    @RequestMapping("/KBClassList")
    @ResponseBody
    public Object BaseClassList() {
        List<BaseClass> baseClasses = baseService.queryAllBaseClass();

        Map<String, Object> ret = new HashMap<>();
        ret.put("baseClasses", baseClasses);

        return ret;
    }

    @RequestMapping("/dossier")
    public String toDossier(Model model, @RequestParam(value = "baseClass") int class_num) {
        Collection<Base> bases = baseService.queryBaseByClass(class_num);
        BaseClass baseClass = baseService.queryBaseClassByClassNum(class_num);
        //System.out.println(bases);
        model.addAttribute("bases", bases);
        model.addAttribute("baseClass", baseClass);

        return "/dossier";
    }

    @RequestMapping("/toBugDetail")
    public String toBugDetail(Model model, @RequestParam(value = "bid") int bid, @RequestParam(value = "baseId") int baseId) {
        BugLib bugLib = new BugLib();
        if(bid == -1) {
            bugLib.setBase_id(baseId);
            model.addAttribute("status", 1);
        } else {
            bugLib = baseService.queryBugLibById(bid);
            model.addAttribute("status", 0);
        }
        model.addAttribute("bugLib", bugLib);

        return "knowledgeBase/bugLibDetail";
    }

    @RequestMapping("/KBtreePage")
    @ResponseBody
    public Object KBtreePage(@RequestParam(value = "bid") int baseId) {
        Collection<Base> base = baseService.queryBaseById(baseId);
        Collection<BaseSecurity> securities = baseService.querySecurityByBaseId(baseId);
        Collection<BaseArticle> articles = baseService.queryArticleByBaseId(baseId);
        Collection<BasePoint> points = baseService.queryPointByBaseId(baseId);
        Collection<BaseItem> items = baseService.queryItemByBaseId(baseId);

        Map<String,Object> args = new HashMap<String,Object>();
        args.put("base", base);
        args.put("securities", securities);
        args.put("articles", articles);
        args.put("points", points);
        args.put("items", items);

        return args;
    }

    @RequestMapping("/KBtreePage2")
    @ResponseBody
    public Object KBtreePage2(@RequestParam(value = "bid") int baseId) {
        List<Base> base = baseService.queryBaseById(baseId);

        BaseNode root = BaseNode.createTree();
        List<BaseNode> nodeLv1 = baseService.queryNodeLv1ByBaseName(base.get(0).getBase_name());
        root.setSubNode(nodeLv1);
        Map<String, List> info = new HashMap<>();
        info.put("indicators", new ArrayList<Indicator>());
        info.put("functionPoints", new ArrayList<FunctionPoint>());
        info.put("checkItems", new ArrayList<CheckItem>());

        for (BaseNode nLv1: nodeLv1 ) {
            if(nLv1.getLeaf() == 1) {//该节点是叶节点，搜索该节点对应的详细信息
                addInfo(nLv1, info);
            } else {//不是叶节点，搜索子节点
                List<BaseNode> nodeLv2 = baseService.queryNodeLv2BySuperId(nLv1.getNode_id());
                nLv1.setSubNode(nodeLv2);
                for (BaseNode nLv2: nodeLv2) {
                    if(nLv2.getLeaf() == 1) {
                        addInfo(nLv2, info);
                    } else {
                        List<BaseNode> nodeLv3 = baseService.queryNodeLv3BySuperId(nLv2.getNode_id());
                        nLv2.setSubNode(nodeLv3);
                        for(BaseNode nLv3: nodeLv3) {//目前最深只有三层，所以一定是叶节点
                            addInfo(nLv3, info);
                        }
                    }

                }
            }
        }

        Map<String,Object> args = new HashMap<String,Object>();
        args.put("base", base);
        args.put("root", root);
        args.put("info", info);

        return args;
    }

    public void addInfo(BaseNode node, Map<String, List> info) {
        switch (node.getInfo_table()) {
            case "INDICATOR":
                info.get("indicators").add(baseService.queryIndicatorById(node.getInfo_id()));
                break;

            case "FUNCTION_POINT":
                info.get("functionPoints").add(baseService.queryFunctionPointById(node.getInfo_id()));
                break;

            case "CHECK_ITEM":
                info.get("checkItems").add(baseService.queryCheckItemById(node.getInfo_id()));
        }
    }

    @RequestMapping("/KBquery")
    @ResponseBody
    public Object KBquery(@RequestParam(value="kw") String keyword, @RequestParam(value="obj") String object, @RequestParam(value="sl") String securityLevel) {
        //System.out.println(keyword + ' ' + object + ' ' + securityLevel);
        Collection<BaseItem> items = baseService.queryItemByInput("%"+object+"%", "%"+keyword+"%", securityLevel);
        for (BaseItem item : items) {
            BasePoint point =  baseService.queryPointById(item.getPoint_id()).get(0);
            item.setPoint_name(point.getPoint_name());

            BaseArticle article = baseService.queryArticleById(point.getArticle_id()).get(0);
            item.setArticle_name(article.getArticle_name());

            BaseSecurity security = baseService.querySecurityById(article.getSecurity_id()).get(0);
            item.setAqjb(security.getSecurity_level());

            Base base = baseService.queryBaseById(security.getBase_id()).get(0);
            item.setBase_name(base.getBase_name());
        }

        return items;
    }

    @RequestMapping("/KBquery2")
    @ResponseBody
    public Object KBquery2(@RequestParam(value="lv1") String lv1, @RequestParam(value="lv2") String lv2, @RequestParam(value="lv3") String lv3, @RequestParam(value="MaxLevel") int MaxLevel) {
        ArrayList<Base> baseList = new ArrayList<>();
        ArrayList<BaseNode> lv1NodesList = new ArrayList<>();
        ArrayList<BaseNode> lv2NodesList = new ArrayList<>();
        ArrayList<BaseNode> lv3NodesList = new ArrayList<>();
        Map<String, List> info = new HashMap<>();
        info.put("indicators", new ArrayList<Indicator>());
        info.put("functionPoints", new ArrayList<FunctionPoint>());
        info.put("checkItems", new ArrayList<CheckItem>());
        switch (MaxLevel) {
            case 2:
                lv2NodesList = (ArrayList<BaseNode>) baseService.queryNodeLv2ByInput("%"+lv1+"%", "%"+lv2+"%");
                for(BaseNode node: lv2NodesList) {
                    addInfo(node, info);
                    BaseNode lv1Node = baseService.findSuperNodeByLv2(node.getSuper_id());
                    lv1NodesList.add(lv1Node);
                    baseList.add(baseService.queryBaseById(lv1Node.getSuper_id()).get(0));
                }
                break;

            case 3:
                lv3NodesList = (ArrayList<BaseNode>) baseService.queryNodeLv3ByInput("%"+lv1+"%", "%"+lv2+"%", "%"+lv3+"%");
                for(BaseNode node: lv3NodesList) {
                    addInfo(node, info);
                    BaseNode lv2Node = baseService.findSuperNodeByLv3(node.getSuper_id());
                    lv2NodesList.add(lv2Node);
                    BaseNode lv1Node = baseService.findSuperNodeByLv2(lv2Node.getSuper_id());
                    lv1NodesList.add(lv1Node);
                    baseList.add(baseService.queryBaseById(lv1Node.getSuper_id()).get(0));
                }
                break;
        }

        Map<String, Object> res = new HashMap<>();
        res.put("baseList", baseList);
        res.put("lv1Nodes", lv1NodesList);
        res.put("lv2Nodes", lv2NodesList);
        res.put("lv3Nodes", lv3NodesList);
        res.put("info", info);
        return res;
    }

    @RequestMapping(value = "/KBItemUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> KBItemUpdate(@RequestBody BaseItem item) {
        baseService.updateItem(item);

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");

        return res;
    }

    @RequestMapping(value = "/KBIndicatorUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> KBIndicatorUpdate(@RequestBody Indicator indicator) {
        baseService.updateIndicator(indicator);

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");

        return res;
    }

    @RequestMapping(value = "/KBFunctionPointUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> KBFunctionPointUpdate(@RequestBody FunctionPoint functionPoint) {
        baseService.updateFunctionPoint(functionPoint);

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");

        return res;
    }

    @RequestMapping(value = "/KBCheckItemUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> KBCheckItemUpdate(@RequestBody CheckItem checkItem) {
        baseService.updateCheckItem(checkItem);

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");

        return res;
    }

    @RequestMapping(value = "/KBBugLibUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> KBBugLibUpdate(@RequestBody BugLib bugLib) {
        baseService.updateBugLib(bugLib);

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");

        return res;
    }

    @RequestMapping("/KBItemDelete")
    @ResponseBody
    public Map<String,Object> KBItemDelete(@RequestBody Map<String, Integer> msg) {
        BaseItem item = baseService.queryItemById(msg.get("item_id")).get(0);
        baseService.deleteItem(msg.get("item_id"));

        Collection<BaseItem> itemsInSamePoint = baseService.queryItemByPointId(item.getPoint_id());

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");
        res.put("itemsInSamePoint", itemsInSamePoint.size());
        res.put("point_id", item.getPoint_id());

        return res;
    }

    @RequestMapping("/KBIndicatorDelete")
    @ResponseBody
    public Map<String,Object> KBIndicatorDelete(@RequestBody Map<String, Integer> msg) {
        Indicator indicator = baseService.queryIndicatorById(msg.get("indicatorId"));
        baseService.deleteIndicatorById(msg.get("indicatorId"));

        BaseNode node3 = baseService.queryNodeByInfo("3", "INDICATOR", msg.get("indicatorId"));
        baseService.deleteNodeById("3", node3.getNode_id());
        int node3Size = baseService.queryNodeWithSameSuperId("3", node3.getSuper_id()).size();
        System.out.println(node3Size);

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");
        res.put("node3Size", node3Size);
        res.put("superId", node3.getSuper_id());

        return res;
    }

    @RequestMapping("/KBFunctionPointDelete")
    @ResponseBody
    public Map<String,Object> KBFunctionPointDelete(@RequestBody Map<String, Integer> msg) {
        FunctionPoint functionPoint = baseService.queryFunctionPointById(msg.get("fid"));
        baseService.deleteFunctionPointById(msg.get("fid"));

        BaseNode node3 = baseService.queryNodeByInfo("3", "FUNCTION_POINT", msg.get("fid"));
        baseService.deleteNodeById("3", node3.getNode_id());
        int node3Size = baseService.queryNodeWithSameSuperId("3", node3.getSuper_id()).size();

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");
        res.put("node3Size", node3Size);
        res.put("superId", node3.getSuper_id());

        return res;
    }

    @RequestMapping("/KBCheckItemDelete")
    @ResponseBody
    public Map<String,Object> KBCheckItemDelete(@RequestBody Map<String, Integer> msg) {
        CheckItem checkItem = baseService.queryCheckItemById(msg.get("cid"));
        baseService.deleteCheckItemById(msg.get("cid"));

        BaseNode node2 = baseService.queryNodeByInfo("2", "CHECK_ITEM", msg.get("cid"));
        baseService.deleteNodeById("2", node2.getNode_id());
        int node2Size = baseService.queryNodeWithSameSuperId("2", node2.getSuper_id()).size();

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");
        res.put("node2Size", node2Size);
        res.put("superId", node2.getSuper_id());

        return res;
    }

    @RequestMapping("/KBNodeDelete")
    @ResponseBody
    public Map<String,Object> KBNodeDelete(@RequestBody Map<String, Integer> msg) {
        Map<String,Object> res = new HashMap<>();

        if(msg.get("level") > 1) {
            BaseNode node = baseService.queryNodeById(String.valueOf(msg.get("level")), msg.get("nodeId"));
            baseService.deleteNodeById(String.valueOf(msg.get("level")), node.getNode_id());
            int nodeSize = baseService.queryNodeWithSameSuperId(String.valueOf(msg.get("level")-1), node.getSuper_id()).size();
            res.put("result", "success");
            res.put("nodeSize", nodeSize);
            res.put("superId", node.getSuper_id());
            res.put("nextLevel", (msg.get("level")-1));
        } else if(msg.get("level") == 1){
            baseService.deleteNodeById(String.valueOf(msg.get("level")), msg.get("nodeId"));
            res.put("result", "end");
            res.put("nodeSize", -1);
            res.put("superId", -1);
            res.put("nextLevel", -1);
        } else{
            res.put("result", "end");
            res.put("nodeSize", -1);
            res.put("superId", -1);
            res.put("nextLevel", -1);
        }
        return res;
    }

    @RequestMapping("/KBPointDelete")
    @ResponseBody
    public Map<String,Object> KBPointDelete(@RequestBody Map<String, Integer> msg) {
        BasePoint point = baseService.queryPointById(msg.get("point_id")).get(0);
        baseService.deletePoint(msg.get("point_id"));

        Collection<BasePoint> pointsInSameArticle = baseService.queryPointByArticleId(point.getArticle_id());

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");
        res.put("pointsInSameArticle", pointsInSameArticle.size());
        res.put("article_id", point.getArticle_id());

        return res;
    }

    @RequestMapping("/KBArticleDelete")
    @ResponseBody
    public Map<String,Object> KBArticleDelete(@RequestBody Map<String, Integer> msg) {
        baseService.deleteArticle(msg.get("article_id"));

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");

        return res;
    }

    @RequestMapping("/KBBugLibDelete")
    @ResponseBody
    public Map<String,Object> KBBugLibDelete(@RequestBody Map<String, Integer> msg) {
        baseService.deleteBugLibById(msg.get("bid"));

        Map<String,Object> res = new HashMap<>();
        res.put("result", "success");

        return res;
    }

    @RequestMapping("/KBAddBaseClass")
    @ResponseBody
    public Map<String,Object> KBAddBaseClass(@RequestBody Map<String, String> msg) {
        List<BaseClass> baseClasses = baseService.queryAllBaseClass();
        Map<String,Object> res = new HashMap<>();
        for (BaseClass baseClass : baseClasses) {
            if(baseClass.getName().equals(msg.get("name"))) {
                res.put("result", 1);
                return res;
            }
        }
        BaseClass newClass = new BaseClass();
        newClass.setName(msg.get("name"));
        baseService.insertBaseClass(newClass);
        menuService.insertSysMenu(new Menu(0, 4, msg.get("name"), "/dossier?baseClass=" + baseService.getClassNumByName(msg.get("name")), "#", new ArrayList<Menu>()));

        res.put("result", 0);
        return res;
    }

    @RequestMapping("/KBBaseClassDelete")
    @ResponseBody
    public Map<String,Object> KBDeleteBaseClass(@RequestBody Map<String, Integer> msg) {
        BaseClass baseClass = baseService.queryBaseClassByClassNum(msg.get("class_num"));
        baseService.deleteBaseClassByClassNum(msg.get("class_num"));

        menuService.deleteSysMenu(baseClass.getName());

        Map<String,Object> res = new HashMap<>();
        res.put("result", 0);

        return res;
    }

    @RequestMapping("/KBAddBase")
    @ResponseBody
    public Map<String,Object> KBAddBase(@RequestBody Base newBase) {
        Collection<Base> base = baseService.queryBaseByName(newBase.getBase_name());

        Map<String,Object> res = new HashMap<>();
        if(base.size() == 0) {
            //System.out.println(newBase.getBase_name() + " " + newBase.getBase_class());
            baseService.insertBase(newBase);
            res.put("result", 0);
        } else {
            res.put("result", 1);
        }

        return res;
    }

    @RequestMapping("/KBDeleteBase")
    @ResponseBody
    public Map<String,Object> KBDeleteBase(@RequestBody Map<String, Integer> msg) {
        baseService.deleteBase(msg.get("base_id"));

        Map<String,Object> res = new HashMap<>();
        res.put("result", 0);

        return res;
    }

    @RequestMapping("/KBAddItem")
    @ResponseBody
    public Map<String,Object> KBAddItem(@RequestBody BaseItem item) {
//        System.out.println(item.getPoint_name() + "  " + item.getArticle_name() + "  " + item.getSecurity_level());
        int baseId = baseService.queryBaseByName(item.getBase_name()).get(0).getBase_id();
        List<BaseSecurity> security = baseService.querySecurityByLevel(item.getAqjb(), String.valueOf(baseId));

        if(security.isEmpty()) {
            cascadeInsert(0, baseId, item);
        } else {
            int securityId = security.get(0).getSecurity_id();
            List<BaseArticle> article = baseService.queryArticleByName(item.getArticle_name(), String.valueOf(securityId));
            if(article.isEmpty()) {
                cascadeInsert(1, securityId, item);
            } else {
                int articleId = article.get(0).getArticle_id();
                List<BasePoint> point = baseService.queryPointByName(item.getPoint_name(), String.valueOf(articleId));
                if(point.isEmpty()) {
                    cascadeInsert(2, articleId, item);
                } else {
                    cascadeInsert(3, point.get(0).getPoint_id(), item);
                }
            }
        }

        Map<String,Object> res = new HashMap<>();
        res.put("result", 0);

        return res;
    }

    public void cascadeInsert(int insCase, int id, BaseItem item) {
        BaseSecurity security = new BaseSecurity();
        BaseArticle article = new BaseArticle();
        BasePoint point = new BasePoint();


        switch(insCase) {
            case 0:
                security.setSecurity_level(item.getAqjb());
                security.setBase_id(id);
                baseService.insertSecurity(security);

            case 1:
                article.setArticle_name(item.getArticle_name());
                article.setSecurity_id(insCase == 1 ? id : security.getSecurity_id());
                baseService.insertArticle(article);

            case 2:
                point.setPoint_name(item.getPoint_name());
                point.setArticle_id(insCase == 2 ? id : article.getArticle_id());
                baseService.insertPoint(point);

            case 3:
                item.setPoint_id(insCase == 3 ? id : point.getPoint_id());
                baseService.insertItem(item);
        }
    }

    @RequestMapping("/KBAddIndicator")
    @ResponseBody
    public Map<String,Object> KBAddIndicator(@RequestBody Map<String, String> msg) {
        int baseId = baseService.queryBaseByName(msg.get("base")).get(0).getBase_id();
        BaseNode lv1Node = baseService.findNodeByNameAndSuperId("1", msg.get("lv1"), baseId);
        Indicator indicator = new Indicator(0, msg.get("lv3"), msg.get("description"), msg.get("molecular"), msg.get("denominator"));

        if(lv1Node == null) {
            cascadeInsertNode(3,1, baseId, msg, "INDICATOR");
        } else {
            BaseNode lv2Node = baseService.findNodeByNameAndSuperId("2", msg.get("lv2"), lv1Node.getNode_id());
            if(lv2Node == null) {
                cascadeInsertNode(3, 2, lv1Node.getNode_id(), msg, "INDICATOR");
            } else {
                baseService.insertIndicator(indicator);
                baseService.insertNode("3", lv2Node.getNode_id(), msg.get("lv3"), "No Descriptions", 1, "INDICATOR", indicator.getIndicatorId());
            }
        }

        Map<String,Object> res = new HashMap<>();
        res.put("result", 0);

        return res;
    }

    @RequestMapping("/KBAddFunctionPoint")
    @ResponseBody
    public Map<String,Object> KBAddFunctionPoint(@RequestBody Map<String, String> msg) {
        int baseId = baseService.queryBaseByName(msg.get("base")).get(0).getBase_id();
        BaseNode lv1Node = baseService.findNodeByNameAndSuperId("1", msg.get("lv1"), baseId);
        FunctionPoint functionPoint = new FunctionPoint(msg.get("serial"), msg.get("description"));

        if(lv1Node == null) {
            cascadeInsertNode(3,1, baseId, msg, "FUNCTION_POINT");
        } else {
            BaseNode lv2Node = baseService.findNodeByNameAndSuperId("2", msg.get("lv2"), lv1Node.getNode_id());
            if(lv2Node == null) {
                cascadeInsertNode(3, 2, lv1Node.getNode_id(), msg, "FUNCTION_POINT");
            } else {
                baseService.insertFunctionPoint(functionPoint);
                baseService.insertNode("3", lv2Node.getNode_id(), msg.get("lv3"), "No Descriptions", 1, "FUNCTION_POINT", functionPoint.getFid());
            }
        }

        Map<String,Object> res = new HashMap<>();
        res.put("result", 0);

        return res;
    }

    @RequestMapping("/KBAddCheckItem")
    @ResponseBody
    public Map<String,Object> KBAddCheckItem(@RequestBody Map<String, String> msg) {
        int baseId = baseService.queryBaseByName(msg.get("base")).get(0).getBase_id();
        BaseNode lv1Node = baseService.findNodeByNameAndSuperId("1", msg.get("lv1"), baseId);
        CheckItem checkItem = new CheckItem(msg.get("serial"), msg.get("advice"), Integer.parseInt(msg.get("weight")));

        if(lv1Node == null) {
            cascadeInsertNode(2,1, baseId, msg, "CHECK_ITEM");
        } else {
            baseService.insertCheckItem(checkItem);
            baseService.insertNode("2", lv1Node.getNode_id(), msg.get("lv2"), "No Descriptions", 1, "CHECK_ITEM", checkItem.getCid());
        }

        Map<String,Object> res = new HashMap<>();
        res.put("result", 0);

        return res;
    }

    public void cascadeInsertNode(int maxLevel, int level, int superId, Map<String, String> msg, String infoTable) {
        int lv1Id = 0, lv2Id = 0, lv3Id = 0;
        switch(level) {
            case 1:
                baseService.insertNode("1", superId, msg.get("lv1"), "", 0, "NULL", -1);
                lv1Id = baseService.findNodeByNameAndSuperId("1", msg.get("lv1"), superId).getNode_id();

            case 2:
                superId = (level == 2 ? superId : lv1Id);
                if(maxLevel == 2) {
                    int infoId = insertInfo(infoTable, msg);
                    baseService.insertNode("2", superId, msg.get("lv2"), "No Descriptions", 1, infoTable, infoId);
                    return;
                } else {
                    baseService.insertNode("2", superId, msg.get("lv2"), "No Descriptions", 0, "NULL", -1);
                    lv2Id = baseService.findNodeByNameAndSuperId("2", msg.get("lv2"), superId).getNode_id();
                    int infoId = insertInfo(infoTable, msg);
                    baseService.insertNode("3", lv2Id, msg.get("lv3"), "No Descriptions", 1, infoTable, infoId);
                }

        }
    }

    public int insertInfo(String infoTable, Map<String, String> msg) {
        int id = 0;
        switch (infoTable) {
            case "INDICATOR":
                Indicator indicator = new Indicator(0, msg.get("lv3"), msg.get("description"), msg.get("molecular"), msg.get("denominator"));
                baseService.insertIndicator(indicator);
                id = indicator.getIndicatorId();
                break;

            case "FUNCTION_POINT":
                FunctionPoint functionPoint = new FunctionPoint(msg.get("serial"), msg.get("description"));
                baseService.insertFunctionPoint(functionPoint);
                id = functionPoint.getFid();
                break;

            case "CHECK_ITEM":
                CheckItem checkItem = new CheckItem(msg.get("serial"), msg.get("advice"), Integer.parseInt(msg.get("weight")));
                baseService.insertCheckItem(checkItem);
                id = checkItem.getCid();
                break;
        }

        return id;
    }

    @RequestMapping("/KBAddBugLib")
    @ResponseBody
    public Map<String,Object> KBAddBugLib(@RequestBody BugLib bugLib) {
        baseService.insertBugLib(bugLib);

        Map<String,Object> res = new HashMap<>();
        res.put("result", 0);

        return res;
    }

    @RequestMapping("/KBfilePage")
    @ResponseBody
    public Object KBfilePage(@RequestParam(value = "bid") int baseId) {
        Collection<Base> base = baseService.queryBaseById(baseId);

        Collection<Attachement> attachements = attachementService.queryFileByPath("%KBFile\\" + String.valueOf(baseId) + "%");

        Map<String,Object> args = new HashMap<String,Object>();
        args.put("base", base);
        args.put("attachements", attachements);

        return args;
    }

    @RequestMapping(value = "/uploadKBFile")
    @ResponseBody
    public Map<String,Object> upLoadKBFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file, @RequestParam(value = "bid") int bid) throws Exception{
        resultMap.put("status", 400);
        if(file!=null&&file.length>0){
            //组合image名称，“;隔开”
            List<String> fileName =new ArrayList<String>();
            PrintWriter out = null;
            try {
                for (int i = 0; i < file.length; i++) {
                    if (!file[i].isEmpty()) {
                        //上传文件，随机名称，";"分号隔开
                        Attachement attachement = FileUtil.uploadKBFile(request, "/" + String.valueOf(bid), file[i], false, "/src/main/resources/KBFile/");
                        fileName.add(attachement.getUri());
                        attachementService.insertAttachement(attachement);
                    }
                }
                //上传成功
                if(fileName!=null&&fileName.size()>0){
                    System.out.println("上传成功！");
                    resultMap.put("status", 200);
                    resultMap.put("message", "上传成功！");
                }else {
                    resultMap.put("status", 500);
                    resultMap.put("message", "上传失败！文件格式错误！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("status", 500);
                resultMap.put("message", "上传异常！");
            }
        }else {
            resultMap.put("status", 500);
            resultMap.put("message", "没有检测到有效文件！");
        }
        return resultMap;
    }

    @RequestMapping("/KBDeleteFile")
    @ResponseBody
    public Object KBDeleteFile(@RequestBody Map<String, String> msg) {
        System.out.println(msg.get("uid"));
        Attachement file = attachementService.queryFileByUid(msg.get("uid"));
        Map<String,Object> res = new HashMap<String,Object>();

        if(FileUtil.removeKBFile(file.getUri())) {
            attachementService.deleteFileByUid(msg.get("uid"));
            res.put("result", "success");
        } else {
            System.out.println("文件删除失败");
            res.put("result", "fail");
        }

        return res;
    }

    @RequestMapping(value = "/KBDownloadFile", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String KBDownloadFile(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "bid") int bid,  @RequestParam(value = "fileName") String fileName) {
        String path = new FileSystemResource("").getFile().getAbsolutePath();
        String url = path + "/src/main/resources/KBFile/" + String.valueOf(bid) + "/" + fileName;//拼接得到文件完整路径

        if (url != null) {
            //设置文件路径
            File file = new File(url);
            if (file.exists()) {
                response.setContentType("application/octet-stream");
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    @RequestMapping("/KBbugLibList")
    @ResponseBody
    public Object KBbugLibList(@RequestParam(value = "bid") int baseId) {
        List<Base> base = baseService.queryBaseById(baseId);
        List<BugLib> bugLibs = baseService.queryBugLibByBaseName(base.get(0).getBase_name());

        Map<String,Object> args = new HashMap<String,Object>();
        args.put("base", base);
        args.put("bugItems", bugLibs);

        return args;
    }
}
