package com.Abinash.Nouveauecommerce.Model;

public class PaymentDetails {
	private PaymentMethod paymentMethod; //
	
	private String paymentStatus;
	
	private String payementId;
	
	private String razorPaymentLinkId;
	
	private String razorPaymentLinkReferenceId;
	
	private String razorPaymentLinkStatus;
	
	private String razorPaymentId;
	
	public PaymentDetails() {
		
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPayementId() {
		return payementId;
	}

	public void setPayementId(String payementId) {
		this.payementId = payementId;
	}

	public String getRazorPaymentLinkId() {
		return razorPaymentLinkId;
	}

	public void setRazorPaymentLinkId(String razorPaymentLinkId) {
		this.razorPaymentLinkId = razorPaymentLinkId;
	}

	public String getRazorPaymentLinkReferenceId() {
		return razorPaymentLinkReferenceId;
	}

	public void setRazorPaymentLinkReferenceId(String razorPaymentLinkReferenceId) {
		this.razorPaymentLinkReferenceId = razorPaymentLinkReferenceId;
	}

	public String getRazorPaymentLinkStatus() {
		return razorPaymentLinkStatus;
	}

	public void setRazorPaymentLinkStatus(String razorPaymentLinkStatus) {
		this.razorPaymentLinkStatus = razorPaymentLinkStatus;
	}

	public String getRazorPaymentId() {
		return razorPaymentId;
	}

	public void setRazorPaymentId(String razorPaymentId) {
		this.razorPaymentId = razorPaymentId;
	}

	public PaymentDetails(PaymentMethod paymentMethod, String paymentStatus, String payementId,
			String razorPaymentLinkId, String razorPaymentLinkReferenceId, String razorPaymentLinkStatus,
			String razorPaymentId) {
		super();
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.payementId = payementId;
		this.razorPaymentLinkId = razorPaymentLinkId;
		this.razorPaymentLinkReferenceId = razorPaymentLinkReferenceId;
		this.razorPaymentLinkStatus = razorPaymentLinkStatus;
		this.razorPaymentId = razorPaymentId;
	}
	
}
