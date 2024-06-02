package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;

import java.util.Arrays;
import java.util.List;

public class ToEOV {

    private static final double a = 6378160.0;
    private static final double b = 6356774.516;
    private static final double e = Math.sqrt((Math.pow(a, 2) - Math.pow(b, 2)) / Math.pow(a, 2));
    private static final double e_ = Math.sqrt((Math.pow(a, 2) - Math.pow(b, 2)) / Math.pow(b, 2));
    private static final double R = 6379743.001;
    private static final double k = 1.003110007693;
    public static final double n = 1.000719704936;
    private static final double m0 = 0.99993;
    private static final double fi_0 = 47.0 + 6.0 / 60.0;
    private static final double lambda_0 = 19.0 + 2.0 / 60.0 + 54.8584 / 3600.0;
    private final double[][] MATRIX_A = new double[24][7];
    private final double[][] MATRIX_l = new double[24][1];
    private double[][] PARAM_FOR_EOV;
    public static double X_EOV;
    public static double Y_EOV;
    public static double H_EOV;
    public static List<Point> COMMON_POINTS;

    public ToEOV() {
    }

    public ToEOV(double X_WGS84, double Y_WGS84, double Z_WGS84) {
        createMatrixA();
        createMatrix_l();
        runMatrixProcess();
        transformWGS84CoordinatesForIUGG67(X_WGS84, Y_WGS84, Z_WGS84);
    }

    public static void setCommonPoints(List<Point> commonPoints) {
        COMMON_POINTS = commonPoints;
    }

