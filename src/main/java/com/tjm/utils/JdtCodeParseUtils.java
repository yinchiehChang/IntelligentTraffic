package com.tjm.utils;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdtCodeParseUtils {

    /**
     * 获得java源文件的结构CompilationUnit
     * @param filePath
     * @return
     * @throws Exception
     */
    public static CompilationUnit getCompilationUnit(String filePath) throws Exception {
        ASTParser astParser = ASTParser.newParser(AST.JLS16); // 非常慢
        //读取文件
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
        byte[] input = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(input);
        bufferedInputStream.close();
        astParser.setSource(new String(input).toCharArray());
        /**/
        CompilationUnit result = (CompilationUnit) (astParser.createAST(null)); // 很慢
        //通过result去获取java文件的属性，如getImports是获取java文件中import的文件的。

        return result;

    }

    /**
     * 获取代码行
     * @param src
     * @return
     */
    public static long getLOC(String src) {
        //代码行
        Path path = Paths.get(src);
        long loc = 0;
        try {
            loc = Files.lines(path).count() + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("代码行： " + loc);
        return loc;
    }

    /**
     * 获取有效代码行
     * @param cu
     * @return
     */
    public static int getValidLines(CompilationUnit cu) {
        String[] validLines = cu.toString().split("\\r?\\n");
//        System.out.println("有效代码行： " + validLines.length);
        return validLines.length;
    }

    /**
     * 获取注释行
     * @param cu
     * @return
     */
    public static int getCommentLines(CompilationUnit cu) {
        List commentList = cu.getCommentList();
//        System.out.println("注释行： " + commentList.size());
        return commentList.size();
    }


    /**
     *获取代码解析信息
     * @param dir
     * @return
     * @throws Exception
     */
    public static Map<String, Integer> getCodeInfo(File dir) throws Exception {
        Map<String, Integer> map = new HashMap<>();

        List<File> fileLists = (List<File>) FileUtils.listFiles(dir,new String[]{"java"},true);//列出该目录下的所有java文件，递归（扩展名不必带.java）
//        List<File> fileLists = (List<File>)FileUtils.listFiles(dir,null,true);//列出该目录下的所有文件，递归
//        List<File> fileLists = (List<File>) FileUtils.listFiles(dir,null,false);//列出该目录下的所有文件，不递归
        int totalLOC = 0;
        int totalValidLines = 0;
        int totalCommentLines = 0;
        List<String> fullClassNameList = new ArrayList<>();
        List<List<String>> fullClassImportList = new ArrayList<>();
        Integer maxFanOut = 0;
        Integer maxFanIn = 0;
        String maxFanOutClass = "";
        String maxFanInClass = "";

        for (File file : fileLists) {
            String path = file.getAbsolutePath();
//            System.out.println(path);
            CompilationUnit cu = getCompilationUnit(path);
            //代码行
            long loc = getLOC(path);
//            System.out.println("代码行： " + loc);
            totalLOC += loc;
            //有效代码行
            int validLines = getValidLines(cu);
//            System.out.println("有效代码行： " + validLines);
            totalValidLines += validLines;
            //注释行
            int commentLines = getCommentLines(cu);
//            System.out.println("注释行： " + commentLines);
            totalCommentLines += commentLines;
            //扇入扇出
//            System.out.println("=================全类名====================");
            String classFullName = getClassFullName(cu, file);
//            System.out.println(classFullName);
            fullClassNameList.add(classFullName);
//            System.out.println("=================导入列表====================");
            List<String> importName = getImportName(cu);
            fullClassImportList.add(importName);
        }

        maxFanOut = getMaxFanOut(fullClassImportList, fullClassNameList);
        maxFanIn = getMaxFanIn(fullClassImportList, fullClassNameList);

        map.put("LOC", totalLOC);
        map.put("ValidLine", totalValidLines);
        map.put("CommentLine",totalCommentLines);
        map.put("MaxFanOut", maxFanOut);
        map.put("MaxFanIn", maxFanIn);

        return map;
    }

    /**
     *
     * @param fullClassImportList
     * @param fullClassNameList
     * @return
     */
    public static Integer getMaxFanOut(List<List<String>> fullClassImportList, List<String> fullClassNameList) {
        Integer maxFanOut = 0;
        String maxFanOutClass = "";
        int indexOut = -1;
        for (List<String> importClassList : fullClassImportList) {
            indexOut++;
            Integer fanOut = 0;
            for (String importClassName : importClassList) {
                for (String className : fullClassNameList) {
                    if (importClassName.equals(className)) {
                        fanOut++;
                        break;
                    }
                }
            }
            if (fanOut > maxFanOut) {
                maxFanOut = fanOut;
                maxFanOutClass = fullClassNameList.get(indexOut);
            }
        }
        return maxFanOut;
    }

    /**
     *
     * @param fullClassImportList
     * @param fullClassNameList
     * @return
     */
    public static Integer getMaxFanIn(List<List<String>> fullClassImportList, List<String> fullClassNameList) {
        Integer maxFanIn = 0;
        String maxFanInClass = "";
        int indexIn = -1;
        for (String className : fullClassNameList) {
            indexIn++;
            Integer fanIn = 0;
            for (List<String> importClassList : fullClassImportList) {
                for (String importClassName : importClassList) {
                    if (className.equals(importClassName)) {
                        fanIn++;
                        break;
                    }
                }
            }
            if (fanIn > maxFanIn) {
                maxFanIn = fanIn;
                maxFanInClass = fullClassNameList.get(indexIn);
            }
        }
        return maxFanIn;
    }


    /**
     *
     * @param cu
     * @param file
     * @return
     */
    public static String getClassFullName(CompilationUnit cu, File file) {
        String importPackageStatement = cu.getPackage().toString();
        String packageName = importPackageStatement.substring(importPackageStatement.indexOf(" ") + 1, importPackageStatement.lastIndexOf(";"));
        String fileName = file.getName();
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        String fullName = packageName + "." + className;

        return fullName;
    }

    /**
     *
     * @param cu
     * @return
     */
    public static List<String> getImportName(CompilationUnit cu) {
        List<String> list = new ArrayList<>();

        List imports = cu.imports();
        for (Object anImport : imports) {
            String s = anImport.toString();
            String importName = s.substring(s.indexOf(" ") + 1, s.lastIndexOf(";"));
            list.add(importName);
        }

        return list;
    }

}
