# Homework 06 - Week 6 - Detailed Design - Class Design #
## GROUP 9 (ISD.VN.20191-09) ##

### Description of the homework ###
* Design the detailed class diagram for AFC system

        UPDATED 3.0: Class of AFC system based on comments and discussion on class
* Class of AFC system:

|No.|Class|
|---|-----|
|1|AFCInterface|
|2|AFCController|
|3|AFCControllerOneWay|
|4|AFCController24hour|
|5|AFCControllerPrepaid|
|6|AFCCalculate|
|7|AFCCheckValidOfCertificate|
|8|AFCCheckValidOfOneWayTicket|
|9|AFCCheckValidOf24hourTicket|
|10|AFCCheckValidOfPrepaidCard|
|11|TravelCertificateInformation|
|12|PrepaidCard|
|13|24hourTicket|
|14|OnewayTicket|
|15|StationSystem|
|16|TransactionHistory|
|17|GateInterface|
|18|TicketRecognizerInterface|
|19|CardScannerInterface|

### Assignment of each member ###
* Design the detailed class:

|No.|Name|Class|
|---|----|-----|
|1|Nguyễn Mạnh Tiến|AFCInterface; AFCController; AFCCheckValidOfOneWayTicket|
|2|Trần Văn Thông|AFCController24hour; AFCCheckValidOfCertificate; PrepaidCard; TransactionHistory|
|3|Phạm Hữu Thọ|AFCCheckValidOfPrepaidCard; OnewayTicket; StationSystem; CardScannerInterface|
|4|Nguyễn Đình Thơ|AFCControllerPrepaid; AFCCheckValidOf24hourTicket; 24hourTicket; TicketRecognizerInterface"
|5|Phùng Thị Trang|AFCControllerOneWay; AFCCalculate; TravelCertificateInformation; GateInterface|

* [Nguyễn Mạnh Tiến] Merge into a detailed class diagram with Relationships Between Classes
### Reviewer for each assignment  ###
* Nguyễn Đình Thơ review for **Phạm Hữu Thọ**
* Phạm Hữu Thọ review for **Trần Văn Thông**
* Trần Văn Thông review for **Nguyễn Mạnh Tiến**
* Nguyễn Mạnh Tiến review for **Phùng Thị Trang**
* Phùng Thị Trang review for **Nguyễn Đình Thơ**