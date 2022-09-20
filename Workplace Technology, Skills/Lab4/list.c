#include <stdlib.h>
#include <stdio.h>
#include "list.h"

struct _LIST_STRUCT_ {
    int head;
    struct _LIST_STRUCT_ *tail;
};
typedef struct _LIST_STRUCT_ LIST_STRUCT;
typedef LIST_STRUCT *LIST_PTR;

// 1. A function that creates an empty list
extern LIST create_list( ) {
    return NULL;
}


// 2. A function that frees the memory needed by a list
extern void free_list( LIST list ) {
    LIST_PTR node = list;
    
    while ((node->tail) != NULL) {

        LIST_PTR temp = node;
        node = node->tail;

        free(temp);
    }

    list = NULL;
}


// 3. A function which prints out a list
extern void print_list( LIST list ) {
    LIST_PTR ptr = list;
    char *sep = "";
    printf( "[" );
    while(ptr != NULL){
        printf( "%s%d", sep, ptr->head );
        ptr = ptr->tail;
        sep = ", ";
    }
    printf( "]\n" );
}


// 4. A function which adds a value to the front of a list
extern LIST prepend( LIST list, int value ) {
    LIST_PTR ptr = list;
    LIST_PTR result = malloc( sizeof( LIST_STRUCT ) );
    result->head = value;
    result->tail = ptr;
    return result;
}

// 5. A function which appends a list (l2) to the end of another list (l1)
extern void append( LIST l1, LIST l2){
    while((*l1->tail) != NULL){
        l1 = l1->tail;
    }

    *l1 = l2;
}


// /* The function which uses the opaque data type. */
// /* It simply forwards the arguments to the auxiliary function sort_. */
// extern void sort( int (*leq)( int fst, int snd ), LIST *list ) {
// sort_( leq, list );
// }

// /* An auxiliary function which uses the transparent data type. */
// static void partition( /* argument list omitted */ ) {
// /* 11 Lines omitted. */
// }


// /* An auxiliary function which uses the transparent data type. */
// static void sort_( int (*leq)( int fst, int snd ), LIST_PTR *list ) {
// /* 12 Lines omitted. */
// }

static int leq( int fst, int snd ) {
    return (fst < snd ? -1 : snd < fst ? 1 : 0);
}