package behind.levelbuilder;

import jeu.CSG;

/**
 * Created by julien on 10/25/15.
 */
public enum Nest {

  T0(0, 100, true),
  T1(5, 100, true),
  T2(10, 100, true),
  T3(15, 100, true),
  T4(20, 100, true),
  T5(25, 100, true),
  T6(30, 100, true),
  T7(35, 100, true),
  T8(40, 100, true),
  T9(45, 100, true),
  T10(50, 100, true),
  T11(55, 100, true),
  T12(60, 100, true),
  T13(65, 100, true),
  T14(70, 100, true),
  T15(75, 100, true),
  T16(80, 100, true),
  T17(85, 100, true),
  T18(90, 100, true),
  T19(95, 100, true),
  T20(100, 100, true),

  R0(0, 45, false),
  R1(0, 50, false),
  R2(0, 55, false),
  R3(0, 60, false),
  R4(0, 65, false),
  R5(0, 70, false),
  R6(0, 75, false),
  R7(0, 80, false),
  R8(0, 85, false),
  R9(0, 90, false),
  R10(0, 95, false),

  L0(100, 45, false),
  L1(100, 50, false),
  L2(100, 55, false),
  L3(100, 60, false),
  L4(100, 65, false),
  L5(100, 70, false),
  L6(100, 75, false),
  L7(100, 80, false),
  L8(100, 85, false),
  L9(100, 90, false),
  L10(100, 95, false);

  float x, y;
  boolean top;

  Nest(int x, int y, boolean top) {
    this.x = (x * CSG.screenWidth) / 100;
    this.y = (y * CSG.height) / 100;
    this.top = top;
  }
}