    private void transformWGS84CoordinatesForIUGG67(double X_WGS84, double Y_WGS84, double Z_WGS84) {
        double deltaX = PARAM_FOR_EOV[0][0];
        double deltaY = PARAM_FOR_EOV[1][0];
        double deltaZ = PARAM_FOR_EOV[2][0];
        double k = PARAM_FOR_EOV[3][0];
        double eX = PARAM_FOR_EOV[4][0];
        double eY = PARAM_FOR_EOV[5][0];
        double eZ = PARAM_FOR_EOV[6][0];
        double[][] Rx =
                {{1.0, 0.0, 0.0},
                        {0.0, Math.cos(eX), Math.sin(eX)},
                        {0.0, -Math.sin(eX), Math.cos(eX)}};
        double[][] Ry =
                {{Math.cos(eY), 0.0, -Math.sin(eY)},
                        {0.0, 1.0, 0.0},
                        {Math.sin(eY), 0.0, Math.cos(eY)}};
        double[][] Rz =
                {{Math.cos(eZ), Math.sin(eZ), 0.0},
                        {-Math.sin(eZ), Math.cos(eZ), 0.0},
                        {0.0, 0.0, 1.0}};
        double kRxRy_00 = k * Rx[0][0] * Ry[0][0] + k * Rx[0][1] * Ry[1][0] + k * Rx[0][2] * Ry[2][0];
        double kRxRy_10 = k * Rx[1][0] * Ry[0][0] + k * Rx[1][1] * Ry[1][0] + k * Rx[1][2] * Ry[2][0];
        double kRxRy_20 = k * Rx[2][0] * Ry[0][0] + k * Rx[2][1] * Ry[1][0] + k * Rx[2][2] * Ry[2][0];

        double kRxRy_01 = k * Rx[0][0] * Ry[0][1] + k * Rx[0][1] * Ry[1][1] + k * Rx[0][2] * Ry[2][1];
        double kRxRy_11 = k * Rx[1][0] * Ry[0][1] + k * Rx[1][1] * Ry[1][1] + k * Rx[1][2] * Ry[2][1];
        double kRxRy_21 = k * Rx[2][0] * Ry[0][1] + k * Rx[2][1] * Ry[1][1] + k * Rx[2][2] * Ry[2][1];

        double kRxRy_02 = k * Rx[0][0] * Ry[0][2] + k * Rx[0][1] * Ry[1][2] + k * Rx[0][2] * Ry[2][2];
        double kRxRy_12 = k * Rx[1][0] * Ry[0][2] + k * Rx[1][1] * Ry[1][2] + k * Rx[1][2] * Ry[2][2];
        double kRxRy_22 = k * Rx[2][0] * Ry[0][2] + k * Rx[2][1] * Ry[1][2] + k * Rx[2][2] * Ry[2][2];

        double kRxRyRz_00 = kRxRy_00 * Rz[0][0] + kRxRy_01 * Rz[1][0] + kRxRy_02 * Rz[2][0];
        double kRxRyRz_10 = kRxRy_10 * Rz[0][0] + kRxRy_11 * Rz[1][0] + kRxRy_12 * Rz[2][0];
        double kRxRyRz_20 = kRxRy_20 * Rz[0][0] + kRxRy_21 * Rz[1][0] + kRxRy_22 * Rz[2][0];

        double kRxRyRz_01 = kRxRy_00 * Rz[0][1] + kRxRy_01 * Rz[1][1] + kRxRy_02 * Rz[2][1];
        double kRxRyRz_11 = kRxRy_10 * Rz[0][1] + kRxRy_11 * Rz[1][1] + kRxRy_12 * Rz[2][1];
        double kRxRyRz_21 = kRxRy_20 * Rz[0][1] + kRxRy_21 * Rz[1][1] + kRxRy_22 * Rz[2][1];

        double kRxRyRz_02 = kRxRy_00 * Rz[0][2] + kRxRy_01 * Rz[1][2] + kRxRy_02 * Rz[2][2];
        double kRxRyRz_12 = kRxRy_10 * Rz[0][2] + kRxRy_11 * Rz[1][2] + kRxRy_12 * Rz[2][2];
        double kRxRyRz_22 = kRxRy_20 * Rz[0][2] + kRxRy_21 * Rz[1][2] + kRxRy_22 * Rz[2][2];

        double X_IUGG67 = deltaX + kRxRyRz_00 * X_WGS84 + kRxRyRz_01 * Y_WGS84 + kRxRyRz_02 * Z_WGS84;
        double Y_IUGG67 = deltaY + kRxRyRz_10 * X_WGS84 + kRxRyRz_11 * Y_WGS84 + kRxRyRz_12 * Z_WGS84;
        double Z_IUGG67 = deltaZ + kRxRyRz_20 * X_WGS84 + kRxRyRz_21 * Y_WGS84 + kRxRyRz_22 * Z_WGS84;

        double p = Math.sqrt(Math.pow(X_IUGG67, 2) + Math.pow(Y_IUGG67, 2));
        double theta = Math.atan(Z_IUGG67 * a / (p * b));
        double Fi_IUGG67 = Math.atan( (Z_IUGG67 + Math.pow(e_, 2) * b * Math.pow(Math.sin(theta), 3)) /
                (p - Math.pow(e, 2) * a * Math.pow(Math.cos(theta), 3)) );
        double Lambda_IUGG67 = Math.atan(Y_IUGG67 / X_IUGG67);
        double N = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(Fi_IUGG67), 2));
        double h_IUGG67 = p / Math.cos(Fi_IUGG67) - N;
        transformIUGG67CoordinatesForEOV(Fi_IUGG67, Lambda_IUGG67, h_IUGG67);
    }

    public static List<Double> getXYZCoordinatesForIUGG67(double Y_EOV, double X_EOV, double Z_EOV){
        double sphereFi_ = 2 * Math.atan( Math.pow(Math.E, (X_EOV - 200000) / (R * m0))) - Math.PI / 2;
        double sphereLambda_ = (Y_EOV - 650000) / (R * m0);
        double sphereFi = Math.asin(Math.sin(sphereFi_) * Math.cos(Math.toRadians(fi_0)) +
                Math.cos(sphereFi_) * Math.sin(Math.toRadians(fi_0) * Math.cos(sphereLambda_)));
        double sphereLambda = Math.asin(Math.cos(sphereFi_) * Math.sin(sphereLambda_) / Math.cos(sphereFi));
        double FI = iterateFi(sphereFi);
        double LAMBDA = Math.toRadians(lambda_0) + sphereLambda / n;
        double N = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(FI), 2));
        double X = (N + Z_EOV) * Math.cos(FI) * Math.cos(LAMBDA);
        double Y = (N + Z_EOV) * Math.cos(FI) * Math.sin(LAMBDA);
        double Z = ((1 - Math.pow(e, 2)) * N + Z_EOV) * Math.sin(FI);
        return Arrays.asList(X, Y, Z);
    }

    private static double iterateFi(double preFi){
        double sphereFi = preFi;
        double Fi = preFi;
        for (int i = 0; i < 4; i++) {
            preFi =  2 * Math.atan(Math.pow(
                    Math.tan(Math.PI / 4 + sphereFi / 2) /
                            (k * Math.pow((1 - e * Math.sin(Fi)) /
                                    (1 + e * Math.sin(Fi)), n * e / 2)),
                    1 / n) ) - Math.PI / 2;
            Fi = preFi;
        }
        return Fi;
    }

    private void transformIUGG67CoordinatesForEOV(double Fi_IUGG67, double Lambda_IUGG67, double h_IUGG67){
        double sphereFi = 2 * Math.atan(k * Math.pow(Math.tan(Math.PI / 4  + Fi_IUGG67 / 2.0), n) *
               Math.pow( (1 - e * Math.sin(Fi_IUGG67)) / (1 + e * Math.sin(Fi_IUGG67)), n * e / 2.0))
         - Math.PI / 2;
        double sphereLambda = n * (Lambda_IUGG67 - Math.toRadians(lambda_0));
        double fi_ = Math.asin(
                Math.sin(sphereFi) * Math.cos(Math.toRadians(fi_0)) - Math.cos(sphereFi) * Math.sin(Math.toRadians(fi_0)) *
                                Math.cos(sphereLambda) );
        double lambda_ = Math.asin(Math.cos(sphereFi)  * Math.sin(sphereLambda) / Math.cos(fi_));
        X_EOV = R * m0 * Math.log(Math.tan(Math.PI / 4 + fi_ / 2)) + 200000;
        Y_EOV =  R * m0 * lambda_ + 650000;
        H_EOV = h_IUGG67;
    }

    private void runMatrixProcess() {
        //A transposed
        double[][] At = new double[7][24];
        for (int i = 0; i < MATRIX_A.length; i++) {
            for (int j = 0; j < MATRIX_A[i].length; j++) {
                At[j][i] = MATRIX_A[i][j];
            }
        }
        //At * A
        double[][] At_A = multiplyMatrix(At, MATRIX_A);
        double[][] inverse_At_A = InverseMatrix.invert(At_A);
        //At * l
        double[][] At_l = multiplyMatrix(At, MATRIX_l);
        //1 / ( At * A ) * (At * l)
        PARAM_FOR_EOV = multiplyMatrix(inverse_At_A, At_l);
    }

    private double[][] multiplyMatrix(double[][] matrix1, double[][] matrix2) {
        double[][] resultMatrix = new double[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                for (int k = 0; k < matrix2.length; k++) {
                    resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return resultMatrix;
    }

    private void createMatrixA() {
        MATRIX_A[0][0] = 1.0;
        MATRIX_A[0][1] = 0.0;
        MATRIX_A[0][2] = 0.0;
        MATRIX_A[0][3] = COMMON_POINTS.get(0).getX_WGS84();
        MATRIX_A[0][4] = 0.0;
        MATRIX_A[0][5] = -1 * COMMON_POINTS.get(0).getZ_WGS84();
        MATRIX_A[0][6] = COMMON_POINTS.get(0).getY_WGS84();

        MATRIX_A[1][0] = 0.0;
        MATRIX_A[1][1] = 1.0;
        MATRIX_A[1][2] = 0.0;
        MATRIX_A[1][3] = COMMON_POINTS.get(0).getY_WGS84();
        MATRIX_A[1][4] = COMMON_POINTS.get(0).getZ_WGS84();
        MATRIX_A[1][5] = 0.0;
        MATRIX_A[1][6] = -1 * COMMON_POINTS.get(0).getX_WGS84();

        MATRIX_A[2][0] = 0.0;
        MATRIX_A[2][1] = 0.0;
        MATRIX_A[2][2] = 1.0;
        MATRIX_A[2][3] = COMMON_POINTS.get(0).getZ_WGS84();
        MATRIX_A[2][4] = -1 * COMMON_POINTS.get(0).getY_WGS84();
        MATRIX_A[2][5] = COMMON_POINTS.get(0).getX_WGS84();
        MATRIX_A[2][6] = 0.0;

        MATRIX_A[3][0] = 1.0;
        MATRIX_A[3][1] = 0.0;
        MATRIX_A[3][2] = 0.0;
        MATRIX_A[3][3] = COMMON_POINTS.get(1).getX_WGS84();
        MATRIX_A[3][4] = 0.0;
        MATRIX_A[3][5] = -1 * COMMON_POINTS.get(1).getZ_WGS84();
        MATRIX_A[3][6] = COMMON_POINTS.get(1).getY_WGS84();

        MATRIX_A[4][0] = 0.0;
        MATRIX_A[4][1] = 1.0;
        MATRIX_A[4][2] = 0.0;
        MATRIX_A[4][3] = COMMON_POINTS.get(1).getY_WGS84();
        MATRIX_A[4][4] = COMMON_POINTS.get(1).getZ_WGS84();
        MATRIX_A[4][5] = 0.0;
        MATRIX_A[4][6] = -1 * COMMON_POINTS.get(1).getX_WGS84();

        MATRIX_A[5][0] = 0.0;
        MATRIX_A[5][1] = 0.0;
        MATRIX_A[5][2] = 1.0;
        MATRIX_A[5][3] = COMMON_POINTS.get(1).getZ_WGS84();
        MATRIX_A[5][4] = -1 * COMMON_POINTS.get(1).getY_WGS84();
        MATRIX_A[5][5] = COMMON_POINTS.get(1).getX_WGS84();
        MATRIX_A[5][6] = 0.0;

        MATRIX_A[6][0] = 1.0;
        MATRIX_A[6][1] = 0.0;
        MATRIX_A[6][2] = 0.0;
        MATRIX_A[6][3] = COMMON_POINTS.get(2).getX_WGS84();
        MATRIX_A[6][4] = 0.0;
        MATRIX_A[6][5] = -1 * COMMON_POINTS.get(2).getZ_WGS84();
        MATRIX_A[6][6] = COMMON_POINTS.get(2).getY_WGS84();

        MATRIX_A[7][0] = 0.0;
        MATRIX_A[7][1] = 1.0;
        MATRIX_A[7][2] = 0.0;
        MATRIX_A[7][3] = COMMON_POINTS.get(2).getY_WGS84();
        MATRIX_A[7][4] = COMMON_POINTS.get(2).getZ_WGS84();
        MATRIX_A[7][5] = 0.0;
        MATRIX_A[7][6] = -1 * COMMON_POINTS.get(2).getX_WGS84();

        MATRIX_A[8][0] = 0.0;
        MATRIX_A[8][1] = 0.0;
        MATRIX_A[8][2] = 1.0;
        MATRIX_A[8][3] = COMMON_POINTS.get(2).getZ_WGS84();
        MATRIX_A[8][4] = -1 * COMMON_POINTS.get(2).getY_WGS84();
        MATRIX_A[8][5] = COMMON_POINTS.get(2).getX_WGS84();
        MATRIX_A[8][6] = 0.0;

        MATRIX_A[9][0] = 1.0;
        MATRIX_A[9][1] = 0.0;
        MATRIX_A[9][2] = 0.0;
        MATRIX_A[9][3] = COMMON_POINTS.get(3).getX_WGS84();
        MATRIX_A[9][4] = 0.0;
        MATRIX_A[9][5] = -1 * COMMON_POINTS.get(3).getZ_WGS84();
        MATRIX_A[9][6] = COMMON_POINTS.get(3).getY_WGS84();

        MATRIX_A[10][0] = 0.0;
        MATRIX_A[10][1] = 1.0;
        MATRIX_A[10][2] = 0.0;
        MATRIX_A[10][3] = COMMON_POINTS.get(3).getY_WGS84();
        MATRIX_A[10][4] = COMMON_POINTS.get(3).getZ_WGS84();
        MATRIX_A[10][5] = 0.0;
        MATRIX_A[10][6] = -1 * COMMON_POINTS.get(3).getX_WGS84();

        MATRIX_A[11][0] = 0.0;
        MATRIX_A[11][1] = 0.0;
        MATRIX_A[11][2] = 1.0;
        MATRIX_A[11][3] = COMMON_POINTS.get(3).getZ_WGS84();
        MATRIX_A[11][4] = -1 * COMMON_POINTS.get(3).getY_WGS84();
        MATRIX_A[11][5] = COMMON_POINTS.get(3).getX_WGS84();
        MATRIX_A[11][6] = 0.0;

        MATRIX_A[12][0] = 1.0;
        MATRIX_A[12][1] = 0.0;
        MATRIX_A[12][2] = 0.0;
        MATRIX_A[12][3] = COMMON_POINTS.get(4).getX_WGS84();
        MATRIX_A[12][4] = 0.0;
        MATRIX_A[12][5] = -1 * COMMON_POINTS.get(4).getZ_WGS84();
        MATRIX_A[12][6] = COMMON_POINTS.get(4).getY_WGS84();

        MATRIX_A[13][0] = 0.0;
        MATRIX_A[13][1] = 1.0;
        MATRIX_A[13][2] = 0.0;
        MATRIX_A[13][3] = COMMON_POINTS.get(4).getY_WGS84();
        MATRIX_A[13][4] = COMMON_POINTS.get(4).getZ_WGS84();
        MATRIX_A[13][5] = 0.0;
        MATRIX_A[13][6] = -1 * COMMON_POINTS.get(4).getX_WGS84();

        MATRIX_A[14][0] = 0.0;
        MATRIX_A[14][1] = 0.0;
        MATRIX_A[14][2] = 1.0;
        MATRIX_A[14][3] = COMMON_POINTS.get(4).getZ_WGS84();
        MATRIX_A[14][4] = -1 * COMMON_POINTS.get(4).getY_WGS84();
        MATRIX_A[14][5] = COMMON_POINTS.get(4).getX_WGS84();
        MATRIX_A[14][6] = 0.0;

        MATRIX_A[15][0] = 1.0;
        MATRIX_A[15][1] = 0.0;
        MATRIX_A[15][2] = 0.0;
        MATRIX_A[15][3] = COMMON_POINTS.get(5).getX_WGS84();
        MATRIX_A[15][4] = 0.0;
        MATRIX_A[15][5] = -1 * COMMON_POINTS.get(5).getZ_WGS84();
        MATRIX_A[15][6] = COMMON_POINTS.get(5).getY_WGS84();

        MATRIX_A[16][0] = 0.0;
        MATRIX_A[16][1] = 1.0;
        MATRIX_A[16][2] = 0.0;
        MATRIX_A[16][3] = COMMON_POINTS.get(5).getY_WGS84();
        MATRIX_A[16][4] = COMMON_POINTS.get(5).getZ_WGS84();
        MATRIX_A[16][5] = 0.0;
        MATRIX_A[16][6] = -1 * COMMON_POINTS.get(5).getX_WGS84();

        MATRIX_A[17][0] = 0.0;
        MATRIX_A[17][1] = 0.0;
        MATRIX_A[17][2] = 1.0;
        MATRIX_A[17][3] = COMMON_POINTS.get(5).getZ_WGS84();
        MATRIX_A[17][4] = -1 * COMMON_POINTS.get(5).getY_WGS84();
        MATRIX_A[17][5] = COMMON_POINTS.get(5).getX_WGS84();
        MATRIX_A[17][6] = 0.0;

        MATRIX_A[18][0] = 1.0;
        MATRIX_A[18][1] = 0.0;
        MATRIX_A[18][2] = 0.0;
        MATRIX_A[18][3] = COMMON_POINTS.get(6).getX_WGS84();
        MATRIX_A[18][4] = 0.0;
        MATRIX_A[18][5] = -1 * COMMON_POINTS.get(6).getZ_WGS84();
        MATRIX_A[18][6] = COMMON_POINTS.get(6).getY_WGS84();

        MATRIX_A[19][0] = 0.0;
        MATRIX_A[19][1] = 1.0;
        MATRIX_A[19][2] = 0.0;
        MATRIX_A[19][3] = COMMON_POINTS.get(6).getY_WGS84();
        MATRIX_A[19][4] = COMMON_POINTS.get(6).getZ_WGS84();
        MATRIX_A[19][5] = 0.0;
        MATRIX_A[19][6] = -1 * COMMON_POINTS.get(6).getX_WGS84();

        MATRIX_A[20][0] = 0.0;
        MATRIX_A[20][1] = 0.0;
        MATRIX_A[20][2] = 1.0;
        MATRIX_A[20][3] = COMMON_POINTS.get(6).getZ_WGS84();
        MATRIX_A[20][4] = -1 * COMMON_POINTS.get(6).getY_WGS84();
        MATRIX_A[20][5] = COMMON_POINTS.get(6).getX_WGS84();
        MATRIX_A[20][6] = 0.0;

        MATRIX_A[21][0] = 1.0;
        MATRIX_A[21][1] = 0.0;
        MATRIX_A[21][2] = 0.0;
        MATRIX_A[21][3] = COMMON_POINTS.get(7).getX_WGS84();
        MATRIX_A[21][4] = 0.0;
        MATRIX_A[21][5] = -1 * COMMON_POINTS.get(7).getZ_WGS84();
        MATRIX_A[21][6] = COMMON_POINTS.get(7).getY_WGS84();

        MATRIX_A[22][0] = 0.0;
        MATRIX_A[22][1] = 1.0;
        MATRIX_A[22][2] = 0.0;
        MATRIX_A[22][3] = COMMON_POINTS.get(7).getY_WGS84();
        MATRIX_A[22][4] = COMMON_POINTS.get(7).getZ_WGS84();
        MATRIX_A[22][5] = 0.0;
        MATRIX_A[22][6] = -1 * COMMON_POINTS.get(7).getX_WGS84();

        MATRIX_A[23][0] = 0.0;
        MATRIX_A[23][1] = 0.0;
        MATRIX_A[23][2] = 1.0;
        MATRIX_A[23][3] = COMMON_POINTS.get(7).getZ_WGS84();
        MATRIX_A[23][4] = -1 * COMMON_POINTS.get(7).getY_WGS84();
        MATRIX_A[23][5] = COMMON_POINTS.get(7).getX_WGS84();
        MATRIX_A[23][6] = 0.0;
    }

    private void createMatrix_l() {
        MATRIX_l[0][0] = COMMON_POINTS.get(0).getX_IUGG67();
        MATRIX_l[1][0] = COMMON_POINTS.get(0).getY_IUGG67();
        MATRIX_l[2][0] = COMMON_POINTS.get(0).getZ_IUGG67();
        MATRIX_l[3][0] = COMMON_POINTS.get(1).getX_IUGG67();
        MATRIX_l[4][0] = COMMON_POINTS.get(1).getY_IUGG67();
        MATRIX_l[5][0] = COMMON_POINTS.get(1).getZ_IUGG67();
        MATRIX_l[6][0] = COMMON_POINTS.get(2).getX_IUGG67();
        MATRIX_l[7][0] = COMMON_POINTS.get(2).getY_IUGG67();
        MATRIX_l[8][0] = COMMON_POINTS.get(2).getZ_IUGG67();
        MATRIX_l[9][0] = COMMON_POINTS.get(3).getX_IUGG67();
        MATRIX_l[10][0] = COMMON_POINTS.get(3).getY_IUGG67();
        MATRIX_l[11][0] = COMMON_POINTS.get(3).getZ_IUGG67();
        MATRIX_l[12][0] = COMMON_POINTS.get(4).getX_IUGG67();
        MATRIX_l[13][0] = COMMON_POINTS.get(4).getY_IUGG67();
        MATRIX_l[14][0] = COMMON_POINTS.get(4).getZ_IUGG67();
        MATRIX_l[15][0] = COMMON_POINTS.get(5).getX_IUGG67();
        MATRIX_l[16][0] = COMMON_POINTS.get(5).getY_IUGG67();
        MATRIX_l[17][0] = COMMON_POINTS.get(5).getZ_IUGG67();
        MATRIX_l[18][0] = COMMON_POINTS.get(6).getX_IUGG67();
        MATRIX_l[19][0] = COMMON_POINTS.get(6).getY_IUGG67();
        MATRIX_l[20][0] = COMMON_POINTS.get(6).getZ_IUGG67();
        MATRIX_l[21][0] = COMMON_POINTS.get(7).getX_IUGG67();
        MATRIX_l[22][0] = COMMON_POINTS.get(7).getY_IUGG67();
        MATRIX_l[23][0] = COMMON_POINTS.get(7).getZ_IUGG67();
    }
}