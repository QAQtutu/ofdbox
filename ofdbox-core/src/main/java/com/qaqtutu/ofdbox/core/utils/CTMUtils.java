package com.qaqtutu.ofdbox.core.utils;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

/**
 * @description:
 * @author: 张家尧
 * @create: 2020/10/01 10:22
 */
public class CTMUtils {


    public static Matrix ctm(Double... ctm) {
        Matrix matrix = DenseMatrix.Factory.zeros(3, 3);
        matrix.setAsDouble(ctm[0], 0, 0);
        matrix.setAsDouble(ctm[1], 0, 1);
        matrix.setAsDouble(ctm[2], 1, 0);
        matrix.setAsDouble(ctm[3], 1, 1);
        matrix.setAsDouble(ctm[4], 2, 0);
        matrix.setAsDouble(ctm[5], 2, 1);
        return matrix;
    }

    public static Matrix move(Matrix matrix, float x, float y) {
        Matrix move = DenseMatrix.Factory.eye(3, 3);
        move.setAsDouble(x, 2, 0);
        move.setAsDouble(y, 2, 1);
        System.out.println(move);
        return matrix.mtimes(move);
    }

    /*
    * 镜像
    * */
    //aX+bY+c=0
    public static Matrix imageMatrix(double a, double b, double c) {
        Matrix image = DenseMatrix.Factory.zeros(3, 3);
        image.setAsDouble(a * a - b * b, 0, 0);
        image.setAsDouble(2 * a * b, 0, 1);
        image.setAsDouble(2 * a * b, 1, 0);
        image.setAsDouble(-(a * a - b * b), 1, 1);
        image.setAsDouble(2 * a * c, 2, 0);
        image.setAsDouble(2 * b * c, 2, 1);
        image.setAsDouble(-(a * a + b * b), 2, 2);

        return image.times(-1 / (a * a + b * b));
    }



}