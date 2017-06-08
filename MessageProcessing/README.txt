
DESCRIPTION: 

This project was implemented to create log file according to incoming sales messages.
There are 3 type of messages:
o Message Type 1 – contains the details of 1 sale E.g apple at 10p
o Message Type 2 – contains the details of a sale and the number of occurrences of that sale. E.g 20 sales of apples at 10p each.
o Message Type 3 – contains the details of a sale and an adjustment operation to be applied to all stored sales of this product type. Operations can be add, subtract, or multiply e.g Add 20p apples would instruct your application to add 20p to each sale of apples you have recorded.

Rules:
 All sales must be recorded
 All messages must be processed
 After every 10th message received your application should log a report detailing the number of sales of each product and their total value.
 After 50 messages your application should log that it is pausing, stop accepting new messages and log a report of the adjustments that have been made to each sale type while the application was running.

-------------------------------------------------------------------------------------------
To implement above system, 5 different classes were implemented:
-> Sale: to keep features of a sale operation, Sale class was created
-> SaleLog: to create log files and realize lof operations, SaleLog classs was created
-> Adjustment: to keep features of adjustment log, a different class is needed and it is Adjustment
-> Operations: to start all operations and parse incoming messages, Operations class was created.
-> EnglishNoun: this class was cited from internet to convert words from plural to singular while parsing incoming messages

--------------------------------------------------------------------------------------------
Steps that followed by myself:
•	First of all,  I separate operations in 2 steps in my brain: processing message and logging. So firstly I started to create message mechanism.
•	I created Sale class and added these variables:  productType and productValue. I created the variables as a private variable, because I do not want that every class can access them.  So, I created getter and setter methods.
•	Created Operations class to process incoming messages. Then, created processIncomingMessages function. I thought that, incoming messages can be sent to the this function as a string and then this function can parse it and create Sale objects. 
	There are 3 message type and all of them have specific ordinary. So, I can parse incoming message according to specific rules. Such as; I can split a message according to spaces and if its second element is “at” then I can say that first element is product name and third element is product value.

  Example : 
  Incoming message: apple at 10p
  After split by space:
  First element: apple
  Second element: at
  Third element: 10p

  Incoming message: 20 sales of apples at 10p
  After split by space:
  First element: 20
  Second element: sales
  Third element: of
  Fourth element: apples
  Fifth element : at
  Sixth element : 10p

  	I saw that second message type has one more feature like count of sale. 
  “20 sales of apples at 10p”  I thought that there are 20 sales operation and all of them has 10p apples. Therefore, I need to create a    new variable to keep sale count in Sale class. So, Sale class has three variables.

  	Another messages type is a little bit different from others. Because it does not require to create a new Sale operation. It needs to    change product value of existing sale operations.
  Example: 
  Incoming message: add 20p apples
  After split by space:
  First element: add
  Second element: 20p
  Third element: apples
  With these message I have to find existing sale operations and should add 20 to their product values. So, I need to keep all sale       operations in a list to access all of them later. Therefore, I created an Arraylist variable in Operations class.

  Incoming message: substract 20p apples
  After split by space:
  First element: substract
  Second element: 20p
  Third element: apples
  Substract 20 from product value of each sale operation in the list

  Incoming message: multiply 20p apples
  After split by space:
  First element: substract
  Second element: 20p
  Third element: apples
  Multiply  product value of each sale operation in the list by 20

•	I added throw exception to the function because, if the incoming message is in incorrect format, then should inform to the caller of the function.
•	After finishing message parsing, I started to think about logging. Yes, I can writing logging and parsing coding  together, but to avoid error ratio, I divide coding in 2 parts. So, it is more clear for me to write all operations. 

  	Created SaleLog class to describe a log model.
  	Created a constructor and the constructor(gets  all sales operation list as a list parameter) and calls a function,             createDetailedProductMap. This function get the list of the all sales operations and redesign all data. 
 
 For example: there can many operations for a product and this function creates only a data for a product.

  The list keep a sale object: product name, product value and operation count. Think that this is the list:
  [0] : sale1(apple, 10, 1)
  [1]: sale2(apple, 10 ,20)
  [2]:sale3( strawberry, 10,1)

  After calling the function, a new hashmap created
  [1]: (apple, 20 ,21) (string, int[])
  [2]: ( strawberry, 10,1) (string, int[])

  Note: there should be only one product in list, so I used hashmap. Hashmap can has only one value but, I need 2 values so, I created  an integer array and kep it in the hashmap as a value.

  	Created a new function, createLog. This function gets the hashmap and create a string to write a text file.
  	Created third function, writeToLogFile. This function gets the string and write to a file. I divided operations in different  functions. 
  	Go back to the Operations class and created a new function, createLog. Added a variable to count each 10. Incoming message and call   the writeToLogFile function to log files.
  I decided to call function of processIncomingMessages inside of this function too. 
  	Now time to create log for adjustment operations. So I need to create a new class, Adjustment. It has values: product name, product   value and operation type. So, I need to keep these data, when I get  the third type of message. Therefore, inside of the operations     class and processIncomingMessages: I parsed messages according to their features. If incoming message has an operation word(add,    substract,multiply) then parse it and create an Adjustment object setting values. And finally add the object to a list. There is a sales   operation list and the list have record more than 1 for each product. I have to do operation for each record but need to write log   just one time. So I used a count.
  	Created a new function inside SaleLog , createAdjustmentLog. This function sort incoming list(which is created inside of  processIncomingMessages). Because, I want to write data to file in an order. After sorting list, I created a string to write a file.
  	Created a new function, writeAdjLogToFile. This function gets list as an attachment and then calls function of the  createAdjustmentLog. The function create log and this function write to file.
  Note: inside of Operations class, I wrote some functions as private, because I do not want no one access them . only creating log file  functions are accessible.
  
•	Now time to test code. I wrote a main inside of operations class and write a code to read data from a file and set a sring then call createLog function.  During my tests I faced with a problem that if product name is plural or singular. Because I did not have any control for it. And also My hashmap has product name as a key value so, I need to convert product name as a singular before adding to the hashmap. So, I found a class on the internet, EnglishNoun, that converts words as plural or singular. I used the singularOf function of the class and convert all product names before adding lists.(in parsing step). Then I test the code again.

•	I forgot to add control to restring incoming messages after 50. Message and redesign the code. (createLog function) I added a Boolean inside of Operations class, acceptMessage. Its default value is true. When message count access to 50, then createLog function sends false and while loop is be finished by automatically.

•	Not:  to read data from file I added my test file path, so if you want to test, you have to change the path inside of main. There are 4 different test data:
  1.	Correct data and has more than 50 operations.
  2.	Wrong  message format data : such as: apple apple 10p
  3.	Empty file
  4.	Wrong  message format data : such as: 10p at apple
