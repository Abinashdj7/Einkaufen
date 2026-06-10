package com.Abinash.Nouveauecommerce.Controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Abinash.Nouveauecommerce.Exception.OrderException;
import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.Order;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Repo.OrderRepo;
import com.Abinash.Nouveauecommerce.Response.PaymentLinkResponse;
import com.Abinash.Nouveauecommerce.Service.OrderService;
import com.Abinash.Nouveauecommerce.Service.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@RestController
@RequestMapping("/api")
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Value("${razorpay.api.key}")
	private String key;

	@Value("${razorpay.api.secret}")
	private String secret;

	@Autowired
	OrderService orderService;

	@Autowired
	UserService userService;

	@Autowired
	OrderRepo orderRepo;

	@PostMapping("/payments/dummy/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLinkDummy(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException{
		User user=userService.findUserProfileByJwt(jwt);
		Order order=orderService.findOrderById(orderId);

		if(!order.getUser().getId().equals(user.getId())) {
			throw new UserException("You can't pay for another user's order");
		}

		try {
			RazorpayClient razorpay=new RazorpayClient(key,secret);
			JSONObject paymentLinkRequest=new JSONObject();
			paymentLinkRequest.put("amount", order.getTotalPrice() * 100);
			paymentLinkRequest.put("currency","INR");
			paymentLinkRequest.put("reference_id", String.valueOf(orderId));
			JSONObject customer=new JSONObject();
			customer.put("name", order.getUser().getFirstName()+" "+order.getUser().getLastName());
			customer.put("email", order.getUser().getEmail());
			paymentLinkRequest.put("customer",customer);
			JSONObject notify=new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);
			paymentLinkRequest.put("callback_url","http://localhost:5173/payment/"+orderId);
			paymentLinkRequest.put("callback_method","get");
			PaymentLink payment=razorpay.paymentLink.create(paymentLinkRequest);
			String paymentLinkId=payment.get("id");
			String payementLinkUrl=payment.get("short_url");

			order.getPaymentDetails().setRazorPaymentLinkId(paymentLinkId);
			order.getPaymentDetails().setRazorPaymentLinkReferenceId(String.valueOf(orderId));
			orderRepo.save(order);

			PaymentLinkResponse res=new PaymentLinkResponse();
			res.setPayment_link_id(paymentLinkId);
			res.setPayment_link_url(payementLinkUrl);

			return new ResponseEntity<PaymentLinkResponse>(res,HttpStatus.CREATED);
		}
		catch(Exception e) {
			logger.error("Failed to create Razorpay payment link for order {}", orderId, e);
			return new ResponseEntity<PaymentLinkResponse>(HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException{
		User user=userService.findUserProfileByJwt(jwt);
		Order order=orderService.findOrderById(orderId);

		if(!order.getUser().getId().equals(user.getId())) {
			throw new UserException("You can't pay for another user's order");
		}

		PaymentLinkResponse paymentLinkResponse=new PaymentLinkResponse();
		paymentLinkResponse.setPayment_link_url("www.randompaymentlink.com");
		paymentLinkResponse.setPayment_link_id(java.util.UUID.randomUUID().toString());
		return new ResponseEntity<PaymentLinkResponse>(paymentLinkResponse,HttpStatus.CREATED);

	}

	@GetMapping("/payments")
	public ResponseEntity<String> redirect(
			@RequestParam(name = "razorpay_payment_id") String paymentId,
			@RequestParam(name = "razorpay_payment_link_id") String paymentLinkId,
			@RequestParam(name = "razorpay_payment_link_reference_id") String orderIdParam,
			@RequestParam(name = "razorpay_payment_link_status") String paymentLinkStatus,
			@RequestParam(name = "razorpay_signature") String signature) throws OrderException{

		Long orderId;
		try {
			orderId = Long.valueOf(orderIdParam);
		} catch (NumberFormatException e) {
			return new ResponseEntity<String>("Invalid order reference", HttpStatus.BAD_REQUEST);
		}

		JSONObject attributes=new JSONObject();
		attributes.put("razorpay_payment_id", paymentId);
		attributes.put("payment_link_id", paymentLinkId);
		attributes.put("payment_link_reference_id", orderIdParam);
		attributes.put("payment_link_status", paymentLinkStatus);
		attributes.put("razorpay_signature", signature);

		boolean isValidSignature;
		try {
			isValidSignature=Utils.verifyPaymentLink(attributes, secret);
		} catch (RazorpayException e) {
			logger.error("Razorpay payment link signature verification failed", e);
			return new ResponseEntity<String>("Payment verification failed", HttpStatus.BAD_REQUEST);
		}

		if(!isValidSignature || !"paid".equalsIgnoreCase(paymentLinkStatus)) {
			return new ResponseEntity<String>("Payment verification failed", HttpStatus.BAD_REQUEST);
		}

		Order order=orderService.findOrderById(orderId);

		if(!String.valueOf(orderId).equals(order.getPaymentDetails().getRazorPaymentLinkReferenceId())) {
			logger.warn("Payment link reference id mismatch for order {}", orderId);
			return new ResponseEntity<String>("Payment verification failed", HttpStatus.BAD_REQUEST);
		}

		order.getPaymentDetails().setRazorPaymentId(paymentId);
		order.getPaymentDetails().setRazorPaymentLinkId(paymentLinkId);
		order.getPaymentDetails().setRazorPaymentLinkStatus(paymentLinkStatus);
		order.getPaymentDetails().setPayementId(paymentId);
		order.getPaymentDetails().setPaymentStatus("Completed");
		order.setOrderStatus("PLACED");
		orderRepo.save(order);

		return new ResponseEntity<String>("Your order has been placed",HttpStatus.ACCEPTED);
	}
}
