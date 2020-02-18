# Package Delivery Application

Package Delivery is a command line program that keeps a record of packages processed.

## Command line arguments

Package Delivery application supports following command line arguments:

##### -data
 * path to initial data about delivery places. Example: *-data=.\src\main\resources\data.txt*
 * example of file format:
		
	3.4 08801
	2 90005
	12.56 08801
	5.5 08079
	3.2 09300	
	
##### -priceList 
 * path to price list. Example: *-priceList=.\src\main\resources\priceList.txt*
 * example of file format:

	10 5.00
	5 2.50
	3 2.00
	2 1.50
	1 1.00
	0.5 0.70
	0.2 0.50

## Maven start

Application is compiled by Maven and could be run with following command.
	
	mvn install exec:java -Dexec.args="-data=.\src\main\resources\data.txt -priceList=.\src\main\resources\priceList.txt"
	
## Usage

##### INPUT
Program gets inputs from console in following format:

	<weight: positive number, >0, maximal 3 decimal places, . (dot) as decimal separator><space><postal code: fixed 5 digits>
	
Examples:

	12.56 08801
	5.5 08079
	
##### OUTPUT
Once per minute are information about delivery places written to the console output. Each line consists of postal code, total weight and total fee of all packages for that postal code.

	<postal code: fixed 5 digits><space><total weight: fixed 3 decimal places, . (dot) as decimal separator><space><total fee: fixed 2 decimal places, . (dot) as decimal separator)>
	
Sample output with fees:
	
	08801 15.960 7.00
	08079 5.500 2.50
	09300 3.200 2.00
	90005 2.000 1.50
	
Information about total fee is displayed only if price list file is connected. Without price list ouput display only postal code and total weight of all packages for that postal code.

	<postal code: fixed 5 digits><space><total weight: fixed 3 decimal places, . (dot) as decimal separator>
	
Sample output without fees:

	08801 15.960
	08079 5.500
	09300 3.200
	90005 2.000
