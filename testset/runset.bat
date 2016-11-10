@echo off
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d @input1.json http://localhost:8080/taxcalculator
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d @input2.json http://localhost:8080/taxcalculator
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d @input3.json http://localhost:8080/taxcalculator
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d @input4.json http://localhost:8080/taxcalculator
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d @input5.json http://localhost:8080/taxcalculator
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d @input6.json http://localhost:8080/taxcalculator