# Prema

    • Login Page :
        ◦ Username – Registered username
        ◦ Password – Specific password provided for the username

    • Main Frame : 
(Displays the username as “User” in right side corner of the main frame) 

        ◦ Menu Bar :
            ▪ File : 
                • Logout (Will open the login page)
                • Exit (Close the mainframe)
            ▪ Edit :
                • Control User accounts (Accessed only by the admin)
                    ◦ User Accounts : (New Window)
                        ▪ Table displays all usernames and passwords
                        ▪ Fields to add new username and password
                        ▪ Buttons to “Save” the data / to “Clear” the data entered into fields.
            ▪ View :
                • Suppliers (Accessed only by the admin)
                    ◦ Suppliers : (New Window)
                        ▪ Fields to add,
                            • Supplier Name
                            • Supplier Number
                            • Address
                            • C-1 amount
                            • Due/Outstanding

                        ▪ Buttons to “Save” the data / to “Clear” the data entered into fields.

                        ▪ Table displays all the data entered using the fields when clicking the “Save” button.
                            • Name
                            • Contact Number
                            • Address
                            • Due
                            • C – 1 amount

                • Customers (Accessed only by the admin)
                    ◦ Customers : (New Window)
                        ▪ Fields to add,
                            • Customer Name
                            • Customer Number
                            • Address
                            • VAT/NBT (Checkbox)
                            • Due/Outstanding

                        ▪ Table contains fields to be entered manually for the products,
                            • Price/Kg (Unit price for weight)
                            • Cube Price (Unit price for cube)

                        ▪ Buttons to “Save” the data / to “Clear” the data entered into fields.

                        ▪ Table displays all the data entered using the fields when clicking the button “Save”.
                            • Name
                            • Contact Number
                            • Address
                            • Due
                            • VAT/NBT (Displays True/False)

                • Stock (Accessed only by the admin)
                    ◦ Customers : (New Window)
                        ▪ Fields to add,
                            • Product
                            • Weight

                        ▪ Buttons to “Save” the data / to “Clear” the data entered into fields.

                        ▪ Table displays all the data entered using the fields when clicking the button “Save”.
                            • Product
                            • Weight

            ▪ Reports :
                • Sales Report
                    ◦ Sales Report : (New Window)
                        ▪ Filter option,
                            • According to the customer name
                            • Date (From when to when need to filter)

                        ▪ Table displays all data according to the filter option when clicking the button “Filter”.
                            • GRN No
                            • Date
                            • User
                            • Customer
                            • Plate No
                            • Net Weight
                            • Discount
                            • Transport
                            • Total
                            • Payment Method

                • Purchase Report
                    ◦ Purchase Report : (New Window)
                        ▪ Filter option,
                            • According to the supplier name
                            • Date (From when to when need to filter)

                        ▪ Table displays all data according to the filter option when clicking the button “Filter”.
                            • GRN No
                            • Date
                            • User
                            • Supplier
                            • Plate No
                            • Net Weight
                            • Total
                            • Payment Method

                        ▪ Displays,
                            • Total Weight (Kg) – Calculate total weight for the filtered duration period.
                            • Total amount - Calculate total amount for the filtered duration period.

                • Final Report
                    ◦ Final Report : (New Window)
                        ▪ Filter option,
                            • Date (From when to when need to filter)
                            • Sales –
                                ◦ Customer (According to the customer name)
                                ◦ Payment (According to the payment method)
                            • Purchasing –
                                ◦ Supplier (According to the supplier name)
                                ◦ Payment (According to the payment method)

                        ▪ Table displays all data according to the filter option when clicking the button “Filter”.
                            • Sales :
                                ◦ Invoice No
                                ◦ Product
                                ◦ Weight (Kg)
                                ◦ Paid Amount
                                ◦ Payment Method
                                ◦ Total (Displayed under the table which calculate the total paid amount of the filtered duration period)

                            • Purchasing :
                                ◦ GRN No
                                ◦ Product
                                ◦ Weight (Kg)
                                ◦ Paid Amount
                                ◦ Payment Method
                                ◦ Total (Displayed under the table which calculate the total paid amount of the filtered duration period)

                        ▪ Export Report (To export the final report into Excel sheet) [Under development].

            ▪ Help :

