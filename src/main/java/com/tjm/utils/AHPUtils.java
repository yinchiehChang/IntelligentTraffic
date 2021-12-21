package com.tjm.utils;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.text.DecimalFormat;

public class AHPUtils {
    /**
     * 矩阵一致性修正值
     */
    private final static double[] RI = new double[]{0, 0, 0, 0.58, 0.96, 1.12, 1.24, 1.32, 1.41, 1.45};
    //权重数组
    double[] relativeWeight;

    /**
     * 采用层次分析法确定指标相对权重(经过归一化处理)
     *
     * @param matrix
     * @param length
     * @return
     */
    public static double[] matrixCompute(double[][] matrix, int length) {
        double[] weight = new double[length];
        int index = 0;
        //计算判断矩阵每行所有元素的几何平均值
        for (int i = 0; i < length; i++) {
            double tempSum = 1;
            for (int j = 0; j < length; j++) {
                tempSum = tempSum * matrix[i][j];
            }
            tempSum = Math.pow((double) tempSum, (double) 1 / length);
            weight[index++] = tempSum;
        }
        //归一化
        double sum = 0;
        for (int i = 0; i < length; i++) {
            sum = sum + weight[i];
        }
        for (int i = 0; i < length; i++) {
            DecimalFormat df = new DecimalFormat("0.00000");
            String format = df.format(weight[i] / sum);
            weight[i] = Double.parseDouble(format);
        }
        return weight;
    }


    /**
     * 获取矩阵的最大特征值
     *
     * @param qualityMatrix1
     * @param relativeWeight
     * @param length
     * @return
     */
    public static double getMaxCharacteristicValue(double[][] qualityMatrix1, double[] relativeWeight, int length) {
        //将数组转化为矩阵
        RealMatrix qualityMatrix = new Array2DRowRealMatrix(qualityMatrix1); // nxn
        RealMatrix weightMatrix = new Array2DRowRealMatrix(relativeWeight);  // nx1
        //矩阵的乘法  nx1
        RealMatrix multiplyMatrix = qualityMatrix.multiply(weightMatrix);
        //获取矩阵的行数
        int row = multiplyMatrix.getRowDimension();
        //获取矩阵的列数 getColumnDimension()
        int col = multiplyMatrix.getColumnDimension();
        //矩阵转化为数组 getdata
        double[][] matrixtoarray = multiplyMatrix.getData();
        //将相乘后矩阵的值存入一维数组
        double[] multiplyWeight = new double[row * col];
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                multiplyWeight[index++] = matrixtoarray[i][j];
            }
        }
        //求解矩阵的最大特征值
        double maxValue = 0;
        for (int i = 0; i < length; i++) {
            maxValue = maxValue + multiplyWeight[i] / relativeWeight[i];
        }
        return maxValue / length;
    }

    /**
     * 判断矩阵的一致性
     *
     * @param maxValue
     * @param length
     * @return
     */
    public static boolean isConsistence(double maxValue, int length) {
        double CI = (maxValue - length) / (length - 1);
        double CR = CI / RI[length];
        if (CR <= 0.1) {
            return true;
        }
        return false;
    }

    /**
     * 权重计算和一致性判断
     * @param qualityMatrix 判断矩阵
     * @return
     */
    public boolean qualityJudge(double[][] qualityMatrix) {

        int length = qualityMatrix.length;

        //获得归一化后属性的相对权重
        relativeWeight = matrixCompute(qualityMatrix, length);

        //深copy
//        relativeQualityWeight = relativeWeight.clone();
        //获取矩阵的最大特征值Z
        double maxCharacteristicValue = getMaxCharacteristicValue(qualityMatrix, relativeWeight, length);
        //判断矩阵的一致性
        boolean consistence = false;
        if (length == 1 || length == 2) {
            consistence = true;
        } else {
            consistence = isConsistence(maxCharacteristicValue, length);
        }

        /*System.out.println("relativeWeight：");
        for (double s : relativeWeight) System.out.print(s + " ");
        System.out.println();*/

        if (!consistence) {
            return false;
        }
        return true;
    }

    public double getValue(String v) {
        switch (v) {
            case "1:9":
                return 0.1111;
            case "1:8":
                return 0.125;
            case "1:7":
                return 0.1429;
            case "1:6":
                return 0.1667;
            case "1:5":
                return 0.2;
            case "1:4":
                return 0.25;
            case "1:3":
                return 0.3333;
            case "1:2":
                return 0.5;
            case "1:1":
                return 1.0;
            case "2:1":
                return 2.0;
            case "3:1":
                return 3.0;
            case "4:1":
                return 4.0;
            case "5:1":
                return 5.0;
            case "6:1":
                return 6.0;
            case "7:1":
                return 7.0;
            case "8:1":
                return 8.0;
            case "9:1":
                return 9.0;
        }
        return 1.0;
    }
}
