#include <stdio.h>
#include <string.h>
#include <limits.h>

//number of customers the program can handle
#define NUM_C 3 

//customer data structure
struct customer{
	char 	f_name[10];
	char    l_name[4];
	int	balance;
};

//customer database as array of structs
struct customer customers[NUM_C];

//function to print binary representation of the database
int print_customers_b(){
	// to be completed
	return(0);
}

//print out the content of our costomer database
int print_customers(){
	int i;
	for(i = 0; i < NUM_C; i++){
		printf("Customer %d   :\n", i);
		printf("   First Name: %s\n", customers[i].f_name);
		printf("   Last Name : %s\n", customers[i].l_name);
		printf("   Balance   : %d\n", customers[i].balance);
	}
	return(0);
}

// function to update forename
int update_f_name(int id, char* f_name){
	strcpy(customers[id].f_name, f_name);
	return(0);
}

// function to update lastname
int update_l_name(int id, char* l_name){
	strcpy(customers[id].l_name, l_name);
	return(0);
}

// function to update an entire customer record
int update_customer(int id, char* f_name, char* l_name){
	strcpy(customers[id].f_name, f_name);
	strcpy(customers[id].l_name, l_name);
	return(0);
}

// function to update balance
int update_balance(int id, int balance){
	customers[id].balance = balance;
	return(0);
}

// a function to populate the database 
// (might be from file or network in a practical setting)
int add_customers(){
	update_customer(0, "Utz", "Rod");
	update_balance(0,500);
	update_customer(1, "Hans", "Wur");
	update_balance(1,50000);
	update_customer(2, "Keith", "Ric");
	update_balance(2,100);
	return(0);
}

void detailed_print(char str[], size_t size) {
	for(size_t i = 0; i < size; i++) {
	    if (str[i] == '\0') {
	        fputs("\\0", stdout);
	    }
	    printf("%c", str[i]);
	}
	puts("");
}

// our interface for a worker that allows name changes
int edit_customer(){
	int option;
	char f_name[256];
	char l_name[256];

	while (1){
		printf( "\n");
		printf( "p = print customers; ");
		printf( "e = edit customer;");
		printf( "x = exit: ");
   		option = getchar( );
		getchar( );
		switch(option){
			case 112: 
				print_customers();
				break;
			case 101: 
				printf( "Customer ID: ");
   				option = getchar( );
				getchar( );
				option = option - 48;
				if (option<0 || option > NUM_C) {
					printf("no customer record!\n");
					return(0);
				}
				printf("New first name: ");
				fgets(f_name, sizeof(f_name), stdin);
				f_name[strcspn(f_name, "\n")] = 0;
				update_f_name(option, f_name);
				printf("New last name: ");
				fgets(l_name, sizeof(f_name), stdin);
				l_name[strcspn(l_name, "\n")] = 0;
				printf("%s\n", l_name);
				update_l_name(option, l_name);
				detailed_print(customers[option].l_name, sizeof(l_name));
				break;
			case 120: 
				return(0);
				break;
			default: 
				break;
		}
		
		print_customers_b();
	}
	
	return(0);
}

int main(void){
	add_customers();

	print_customers();
	
	edit_customer();

	print_customers();
}

