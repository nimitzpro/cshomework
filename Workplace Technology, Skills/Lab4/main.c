#include <stdlib.h>
#include <stdio.h>
#include "list.c"

// Alexander Stradnic 119377263

int main( int argc, char **argv ){

    LIST list1 = create_list();
    LIST list2 = create_list();
    LIST list3 = create_list();
    LIST list4 = create_list();

    list2 = prepend(list2, 7);

    list3 = prepend(list3, 6);
    list3 = prepend(list3, 5);


    list4 = prepend(list4, 2);
    list4 = prepend(list4, 3);
    list4 = prepend(list4, 4);
    list4 = prepend(list4, 1);
    list4 = prepend(list4, 0);

    print_list(list4);

    append(list4, list3);

    print_list(list4);

}