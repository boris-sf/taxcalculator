GENERAL INFORMATION
--------------------------------------------------------------------------------------------------------------------------------------
The test task is designed as Spring Boot application. 
The result of maven build procedure is executable jar that may be run with java -jar command.
When app is running the tax calculation service is available via http://localhost:8080/taxcalculator
(see testset/runset.bat for ivocation examples)


DESIGN
--------------------------------------------------------------------------------------------------------------------------------------

         TaxCalculationController    PurchaseValidator                   TaxCalculationService                       ProductService
    input      |                            |                                     |                                         |
-------------> |                            |        PurchaseToProductConverter   |                TaxRateService           |
               |   validate received data   |                 |                   |                  	 |                  |
               | -------------------------> |                 |                   |                      |                  |
               |                                              |                   |                      |                  |
               |         convert input DTOs to domain         |                   |                      |                  |
               | -------------------------------------------> |                   |                      |                  |
               | <------------- List<Product> --------------- |                   |                      |                  |
               |                                                                  |                      |                  |
               |                                                                  |                      |                  |
               |                    call tax calculation                          |                      |                  |
               | ---------------------------------------------------------------> |                      |                  |
               |                                                                  |  tax rate for item   |                  |
               |                                                                  | -------------------> |                  |
               |                                                                  |                      |  is item free    |
               |                                                       iterates over all items,          |  of basic tax?   |
               |                                               calculates sum of individual tax values,  | ---------------> |
               |                                                     rounds obtained sum according       | <--- boolean --- |
               |                                                        ro rules described in spec       |                  |
               |                                                                  |                      |                  |
               |                                                                  |                      |    is imported?  |
               |                                                                  |                      | ---------------> |
               |                                                                  |                      | <--- boolean --- |
               |                                                                  | <----- double ------ |
               | <-------- double (rounded sum of individual taxes) ------------- |
               |
<-- SalesTax --|


TaxCalculationController is REST endpoint implementation. It:
* accepts List of purchased items that have description, unitPrice and count
* validates received items to have non-empty description and positive values for price and count
* converts items to domain objects (Product) and passes them to TaxCalculationService
* calculated taxes are returned to the client as an instance of SalesTax. 
* Proper formatting of tax value is done with TaxValueSerializer (custom json serializer applied to SalesTax#salesTax property)
* any errors caused by invalid/unparsable input JSON or validation faults are handled in TaxCalculationController#handle method
  which extract and passes to the client error message contained in the root cause Exception

TaxCalculationService calculates total tax value. For that it:
* calculates sum of individual tax values with price*rate/100 formula (without rounding them)
* tax rates for each of the products comes from TaxRateService
* rounds obtained sum according to rules in spec

TaxCalculationService:
* consults ProductService whether product is either free of basic tax (book, medical product or food) or imported
* calculates tax rate according to rules from spec

ProductService responsible for:
* figuring out whether item is either book, medical product or food by finding corresponding words in product's description (case insenitive)
* figuriong out whether item is imported by finding 'imported' word in product's description (case insensitive)