package com.ofdbox.core.utils;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import java.awt.geom.AffineTransform;

public class MatrixUtils {

    public static Matrix base() {
        Matrix matrix = DenseMatrix.Factory.zeros(3, 3);
        matrix.setAsDouble(1.0D, 0, 0);
        matrix.setAsDouble(1.0D, 1, 1);
        matrix.setAsDouble(1.0D, 2, 2);
        return matrix;
    }

    public static Matrix create(double d1, double d2, double d3, double d4, double d5, double d6) {
        Matrix matrix = base();
        matrix.setAsDouble(d1, 0, 0);
        matrix.setAsDouble(d2, 0, 1);
        matrix.setAsDouble(d3, 1, 0);
        matrix.setAsDouble(d4, 1, 1);
        matrix.setAsDouble(d5, 2, 0);
        matrix.setAsDouble(d6, 2, 1);
        return matrix;
    }

    public static Matrix scale(Matrix matrix, double x, double y) {
        return matrix.mtimes(create(x, 0, 0, y, 0, 0));
    }

    public static Matrix move(Matrix matrix, double x, double y) {
        return matrix.mtimes(create(1, 0, 0, 1, x, y));
    }

    public static Matrix ctm(Double... ctm) {
        return create(ctm[0], ctm[1], ctm[2], ctm[3], ctm[4], ctm[5]);
    }

    /*
     * 镜像
     * */
    //aX+bY+c=0
    public static Matrix imageMatrix(Matrix matrix, double a, double b, double c) {
        Matrix image = DenseMatrix.Factory.zeros(3, 3);
        image.setAsDouble(a * a - b * b, 0, 0);
        image.setAsDouble(2 * a * b, 0, 1);
        image.setAsDouble(2 * a * b, 1, 0);
        image.setAsDouble(-(a * a - b * b), 1, 1);
        image.setAsDouble(2 * a * c, 2, 0);
        image.setAsDouble(2 * b * c, 2, 1);
        image.setAsDouble(-(a * a + b * b), 2, 2);

        image = image.times(-1 / (a * a + b * b));

        return matrix.mtimes(image);
    }

    public static AffineTransform createAffineTransform(Matrix matrix) {
        return new AffineTransform(matrix.getAsFloat(0, 0), matrix.getAsFloat(0, 1), matrix.getAsFloat(1, 0), matrix.getAsFloat(1, 1), matrix.getAsFloat(2, 0), matrix.getAsFloat(2, 1));
    }


    public static Tuple2<Double, Double> pointTransform(Matrix ctm, Double x, Double y) {
        org.ujmp.core.Matrix m = DenseMatrix.Factory.zeros(1, 3);
        m.setAsDouble(x, 0, 0);
        m.setAsDouble(y, 0, 1);
        m.setAsDouble(1, 0, 2);

        m = m.mtimes(ctm);
        return new Tuple2<>(m.getAsDouble(0, 0), m.getAsDouble(0, 1));
    }

}
