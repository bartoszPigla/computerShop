package services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import models.Product;

@Component
@Scope(value="session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart implements Serializable{
	private static final long serialVersionUID = -2825428146690940362L;
	private List<Product> productList;
	
	public ShoppingCart() {
		productList=new ArrayList<Product>();
	}
	
	public int getNumberOfProduct(){
		return productList.size();
	}
	
	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public void addProduct(Product product){
		productList.add(product);
	}
	
	public void removeProduct(Product product){
		productList.remove(product);
	}
	
	public void removeProduct(Long id){
		if(id==null)
			return;
		for(Product p:productList)
			if(id==p.getId()){
				productList.remove(p);
				return;
			}
	}
	
	public boolean contains(Long productId){
		for(Product p:productList)
			if(p.getId()==productId)
				return true;
		return false;
	}
	
	public int orderCost(){
		return productList.stream().mapToInt(Product::getPrice).sum();
	}
}
