package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;

import java.util.Arrays;
import java.util.List;

public class ToWGS {

    private static final double a = 6378137.0;
    private static final double b = 6356752.314;
    public static final double e = Math.sqrt((Math.pow(a, 2) - Math.pow(b, 2)) / Math.pow(a, 2));
    private static final double e_ = Math.sqrt((Math.pow(a, 2) - Math.pow(b, 2)) / Math.pow(b, 2));
    private final double[][] MATRIX_A = new double[24][7];
    private final double[][] MATRIX_l = new double[24][1];
    public double[][] PARAM_FOR_WGS;
    public List<Point> referencePoints;
    public static double Fi_WGS84;
    public static double Lambda_WGS84;
    public static double h_WGS84;
    public static double X_WGS84;
    public static double Y_WGS84;
    public static double Z_WGS84;

    public ToWGS(double X_IUGG67, double Y_IUGG67, double Z_IUGG67, List<Point> referencePoints) {
        this.referencePoints = referencePoints;
        createMatrixA();
        createMatrix_l();
        runMatrixProcess();
        transformIUGG67CoordinatesForWGS84(X_IUGG67, Y_IUGG67, Z_IUGG67);
    }

    private void transformIUGG67CoordinatesForWGS84(double X_IUGG67, double Y_IUGG67, double Z_IUGG67){
      double deltaX = PARAM_FOR_WGS[0][0];
      double deltaY = PARAM_FOR_WGS[1][0];
      double deltaZ = PARAM_FOR_WGS[2][0];
      double k = PARAM_FOR_WGS[3][0];
      double eX = PARAM_FOR_WGS[4][0];
      double eY = PARAM_FOR_WGS[5][0];
      double eZ = PARAM_FOR_WGS[6][0];
      double[][] Rx =
                {{1.0, 0.0, 0.0},
                        {0.0, Math.cos(eX), Math.sin(eX)},
                        {0.0, - Math.sin(eX), Math.cos(eX)}};
      double[][] Ry =
                {{Math.cos(eY), 0.0, - Math.sin(eY)},
                        {0.0, 1.0, 0.0},
                        {Math.sin(eY), 0.0, Math.cos(eY)}};
      double[][] Rz =
                {{Math.cos(eZ), Math.sin(eZ), 0.0},
                        {- Math.sin(eZ), Math.cos(eZ), 0.0},
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

        X_WGS84 = deltaX + kRxRyRz_00 * X_IUGG67 + kRxRyRz_01 * Y_IUGG67 + kRxRyRz_02 * Z_IUGG67;
        Y_WGS84 = deltaY + kRxRyRz_10 * X_IUGG67 + kRxRyRz_11 *  Y_IUGG67 + kRxRyRz_12 * Z_IUGG67;
        Z_WGS84 = deltaZ + kRxRyRz_20 * X_IUGG67 + kRxRyRz_21 * Y_IUGG67 + kRxRyRz_22 * Z_IUGG67;
        List<Double> geographicalCoordinates = getGeographicalCoordinatesDegreesForWGS84(X_WGS84, Y_WGS84, Z_WGS84);
        Fi_WGS84 = geographicalCoordinates.get(0);
        Lambda_WGS84 = geographicalCoordinates.get(1);
        h_WGS84 = geographicalCoordinates.get(2);
    }

    public static List<Double> getGeographicalCoordinatesDegreesForWGS84(double X_WGS84, double Y_WGS84, double Z_WGS84){
        double p = Math.sqrt(Math.pow(X_WGS84, 2) + Math.pow(Y_WGS84, 2));
        double theta = Math.atan(Z_WGS84 * a / (p * b));
        double FI_WGS84 = Math.atan( (Z_WGS84 + Math.pow(e_, 2) * b * Math.pow(Math.sin(theta), 3)) /
                (p - Math.pow(e, 2) * a * Math.pow(Math.cos(theta), 3)) );
        double LAMBDA_WGS84 = Math.atan(Y_WGS84 / X_WGS84);
        double N = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(FI_WGS84), 2));
        double H_WGS84 = p / Math.cos(FI_WGS84) - N;
        return Arrays.asList(Math.toDegrees(FI_WGS84), Math.toDegrees(LAMBDA_WGS84), H_WGS84);
    }

    public static List<Double> getXYZCoordinatesForWGS84ByDegrees(double Fi_WGS84, double Lambda_WGS84, double h_WGS84){

        double N = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(Math.toRadians(Fi_WGS84)), 2));
        double X = (N + h_WGS84) * Math.cos(Math.toRadians(Fi_WGS84)) * Math.cos(Math.toRadians(Lambda_WGS84));
        double Y = (N + h_WGS84) * Math.cos(Math.toRadians(Fi_WGS84)) * Math.sin(Math.toRadians(Lambda_WGS84));
        double Z = ((1 - Math.pow(e, 2)) * N + h_WGS84) * Math.sin(Math.toRadians(Fi_WGS84));

        return Arrays.asList(X, Y, Z);
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
        PARAM_FOR_WGS = multiplyMatrix(inverse_At_A, At_l);
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

    private void createMatrixA(){
        MATRIX_A[0][0] = 1.0;
        MATRIX_A[0][1] = 0.0;
        MATRIX_A[0][2] = 0.0;
        MATRIX_A[0][3] = referencePoints.get(0).getX_IUGG67();
        MATRIX_A[0][4] = 0.0;
        MATRIX_A[0][5] = - 1 * referencePoints.get(0).getZ_IUGG67();
        MATRIX_A[0][6] = referencePoints.get(0).getY_IUGG67();

        MATRIX_A[1][0] = 0.0;
        MATRIX_A[1][1] = 1.0;
        MATRIX_A[1][2] = 0.0;
        MATRIX_A[1][3] = referencePoints.get(0).getY_IUGG67();
        MATRIX_A[1][4] = referencePoints.get(0).getZ_IUGG67();
        MATRIX_A[1][5] = 0.0;
        MATRIX_A[1][6] = - 1 * referencePoints.get(0).getX_IUGG67();

        MATRIX_A[2][0] = 0.0;
        MATRIX_A[2][1] = 0.0;
        MATRIX_A[2][2] = 1.0;
        MATRIX_A[2][3] = referencePoints.get(0).getZ_IUGG67();
        MATRIX_A[2][4] = - 1 * referencePoints.get(0).getY_IUGG67();
        MATRIX_A[2][5] = referencePoints.get(0).getX_IUGG67();
        MATRIX_A[2][6] = 0.0;

        MATRIX_A[3][0] = 1.0;
        MATRIX_A[3][1] = 0.0;
        MATRIX_A[3][2] = 0.0;
        MATRIX_A[3][3] = referencePoints.get(1).getX_IUGG67();
        MATRIX_A[3][4] = 0.0;
        MATRIX_A[3][5] = - 1 * referencePoints.get(1).getZ_IUGG67();
        MATRIX_A[3][6] = referencePoints.get(1).getY_IUGG67();

        MATRIX_A[4][0] = 0.0;
        MATRIX_A[4][1] = 1.0;
        MATRIX_A[4][2] = 0.0;
        MATRIX_A[4][3] = referencePoints.get(1).getY_IUGG67();
        MATRIX_A[4][4] = referencePoints.get(1).getZ_IUGG67();
        MATRIX_A[4][5] = 0.0;
        MATRIX_A[4][6] = - 1 * referencePoints.get(1).getX_IUGG67();

        MATRIX_A[5][0] = 0.0;
        MATRIX_A[5][1] = 0.0;
        MATRIX_A[5][2] = 1.0;
        MATRIX_A[5][3] = referencePoints.get(1).getZ_IUGG67();
        MATRIX_A[5][4] = - 1 * referencePoints.get(1).getY_IUGG67();
        MATRIX_A[5][5] = referencePoints.get(1).getX_IUGG67();
        MATRIX_A[5][6] = 0.0;

        MATRIX_A[6][0] = 1.0;
        MATRIX_A[6][1] = 0.0;
        MATRIX_A[6][2] = 0.0;
        MATRIX_A[6][3] = referencePoints.get(2).getX_IUGG67();
        MATRIX_A[6][4] = 0.0;
        MATRIX_A[6][5] = - 1 * referencePoints.get(2).getZ_IUGG67();
        MATRIX_A[6][6] = referencePoints.get(2).getY_IUGG67();

        MATRIX_A[7][0] = 0.0;
        MATRIX_A[7][1] = 1.0;
        MATRIX_A[7][2] = 0.0;
        MATRIX_A[7][3] = referencePoints.get(2).getY_IUGG67();
        MATRIX_A[7][4] = referencePoints.get(2).getZ_IUGG67();
        MATRIX_A[7][5] = 0.0;
        MATRIX_A[7][6] = - 1 * referencePoints.get(2).getX_IUGG67();

        MATRIX_A[8][0] = 0.0;
        MATRIX_A[8][1] = 0.0;
        MATRIX_A[8][2] = 1.0;
        MATRIX_A[8][3] = referencePoints.get(2).getZ_IUGG67();
        MATRIX_A[8][4] = - 1 * referencePoints.get(2).getY_IUGG67();
        MATRIX_A[8][5] = referencePoints.get(2).getX_IUGG67();
        MATRIX_A[8][6] = 0.0;

        MATRIX_A[9][0] = 1.0;
        MATRIX_A[9][1] = 0.0;
        MATRIX_A[9][2] = 0.0;
        MATRIX_A[9][3] = referencePoints.get(3).getX_IUGG67();
        MATRIX_A[9][4] = 0.0;
        MATRIX_A[9][5] = - 1 * referencePoints.get(3).getZ_IUGG67();
        MATRIX_A[9][6] = referencePoints.get(3).getY_IUGG67();

        MATRIX_A[10][0] = 0.0;
        MATRIX_A[10][1] = 1.0;
        MATRIX_A[10][2] = 0.0;
        MATRIX_A[10][3] = referencePoints.get(3).getY_IUGG67();
        MATRIX_A[10][4] = referencePoints.get(3).getZ_IUGG67();
        MATRIX_A[10][5] = 0.0;
        MATRIX_A[10][6] = - 1 * referencePoints.get(3).getX_IUGG67();

        MATRIX_A[11][0] = 0.0;
        MATRIX_A[11][1] = 0.0;
        MATRIX_A[11][2] = 1.0;
        MATRIX_A[11][3] = referencePoints.get(3).getZ_IUGG67();
        MATRIX_A[11][4] = - 1 * referencePoints.get(3).getY_IUGG67();
        MATRIX_A[11][5] = referencePoints.get(3).getX_IUGG67();
        MATRIX_A[11][6] = 0.0;

        MATRIX_A[12][0] = 1.0;
        MATRIX_A[12][1] = 0.0;
        MATRIX_A[12][2] = 0.0;
        MATRIX_A[12][3] = referencePoints.get(4).getX_IUGG67();
        MATRIX_A[12][4] = 0.0;
        MATRIX_A[12][5] = - 1 * referencePoints.get(4).getZ_IUGG67();
        MATRIX_A[12][6] = referencePoints.get(4).getY_IUGG67();

        MATRIX_A[13][0] = 0.0;
        MATRIX_A[13][1] = 1.0;
        MATRIX_A[13][2] = 0.0;
        MATRIX_A[13][3] = referencePoints.get(4).getY_IUGG67();
        MATRIX_A[13][4] = referencePoints.get(4).getZ_IUGG67();
        MATRIX_A[13][5] = 0.0;
        MATRIX_A[13][6] = - 1 * referencePoints.get(4).getX_IUGG67();

        MATRIX_A[14][0] = 0.0;
        MATRIX_A[14][1] = 0.0;
        MATRIX_A[14][2] = 1.0;
        MATRIX_A[14][3] = referencePoints.get(4).getZ_IUGG67();
        MATRIX_A[14][4] = - 1 * referencePoints.get(4).getY_IUGG67();
        MATRIX_A[14][5] = referencePoints.get(4).getX_IUGG67();
        MATRIX_A[14][6] = 0.0;

        MATRIX_A[15][0] = 1.0;
        MATRIX_A[15][1] = 0.0;
        MATRIX_A[15][2] = 0.0;
        MATRIX_A[15][3] = referencePoints.get(5).getX_IUGG67();
        MATRIX_A[15][4] = 0.0;
        MATRIX_A[15][5] = - 1 * referencePoints.get(5).getZ_IUGG67();
        MATRIX_A[15][6] = referencePoints.get(5).getY_IUGG67();

        MATRIX_A[16][0] = 0.0;
        MATRIX_A[16][1] = 1.0;
        MATRIX_A[16][2] = 0.0;
        MATRIX_A[16][3] = referencePoints.get(5).getY_IUGG67();
        MATRIX_A[16][4] = referencePoints.get(5).getZ_IUGG67();
        MATRIX_A[16][5] = 0.0;
        MATRIX_A[16][6] = - 1 * referencePoints.get(5).getX_IUGG67();

        MATRIX_A[17][0] = 0.0;
        MATRIX_A[17][1] = 0.0;
        MATRIX_A[17][2] = 1.0;
        MATRIX_A[17][3] = referencePoints.get(5).getZ_IUGG67();
        MATRIX_A[17][4] = - 1 * referencePoints.get(5).getY_IUGG67();
        MATRIX_A[17][5] = referencePoints.get(5).getX_IUGG67();
        MATRIX_A[17][6] = 0.0;

        MATRIX_A[18][0] = 1.0;
        MATRIX_A[18][1] = 0.0;
        MATRIX_A[18][2] = 0.0;
        MATRIX_A[18][3] = referencePoints.get(6).getX_IUGG67();
        MATRIX_A[18][4] = 0.0;
        MATRIX_A[18][5] = - 1 * referencePoints.get(6).getZ_IUGG67();
        MATRIX_A[18][6] = referencePoints.get(6).getY_IUGG67();

        MATRIX_A[19][0] = 0.0;
        MATRIX_A[19][1] = 1.0;
        MATRIX_A[19][2] = 0.0;
        MATRIX_A[19][3] = referencePoints.get(6).getY_IUGG67();
        MATRIX_A[19][4] = referencePoints.get(6).getZ_IUGG67();
        MATRIX_A[19][5] = 0.0;
        MATRIX_A[19][6] = - 1 * referencePoints.get(6).getX_IUGG67();

        MATRIX_A[20][0] = 0.0;
        MATRIX_A[20][1] = 0.0;
        MATRIX_A[20][2] = 1.0;
        MATRIX_A[20][3] = referencePoints.get(6).getZ_IUGG67();
        MATRIX_A[20][4] = - 1 * referencePoints.get(6).getY_IUGG67();
        MATRIX_A[20][5] = referencePoints.get(6).getX_IUGG67();
        MATRIX_A[20][6] = 0.0;

        MATRIX_A[21][0] = 1.0;
        MATRIX_A[21][1] = 0.0;
        MATRIX_A[21][2] = 0.0;
        MATRIX_A[21][3] = referencePoints.get(7).getX_IUGG67();
        MATRIX_A[21][4] = 0.0;
        MATRIX_A[21][5] = - 1 * referencePoints.get(7).getZ_IUGG67();
        MATRIX_A[21][6] = referencePoints.get(7).getY_IUGG67();

        MATRIX_A[22][0] = 0.0;
        MATRIX_A[22][1] = 1.0;
        MATRIX_A[22][2] = 0.0;
        MATRIX_A[22][3] = referencePoints.get(7).getY_IUGG67();
        MATRIX_A[22][4] = referencePoints.get(7).getZ_IUGG67();
        MATRIX_A[22][5] = 0.0;
        MATRIX_A[22][6] = - 1 * referencePoints.get(7).getX_IUGG67();

        MATRIX_A[23][0] = 0.0;
        MATRIX_A[23][1] = 0.0;
        MATRIX_A[23][2] = 1.0;
        MATRIX_A[23][3] = referencePoints.get(7).getZ_IUGG67();
        MATRIX_A[23][4] = - 1 * referencePoints.get(7).getY_IUGG67();
        MATRIX_A[23][5] = referencePoints.get(7).getX_IUGG67();
        MATRIX_A[23][6] = 0.0;
    }

    private void createMatrix_l(){
        MATRIX_l[0][0] = referencePoints.get(0).getX_WGS84();
        MATRIX_l[1][0] = referencePoints.get(0).getY_WGS84();
        MATRIX_l[2][0] = referencePoints.get(0).getZ_WGS84();
        MATRIX_l[3][0] = referencePoints.get(1).getX_WGS84();
        MATRIX_l[4][0] = referencePoints.get(1).getY_WGS84();
        MATRIX_l[5][0] = referencePoints.get(1).getZ_WGS84();
        MATRIX_l[6][0] = referencePoints.get(2).getX_WGS84();
        MATRIX_l[7][0] = referencePoints.get(2).getY_WGS84();
        MATRIX_l[8][0] = referencePoints.get(2).getZ_WGS84();
        MATRIX_l[9][0] = referencePoints.get(3).getX_WGS84();
        MATRIX_l[10][0] = referencePoints.get(3).getY_WGS84();
        MATRIX_l[11][0] = referencePoints.get(3).getZ_WGS84();
        MATRIX_l[12][0] = referencePoints.get(4).getX_WGS84();
        MATRIX_l[13][0] = referencePoints.get(4).getY_WGS84();
        MATRIX_l[14][0] = referencePoints.get(4).getZ_WGS84();
        MATRIX_l[15][0] = referencePoints.get(5).getX_WGS84();
        MATRIX_l[16][0] = referencePoints.get(5).getY_WGS84();
        MATRIX_l[17][0] = referencePoints.get(5).getZ_WGS84();
        MATRIX_l[18][0] = referencePoints.get(6).getX_WGS84();
        MATRIX_l[19][0] = referencePoints.get(6).getY_WGS84();
        MATRIX_l[20][0] = referencePoints.get(6).getZ_WGS84();
        MATRIX_l[21][0] = referencePoints.get(7).getX_WGS84();
        MATRIX_l[22][0] = referencePoints.get(7).getY_WGS84();
        MATRIX_l[23][0] = referencePoints.get(7).getZ_WGS84();
    }

}
