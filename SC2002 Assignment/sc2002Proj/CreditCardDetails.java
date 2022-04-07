package sc2002Proj;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreditCardDetails {
	private String holderFName;
	private String holderLName;
	private String ccNum;
	private LocalDate expiryDate;
	private String[] billAddr;
	
	//constructors
	public CreditCardDetails() {
		holderFName = null;
		holderLName = null;
		ccNum = null;
		expiryDate = null;
		billAddr = null;
	}
	
	public void setHolderFName(String f) {
		this.holderFName = f;
	}
	public String getHolderFName() {
		return holderFName;
	}
	public void setHolderLName(String l) {
		this.holderLName = l;
	}
	public String getHolderLName() {
		return holderLName;
	}
	public void setCcNum(String a) {
		this.ccNum = a;
	}
	public String getCcNum() {
		return ccNum;
	}
	public void setExpDate(String e) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.expiryDate = LocalDate.parse(e, formatter);
		
	}
	public LocalDate getExpDate() {
		return expiryDate;
	}
	
	public void setBillAddr(String[] bilAdr) {
		this.billAddr = bilAdr;
	}
	public String[] getBillAddr() {
		return billAddr;
	}
}
