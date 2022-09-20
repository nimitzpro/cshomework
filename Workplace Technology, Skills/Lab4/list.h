#ifndef _SINGLE_H_
#define _SINGLE_H_
typedef void* LIST;
extern LIST create_list( );
extern void print_list( LIST list );
extern LIST prepend( LIST list, int value );
extern void append( LIST l1, LIST l2 );
extern LIST insert1( int (*compare)( const int, const int ),
                    const int value, LIST list );
extern void insert2( int (*compare)( const int, const int ),
                     const int value, LIST* list );
#endif

#ifndef NULL
#define NULL (0)
#endif