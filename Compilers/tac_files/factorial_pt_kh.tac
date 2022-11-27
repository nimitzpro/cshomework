x := in;
t1 := 0;
t3 := x;
t2 := t1 < t3;
if (t2 == 0) goto l1;
t4 := 1;
fact := t4;
l2:
t5 := x;
t7 := 0;
t6 := t5 == t7;
if (t6 != 0) goto l3;
t8 := 0;
t9 := fact;
t10 := x;
t8 := t9 * t10;
fact := t8;
t12 := x;
t13 := 1;
t11 := t12 - t13;
x := t11;
goto l2;
l3:
t14 := fact;
out := t14;
l1:
halt;