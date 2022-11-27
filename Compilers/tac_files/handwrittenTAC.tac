x := in;
t1 := 0;
t2 := x;
t3 := t1 < t2;
if (t3 == 0) goto l1;
  t4 := 1;
  fact := t4;
  l2:
    t5 := x;
    t6 := 0;
    t7 := t5 > t6;
    if (t7 == 0) goto l3;
      t8 := fact;
      t9 := x;
      t10 := t8 * t9;
      fact := t10;
      t11 := x;
      t12 := 1;
      t13 := t11 - t12;
      x := t13;
      goto l2;
    l3:
      out := fact;
l1:
halt;