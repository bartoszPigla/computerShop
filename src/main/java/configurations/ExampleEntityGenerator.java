package configurations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.Customer;
import models.Disk;
import models.HardDisk;
import models.ProductOrder;
import models.SsdDisk;
import models.Laptop;
import productFilters.SsdDiskFilter;

public class ExampleEntityGenerator {
	public static List<Object> createTwoProductsToOneOrder(){
		ProductOrder o=ExampleEntityGenerator.createExampleOrder(0);
		
		SsdDisk p1=(SsdDisk)ExampleEntityGenerator.createExampleDisk(new SsdDisk());
		p1.setReadSpeed(10);
		p1.setWriteSpeed(20);
		
		HardDisk p2=(HardDisk)ExampleEntityGenerator.createExampleDisk(new HardDisk());
		p2.setRotationSpeed(7200);
		
		p1.addOrder(o);
		p2.addOrder(o);
		
		o.addProduct(p1);
		o.addProduct(p2);
		return Arrays.asList(p1,p2,o);
	}
	public static Customer createExampleCustomer(long id) {
		Customer customer = new Customer();
		customer.setFirstName("Bartek");
		customer.setLastName("P");
		customer.setId(id);
		customer.seteMail("bartek217a@gmail.com");
		customer.setPhoneNumber("664220607");
		customer.setStreetName("Wojskowa");
		customer.setStreetNumber(24);
		customer.setHouseNumber(54);
		customer.setPassword("1234");
		customer.setRoles(Arrays.asList("ROLE_ADMIN","ADMIN","USER"));
		return customer;
	}

	public static List<Customer> createExampleCustomerList(int size) {
		List<Customer> list = new ArrayList<>(size);
		for (int i = 1; i <= size; i++)
			list.add(createExampleCustomer(i));
		return list;
	}

	public static ProductOrder createExampleOrder(long id){
		ProductOrder order=new ProductOrder();
		order.setId(id);

		//order.setOrderDate(new Date());
		/*
		List<Product> productList=new ArrayList<>();
		for(Product p:createExampleDiskList(3))
			productList.add(p);
		*/
		//order.setOrderedProducts(productList);
		
		//order.setPurchasingCustomer(createExampleCustomer(0));
		
		return order;
	}
	
	public static List<ProductOrder> createExampleOrderList(int size){
		List<ProductOrder> list = new ArrayList<>(size);
		for (int i = 1; i <= size; i++)
			list.add(createExampleOrder(i));
		return list;
	}

	public static Disk createExampleDisk(Disk disk) {
		// disk.setId(id);
		disk.setProducer("producer");
		disk.setModel("model");
		disk.setDiskSize("3.5'");
		disk.setDiskInterface("SATA III");
		disk.setDimensions("130 x 101 x 17 mm");
		disk.setCapacity(120);
		disk.setWeight(80);
		disk.setDiskSize("2.5'");
		disk.setPrice(300);
		disk.setImageDirectory("/jpg/ssdDisk.jpg");
		disk.setDescription("bardzo dobry dysk");
		return disk;
	}
	
	public static Laptop createExampleLaptop(){
		Laptop laptop=new Laptop();
		laptop.setBattery("battery");
		laptop.setCamera("camera");
		laptop.setColour("colour");
		laptop.setDescription("descr");
		laptop.setDimensions("dimensions");
		laptop.setDisplayResolution("displRes");
		laptop.setDisplaySize("displaySize");
		laptop.setGraphicsCard("graphCard");
		laptop.setImageDirectory("ssdDisk.jpg");
		laptop.setMatrixType("matrix");
		laptop.setModel("model");
		laptop.setOperatingSystemName("systemName");
		laptop.setOperatingSystemVersion("systemVersion");
		laptop.setPorts("ports");
		laptop.setPrice(1000);
		laptop.setProcessorClockSpeed("clockSpeed");
		laptop.setProcessorName("name");
		laptop.setProducer("producer");
		laptop.setRam(12);
		laptop.setStorage(231);
		laptop.setWirelessConnectivity("fsdf");
		return laptop;
	}

	public static List<Disk> createExampleDiskList(int size) {
		List<Disk> list = new ArrayList<>();
		for (int i = 0; i < size; i++)
			list.add(createExampleDisk(new SsdDisk()));
		return list;
	}

	public static SsdDiskFilter createExampleFilter() {
		SsdDiskFilter filter = new SsdDiskFilter();

		filter.setProducerList(Arrays.asList(new String[] { "Samsung", "Kingston" }));
		filter.setDiskSizeList(Arrays.asList(new String[] { "2.5'", "3.5'" }));
		filter.setDiskInterfaceList(Arrays.asList(new String[] { "SATA III", "mSATA" }));
		filter.setMinPrice(0);

		return filter;
	}


	public static List<HardDisk> createExampleHardDiskList() {
		HardDisk hdd1 = (HardDisk) ExampleEntityGenerator.createExampleDisk(new HardDisk());
		hdd1.setProducer("seagate");
		hdd1.setRotationSpeed(7200);

		HardDisk hdd2 = (HardDisk) ExampleEntityGenerator.createExampleDisk(new HardDisk());
		hdd2.setProducer("samsung");
		hdd2.setRotationSpeed(5000);
		return Arrays.asList(hdd1,hdd2);
	}

	public static List<Laptop> createExampleLaptopList() {
		Laptop l1 = ExampleEntityGenerator.createExampleLaptop();
		l1.setProducer("lenovo");

		Laptop l2 = ExampleEntityGenerator.createExampleLaptop();
		l1.setProducer("samsung");

		return Arrays.asList(l1,l2);
	}

	public static List<SsdDisk> createExampleSsdDiskList() {
		SsdDisk sd1 = (SsdDisk) ExampleEntityGenerator.createExampleDisk(new SsdDisk());
		sd1.setProducer("seagate");
		sd1.setWriteSpeed(1);
		sd1.setReadSpeed(100);

		SsdDisk sd2 = (SsdDisk) ExampleEntityGenerator.createExampleDisk(new SsdDisk());
		sd2.setProducer("samsung");
		sd2.setWriteSpeed(1);
		sd2.setReadSpeed(100);
		return Arrays.asList(sd1,sd2);
	}

}
