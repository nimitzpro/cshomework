t0 = 0; # if 0 < x then
t1 = x;
t2 = t0 < t1;
if (t2 != 1) goto l0;
t3 = 1; # fact := 1;
fact = t3;
l1: # repeat
t4 = fact; # fact := fact * x;
t5 = x;
t4 = t4 * t5;
fact = t4;
t6 = x; # x := x - 1
t7 = 1;
t6 = t6 - t7;
x = t6;
t8 = x; # until x = 0;
t9 = 0;
t10 = t8 = t9;
if (t10 != 1) goto l1;
t11 = fact;
# write(t11); # write fact