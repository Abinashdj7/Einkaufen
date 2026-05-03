package com.Abinash.Nouveauecommerce.Controller;

import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Abinash.Nouveauecommerce.Exception.OrderException;
import com.Abinash.Nouveauecommerce.Model.Order;
import com.Abinash.Nouveauecommerce.Repo.OrderRepo;
import com.Abinash.Nouveauecommerce.Response.PaymentLinkResponse;
import com.Abinash.Nouveauecommerce.Service.OrderService;
import com.Abinash.Nouveauecommerce.Service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;

@CrossOrigin(origins = "*",allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api")
public class PaymentController {
	
	//@Value("${razorpay.api.key}")
	String key;
	
	//@Value("${razorpay.api.secret}")
	String secret;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderRepo orderRepo;
	
	@PostMapping("/payments/dummy/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLinkDummy(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException{
			try {
			Order order=orderService.findOrderById(orderId);
			RazorpayClient razorpay=new RazorpayClient(key,secret);
			JSONObject paymentLinkRequest=new JSONObject();
			paymentLinkRequest.put("amount", order.getTotalPrice() * 100);
			paymentLinkRequest.put("currency","INR");
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
			System.out.println(paymentLinkRequest);
			PaymentLink payment=razorpay.paymentLink.create(paymentLinkRequest);
			String paymentLinkId=payment.get("id");
			String payementLinkUrl=payment.get("short_url");
			PaymentLinkResponse res=new PaymentLinkResponse();
			res.setPayment_link_id(paymentLinkId);
			res.setPayment_link_url(payementLinkUrl);
			
			return new ResponseEntity<PaymentLinkResponse>(res,HttpStatus.CREATED);
		}
			catch(Exception e) {
				e.printStackTrace();
			}
		return null;
		}
	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException{
		Order order=orderService.findOrderById(orderId);
		PaymentLinkResponse paymentLinkResponse=new PaymentLinkResponse();
		paymentLinkResponse.setPayment_link_url("www.randompaymentlink.com");
		Random random=new Random();
		paymentLinkResponse.setPayment_link_id(random.ints().toString());
		return new ResponseEntity<PaymentLinkResponse>(paymentLinkResponse,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/payments")
	public ResponseEntity<String> redirect(@RequestParam(name= "payment_id") String paymentId,@RequestParam(name="order_id") Long orderId) throws OrderException{
		Order order =orderService.findOrderById(orderId);
		RazorpayClient razorpay = null;
		try {
			razorpay = new RazorpayClient(key,secret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Payment payment=razorpay.payments.fetch(paymentId);
			
			if(payment.get("status").equals("captured")) {
				order.getPaymentDetails().setPayementId(paymentId);
				order.getPaymentDetails().setPaymentStatus("Completed");
				order.setOrderStatus("PLACED");
				orderRepo.save(order);
				
				return new ResponseEntity<String>("Your order has been placed",HttpStatus.ACCEPTED);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	}