(Divided into two rows and 1st row contains two separate columns)

1st Row – 
1st Column:-

        ◦ New Truck Record : (1st Option)

            ▪ Date (Displays the current date in the right side of the column)
            ▪ Field to enter “Plate No” (the incoming or outgoing track plate no will be entered)
            ▪ Options to select “Customer” or “Supplier Name”
            ▪ Weight (Record the weight automatically when track is in the scale which calculate the total weight in Kg)
            ▪ Button (Mark as In [When track is]/ Mark as Out [When track is out]) -  by clicking this button data will be recorded into the “Truck record history”.

        ◦ Truck record history :

            ▪ Field to enter “Truck Plate No” which filters the data according to the truck plate no on the current date.
            ▪ Table displays the truck record history,
                • Plate No
                • Supplier
                • In Time
                • In Weight
                • Out Time
                • Out Weight
                • Net Weight
                • Status ( Whether the progress Completed or In Progress)

2nd Column:-

        ◦ Invoice :

            ▪ Invoice No (Automatically increments)
            ▪ Customer (Select the customer from the list)
            ▪ Date (Current date displays)
            ▪ Plate No (To be included manually)
            ▪ Product (To be selected from the list)
            ▪ First Weight (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
            ▪ Second Weight (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
            ▪ In Time (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
            ▪ Out Time (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
            ▪ Selection :
                • Weight - (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
                • Cube (Need to be given manually according to the count of cubes added to the truck)

            ▪ Total Amount (Calculated according to the Weight/Cube with its unit price [Weight/Cube * Unit Price])
            ▪ Discount ( Manual Input [Will be reduced from total amount])
            ▪ Transport (Manual Input [Added with the total amount])
            ▪ Net Total ( Calculated from the total amount, discount and transport)
            ▪ Paid (Manual Input, Paid amount)
            ▪ Due (More amount to be delivered to the company  by the customer/ to customer by the company)
            ▪ Payment Method : (Needs to be selected)
                • Cash
                • Card
                • Cheque
                • Credit
                • Memo

            ▪ Buttons to “Save” the data / to “Clear” the data entered in the fields.
(When “Save” button clicked we will be having a dialog box asking to select whether need to print the invoice or not)
(If select “Yes” a dialog box popup to save the invoice as PDF)
(When saved it will be opened automatically [Will have the sample report as PDF])

        ◦ New Purchase :

            ▪ GRN No (Automatically increments)
            ▪ Supplier (Select the supplier from the list)
            ▪ Date (Current date displays)
            ▪ Plate No (To be included manually)
            ▪ Product (Display according to the supplier)
            ▪ First Weight (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
            ▪ Second Weight (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
            ▪ In Time (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
            ▪ Out Time (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
            ▪ Net Weight - (Automatically deduct if it is recorded, from the truck record history when giving the truck plate no manually)
            ▪ Total Amount (Calculated according to the Net Weight with its unit price [Weight * Unit Price])
            ▪ Paid (Manual Input, Paid amount)
            ▪ Due (More amount to be delivered to the company  by the customer/ to customer by the company)
            ▪ Payment Method : (Needs to be selected)
                • Cash
                • Card
                • Cheque
                • Credit
                • Memo

            ▪ Buttons to “Save” the data / to “Clear” the data entered in the fields.
(When “Save” button clicked we will be having a dialog box asking to select whether need to print the new purchase details or not)
(If select “Yes” a dialog box popup to save the new purchase details as PDF)
(When saved it will be opened automatically [Will have the sample report as PDF])

2nd Row -

        ◦ Purchase Report :
(Displays the same window which open when clicking the “Purchase Report” option from the “Reports” in the menu bar)

        ◦ Sales Report :
(Displays the same window which open when clicking the “Sales Report” option from the “Reports” in the menu bar)

        ◦ Final Report :
(Displays the same window which open when clicking the “Final Report” option from the “Reports” in the menu bar)

        ◦ Suppliers :
(Displays the same window which open when clicking the “Suppliers” option from the “View” in the menu bar)

        ◦ Customers :
(Displays the same window which open when clicking the “Customers” option from the “View” in the menu bar)

