# Detailed Design #
## GROUP 9 (ISD.VN.20191-09) ##

## I. Homework 06 - Week 6 - Class Design ##
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

## II. Homework 07 - Week 7 - Data Modeling ##
### Description of the homework ###
* Design the detailed database for AFC system

### Assignment of each member ###
* Design the AFC Conceptual ER Diagram by this tool: [link](https://www.draw.io/) (choose "Device" for editing the local file))
#### a. Nguyễn Mạnh Tiến ####
* Design the AFC Conceptual ER Diagram: Oneway ticket; TravelCertificateInformation; StationSystem; TransactionHistory
* Design the AFC Logical ER Diagram: Oneway ticket; TravelCertificateInformation; StationSystem; TransactionHistory
* Write a detail design of element in the DB diagram: Oneway ticket; TravelCertificateInformation;
* Edit, proofread and improve the SDD file from the team member's update.
#### b. Nguyễn Đình Thơ ####
* Design the AFC Logical ER Diagram: PrepaidCard (must have your own astah file and picture)
* Update the file [ISD.VN.20191-09] AFC Logical ER Diagram.asta with your AFC Logical ER Diagram: PrepaidCard
* In the SDD file, write a detail design of element: StationSystem;

#### c. Trần Văn Thông ####
* Design the AFC Logical ER Diagram: 24hourTicket (must have your own astah file and picture)
* Update the file [ISD.VN.20191-09] AFC Logical ER Diagram.asta with your AFC Logical ER Diagram: 24hourTicket
* In the SDD file, write a detail design of element: TransactionHistory;

#### d. Phạm Hữu Thọ ####
* Design the AFC Conceptual ER Diagram: Prepaid Card (must have your own drawio file and picture)
* Update the file [ISD.VN.20191-09] AFC Conceptual ER Diagram.drawio with your AFC Conceptual ER Diagram.drawio: Prepaid Card
* In the SDD file, write a detail design of element: PrepaidCard;

#### e. Phùng Thị Trang ####
* Design the AFC Conceptual ER Diagram: 24hour Ticket (must have your own drawio file and picture)
* Update the file [ISD.VN.20191-09] AFC Conceptual ER Diagram.drawio with your AFC Conceptual ER Diagram.drawio: 24hour Ticket
* In the SDD file, write a detail design of element: 24hourTicket;

### Reviewer for each assignment  ###
* Nguyễn Mạnh Tiến review for **Phạm Hữu Thọ** and *all team's members (optional)*
* Nguyễn Đình Thơ review for **Trần Văn Thông**
* Phạm Hữu Thọ review for **Phùng Thị Trang**
* Trần Văn Thông review for **Nguyễn Mạnh Tiến**
* Phùng Thị Trang review for **Nguyễn Đình Thơ**