package com.Retail.Retailer.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.Retail.Retailer.Model.CustomerPoints;
import com.Retail.Retailer.Model.Transaction;

@Service
public class RetailerService {
	
	 public CustomerPoints calculatePoints(List<Transaction> transactions) {
	        Map<String, Map<String, Integer>> customerPoints = new HashMap<>();
	        
	        
	        for (Transaction transaction : transactions) {
	            String customerId = transaction.getCustomerId();
	            String month = transaction.getMonth();
	            int points = calculatePoints(transaction.getAmount());

	           
	            customerPoints
	                .computeIfAbsent(customerId, k -> new HashMap<>())
	                .merge(month, points, Integer::sum);
	        }

	        
	        CustomerPoints result = new CustomerPoints();
	        for (Map.Entry<String, Map<String, Integer>> entry : customerPoints.entrySet()) {
	            String customerId = entry.getKey();
	            Map<String, Integer> monthlyPoints = entry.getValue();
	            int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

	            result.setCustomerId(customerId);
	            result.setMonthlyPoints(monthlyPoints);
	            result.setTotalPoints(totalPoints);
	        }

	        return result;
	    }
	 
	 public  int calculatePoints(double amount) {
	        int points = 0;

	        
	        if (amount > 100) {
	            points += (int) (amount - 100) * 2; 
	            amount = 100;
	        }

	     
	        if (amount > 50) {
	            points += (int) (amount - 50) * 1; 
	        }

	        return points;
	    }
	

}
