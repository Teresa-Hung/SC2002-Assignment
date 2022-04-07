# SC2002-Assignment

## Reservation Package
### Reservation Class:
#### Methods
- ```setReservStatus(ReservStatus s)``` / ```setReservCode(String s)``` / ```setRoom(Room[] roomlist)``` / ```setNumAdult()``` / ```setNumChild()``` 
- ```setDates(boolean checkin, boolean checkout)```: boolean, checkin & checkout indicate whether to update 
- ```checkDates(LocalDate dCI, LocalDate dCO)```: boolean, check whether dates are valid
- ```getReservStatus()```: enum, CONFIRMED / IN_WAITLIST / CHECKED_IN / EXPIRED
- ```getReservCode()```: String / ```getRoom()```: Room
- ```getCheckInDate()``` / ```getCheckOutDate()```: LocalDate, refer to [this website](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html#parse-java.lang.CharSequence-java.time.format.DateTimeFormatter-).
- ```getRCheckInDay()``` / ```getCheckOutDay()```: enum, get week of day.
- ```printReceipt()```: void.

### ReservationManager Class
#### Methods
- ```createReserv(Reservation r)```: boolean, return true if reservation is successfully created; otherwise, return false.
- ```createReservCode()```: String, randomly generate a code - 1 capital letter + 3 digits.
- ```updateReserv(Reservation[] rlist)```: void, input reservation code to update the reservation details. Print the receipt after the update.

### CheckIn Class
#### Methods
- ```checkIn(Reservation r)```: boolean, customers can check in from 2pm on the scheduled check-in date. Return true if successfully check in.
- ```expire(Reservation r)```: void, the reservation will be expired if no one checks in by 2am on the next day of scheduled check-in date.
