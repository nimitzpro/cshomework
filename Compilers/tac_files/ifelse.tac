x := in;
t1 := x;
t3 := 0;
t2 := t1 == t3;
if (t2 == 0) goto l1;
t4 := 1;
out := t4;
goto l2;
l1:
t5 := 2;
out := t5;
l2:
halt;